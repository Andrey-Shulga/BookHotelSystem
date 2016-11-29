package com.epam.as.bookhotel.dao;

import com.epam.as.bookhotel.dao.jdbc.JdbcDaoFactory;
import com.epam.as.bookhotel.exception.ConnectionPoolException;

public abstract class DaoFactory {

    public static DaoFactory createFactory() {
        return new JdbcDaoFactory();
    }

    public abstract UserDao getUserDao() throws ConnectionPoolException;

    public abstract void beginTx();

    public abstract void rollback();

    public abstract void commit();


}