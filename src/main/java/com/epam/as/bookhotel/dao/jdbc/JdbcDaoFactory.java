package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;


public class JdbcDaoFactory extends DaoFactory {

    private static ConnectionPool pool;
    private Connection connection;

    public JdbcDaoFactory() throws ConnectionPoolException {
        this.connection = pool.getConnection();
    }

    public static void setPool(ConnectionPool pool) {
        JdbcDaoFactory.pool = pool;
    }

    @Override
    public UserDao getUserDao() throws ConnectionPoolException {
        return new JdbcUserDao(connection);
    }

    @Override
    public void beginTx() throws ConnectionPoolException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);
        }
    }

    @Override
    public void rollback() throws ConnectionPoolException {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);
        }
    }

    @Override
    public void commit() throws ConnectionPoolException {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);
        }
    }

}
