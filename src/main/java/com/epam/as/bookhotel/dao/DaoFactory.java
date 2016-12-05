package com.epam.as.bookhotel.dao;

import com.epam.as.bookhotel.dao.jdbc.JdbcDaoFactory;
import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DaoFactory implements AutoCloseable {

    public static DaoFactory createFactory() throws ConnectionPoolException {
        return new JdbcDaoFactory();
    }

    public abstract Connection getConnection();

    public abstract UserDao getUserDao() throws ConnectionPoolException;

    public abstract void beginTx() throws SQLException, ConnectionPoolException;

    public abstract void rollback() throws SQLException, ConnectionPoolException;

    public abstract void commit() throws ConnectionPoolException;

    @Override
    public void close() throws DaoException {

    }
}