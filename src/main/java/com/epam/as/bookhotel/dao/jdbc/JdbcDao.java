package com.epam.as.bookhotel.dao.jdbc;


import com.epam.as.bookhotel.dao.Dao;
import com.epam.as.bookhotel.exception.*;
import com.epam.as.bookhotel.listener.ConnectionPoolInitListener;
import com.epam.as.bookhotel.model.BaseEntity;
import com.epam.as.bookhotel.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract DAO for JDBC operations with entities
 *
 * @param <T> generic type for any entity
 */

abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {

    private static final String QUERY_PROPERTY_FILE = "query.properties";
    private static final String NON_UNIQUE_FIELD_ERROR_CODE = "23505";
    private static final String DATABASE_CONNECTION_FAILURE_ERROR_CODE = "08006";
    private static final int ZERO = 0;
    private static final int INITIAL_COUNT = 1;
    private static final Logger logger = LoggerFactory.getLogger(JdbcDao.class);
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
     * General method for operation "insert" entities to database
     *
     * @param entity     the entity which need inserts in database
     * @param parameters the list of parameters for prepare PrepareStatements
     * @param key        property key for reading insert query from property file
     * @return inserted entity with received id
     * @throws JdbcDaoException if any exceptions occurred with jdbc operations
     */
    @Override
    public T save(T entity, List<Object> parameters, String key) throws JdbcDaoException {

        try {
            //load file with property to property manager
            PropertyManager pm = new PropertyManager(QUERY_PROPERTY_FILE);
            if (entity.getId() == null) {
                logger.debug("{} trying to INSERT entity \"{}\" to database...", this.getClass().getSimpleName(), entity);
                try (PreparedStatement ps = connection.prepareStatement(pm.getPropertyKey(key), Statement.RETURN_GENERATED_KEYS)) {
                    //set the list of parameters to PrepareStatement
                    setParametersToPs(parameters, ps);
                    ps.execute();
                    //set id received from ResultSet in entity
                    setId(entity, ps);
                } catch (SQLException e) {
                    //if field's value which inserted in column which keeps only unique values exist - throw exception about it
                    if (NON_UNIQUE_FIELD_ERROR_CODE.equals(e.getSQLState())) throw new NonUniqueFieldException(e);
                    try {
                        //if database was down and connections in pool are not valid (closed), trying to clear pool and fill it with new connections
                        if (DATABASE_CONNECTION_FAILURE_ERROR_CODE.equals(e.getSQLState()) && (connection.isClosed())) {
                            getUpConnectionPool();
                        }
                    } catch (SQLException ex) {
                        throw new JdbcDaoException(ex);
                    }
                    throw new JdbcDaoException(e);
                }
            }
        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(e);
        }

        return entity;
    }

    /**
     * General method for operation "update" entities to database
     *
     * @param entity the entity which need updates in database
     * @param parameters the list of parameters for prepare PrepareStatements
     * @param key property key for reading update query from property file
     * @return updated entity
     * @throws JdbcDaoException if any exceptions occurred with jdbc operations
     */
    @Override
    public T update(T entity, List<Object> parameters, String key) throws JdbcDaoException {

        logger.debug("{} trying to UPDATE entity \"{}\" in database...", this.getClass().getSimpleName(), entity);
        try {
            PropertyManager pm = new PropertyManager(QUERY_PROPERTY_FILE);
            try (PreparedStatement ps = connection.prepareStatement(pm.getPropertyKey(key))) {
                setParametersToPs(parameters, ps);
                int result = ps.executeUpdate();
                //if ResultSet return 0 (no fields was updated) throw exception about it
                if (result == ZERO) throw new UnableUpdateFieldException();
                else
                    logger.debug("{} updated success. Updates entity {}", entity.getClass().getSimpleName(), entity);
            } catch (SQLException e) {
                try {
                    //if database was down and connections in pool are not valid (closed), trying to clear pool and fill it with new connections
                    if (DATABASE_CONNECTION_FAILURE_ERROR_CODE.equals(e.getSQLState()) && (connection.isClosed())) {
                        getUpConnectionPool();
                    }
                } catch (SQLException ex) {
                    throw new JdbcDaoException(ex);
                }
                throw new JdbcDaoException(e);
            }
        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(e);
        }

        return entity;
    }

    /**
     * General method for operation "select" entities to database
     *
     * @param entity the entity which need finds in database
     * @param parameters the list of parameters for prepare PrepareStatements
     * @param key property key for reading update query from property file
     * @param locale user's locale for select only localized entities from database
     * @return the list of found entities
     * @throws JdbcDaoException if any exceptions occurred with jdbc operations
     */
    @Override
    public List<T> findByParameters(T entity, List<Object> parameters, String key, String locale) throws JdbcDaoException {

        logger.debug("{} trying to FIND entity \"{}\" in database...", this.getClass().getSimpleName(), entity.getClass().getSimpleName());
        List<T> entities = new ArrayList<>();
        try {
            PropertyManager pm = new PropertyManager(QUERY_PROPERTY_FILE);
            //get and format query from file for find localisation values
            String localeQuery = getQueryByLocale(pm.getPropertyKey(key), locale);
            try (PreparedStatement ps = connection.prepareStatement(localeQuery)) {
                setParametersToPs(parameters, ps);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    T newEntity = setRsToField(rs, entity);
                    entities.add(newEntity);
                }
            } catch (SQLException e) {
                try {
                    //if database was down and connections in pool are not valid (closed), trying to clear pool and fill it with new connections
                    if (DATABASE_CONNECTION_FAILURE_ERROR_CODE.equals(e.getSQLState()) && (connection.isClosed())) {
                        getUpConnectionPool();
                    }
                } catch (SQLException ex) {
                    throw new JdbcDaoException(ex);
                }

                throw new JdbcDaoException(e);
            }
        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(e);
        }

        return entities;
    }

    /**
     * Invokes methods for clearing connection pool from "dead" connection and fill it again with new connections (after possible database down).
     *
     * @throws JdbcDaoException on exceptions from Connection pool.
     */
    void getUpConnectionPool() throws JdbcDaoException {

        logger.debug("Connection is invalid. Perhaps database downed. Trying to initialize and fill connection pool again.");
        try {
            //clear pool from connections
            ConnectionPoolInitListener.getPool().close();
            //fill pool with new connections
            ConnectionPoolInitListener.getPool().fillPool();
        } catch (ConnectionPoolException e) {
            throw new JdbcDaoException(e);
        }

    }

    /**
     * Format query by user's locale
     *
     * @param query the sql query
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
     * @param ps PrepareStatement
     * @throws SQLException if any jdbc errors occurred
     */
    private void setParametersToPs(List<Object> parameters, PreparedStatement ps) throws SQLException {

        int count = INITIAL_COUNT;
        for (Object parameter : parameters) {
            logger.debug("param = {}", parameter);
            ps.setObject(count, parameter);
            count++;
        }
        parameters.clear();
    }

    /**
     * Set returning from ResultSet id in entity
     *
     * @param entity inserted entity
     * @param ps PrepareStatement
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
     * @param rs the ResultSet
     * @param entity entity which need for set fields
     * @return entity with set fields
     * @throws SQLException if any jdbc errors occurred
     */
    abstract T setRsToField(ResultSet rs, T entity) throws SQLException;

}