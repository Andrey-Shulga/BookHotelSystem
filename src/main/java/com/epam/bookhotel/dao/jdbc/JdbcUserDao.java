package com.epam.bookhotel.dao.jdbc;

import com.epam.bookhotel.dao.UserDao;
import com.epam.bookhotel.entity.User;
import com.epam.bookhotel.entity.UserLocale;
import com.epam.bookhotel.entity.UserRole;
import com.epam.bookhotel.entity.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Jdbc DAO for entity User.
 */

class JdbcUserDao extends JdbcDao<User> implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcUserDao.class);

    JdbcUserDao(Connection connection) {
        super(connection);
    }

    @Override
    User setRsToField(ResultSet rs, User user) throws SQLException {

        user.setId(rs.getInt(COLUMN_INDEX_1));
        user.setLogin(rs.getString(COLUMN_INDEX_2));
        user.setPassword(rs.getString(COLUMN_INDEX_3));
        user.setRole(new UserRole(UserType.valueOf(rs.getString(COLUMN_INDEX_4))));
        user.setLocale(new UserLocale(rs.getString(COLUMN_INDEX_5)));
        logger.debug("Found entity: {}", user);
        return user;
    }


}