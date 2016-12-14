package com.epam.as.bookhotel.dao.jdbc;


import com.epam.as.bookhotel.dao.Dao;
import com.epam.as.bookhotel.exception.DatabaseConnectionException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.UserExistException;
import com.epam.as.bookhotel.model.BaseEntity;
import com.epam.as.bookhotel.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {

    private static final String QUERY_PROPERTY_FILE = "query.properties";
    private static final String ENTITY_EXIST_ERROR_CODE = "23505";
    private static final String DATABASE_CONNECT_LOST_ERROR_CODE = "08006";
    private static final Logger logger = LoggerFactory.getLogger(JdbcDao.class);
    private Connection connection;

    JdbcDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public T save(T entity, String queryKey) throws JdbcDaoException {

        //insert entity
        PropertyManager propertyManager;
        try {
            propertyManager = new PropertyManager(QUERY_PROPERTY_FILE);

            if (entity.getId() == 0) {
                logger.debug("{} trying to INSERT entity \"{}\" to database...", this.getClass().getSimpleName(), entity);
                try (PreparedStatement ps = connection.prepareStatement(propertyManager.getPropertyKey(queryKey), Statement.RETURN_GENERATED_KEYS)) {
                    setFindFieldToPs(ps, entity);
                    ps.execute();
                    setId(entity, ps);
                } catch (SQLException e) {
                    if (ENTITY_EXIST_ERROR_CODE.equals(e.getSQLState()))
                        throw new UserExistException(e);
                    else
                        throw new JdbcDaoException(e);

                }
                //update entity
            } else {
                logger.debug("{} trying to UPDATE entity \"{}\" in database...", this.getClass().getSimpleName(), entity);
                try (PreparedStatement ps = connection.prepareStatement(propertyManager.getPropertyKey(queryKey))) {
                    setUpdateFieldToPs(ps, entity);
                    int result = ps.executeUpdate();
                    if (result != 0)
                        logger.debug("{} updated success. Updates entity {}", entity.getClass().getSimpleName(), entity);
                } catch (SQLException e) {
                    throw new JdbcDaoException(e);
                }
            }
        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(e);
        }
        return entity;
    }

    @Override
    public List<T> findByParameters(T entity, List<String> parameters, String queryKey) throws JdbcDaoException {
        logger.debug("{} trying to FIND entity \"{}\" in database...", this.getClass().getSimpleName(), entity.getClass().getSimpleName());
        List<T> entities = new ArrayList<>();
        PropertyManager propertyManager;
        try {
            propertyManager = new PropertyManager(QUERY_PROPERTY_FILE);
            int count = 1;
            try (PreparedStatement ps = connection.prepareStatement(propertyManager.getPropertyKey(queryKey))) {
                for (String parameter : parameters) {
                    ps.setString(count, parameter);
                    count++;
                }
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    entity = setRsToField(rs, entity);
                    entities.add(entity);
                }
            } catch (SQLException e) {
                if (DATABASE_CONNECT_LOST_ERROR_CODE.equals(e.getSQLState()))
                    throw new DatabaseConnectionException(e);
                else
                    throw new JdbcDaoException(e);
            }
        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(e);
        }
        return entities;
    }

    private void setId(T entity, PreparedStatement ps) throws SQLException {
        ResultSet generatedId = ps.getGeneratedKeys();
        generatedId.next();
        int id = generatedId.getInt(1);
        entity.setId(id);
        if (entity.getId() != 0)
            logger.debug("Insert success. Entity {} received id = {}", entity.getClass().getSimpleName(), entity.getId());
    }

    abstract void setUpdateFieldToPs(PreparedStatement ps, T entity) throws SQLException;

    abstract void setFindFieldToPs(PreparedStatement ps, T entity) throws SQLException;

    abstract T setRsToField(ResultSet rs, T entity) throws SQLException;
}
