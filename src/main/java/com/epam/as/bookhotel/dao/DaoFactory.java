package com.epam.as.bookhotel.dao;

import com.epam.as.bookhotel.dao.jdbc.JdbcDaoFactory;

public abstract class DaoFactory {

    public static DaoFactory createFactory() {
        return new JdbcDaoFactory();
    }

    public abstract UserDao getUserDao();

    public abstract void beginTx();

    public abstract void rollback();

    public abstract void commit();


}