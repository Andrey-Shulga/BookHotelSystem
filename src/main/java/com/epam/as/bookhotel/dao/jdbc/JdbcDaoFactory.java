package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.OrderDao;
import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.pool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;


public class JdbcDaoFactory extends DaoFactory {

    private static final Logger logger = LoggerFactory.getLogger(JdbcDaoFactory.class);
    private static ConnectionPool pool;
    private Connection connection;

    public JdbcDaoFactory() throws ConnectionPoolException {
        this.connection = pool.getConnection();
    }

    public static void setPool(ConnectionPool pool) {
        JdbcDaoFactory.pool = pool;
    }


    public Connection getConnection() {
        return connection;
    }

    @Override
    public UserDao getUserDao() throws ConnectionPoolException {
        return new JdbcUserDao(connection);
    }

    @Override
    public OrderDao getOrderDao() {
        return new JdbcOrderDao(connection);
    }

    @Override
    public void beginTx() throws ConnectionPoolException {
        try {
            if ((!connection.isClosed()) && (connection.getAutoCommit())) {
                connection.setAutoCommit(false);
                logger.debug("Transaction open...");
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);
        }
    }

    @Override
    public void rollback() throws ConnectionPoolException {
        try {
            if ((!connection.isClosed()) && (!connection.getAutoCommit())) {
                connection.rollback();
                connection.setAutoCommit(true);
                logger.debug("Transaction rollback.");
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);
        }
    }

    @Override
    public void commit() throws ConnectionPoolException {
        try {
            if ((!connection.isClosed()) && (!connection.getAutoCommit())) {
                connection.commit();
                logger.debug("Transaction commit.");
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);
        }
    }

    @Override
    public void close() throws JdbcDaoException {
        pool.putConnectionToPool(connection);
    }
}
