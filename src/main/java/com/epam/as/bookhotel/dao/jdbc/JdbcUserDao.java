package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;


public class JdbcUserDao extends JdbcDao<User> implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcUserDao.class);

    JdbcUserDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getInsertQuery() {
        return null;
    }


    @Override
    public void setFieldToPs() {

    }


}
