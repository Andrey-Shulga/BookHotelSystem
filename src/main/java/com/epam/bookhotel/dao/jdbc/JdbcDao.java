package com.epam.bookhotel.dao.jdbc;


import com.epam.bookhotel.dao.Dao;
import com.epam.bookhotel.entity.BaseEntity;
import com.epam.bookhotel.exception.JdbcDaoException;
import com.epam.bookhotel.exception.NonUniqueFieldException;
import com.epam.bookhotel.exception.PropertyManagerException;
import com.epam.bookhotel.exception.UnableUpdateFieldException;
import com.epam.bookhotel.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.bookhotel.constant.ConstantsHolder.ZERO;

/**
 * Abstract DAO for CRUD operations
 *
 * @param <T> generic type for any entity
 */

abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {

    private static final Logger logger = LoggerFactory.getLogger(JdbcDao.class);
    private static final String QUERY_PROPERTY_FILE = "query.properties";
    private static final String NON_UNIQUE_FIELD_ERROR_CODE = "23505";
    private static final int INITIAL_COUNT = 1;
    private String query;
    private Connection connection;

    JdbcDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Return file name of property file with queries for other JdbcDao
     *
     * @return the name of property file
     */
    static String getQueryPropertyFile() {
        return QUERY_PROPERTY_FILE;
    }

    /**
     * General method for operation "insert" entity to database
     *
     * @param entity     the entity which need inserts in database
     * @param parameters the list of parameters for prepare PrepareStatements
     * @param key        property key for reading insert query from property file
     * @return inserted entity with received id
     * @throws JdbcDaoException if any exceptions occurred with jdbc operations
     */
    @Override
    public T save(T entity, List<Object> parameters, String key) throws JdbcDaoException {

        //load file with property to property manager
        query = getSqlQuery(key);
        if (entity.getId() == null) {
            logger.debug("{} trying to INSERT entity \"{}\" to database...", this.getClass().getSimpleName(), entity);
            try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                //set the list of parameters to PrepareStatement
                setParametersToPs(parameters, ps);
                ps.execute();
                //set id received from ResultSet in entity
                setId(entity, ps);
            } catch (SQLException e) {
                //if field's value which inserted in column which keeps only unique values exist - throw exception about it
                if (NON_UNIQUE_FIELD_ERROR_CODE.equals(e.getSQLState())) throw new NonUniqueFieldException(e);
                throw new JdbcDaoException(e);
            }
        }

        return entity;
    }

    /**
     * General method for operation "update" entity to database
     *
     * @param entity     the entity which need updates in database
     * @param parameters the list of parameters for prepare PrepareStatements
     * @param key        property key for reading update query from property file
     * @throws JdbcDaoException if any exceptions occurred with jdbc operations
     */
    @Override
    public void update(T entity, List<Object> parameters, String key) throws JdbcDaoException {

        logger.debug("{} trying to UPDATE entity \"{}\" in database...", this.getClass().getSimpleName(), entity);
        query = getSqlQuery(key);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            setParametersToPs(parameters, ps);
            int result = ps.executeUpdate();
            //if ResultSet return 0 (no fields was updated) throw exception about it
            if (result == ZERO) throw new UnableUpdateFieldException();
            else
                logger.debug("{} updated success. Updates entity {}", entity.getClass().getSimpleName(), entity);
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
    }

    /**
     * General method for operation "searching" entities to database
     *
     * @param entity     the entity which need finds in database
     * @param parameters the list of parameters for prepare PrepareStatements
     * @param key        property key for reading update query from property file
     * @param locale     user's locale for select only localized entities from database
     * @return the list of found entities
     * @throws JdbcDaoException if any exceptions occurred with jdbc operations
     */
    @Override
    public List<T> findAllByParameters(T entity, List<Object> parameters, String key, String locale) throws JdbcDaoException {

        logger.debug("{} trying to FIND entities \"{}\" in database...", this.getClass().getSimpleName(), entity.getClass().getSimpleName());
        query = getSqlQuery(key);
        List<T> entities = new ArrayList<>();
        //get and format query from file for find localisation values
        String localeQuery = getQueryByLocale(query, locale);
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

        return entities;
    }

    /**
     * General method for operation "searching" single entity to database
     *
     * @param entity     the entity which need finds in database
     * @param parameters the list of parameters for prepare PrepareStatements
     * @param key        property key for reading update query from property file
     * @return found entity
     * @throws JdbcDaoException if any exceptions occurred with jdbc operations
     */
    @Override
    public T findByParameters(T entity, List<Object> parameters, String key) throws JdbcDaoException {

        logger.debug("{} trying to FIND entity \"{}\" in database...", this.getClass().getSimpleName(), entity.getClass().getSimpleName());
        query = getSqlQuery(key);
        T newEntity = null;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            setParametersToPs(parameters, ps);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) newEntity = setRsToField(rs, entity);
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
        return newEntity;
    }

    /**
     * Get sql query from properties
     *
     * @param key property key
     * @return sql query
     * @throws JdbcDaoException wrap for exception throws in method
     */
    private String getSqlQuery(String key) throws JdbcDaoException {

        String sqlQuery;
        try {
            PropertyManager pm = new PropertyManager(QUERY_PROPERTY_FILE);
            sqlQuery = pm.getPropertyKey(key);
        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(e);
        }
        return sqlQuery;
    }

    /**
     * Format query by user's locale
     *
     * @param query  the sql query
     * @param locale user's locale
     * @return formatted sql query
     */
    private String getQueryByLocale(String query, String locale) {

        return String.format(query, locale, locale);
    }

    /**
     * Set values to PrepareStatement
     *
     * @param parameters values
     * @param ps         PrepareStatement
     * @throws SQLException if any jdbc errors occurred
     */
    private void setParametersToPs(List<Object> parameters, PreparedStatement ps) throws SQLException {

        int count = INITIAL_COUNT;
        for (Object parameter : parameters) {
            ps.setObject(count, parameter);
            count++;
        }
        parameters.clear();
    }

    /**
     * Set returning from ResultSet id in entity
     *
     * @param entity inserted entity
     * @param ps     PrepareStatement
     * @throws SQLException if any jdbc errors occurred
     */
    void setId(T entity, PreparedStatement ps) throws SQLException {

        ResultSet generatedId = ps.getGeneratedKeys();
        generatedId.next();
        int id = generatedId.getInt(1);
        entity.setId(id);
        if (entity.getId() != null)
            logger.debug("Insert success. Entity {} received id = {}", entity.getClass().getSimpleName(), entity.getId());
    }

    /**
     * Set values from ResultSet to entity's fields.
     *
     * @param rs     the ResultSet
     * @param entity entity which need for set fields
     * @return entity with set fields
     * @throws SQLException if any jdbc errors occurred
     */
    abstract T setRsToField(ResultSet rs, T entity) throws SQLException;

}