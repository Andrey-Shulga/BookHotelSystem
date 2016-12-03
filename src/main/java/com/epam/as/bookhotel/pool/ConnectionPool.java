package com.epam.as.bookhotel.pool;


import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionPool {

    private static final String dbPropertyFileName = "database.properties";
    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    private static int connectionCount = 0;
    private static String url;
    private static String username;
    private static String password;
    private static int poolStartSize;
    private static int poolMaxSize;
    private static long pollConnectionTimeout;
    private static BlockingQueue<Connection> connections;

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        return ConectionPoolHolder.instance;
    }


    private static Connection getNewConnection(String url, String username, String password) throws ConnectionPoolException {

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


    public static void putConnectionToPool(Connection returnedConnection) {
        if (returnedConnection != null) {
            connections.offer(returnedConnection);
            logger.debug("Factory returned connection back to pool, now total connections in pool = {}", connections.size());
        }
    }

    public static void close() throws ConnectionPoolException {
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

    public void fillPool() throws ConnectionPoolException, PropertyManagerException {

        poolConfigure();

        logger.debug("Maximum limit of connections in the pool = {} connections", poolMaxSize);
        logger.debug("Trying to create initial connection pool = {} connections...", poolStartSize);
        for (int i = 0; i < poolStartSize; i++) {
            Connection connection = getNewConnection(url, username, password);
            if (connection != null)
                connections.offer(connection);
        }
        logger.debug("Initial connection pool with {} connections was created.", connections.size());
    }

    private void poolConfigure() throws ConnectionPoolException, PropertyManagerException {
        PropertyManager.getInstance().loadPropertyFromFile(dbPropertyFileName);
        Properties properties = PropertyManager.getInstance().getProperties();

        String drivers = properties.getProperty("jdbc.drivers");

        try {
            Class.forName(drivers);
        } catch (ClassNotFoundException e) {

            throw new ConnectionPoolException(e);
        }
        url = properties.getProperty("jdbc.url");
        username = properties.getProperty("jdbc.username");
        password = properties.getProperty("jdbc.password");
        poolStartSize = Integer.parseInt(properties.getProperty("pool.start.size"));
        poolMaxSize = Integer.parseInt(properties.getProperty("pool.max.size"));
        pollConnectionTimeout = Long.parseLong(properties.getProperty("pool.pollconnection.timeout"));
        connections = new ArrayBlockingQueue<>(poolMaxSize);
    }

    private static class ConectionPoolHolder {
        private static final ConnectionPool instance = new ConnectionPool();
    }
}

