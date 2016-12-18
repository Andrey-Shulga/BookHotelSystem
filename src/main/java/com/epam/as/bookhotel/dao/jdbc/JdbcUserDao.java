package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class JdbcUserDao extends JdbcDao<User> implements UserDao {

    JdbcUserDao(Connection connection) {
        super(connection);
    }

    @Override
    public void setFieldToPs(PreparedStatement ps, User entity) throws SQLException {
        ps.setString(1, entity.getLogin());
        ps.setString(2, entity.getPassword());
        ps.setString(3, entity.getRole().toString());
    }


}
