package com.epam.as.bookhotel.service;


import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.exception.*;
import com.epam.as.bookhotel.model.User;
import com.epam.as.bookhotel.util.PasswordStorage;

import java.util.ArrayList;
import java.util.List;

public class UserService extends ParentService {

    private static final String REGISTER_USER_KEY = "insert.user";
    private static final String FIND_LOGIN_USER_KEY = "find.user.login";
    private static final String UPDATE_USER_LOCALE_KEY = "update.user.locale";
    private static final List<String> parameters = new ArrayList<>();

    public User register(User user) throws ServiceException {

        String hashPassword;
        try {
            hashPassword = PasswordStorage.createHash(user.getPassword());
            user.setPassword(hashPassword);
        } catch (PasswordStorage.CannotPerformOperationException e) {
            throw new ServiceException(e);
        }
        parameters.add(user.getRole().toString());
        parameters.add(user.getLogin());
        parameters.add(user.getPassword());
        parameters.add(user.getLocale().getLocaleName());
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            UserDao userDao = daoFactory.getUserDao();
            userDao.save(user, parameters, REGISTER_USER_KEY);
        } catch (NonUniqueFieldException e) {

            throw new UserExistException(e);

        } catch (DaoException e) {

            throw new ServiceException(e);
        }
        return user;
    }

    public User login(User user) throws ServiceException {

        parameters.add(user.getLogin());
        final String testPassword = user.getPassword();
        List<User> usersList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            UserDao userDao = daoFactory.getUserDao();
            usersList = userDao.findByParameters(user, parameters, FIND_LOGIN_USER_KEY, user.getLocale().getLocaleName());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        final String correctHash = user.getPassword();
        try {
            if ((usersList.isEmpty()) || (!PasswordStorage.verifyPassword(testPassword, correctHash))) {
                throw new UserNotFoundException();
            }
        } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException | UserNotFoundException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    public User saveUserLocale(User user) throws ServiceException {

        parameters.add(user.getRole().getRole().toString());
        parameters.add(user.getLogin());
        parameters.add((user.getPassword()));
        parameters.add(user.getLocale().getLocaleName());
        parameters.add(String.valueOf(user.getId()));
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            UserDao userDao = daoFactory.getUserDao();
            userDao.update(user, parameters, UPDATE_USER_LOCALE_KEY);
        } catch (DaoException e) {

            throw new ServiceException(e);
        }
        return user;
    }
}