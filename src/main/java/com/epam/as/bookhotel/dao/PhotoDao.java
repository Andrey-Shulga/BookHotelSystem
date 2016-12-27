package com.epam.as.bookhotel.dao;

import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.model.Photo;

public interface PhotoDao extends Dao<Photo> {

    Photo addPhoto(Photo photo) throws JdbcDaoException;
}
