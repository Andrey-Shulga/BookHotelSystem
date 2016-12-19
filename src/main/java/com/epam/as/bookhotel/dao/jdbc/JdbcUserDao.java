package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.model.User;
import com.epam.as.bookhotel.model.UserRole;
import com.epam.as.bookhotel.model.UserType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


class JdbcUserDao extends JdbcDao<User> implements UserDao {

    private static final int INDEX_1 = 1;
    private static final int INDEX_2 = 2;
    private static final int INDEX_3 = 3;
    private static final int INDEX_4 = 4;

    JdbcUserDao(Connection connection) {
        super(connection);
    }

    @Override
    User setRsToField(ResultSet rs, User user) throws SQLException {
        user.setId(rs.getInt(INDEX_1));
        user.setLogin(rs.getString(INDEX_2));
        user.setPassword(rs.getString(INDEX_3));
        user.setRole(new UserRole(UserType.valueOf(rs.getString(INDEX_4))));
        return user;
    }


}
