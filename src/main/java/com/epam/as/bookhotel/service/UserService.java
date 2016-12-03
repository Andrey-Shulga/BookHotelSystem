package com.epam.as.bookhotel.service;


import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.User;

import javax.servlet.http.HttpServletRequest;

public class UserService extends BaseService {

    public Boolean register(User user, HttpServletRequest request) throws ConnectionPoolException, ServiceException {

        try {
            try (DaoFactory daoFactory = DaoFactory.createFactory()) {
                daoFactory.beginTx();
                UserDao userDao = daoFactory.getUserDao();

            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }

        return null;
    }
}
