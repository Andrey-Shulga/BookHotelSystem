package com.epam.as.bookhotel.service;


import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.exception.*;
import com.epam.as.bookhotel.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class UserService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User register(User user, HttpServletRequest request) throws ConnectionPoolException, ServiceException, PropertyManagerException, JdbcDaoException, UserExistingException, DatabaseConnectionException {

        DaoFactory daoFactory = DaoFactory.createFactory();
        UserDao userDao = daoFactory.getUserDao();
        try {
            userDao.save(user);
        } finally {
            daoFactory.returnConnectionToPool();
        }
        return user;
    }

}

