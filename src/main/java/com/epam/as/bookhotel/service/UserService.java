package com.epam.as.bookhotel.service;


import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.model.User;

import javax.servlet.http.HttpServletRequest;

public class UserService extends BaseService {

    public Boolean register(User user, HttpServletRequest request) throws ConnectionPoolException {

        daoFactory = DaoFactory.createFactory();

        return null;
    }
}
