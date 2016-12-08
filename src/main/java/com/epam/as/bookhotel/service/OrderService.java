package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.OrderDao;
import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.Order;

import javax.servlet.http.HttpServletRequest;

public class OrderService {

    public Order makeOrder(Order order, HttpServletRequest req) throws ConnectionPoolException, PropertyManagerException, JdbcDaoException {
        Order newOrder;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            newOrder = orderDao.save(order);
        } catch (JdbcDaoException e) {
            throw new JdbcDaoException(e);
        }
        return newOrder;
    }

}
