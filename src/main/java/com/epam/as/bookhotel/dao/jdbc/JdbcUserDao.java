package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.model.User;

import java.sql.Connection;


public class JdbcUserDao extends JdbcDao<User> implements UserDao {

    JdbcUserDao(Connection connection) {
        super(connection);
    }

    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void delete(User entity) {

    }
}
