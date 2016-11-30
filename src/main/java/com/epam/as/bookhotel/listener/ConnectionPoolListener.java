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

        ConnectionPool pool = new ConnectionPool();

        try {
            pool.fillPool();
        } catch (ConnectionPoolException e) {
            logger.error("Connection pool creating error occurred", e);
        } catch (PropertyManagerException e) {
            logger.error("Can't open file {} for reading properties.", e);
        }
        JdbcDaoFactory.setPool(pool);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.close();
        } catch (ConnectionPoolException e) {
            logger.error("Connection pool creating error occurred", e);
        }

    }
}
