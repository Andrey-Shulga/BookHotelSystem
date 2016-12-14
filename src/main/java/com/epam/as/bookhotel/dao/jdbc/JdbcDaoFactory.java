package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.*;
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

    public JdbcDaoFactory() throws JdbcDaoException {
        try {
            this.connection = pool.getConnection();
        } catch (ConnectionPoolException e) {
            throw new JdbcDaoException(e);
        }
    }

    public static void setPool(ConnectionPool pool) {
        JdbcDaoFactory.pool = pool;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public UserDao getUserDao() {
        return new JdbcUserDao(connection);
    }

    @Override
    public OrderDao getOrderDao() {
        return new JdbcOrderDao(connection);
    }

    @Override
    public RoomDao getRoomDao() {
        return new JdbcRoomDao(connection);
    }

    @Override
    public ConfirmationOrderDao getConfirmationOrderDao() {
        return new JdbcConfirmationOrderDao(connection);
    }

    @Override
    public void beginTx() throws JdbcDaoException {
        try {
            if ((!connection.isClosed()) && (connection.getAutoCommit())) {
                connection.setAutoCommit(false);
                logger.debug("Transaction open...");
            }
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
    }

    @Override
    public void rollback() throws JdbcDaoException {
        try {
            if ((!connection.isClosed()) && (!connection.getAutoCommit())) {
                connection.rollback();
                connection.setAutoCommit(true);
                logger.debug("Transaction rollback.");
            }
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
    }

    @Override
    public void commit() throws JdbcDaoException {
        try {
            if ((!connection.isClosed()) && (!connection.getAutoCommit())) {
                connection.commit();
                logger.debug("Transaction commit.");
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
    }

    @Override
    public void close() {
        pool.putConnectionToPool(connection);
    }
}
