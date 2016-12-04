package com.epam.as.bookhotel.service;


import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.exception.*;
import com.epam.as.bookhotel.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class UserService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public Boolean register(User user, HttpServletRequest request) throws ConnectionPoolException, ServiceException, PropertyManagerException, JdbcDaoException, UserExistingException, DatabaseConnectionException {

        Boolean done;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            daoFactory.beginTx();
            UserDao userDao = daoFactory.getUserDao();
            userDao.save(user);
            daoFactory.commit();
            done = true;
        } catch (Exception e) {
            try {
                if (!daoFactory.getConnection().getAutoCommit()) daoFactory.rollback();
            } catch (SQLException e1) {
                throw new ConnectionPoolException(e);
            }
            throw new ServiceException(e);
        }
        return done;
    }
}
