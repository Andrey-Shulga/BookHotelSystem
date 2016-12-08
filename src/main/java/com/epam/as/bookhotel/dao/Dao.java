package com.epam.as.bookhotel.dao;

import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.BaseEntity;

import java.util.List;

/**
 * An universal interface for DAO objects.
 */
public interface Dao<T extends BaseEntity> {

    T save(T entity) throws PropertyManagerException, JdbcDaoException;

    List<T> findAllById(T entity, int id) throws PropertyManagerException, JdbcDaoException;

    void delete(T entity);

    void deleteById(int id);

    T find(T entity) throws PropertyManagerException, JdbcDaoException;
}
