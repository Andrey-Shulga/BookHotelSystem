package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.User;
import com.epam.as.bookhotel.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class JdbcUserDao extends JdbcDao<User> implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcUserDao.class);
    private static final String INSERT_USER_PROPERTY_KEY = "insert.user";
    private static final String UPDATE_USER_PROPERTY_KEY = "update.user";
    private static final String FIND_USER_PROPERTY_KEY = "find.user";


    JdbcUserDao(Connection connection) {
        super(connection);
    }

    @Override
    void setFindFieldToPs(PreparedStatement ps, User entity) throws SQLException {
        ps.setString(1, entity.getLogin());

    }

    @Override
    void setUpdateFieldToPs(PreparedStatement ps, User entity) throws SQLException {
        ps.setString(2, entity.getLogin());
        ps.setString(3, entity.getPassword());
        ps.setString(1, Integer.toString(entity.getId()));
    }

    @Override
    String getUpdateQuery() throws PropertyManagerException {
        PropertyManager propertyManager = new PropertyManager(QUERY_PROPERTY_FILE);
        logger.debug("Using prepare statement command: {}", propertyManager.getPropertyKey(UPDATE_USER_PROPERTY_KEY));
        return propertyManager.getPropertyKey(UPDATE_USER_PROPERTY_KEY);
    }


    @Override
    protected String getInsertQuery() throws PropertyManagerException {
        PropertyManager propertyManager = new PropertyManager(QUERY_PROPERTY_FILE);
        logger.debug("Using prepare statement command: {}", propertyManager.getPropertyKey(INSERT_USER_PROPERTY_KEY));
        return propertyManager.getPropertyKey(INSERT_USER_PROPERTY_KEY);
    }


    @Override
    public void setInsertFieldToPs(PreparedStatement ps, User entity) throws SQLException {
        ps.setString(1, entity.getLogin());
        ps.setString(2, entity.getPassword());
        ps.setString(3, entity.getRole().toString());
    }

    @Override
    protected String getFindQuery() throws PropertyManagerException {
        PropertyManager propertyManager = new PropertyManager(QUERY_PROPERTY_FILE);
        logger.debug("Using prepare statement command: {}", propertyManager.getPropertyKey(FIND_USER_PROPERTY_KEY));
        return propertyManager.getPropertyKey(FIND_USER_PROPERTY_KEY);
    }
}
