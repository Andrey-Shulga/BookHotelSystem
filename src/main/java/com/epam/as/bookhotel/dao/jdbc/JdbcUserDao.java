package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.model.User;
import com.epam.as.bookhotel.model.UserLocale;
import com.epam.as.bookhotel.model.UserRole;
import com.epam.as.bookhotel.model.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


class JdbcUserDao extends JdbcDao<User> implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcUserDao.class);
    private static final int INDEX_1 = 1;
    private static final int INDEX_2 = 2;
    private static final int INDEX_3 = 3;
    private static final int INDEX_4 = 4;
    private static final int INDEX_5 = 5;

    JdbcUserDao(Connection connection) {
        super(connection);
    }

    @Override
    User setRsToField(ResultSet rs, User user) throws SQLException {
        user.setId(rs.getInt(INDEX_1));
        user.setLogin(rs.getString(INDEX_2));
        user.setPassword(rs.getString(INDEX_3));
        user.setRole(new UserRole(UserType.valueOf(rs.getString(INDEX_4))));
        user.setLocale(new UserLocale(rs.getString(INDEX_5)));
        logger.debug("Found entity: {}", user);
        return user;
    }


}
