package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.pool.ConnectionPool;


public class JdbcDaoFactory extends DaoFactory {

    public JdbcDaoFactory() {
    }


    @Override
    public UserDao getUserDao() {
        return new JdbcUserDao(ConnectionPool.getConnection());
    }

    @Override
    public void beginTx() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public void commit() {

    }

}
