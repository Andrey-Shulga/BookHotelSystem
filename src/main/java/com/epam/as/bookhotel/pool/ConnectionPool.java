package com.epam.as.bookhotel.pool;


import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionPool {

    private static final String dbPropertyFileName = "database.properties";
    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    private int connectionCount = 0;
    private String url;
    private String username;
    private String password;
    private int poolStartSize;
    private int poolMaxSize;
    private long pollConnectionTimeout;
    private BlockingQueue<Connection> connections;

    private Connection getNewConnection(String url, String username, String password) throws ConnectionPoolException {

        Connection connection;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {

            throw new ConnectionPoolException(e);
        }
        connectionCount++;
        //logger.debug("The current number of connections = {}", connectionCount);
        return connection;
    }


    public void putConnectionToPool(Connection returnedConnection) {

        if (returnedConnection != null) {
            connections.offer(returnedConnection);
            logger.debug("Factory returned connection back to pool, now total connections in pool = {}", connections.size());
        }
    }

    public void close() throws ConnectionPoolException {

        for (Connection con : connections)
            try {
                if (!con.isClosed())
                    con.close();
            } catch (SQLException e) {
                throw new ConnectionPoolException(e);
            }
        logger.debug("Connection pool was closed.");
    }

    public synchronized Connection getConnection() throws ConnectionPoolException {

        Connection connection;
        logger.debug("Factory trying to take connection from the pool...");
        if (connectionCount < poolMaxSize) {
            connection = connections.poll();
            if (connection == null) {
                logger.debug("No connections in pool! Trying to get new connection...");
                return getNewConnection(url, username, password);
            }
            logger.debug("The connection was taken. Total connections in the pool now = {}", connections.size());
        } else {
            logger.debug("Max limit of connection pool = {} reached. No new connection " +
                    "will be create, wait for release any connection...", poolMaxSize);
            try {
                connection = connections.poll(pollConnectionTimeout, TimeUnit.SECONDS);
            } catch (InterruptedException e) {

                throw new ConnectionPoolException(e);
            }
        }
        return connection;
    }

    public void fillPool() throws ConnectionPoolException {

        poolConfigure();
        logger.debug("Maximum limit of connections to database in the pool = {} connections", poolMaxSize);
        logger.debug("Trying to create initial connection pool = {} connections...", poolStartSize);
        for (int i = 0; i < poolStartSize; i++) {
            Connection connection;
            connection = getNewConnection(url, username, password);
            if (connection != null)
                connections.offer(connection);
        }
        logger.debug("Initial connection pool with {} connections was created.", connections.size());
    }

    private void poolConfigure() throws ConnectionPoolException {

        try {
            PropertyManager propertyManager = new PropertyManager(dbPropertyFileName);
            String drivers = propertyManager.getPropertyKey("jdbc.drivers");

            try {
                Class.forName(drivers);
            } catch (ClassNotFoundException e) {

                throw new ConnectionPoolException(e);
            }
            url = propertyManager.getPropertyKey("jdbc.url");
            username = propertyManager.getPropertyKey("jdbc.username");
            password = propertyManager.getPropertyKey("jdbc.password");
            poolStartSize = Integer.parseInt(propertyManager.getPropertyKey("pool.start.size"));
            poolMaxSize = Integer.parseInt(propertyManager.getPropertyKey("pool.max.size"));
            pollConnectionTimeout = Long.parseLong(propertyManager.getPropertyKey("pool.pollconnection.timeout"));
        } catch (PropertyManagerException e) {
            throw new ConnectionPoolException(e);
        }
        connections = new ArrayBlockingQueue<>(poolMaxSize);
    }

}

