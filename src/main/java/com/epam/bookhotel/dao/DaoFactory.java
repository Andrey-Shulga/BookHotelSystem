package com.epam.bookhotel.dao;

import com.epam.bookhotel.dao.jdbc.JdbcDaoFactory;
import com.epam.bookhotel.exception.DaoException;
import com.epam.bookhotel.exception.JdbcDaoException;

/**
 * Abstract factory for producing others factories.
 */

public abstract class DaoFactory implements AutoCloseable {

    /**
     * Produce Jdbc factory
     *
     * @return Jdbc factory
     * @throws DaoException parent exception for any exceptions from factories
     */
    public static DaoFactory createJdbcDaoFactory() throws DaoException {

        try {
            return new JdbcDaoFactory();
        } catch (JdbcDaoException e) {
            throw new DaoException(e);
        }
    }

    public abstract UserDao getUserDao();

    public abstract OrderDao getOrderDao();

    public abstract RoomDao getRoomDao();

    public abstract BedDao getBedDao();

    public abstract RoomTypeDao getRoomTypeDao();

    public abstract PhotoDao getPhotoDao();

    public abstract void beginTx() throws JdbcDaoException;

    public abstract void rollback() throws JdbcDaoException;

    public abstract void commit() throws JdbcDaoException;

    @Override
    public void close() throws JdbcDaoException {

    }


}