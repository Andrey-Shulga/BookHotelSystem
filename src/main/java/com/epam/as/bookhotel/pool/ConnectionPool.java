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

/**
 * Pool keeps connections with database.
 * While creating pool gets new connections from database, but no more definite start size in property.
 * if no connections in pool, pool create new connection, but not more than the number of the properties
 */

public class ConnectionPool {

    private static final String dbPropertyFileName = "database.properties";
    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    private static final String JDBC_DRIVERS_KEY = "jdbc.drivers";
    private static final String JDBC_URL_KEY = "jdbc.url";
    private static final String JDBC_USERNAME_KEY = "jdbc.username";
    private static final String JDBC_PASSWORD_KEY = "jdbc.password";
    private static final String JDBC_POOL_START_SIZE_KEY = "pool.start.size";
    private static final String JDBC_POOL_MAX_SIZE_KEY = "pool.max.size";
    private static final String JDBC_POOL_CONN_TIME_OUT_KEY = "pool.pollconnection.timeout";
    private int connectionCount = 0;
    private String url;
    private String username;
    private String password;
    private int poolStartSize;
    private int poolMaxSize;
    private long pollConnectionTimeout;
    private BlockingQueue<Connection> connections;

    /**
     * Fill pool with new connections
     *
     * @throws ConnectionPoolException if any exception in pool occurred
     */
    public void fillPool() throws ConnectionPoolException {

        //read settings for pool from property
        poolConfigure();
        logger.debug("Maximum limit of connections to database in the pool = {} connections", poolMaxSize);
        logger.debug("Trying to create initial connection pool = {} connections...", poolStartSize);
        //fill pool with connections no more definite start size
        for (int i = 0; i < poolStartSize; i++) {
            Connection connection;
            connection = getNewConnection(url, username, password);
            if (connection != null)
                connections.offer(connection);
        }
        logger.debug("Initial connection pool with {} connections was created.", connections.size());
    }

    /**
     * Configure pool connection with database by settings from property file
     *
     * @throws ConnectionPoolException if any exception in pool occurred
     */
    private void poolConfigure() throws ConnectionPoolException {

        try {
            PropertyManager propertyManager = new PropertyManager(dbPropertyFileName);
            String drivers = propertyManager.getPropertyKey(JDBC_DRIVERS_KEY);

            try {
                Class.forName(drivers);
            } catch (ClassNotFoundException e) {

                throw new ConnectionPoolException(e);
            }
            url = propertyManager.getPropertyKey(JDBC_URL_KEY);
            username = propertyManager.getPropertyKey(JDBC_USERNAME_KEY);
            password = propertyManager.getPropertyKey(JDBC_PASSWORD_KEY);
            poolStartSize = Integer.parseInt(propertyManager.getPropertyKey(JDBC_POOL_START_SIZE_KEY));
            poolMaxSize = Integer.parseInt(propertyManager.getPropertyKey(JDBC_POOL_MAX_SIZE_KEY));
            pollConnectionTimeout = Long.parseLong(propertyManager.getPropertyKey(JDBC_POOL_CONN_TIME_OUT_KEY));
        } catch (PropertyManagerException e) {
            throw new ConnectionPoolException(e);
        }
        connections = new ArrayBlockingQueue<>(poolMaxSize);
    }

    /**
     * Create new connection with database
     *
     * @param url      URL for database
     * @param username login for database user
     * @param password password for database user
     * @return new connection with database
     * @throws ConnectionPoolException if any exception in pool occurred
     */
    private Connection getNewConnection(String url, String username, String password) throws ConnectionPoolException {

        Connection connection;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {

            throw new ConnectionPoolException(e);
        }
        connectionCount++;

        return connection;
    }

    /**
     * Gives connections from pool or new connections.
     *
     * @return connection from pool or new connection
     * @throws ConnectionPoolException if any exception in pool occurred
     */
    public synchronized Connection getConnection() throws ConnectionPoolException {

        Connection connection;
        logger.debug("Factory trying to take connection from the pool...");
        //get connection from pool
        if (connectionCount < poolMaxSize) {
            connection = connections.poll();
            //if no more connections in pool and max limit not reached, try to create new connection
            if (connection == null) {
                logger.debug("No connections in pool! Trying to get new connection...");
                return getNewConnection(url, username, password);
            }
            logger.debug("The connection was taken. Total connections in the pool now = {}", connections.size());
            //if application need connection but no connection in pool and reached max limit - wait until appear free connection
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

    /**
     * Return connection back to pool
     *
     * @param returnedConnection the connection which must be returned in pool
     */
    public void putConnectionToPool(Connection returnedConnection) {

        if (returnedConnection != null) {
            connections.offer(returnedConnection);
            logger.debug("Factory returned connection back to pool, now total connections in pool = {}", connections.size());
        }
    }

    /**
     * Close all connection in pool and clear it
     *
     * @throws ConnectionPoolException if any exception in pool occurred
     */
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


}

