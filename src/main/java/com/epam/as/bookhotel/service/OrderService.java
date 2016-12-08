package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.OrderDao;
import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.Order;

import java.util.List;

public class OrderService {

    public Order makeOrder(Order order) throws ConnectionPoolException, PropertyManagerException, JdbcDaoException {
        Order newOrder;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            newOrder = orderDao.save(order);
        } catch (JdbcDaoException e) {
            throw new JdbcDaoException(e);
        }
        return newOrder;
    }

    public List<Order> findOrdersByUserId(Order order) throws ConnectionPoolException, JdbcDaoException, PropertyManagerException {
        List<Order> orderList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            orderList = orderDao.findAllById(order, order.getUserId());
        }
        return orderList;
    }

}
