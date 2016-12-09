package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.User;
import com.epam.as.bookhotel.model.UserType;
import com.epam.as.bookhotel.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JdbcUserDao extends JdbcDao<User> implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcUserDao.class);
    private static final String INSERT_USER_PROPERTY_KEY = "insert.user";
    private static final String FIND_USER_PROPERTY_KEY = "find.user";
    private Connection connection;


    JdbcUserDao(Connection connection) {
        super(connection);
        this.connection = connection;
    }

    @Override
    void setFindQueryRsToField(ResultSet rs, User entity) throws SQLException {
        rs.next();
        entity.setId(rs.getInt(1));
        logger.debug("Search success. Entity {} with id {} found in database.", entity.getClass().getSimpleName(), entity.getId());
        logger.debug("{} trying to find role for {} in database...", this.getClass().getSimpleName(), entity);
        String userRole = rs.getString(2);
        entity.setRole(UserType.valueOf(userRole));
        logger.debug("User role \"{}\" found and set.", entity.getRole().toString());
    }

    @Override
    void setIdFieldToPs(PreparedStatement ps, int id) throws SQLException {

    }

    @Override
    public void setInsertQueryFieldToPs(PreparedStatement ps, User entity) throws SQLException {
        ps.setString(1, entity.getLogin());
        ps.setString(2, entity.getPassword());
        ps.setString(3, entity.getRole().toString());
    }

    @Override
    void setFindQueryFieldToPs(PreparedStatement ps, User entity) throws SQLException {
        ps.setString(1, entity.getLogin());
        ps.setString(2, entity.getPassword());
    }

    @Override
    protected String getInsertQuery() throws PropertyManagerException {
        PropertyManager propertyManager = new PropertyManager(QUERY_PROPERTY_FILE);
        logger.debug("Using prepare statement command: {}", propertyManager.getPropertyKey(INSERT_USER_PROPERTY_KEY));
        return propertyManager.getPropertyKey(INSERT_USER_PROPERTY_KEY);
    }

    @Override
    protected String getFindQuery() throws PropertyManagerException {
        PropertyManager propertyManager = new PropertyManager(QUERY_PROPERTY_FILE);
        logger.debug("Using prepare statement command: {}", propertyManager.getPropertyKey(FIND_USER_PROPERTY_KEY));
        return propertyManager.getPropertyKey(FIND_USER_PROPERTY_KEY);
    }

}
