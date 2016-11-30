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

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        try {
            ConnectionPool.getInstance().fillPool();
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException occurred", e);
        } catch (PropertyManagerException e) {
            logger.error("PropertyManagerException occurred", e);
        }
        JdbcDaoFactory.setPool(ConnectionPool.getInstance());



    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.close();
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException occurred", e);
        }

    }
}
