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
import java.sql.SQLException;
import java.util.Properties;


public class JdbcUserDao extends JdbcDao<User> implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcUserDao.class);
    private static final String INSERT_USER_PROPERTY_KEY = "insert.user";
    private static final String UPDATE_USER_PROPERTY_KEY = "update.user";
    private static final String DEFAULT_USER_ROLE = "USER";

    JdbcUserDao(Connection connection) {
        super(connection);
    }

    @Override
    void setUpdateFieldToPs(PreparedStatement ps, User entity) throws SQLException {
        ps.setString(2, entity.getLogin());
        ps.setString(3, entity.getPassword());
        ps.setString(1, Integer.toString(entity.getId()));
    }

    @Override
    String getUpdateQuery() throws PropertyManagerException {
        PropertyManager.getInstance().loadPropertyFromFile(QUERY_PROPERTY_FILE);
        Properties properties = PropertyManager.getInstance().getProperties();
        return properties.getProperty(UPDATE_USER_PROPERTY_KEY);
    }

    @Override
    void setRole(User entity) {
        if (entity.getRole() == null) {
            entity.setRole(UserType.valueOf(DEFAULT_USER_ROLE));
            logger.debug("Entity {} assigned role \"{}\".", entity.getClass().getSimpleName(), entity.getRole());
        }
    }


    @Override
    protected String getInsertQuery() throws PropertyManagerException {
        PropertyManager.getInstance().loadPropertyFromFile(QUERY_PROPERTY_FILE);
        Properties properties = PropertyManager.getInstance().getProperties();
        return properties.getProperty(INSERT_USER_PROPERTY_KEY);
    }


    @Override
    public void setInsertFieldToPs(PreparedStatement ps, User entity) throws SQLException {
        ps.setString(1, entity.getLogin());
        ps.setString(2, entity.getPassword());
    }
}
