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
import java.util.Properties;


public class JdbcUserDao extends JdbcDao<User> implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcUserDao.class);
    private static final String INSERT_USER_PROPERTY_KEY = "insert.user";

    JdbcUserDao(Connection connection) {
        super(connection);
    }


    @Override
    protected String getInsertQuery() throws PropertyManagerException {
        PropertyManager.getInstance().loadPropertyFromFile(QUERY_PROPERTY_FILE);
        Properties properties = PropertyManager.getInstance().getProperties();
        return properties.getProperty(INSERT_USER_PROPERTY_KEY);
    }


    @Override
    public void setFieldToPs(PreparedStatement ps, User entity) throws SQLException {
        ps.setString(2, entity.getLogin());
        ps.setString(3, entity.getPassword());
    }
}
