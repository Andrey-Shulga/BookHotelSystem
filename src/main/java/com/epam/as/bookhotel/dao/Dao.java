package com.epam.as.bookhotel.dao;

import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.BaseEntity;

/**
 * An universal interface for DAO objects.
 */
public interface Dao<T extends BaseEntity> {

    T save(T entity) throws PropertyManagerException, JdbcDaoException;

    T findById(int id);

    void delete(T entity);

    void deleteById(int id);
}
