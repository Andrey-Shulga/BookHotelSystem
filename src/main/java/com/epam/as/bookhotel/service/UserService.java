package com.epam.as.bookhotel.service;


import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final String REGISTER_USER_KEY = "insert.user";
    private static final String FIND_LOGIN_USER_KEY = "find.user.login";
    private List<String> parameters = new ArrayList<>();

    public User register(User user) throws PropertyManagerException, ConnectionPoolException, JdbcDaoException {
        User regUser;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            UserDao userDao = daoFactory.getUserDao();
            regUser = userDao.save(user, REGISTER_USER_KEY);
        } catch (JdbcDaoException e) {
            throw new JdbcDaoException(e);
        }
        return regUser;
    }

    public User login(User user) throws JdbcDaoException, ConnectionPoolException, PropertyManagerException {
        List<User> foundUsers;
        parameters.add(user.getLogin());
        parameters.add(user.getPassword());
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            UserDao userDao = daoFactory.getUserDao();
            foundUsers = userDao.findByParameters(user, parameters, FIND_LOGIN_USER_KEY);
        } catch (JdbcDaoException e) {
            throw new JdbcDaoException(e);
        }
        return foundUsers.get(0);
    }

}