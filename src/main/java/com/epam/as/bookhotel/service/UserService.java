package com.epam.as.bookhotel.service;


import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.exception.*;
import com.epam.as.bookhotel.model.User;
import com.epam.as.bookhotel.util.PasswordStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserService extends ParentService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final String REGISTER_USER_KEY = "insert.user";
    private static final String FIND_LOGIN_USER_KEY = "find.user.login";
    private static final List<String> parameters = new ArrayList<>();
    private static final int INDEX_0 = 0;

    public User register(User user) throws ServiceException {

        String hashPassword;
        try {
            hashPassword = PasswordStorage.createHash(user.getPassword());
            user.setPassword(hashPassword);
        } catch (PasswordStorage.CannotPerformOperationException e) {
            throw new ServiceException(e);
        }
        parameters.add(user.getLogin());
        parameters.add(user.getPassword());
        parameters.add(user.getRole().toString());
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
            usersList = userDao.findByParameters(user, parameters, FIND_LOGIN_USER_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        final String correctHash = user.getPassword();
        try {
            logger.debug("User's password validation result = {}", PasswordStorage.verifyPassword(testPassword, correctHash));
            if (usersList.isEmpty() || !(PasswordStorage.verifyPassword(testPassword, correctHash))) {
                throw new UserNotFoundException();
            }
        } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException | UserNotFoundException e) {
            throw new ServiceException(e);
        }

        return user;
    }

}