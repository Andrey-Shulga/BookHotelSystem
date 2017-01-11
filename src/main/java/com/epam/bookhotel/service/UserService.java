package com.epam.bookhotel.service;


import com.epam.bookhotel.dao.DaoFactory;
import com.epam.bookhotel.dao.UserDao;
import com.epam.bookhotel.entity.User;
import com.epam.bookhotel.exception.*;
import com.epam.bookhotel.util.PasswordStorage;

import java.util.List;

/**
 * Service serves operation with entity User
 */

public class UserService extends ParentService {

    private static final String REGISTER_USER_KEY = "insert.user";
    private static final String FIND_LOGIN_USER_KEY = "find.user.login";
    private static final String UPDATE_USER_LOCALE_KEY = "update.user.locale";

    /**
     * Save new user
     *
     * @param user entity for inserting
     * @return user with id
     * @throws ServiceException if any exception in service occurred
     */
    public User register(User user) throws ServiceException {

        String hashPassword;
        try {
            //create hashing password from entered password
            hashPassword = PasswordStorage.createHash(user.getPassword());
            user.setPassword(hashPassword);
        } catch (PasswordStorage.CannotPerformOperationException e) {
            throw new ServiceException(e);
        }
        parameters.add(user.getRole().toString());
        parameters.add(user.getLogin());
        parameters.add(user.getPassword());
        parameters.add(user.getLocale().getLocaleName());
        try (DaoFactory daoFactory = DaoFactory.createJdbcDaoFactory()) {
            UserDao userDao = daoFactory.getUserDao();
            userDao.save(user, parameters, REGISTER_USER_KEY);
        } catch (NonUniqueFieldException e) {

            throw new UserExistException(e);

        } catch (DaoException e) {

            throw new ServiceException(e);
        }
        return user;
    }

    /**
     * Log in user by searching it.
     *
     * @param user entity fro searching
     * @return found user
     * @throws ServiceException if any exception in service occurred
     */
    public User login(User user) throws ServiceException {

        parameters.add(user.getLogin());
        final String testPassword = user.getPassword();
        List<User> usersList;
        try (DaoFactory daoFactory = DaoFactory.createJdbcDaoFactory()) {
            UserDao userDao = daoFactory.getUserDao();
            usersList = userDao.findByParameters(user, parameters, FIND_LOGIN_USER_KEY, user.getLocale().getLocaleName());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        final String correctHash = user.getPassword();
        try {
            //check entered password with found hash password
            if ((usersList.isEmpty()) || (!PasswordStorage.verifyPassword(testPassword, correctHash))) {
                throw new UserNotFoundException();
            }
        } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException | UserNotFoundException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    /**
     * Update user's locale
     *
     * @param user entity for updating
     * @return updated user
     * @throws ServiceException if any exception in service occurred
     */
    public User saveUserLocale(User user) throws ServiceException {

        parameters.add(user.getRole().getRole().toString());
        parameters.add(user.getLogin());
        parameters.add((user.getPassword()));
        parameters.add(user.getLocale().getLocaleName());
        parameters.add(user.getId());
        try (DaoFactory daoFactory = DaoFactory.createJdbcDaoFactory()) {
            UserDao userDao = daoFactory.getUserDao();
            userDao.update(user, parameters, UPDATE_USER_LOCALE_KEY);
        } catch (DaoException e) {

            throw new ServiceException(e);
        }
        return user;
    }
}