package com.epam.as.bookhotel.dao;

import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.model.BaseEntity;

import java.util.List;

/**
 * An universal interface for DAO objects.
 */
public interface Dao<T extends BaseEntity> {

    T save(T entity, String queryKey) throws JdbcDaoException;

    List<T> findByParameters(T entity, List<String> parameters, String queryKey) throws JdbcDaoException;
}
