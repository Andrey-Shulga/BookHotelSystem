package com.epam.bookhotel.listener;


import com.epam.bookhotel.dao.jdbc.JdbcDaoFactory;
import com.epam.bookhotel.exception.ConnectionPoolException;
import com.epam.bookhotel.pool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Initialize or close connection pool for application
 */

@WebListener
public class ConnectionPoolInitListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionPoolInitListener.class);
    private static final String ERROR_MESSAGE = "ConnectionPoolException occurred";
    private static ConnectionPool pool;

    public static ConnectionPool getPool() {
        return pool;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        pool = new ConnectionPool();
        try {
            pool.fillPool();
        } catch (ConnectionPoolException e) {
            try {
                pool.close();
            } catch (ConnectionPoolException ex) {
                logger.error(ERROR_MESSAGE, ex);
            }
            logger.error(ERROR_MESSAGE, e);
        }
        JdbcDaoFactory.setPool(pool);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        try {
            pool.close();
        } catch (ConnectionPoolException e) {
            logger.error(ERROR_MESSAGE, e);
        }
    }
}
