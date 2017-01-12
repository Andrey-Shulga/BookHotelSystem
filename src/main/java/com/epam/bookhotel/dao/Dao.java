package com.epam.bookhotel.dao;

import com.epam.bookhotel.entity.BaseEntity;
import com.epam.bookhotel.exception.JdbcDaoException;

import java.util.List;

/**
 * An universal interface for DAO objects operations.
 */
public interface Dao<T extends BaseEntity> {

    //Operation for save entity
    T save(T entity, List<Object> parameters, String key) throws JdbcDaoException;

    //Operation for update entity
    void update(T entity, List<Object> parameters, String key) throws JdbcDaoException;

    //Operation for searching several entities by parameters
    List<T> findAllByParameters(T entity, List<Object> parameters, String key, String locale) throws JdbcDaoException;

    //Operation for searching entity by parameters
    T findByParameters(T entity, List<Object> parameters, String key) throws JdbcDaoException;

}