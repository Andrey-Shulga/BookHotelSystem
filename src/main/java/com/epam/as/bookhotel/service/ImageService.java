package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.PhotoDao;
import com.epam.as.bookhotel.exception.DaoException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class ImageService extends ParentService {

    private static final String FIND_PHOTO_BY_ID_KEY = "find.photo.by.id";
    private static final String BLANK_LOCALE = "";
    private static final List<String> parameters = new ArrayList<>();

    public Photo findPhotoById(Photo photo) throws ServiceException {

        parameters.add(photo.getId().toString());
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            PhotoDao photoDao = daoFactory.getPhotoDao();
            photoDao.findByParameters(photo, parameters, FIND_PHOTO_BY_ID_KEY, BLANK_LOCALE);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return photo;
    }
}
