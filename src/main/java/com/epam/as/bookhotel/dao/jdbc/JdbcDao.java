package com.epam.as.bookhotel.dao.jdbc;


import com.epam.as.bookhotel.dao.Dao;
import com.epam.as.bookhotel.model.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {

    private static final Logger logger = LoggerFactory.getLogger(JdbcDao.class);
    private Connection connection;

    JdbcDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public T save(T entity) {
        if (entity.getId() == null) {
            logger.debug("{} trying to save entity \"{}\" to database...", this.getClass().getSimpleName(), entity.getClass().getSimpleName());
            String insertQuery = getInsertQuery();
        } else {

        }
        return entity;
    }

    protected abstract String getInsertQuery();

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

    public abstract void setFieldToPs();
}
