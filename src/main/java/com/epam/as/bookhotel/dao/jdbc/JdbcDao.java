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

    JdbcDao() {
    }

    static String getQueryPropertyFile() {
        return QUERY_PROPERTY_FILE;
    }

    @Override
    public T save(T entity, List<String> parameters, String queryKey) throws JdbcDaoException {

        try {
            PropertyManager pm = new PropertyManager(QUERY_PROPERTY_FILE);
            if (entity.getId() == null) {
                logger.debug("{} trying to INSERT entity \"{}\" to database...", this.getClass().getSimpleName(), entity);
                try (PreparedStatement ps = connection.prepareStatement(pm.getPropertyKey(queryKey), Statement.RETURN_GENERATED_KEYS)) {
                    setParametersToPs(parameters, ps);
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
            PropertyManager pm = new PropertyManager(QUERY_PROPERTY_FILE);
            try (PreparedStatement ps = connection.prepareStatement(pm.getPropertyKey(queryKey))) {
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

        return entity;
    }

    @Override
    public List<T> findByParameters(T entity, List<String> parameters, String queryKey, String locale) throws JdbcDaoException {

        logger.debug("{} trying to FIND entity \"{}\" in database...", this.getClass().getSimpleName(), entity.getClass().getSimpleName());
        List<T> entities = new ArrayList<>();
        try {
            PropertyManager pm = new PropertyManager(QUERY_PROPERTY_FILE);
            String localeQuery = getQueryByLocale(pm.getPropertyKey(queryKey), locale);
            try (PreparedStatement ps = connection.prepareStatement(localeQuery)) {
                setParametersToPs(parameters, ps);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    T newEntity = setRsToField(rs, entity);
                    entities.add(newEntity);
                }
            } catch (SQLException e) {

                throw new JdbcDaoException(e);
            }
        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(e);
        }

        return entities;
    }

    private String getQueryByLocale(String query, String locale) {

        return String.format(query, locale, locale);
    }

    private synchronized void setParametersToPs(List<String> parameters, PreparedStatement ps) throws SQLException {

        int count = INITIAL_COUNT;
        for (String parameter : parameters) {
            ps.setString(count, parameter);
            count++;
        }
        parameters.clear();
    }

    void setId(T entity, PreparedStatement ps) throws SQLException {
        ResultSet generatedId = ps.getGeneratedKeys();
        generatedId.next();
        int id = generatedId.getInt(1);
        entity.setId(id);
        if (entity.getId() != null)
            logger.debug("Insert success. Entity {} received id = {}", entity.getClass().getSimpleName(), entity.getId());
    }

    abstract T setRsToField(ResultSet rs, T entity) throws SQLException;

}