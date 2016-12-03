package com.epam.as.bookhotel.dao.jdbc;


import com.epam.as.bookhotel.dao.Dao;
import com.epam.as.bookhotel.exception.DaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {

    static final String QUERY_PROPERTY_FILE = "query.properties";
    private static final Logger logger = LoggerFactory.getLogger(JdbcDao.class);
    private Connection connection;

    JdbcDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public T save(T entity) throws PropertyManagerException, DaoException {
        if (entity.getId() == null) {
            logger.debug("{} trying to save entity \"{}\" to database...", this.getClass().getSimpleName(), entity.getClass().getSimpleName());
            String insertQuery = getInsertQuery();
            try {
                PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                setFieldToPs(ps, entity);
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        } else {

        }
        return entity;
    }

    abstract String getInsertQuery() throws PropertyManagerException;

    @Override
    public T findById(int id) {
        return null;
    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public void deleteById(int id) {

    }

    public abstract void setFieldToPs(PreparedStatement ps, T entity) throws SQLException;
}
