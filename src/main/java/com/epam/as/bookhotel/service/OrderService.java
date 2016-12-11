package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.OrderDao;
import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

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
            orderList = orderDao.findAllById(order);
        }
        return orderList;
    }

    public List<Order> findAllOrders(Order order) throws ConnectionPoolException, JdbcDaoException, PropertyManagerException {
        List<Order> orderList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            orderList = orderDao.findAll(order);
        }
        return orderList;
    }

}
