package com.epam.as.bookhotel.dao.jdbc;


import com.epam.as.bookhotel.dao.Dao;
import com.epam.as.bookhotel.exception.*;
import com.epam.as.bookhotel.model.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

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
                setInsertFieldToPs(ps, entity);
                ps.executeUpdate();
                setId(entity, ps);
            } catch (SQLException e) {
                if (DATABASE_CONNECT_LOST_ERROR_CODE.equals(e.getSQLState()))
                    throw new DatabaseConnectionException(e);
                if (USER_EXIST_ERROR_CODE.equals(e.getSQLState()))
                    throw new UserExistingException(e);
            }
            //update entity
        } else {
            logger.debug("{} trying to UPDATE entity \"{}\" in database...", this.getClass().getSimpleName(), entity.getClass().getSimpleName());
            String updateQuery = getUpdateQuery();
            try {
                PreparedStatement ps = connection.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS);
                setUpdateFieldToPs(ps, entity);
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e) {
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
            setFindFieldToPs(ps, entity);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            if (rs != null) {
                rs.next();
                getId(entity, rs);
                setRsToField(rs, entity);
                rs.close();
            }
            ps.close();
        } catch (SQLException e) {
            if (USER_NOT_FOUND_ERROR_CODE.equals(e.getSQLState()))
                throw new UserNotFoundException(e);
        }
        return entity;
    }

    private void getId(T entity, ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        entity.setId(id);
        rs.close();
        logger.debug("Search success. Entity {} with id {} found in database.", entity.getClass().getSimpleName(), entity.getId());
    }

    private void setId(T entity, PreparedStatement ps) throws SQLException {
        ResultSet generatedId = ps.getGeneratedKeys();
        generatedId.next();
        int id = generatedId.getInt(1);
        entity.setId(id);
        ps.close();
        logger.debug("Insert success. Entity {} received id = {}", entity.getClass().getSimpleName(), entity.getId());
    }

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

    abstract String getInsertQuery() throws PropertyManagerException;

    abstract String getUpdateQuery() throws PropertyManagerException;

    abstract String getFindQuery() throws PropertyManagerException;

    abstract void setInsertFieldToPs(PreparedStatement ps, T entity) throws SQLException;

    abstract void setFindFieldToPs(PreparedStatement ps, T entity) throws SQLException;

    abstract void setUpdateFieldToPs(PreparedStatement ps, T entity) throws SQLException;

    abstract void setRsToField(ResultSet ps, T entity) throws SQLException;
}
