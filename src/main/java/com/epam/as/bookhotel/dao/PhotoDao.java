package com.epam.as.bookhotel.dao;

import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.model.Photo;

import java.util.List;

public interface PhotoDao extends Dao<Photo> {

    Photo addPhoto(Photo photo, List<String> parameters, String queryKey) throws JdbcDaoException;
}
