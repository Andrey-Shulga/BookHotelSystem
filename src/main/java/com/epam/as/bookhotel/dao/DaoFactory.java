package com.epam.as.bookhotel.dao;

import com.epam.as.bookhotel.dao.jdbc.JdbcDaoFactory;
import com.epam.as.bookhotel.exception.DaoException;
import com.epam.as.bookhotel.exception.JdbcDaoException;

public abstract class DaoFactory implements AutoCloseable {

    public static DaoFactory createFactory() throws DaoException {
        try {
            return new JdbcDaoFactory();
        } catch (JdbcDaoException e) {
            throw new DaoException(e);
        }
    }

    public abstract UserDao getUserDao();

    public abstract void beginTx() throws JdbcDaoException;

    public abstract void rollback() throws JdbcDaoException;

    public abstract void commit() throws JdbcDaoException;

    @Override
    public void close() throws JdbcDaoException {

    }

    public abstract OrderDao getOrderDao();

    public abstract RoomDao getRoomDao();

}