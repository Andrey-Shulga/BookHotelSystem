package com.epam.as.bookhotel.dao;


import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.User;

public interface UserDao extends Dao<User> {
    void findSetUserRole(User entity) throws PropertyManagerException, JdbcDaoException;
}
