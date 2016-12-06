package com.epam.as.bookhotel.service;


import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User register(User user, HttpServletRequest request) throws PropertyManagerException, ConnectionPoolException, JdbcDaoException {
        User regUser;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            UserDao userDao = daoFactory.getUserDao();
            regUser = userDao.save(user);
        } catch (JdbcDaoException e) {
            throw new JdbcDaoException(e);
        }
        return regUser;
    }

    public User login(User user) throws JdbcDaoException, ConnectionPoolException, PropertyManagerException {
        User foundUser;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            UserDao userDao = daoFactory.getUserDao();
            foundUser = userDao.find(user);
        } catch (JdbcDaoException e) {
            throw new JdbcDaoException(e);
        }
        return foundUser;
    }

}