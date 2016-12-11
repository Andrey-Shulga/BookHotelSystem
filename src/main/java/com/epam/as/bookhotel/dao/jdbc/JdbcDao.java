package com.epam.as.bookhotel.dao.jdbc;


import com.epam.as.bookhotel.dao.Dao;
import com.epam.as.bookhotel.exception.*;
import com.epam.as.bookhotel.model.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {

    static final String QUERY_PROPERTY_FILE = "query.properties";
    private static final String USER_EXIST_ERROR_CODE = "23505";
    private static final String USER_NOT_FOUND_ERROR_CODE = "24000";
    private static final String DATABASE_CONNECT_LOST_ERROR_CODE = "08006";
    private static final Logger logger = LoggerFactory.getLogger(JdbcDao.class);
    private Connection connection;

    JdbcDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public T save(T entity) throws PropertyManagerException, JdbcDaoException {
        //insert entity
        if (entity.getId() == null) {
            logger.debug("{} trying to INSERT entity \"{}\" to database...", this.getClass().getSimpleName(), entity);
            String insertQuery = getInsertQuery();
            try {
                PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                setInsertQueryFieldToPs(ps, entity);
                ps.executeUpdate();
                setId(entity, ps);
            } catch (SQLException e) {
                if (DATABASE_CONNECT_LOST_ERROR_CODE.equals(e.getSQLState()))
                    throw new DatabaseConnectionException(e);
                if (USER_EXIST_ERROR_CODE.equals(e.getSQLState()))
                    throw new UserExistingException(e);
                else
                    throw new JdbcDaoException(e);
            }

        }
        return entity;
    }

    @Override
    public T find(T entity) throws PropertyManagerException, JdbcDaoException {
        logger.debug("{} trying to FIND entity \"{}\" in database...", this.getClass().getSimpleName(), entity);
        String findQuery = getFindQuery();
        try {
            PreparedStatement ps = connection.prepareStatement(findQuery);
            setFindQueryFieldToPs(ps, entity);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            setFindQueryRsToField(rs, entity);
            ps.close();
        } catch (SQLException e) {
            if (DATABASE_CONNECT_LOST_ERROR_CODE.equals(e.getSQLState()))
                throw new DatabaseConnectionException(e);
            if (USER_NOT_FOUND_ERROR_CODE.equals(e.getSQLState()))
                throw new UserNotFoundException(e);
            else
                throw new JdbcDaoException(e);
        }
        return entity;
    }


    @Override
    public List<T> findAllByParameter(T entity) throws PropertyManagerException, JdbcDaoException {

        List<T> entities = new ArrayList<>();
        String findQuery = getFindQuery();
        try {
            PreparedStatement ps = connection.prepareStatement(findQuery);
            setFindQueryFieldToPs(ps, entity);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                entity = setFindQueryRsToField(rs, entity);
                entities.add(entity);
            }
        } catch (SQLException e) {
            if (DATABASE_CONNECT_LOST_ERROR_CODE.equals(e.getSQLState()))
                throw new DatabaseConnectionException(e);
            else
                throw new JdbcDaoException(e);
        }

        return entities;
    }

    @Override
    public List<T> findAll(T entity) throws PropertyManagerException, JdbcDaoException {
        List<T> entities = new ArrayList<>();
        String findAllQuery = getFindAllQuery();
        logger.debug("{} trying to FIND all entities \"{}\" in database...",
                this.getClass().getSimpleName(), entity.getClass().getSimpleName());
        try {
            PreparedStatement ps = connection.prepareStatement(findAllQuery);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                entity = setFindALLQueryRsToField(rs, entity);
                entities.add(entity);
            }
        } catch (SQLException e) {
            if (DATABASE_CONNECT_LOST_ERROR_CODE.equals(e.getSQLState()))
                throw new DatabaseConnectionException(e);
            else
                throw new JdbcDaoException(e);
        }
        for (T element : entities) {
            logger.debug("Found entity: {}", element);
        }
        return entities;
    }

    private void setId(T entity, PreparedStatement ps) throws SQLException {
        ResultSet generatedId = ps.getGeneratedKeys();
        generatedId.next();
        int id = generatedId.getInt(1);
        entity.setId(id);
        ps.close();
        logger.debug("Insert success. Entity {} received id = {}", entity.getClass().getSimpleName(), entity.getId());
    }

    abstract T setFindALLQueryRsToField(ResultSet rs, T entity) throws SQLException;

    abstract String getFindAllQuery() throws PropertyManagerException;

    abstract String getInsertQuery() throws PropertyManagerException;
    abstract String getFindQuery() throws PropertyManagerException;

    abstract void setInsertQueryFieldToPs(PreparedStatement ps, T entity) throws SQLException;

    abstract void setFindQueryFieldToPs(PreparedStatement ps, T entity) throws SQLException;

    abstract T setFindQueryRsToField(ResultSet rs, T entity) throws SQLException;
}
