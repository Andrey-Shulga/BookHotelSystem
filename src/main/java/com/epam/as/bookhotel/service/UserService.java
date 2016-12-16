package com.epam.as.bookhotel.service;


import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.UserDao;
import com.epam.as.bookhotel.exception.*;
import com.epam.as.bookhotel.model.User;
import com.epam.as.bookhotel.model.UserRole;
import com.epam.as.bookhotel.model.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserService extends ParentService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final String REGISTER_USER_KEY = "insert.user";
    private static final String FIND_LOGIN_USER_KEY = "find.user.login";
    private static final int FIRST_USER_IN_LIST_INDEX = 0;

    private List<String> parameters = new ArrayList<>();

    public User register(User user) throws ServiceException {

        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            UserDao userDao = daoFactory.getUserDao();
            userDao.save(user, REGISTER_USER_KEY);
        } catch (NonUniqueFieldException e) {

            throw new UserExistException(e);

        } catch (DaoException e) {

            throw new ServiceException(e);
        }
        return user;
    }

    public User login(User user) throws ServiceException {

        List<List<Object>> resultList;
        parameters.add(user.getLogin());
        parameters.add(user.getPassword());
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            UserDao userDao = daoFactory.getUserDao();
            resultList = userDao.findByParameters(user, parameters, FIND_LOGIN_USER_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        for (List<Object> rows : resultList) {
            user.setId((Integer) rows.get(0));
            user.setLogin((String) rows.get(1));
            user.setPassword((String) rows.get(2));
            UserRole userRole = new UserRole(UserType.valueOf((String) rows.get(3)));
            user.setRole(userRole);
            logger.debug("Search success. Entity {} found in database.", user);
        }
        if (user.getId() == null) throw new UserNotFoundException();
        return user;
    }

}