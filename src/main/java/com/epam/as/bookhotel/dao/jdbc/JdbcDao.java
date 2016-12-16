package com.epam.as.bookhotel.dao.jdbc;


import com.epam.as.bookhotel.dao.Dao;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.NonUniqueFieldException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.UnableUpdateFieldException;
import com.epam.as.bookhotel.model.BaseEntity;
import com.epam.as.bookhotel.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {

    private static final String QUERY_PROPERTY_FILE = "query.properties";
    private static final String NON_UNIQUE_FIELD_ERROR_CODE = "23505";
    private static final int ZERO = 0;
    private static final int INITIAL_COUNT = 1;
    private static final Logger logger = LoggerFactory.getLogger(JdbcDao.class);
    private Connection connection;

    JdbcDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public T save(T entity, String queryKey) throws JdbcDaoException {

        PropertyManager propertyManager;
        try {
            propertyManager = new PropertyManager(QUERY_PROPERTY_FILE);
            if (entity.getId() == null) {
                logger.debug("{} trying to INSERT entity \"{}\" to database...", this.getClass().getSimpleName(), entity);
                try (PreparedStatement ps = connection.prepareStatement(propertyManager.getPropertyKey(queryKey), Statement.RETURN_GENERATED_KEYS)) {
                    setFieldToPs(ps, entity);
                    ps.execute();
                    setId(entity, ps);
                } catch (SQLException e) {
                    if (NON_UNIQUE_FIELD_ERROR_CODE.equals(e.getSQLState()))
                        throw new NonUniqueFieldException(e);
                    throw new JdbcDaoException(e);
                }
            }
        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(e);
        }
        return entity;
    }

    @Override
    public T update(T entity, List<String> parameters, String queryKey) throws JdbcDaoException {

        logger.debug("{} trying to UPDATE entity \"{}\" in database...", this.getClass().getSimpleName(), entity);
        try {
            PropertyManager propertyManager = new PropertyManager(QUERY_PROPERTY_FILE);
            try (PreparedStatement ps = connection.prepareStatement(propertyManager.getPropertyKey(queryKey))) {
                setParametersToPs(parameters, ps);
                int result = ps.executeUpdate();
                if (result == ZERO) throw new UnableUpdateFieldException();
                else
                    logger.debug("{} updated success. Updates entity {}", entity.getClass().getSimpleName(), entity);
            } catch (SQLException e) {
                throw new JdbcDaoException(e);
            }
        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(e);
        }
        parameters.clear();
        return entity;
    }

    @Override
    public List<List<Object>> findByParameters(T entity, List<String> parameters, String queryKey) throws JdbcDaoException {

        logger.debug("{} trying to FIND entity \"{}\" in database...", this.getClass().getSimpleName(), entity.getClass().getSimpleName());
        List<List<Object>> resultList;
        try {
            PropertyManager propertyManager = new PropertyManager(QUERY_PROPERTY_FILE);
            try (PreparedStatement ps = connection.prepareStatement(propertyManager.getPropertyKey(queryKey))) {
                setParametersToPs(parameters, ps);
                ResultSet rs = ps.executeQuery();
                resultList = extractRsToList(rs);
            } catch (SQLException e) {

                throw new JdbcDaoException(e);
            }
        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(e);
        }
        parameters.clear();
        return resultList;
    }

    private void setParametersToPs(List<String> parameters, PreparedStatement ps) throws SQLException {
        int count = INITIAL_COUNT;
        for (String parameter : parameters) {
            ps.setString(count, parameter);
            count++;
        }
    }

    private List<List<Object>> extractRsToList(ResultSet rs) throws JdbcDaoException {
        List<List<Object>> resultList = new ArrayList<>();
        try {
            ResultSetMetaData meta = rs.getMetaData();
            int columns = meta.getColumnCount();
            while (rs.next()) {
                List<Object> rows = new ArrayList<>();
                for (int i = 1; i <= columns; i++) {
                    Object value = rs.getObject(i);
                    rows.add(value);
                }
                resultList.add(rows);
            }
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
        return resultList;
    }

    private void setId(T entity, PreparedStatement ps) throws SQLException {
        ResultSet generatedId = ps.getGeneratedKeys();
        generatedId.next();
        int id = generatedId.getInt(1);
        entity.setId(id);
        if (entity.getId() != null)
            logger.debug("Insert success. Entity {} received id = {}", entity.getClass().getSimpleName(), entity.getId());
    }


    abstract void setFieldToPs(PreparedStatement ps, T entity) throws SQLException;

}
