package com.epam.as.bookhotel.service;


import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class UserService extends BaseService {

    public Boolean register(User user, HttpServletRequest request) throws ConnectionPoolException, ServiceException, PropertyManagerException, JdbcDaoException {

        Boolean done = false;
        try {
            try (DaoFactory daoFactory = DaoFactory.createFactory()) {
                daoFactory.beginTx();
                UserDao userDao = daoFactory.getUserDao();
                userDao.save(user);
                daoFactory.commit();
                done = true;
            }
        } catch (Exception e) {
            try {
                if (!done) daoFactory.rollback();

            } catch (SQLException e1) {
                throw new ConnectionPoolException(e);
            }
            throw new ServiceException(e);
        }
        return done;
    }
}
