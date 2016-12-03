package com.epam.as.bookhotel.dao.jdbc;


import com.epam.as.bookhotel.dao.Dao;
import com.epam.as.bookhotel.exception.DaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

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
                ps.executeUpdate();
                setId(entity, ps);
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        } else {

        }
        return entity;
    }

    private void setId(T entity, PreparedStatement ps) throws SQLException {
        ResultSet generatedId = ps.getGeneratedKeys();
        generatedId.next();
        int id = generatedId.getInt(1);
        entity.setId(id);
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
