package com.epam.as.bookhotel.listener;


import com.epam.as.bookhotel.dao.jdbc.JdbcDaoFactory;
import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.pool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ConnectionPoolListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionPoolListener.class);
    private ConnectionPool pool;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        pool = new ConnectionPool();
        try {
            pool.fillPool();
        } catch (ConnectionPoolException | PropertyManagerException e) {
            logger.error("ConnectionPoolException occurred", e);
        }
        JdbcDaoFactory.setPool(pool);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            pool.close();
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException occurred", e);
        }
    }
}
