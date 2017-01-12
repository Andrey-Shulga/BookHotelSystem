package com.epam.bookhotel.service;

import com.epam.bookhotel.dao.DaoFactory;
import com.epam.bookhotel.dao.PhotoDao;
import com.epam.bookhotel.entity.Photo;
import com.epam.bookhotel.exception.DaoException;
import com.epam.bookhotel.exception.ServiceException;

/**
 * Service serves operation with entity Photo
 */

public class ImageService extends ParentService {

    private static final String FIND_PHOTO_BY_ID_KEY = "find.photo.by.id";

    /**
     * Search photo by its id
     *
     * @param photo entity for searching
     * @return found photo
     * @throws ServiceException if any exception in service occurred.
     */
    public Photo findPhotoById(Photo photo) throws ServiceException {

        parameters.add(photo.getId());
        Photo foundPhoto;
        try (DaoFactory daoFactory = DaoFactory.createJdbcDaoFactory()) {
            PhotoDao photoDao = daoFactory.getPhotoDao();
            foundPhoto = photoDao.findByParameters(photo, parameters, FIND_PHOTO_BY_ID_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return foundPhoto;
    }
}
