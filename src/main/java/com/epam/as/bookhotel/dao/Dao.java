package com.epam.as.bookhotel.dao;

import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.model.BaseEntity;

import java.util.List;

/**
 * An universal interface for DAO objects.
 */
public interface Dao<T extends BaseEntity> {

    T save(T entity, List<Object> parameters, String queryKey) throws JdbcDaoException;

    T update(T entity, List<Object> parameters, String queryKey) throws JdbcDaoException;

    List<T> findByParameters(T entity, List<Object> parameters, String queryKey, String locale) throws JdbcDaoException;

}