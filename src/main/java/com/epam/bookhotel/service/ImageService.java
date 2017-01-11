package com.epam.bookhotel.service;

import com.epam.bookhotel.dao.DaoFactory;
import com.epam.bookhotel.dao.PhotoDao;
import com.epam.bookhotel.entity.Photo;
import com.epam.bookhotel.exception.DaoException;
import com.epam.bookhotel.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Service serves operation with entity Photo
 */

public class ImageService extends ParentService {

    private static final String FIND_PHOTO_BY_ID_KEY = "find.photo.by.id";
    private static final String BLANK_LOCALE = "";
    private final List<Object> parameters = new ArrayList<>();

    /**
     * Search photo by its id
     *
     * @param photo entity for searching
     * @return found photo
     * @throws ServiceException if any exception in service occurred.
     */
    public Photo findPhotoById(Photo photo) throws ServiceException {

        parameters.add(photo.getId());
        try (DaoFactory daoFactory = DaoFactory.createJdbcDaoFactory()) {
            PhotoDao photoDao = daoFactory.getPhotoDao();
            photoDao.findByParameters(photo, parameters, FIND_PHOTO_BY_ID_KEY, BLANK_LOCALE);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return photo;
    }
}
