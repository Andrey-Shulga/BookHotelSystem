package com.epam.as.bookhotel.dao.jdbc;


import com.epam.as.bookhotel.dao.Dao;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.UserExistingException;
import com.epam.as.bookhotel.model.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {

    static final String QUERY_PROPERTY_FILE = "query.properties";
    private static final String USER_EXIST_ERROR_CODE = "23505";
    private static final Logger logger = LoggerFactory.getLogger(JdbcDao.class);
    private Connection connection;

    JdbcDao(Connection connection) {
        this.connection = connection;
    }



    @Override
    public T save(T entity) throws PropertyManagerException, JdbcDaoException, UserExistingException {
        //insert entity
        if (entity.getId() == null) {
            logger.debug("{} trying to INSERT entity \"{}\" to database...", this.getClass().getSimpleName(), entity.getClass().getSimpleName());
            String insertQuery = getInsertQuery();
            try {
                PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                setInsertFieldToPs(ps, entity);
                ps.executeUpdate();
                setId(entity, ps);
            } catch (SQLException e) {
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

            } catch (SQLException e) {
                throw new JdbcDaoException(e);
            }
        }
        return entity;
    }

    abstract void setUpdateFieldToPs(PreparedStatement ps, T entity) throws SQLException;

    abstract String getUpdateQuery() throws PropertyManagerException;

    private void setId(T entity, PreparedStatement ps) throws SQLException {
        ResultSet generatedId = ps.getGeneratedKeys();
        generatedId.next();
        int id = generatedId.getInt(1);
        entity.setId(id);
        logger.debug("Insert success. Entity id = {}", id);
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

    abstract void setInsertFieldToPs(PreparedStatement ps, T entity) throws SQLException;
}
