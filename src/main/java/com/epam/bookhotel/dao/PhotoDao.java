package com.epam.bookhotel.dao;

import com.epam.bookhotel.entity.Photo;
import com.epam.bookhotel.exception.JdbcDaoException;

import java.util.List;

/**
 * An interface for DAO operations with entity Photo
 */

public interface PhotoDao extends Dao<Photo> {

    /**
     * Inserts entity Photo to database.
     *
     * @param photo      the entity which need inserts in database
     * @param parameters the list of parameters for prepare PrepareStatements
     * @param queryKey   property key for reading insert query from property file
     * @return inserted entity with received id
     * @throws JdbcDaoException if any exceptions occurred with jdbc operations
     */
    Photo addPhoto(Photo photo, List<Object> parameters, String queryKey) throws JdbcDaoException;
}
