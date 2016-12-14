package com.epam.as.bookhotel.service;


import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.exception.DaoException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.exception.UserNotFoundException;
import com.epam.as.bookhotel.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService extends ParentService {

    private static final String REGISTER_USER_KEY = "insert.user";
    private static final String FIND_LOGIN_USER_KEY = "find.user.login";
    private static final String USER_NOT_FOUND_ERROR_MSG = "login.error.notfound";
    private List<String> parameters = new ArrayList<>();

    public User register(User user) throws ServiceException {
        User regUser;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            UserDao userDao = daoFactory.getUserDao();
            regUser = userDao.save(user, REGISTER_USER_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return regUser;
    }

    public User login(User user) throws ServiceException {
        List<User> foundUsers;
        parameters.add(user.getLogin());
        parameters.add(user.getPassword());
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            UserDao userDao = daoFactory.getUserDao();
            foundUsers = userDao.findByParameters(user, parameters, FIND_LOGIN_USER_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        if (foundUsers.isEmpty()) throw new UserNotFoundException(USER_NOT_FOUND_ERROR_MSG);
        return foundUsers.get(0);
    }

}