package com.epam.as.bookhotel.dao;

import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.model.BaseEntity;

import java.util.List;

/**
 * An universal interface for DAO objects.
 */
public interface Dao<T extends BaseEntity> {

    T save(T entity, String queryKey) throws JdbcDaoException;

    T update(T entity, List<String> parameters, String queryKey) throws JdbcDaoException;

    List<List<Object>> findByParameters(T entity, List<String> parameters, String queryKey) throws JdbcDaoException;

}
