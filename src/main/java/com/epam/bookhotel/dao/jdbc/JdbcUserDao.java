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

import static com.epam.bookhotel.constant.Constants.*;

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

        User newUser = new User();
        newUser.setId(rs.getInt(COLUMN_INDEX_1));
        newUser.setLogin(rs.getString(COLUMN_INDEX_2));
        newUser.setPassword(rs.getString(COLUMN_INDEX_3));
        newUser.setRole(new UserRole(UserType.valueOf(rs.getString(COLUMN_INDEX_4))));
        newUser.setLocale(new UserLocale(rs.getString(COLUMN_INDEX_5)));
        logger.debug("Found entity: {}", newUser);
        return newUser;
    }


}