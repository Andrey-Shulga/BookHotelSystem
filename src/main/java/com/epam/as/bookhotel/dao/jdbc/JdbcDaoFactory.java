package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.UserDao;

import java.sql.Connection;


public class JdbcDaoFactory extends DaoFactory {

    private Connection connection;

    @Override
    public UserDao getUserDao() {
        return new JdbcUserDao(connection);
    }

}
