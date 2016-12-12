package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.OrderDao;
import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private static final String FIND_ORDER_BY_USER_ID_KEY = "find.orders.by.user.id";
    private static final String FIND_ALL_ORDERS_KEY = "find.all.orders";
    private static final String FIND_ALL_ORDERS_BY_STATUS_KEY = "find.all.orders.by.status";
    private static final String ORDERS_STATUS_UNCONFIRMED = "unconfirmed";
    private static final String INSERT_ORDER_KEY = "insert.order";
    private List<String> parameters = new ArrayList<>();

    public Order makeOrder(Order order) throws ConnectionPoolException, PropertyManagerException, JdbcDaoException {
        Order newOrder;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            newOrder = orderDao.save(order, INSERT_ORDER_KEY);
        } catch (JdbcDaoException e) {
            throw new JdbcDaoException(e);
        }
        return newOrder;
    }

    public List<Order> findOrdersByUserId(Order order) throws ConnectionPoolException, JdbcDaoException, PropertyManagerException {
        List<Order> orderList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            parameters.add(String.valueOf(order.getUser().getId()));
            orderList = orderDao.findByParameters(order, parameters, FIND_ORDER_BY_USER_ID_KEY);
        }
        return orderList;
    }

    public List<Order> findAllOrders(Order order) throws ConnectionPoolException, JdbcDaoException, PropertyManagerException {
        List<Order> orderList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            orderList = orderDao.findByParameters(order, parameters, FIND_ALL_ORDERS_KEY);
        }
        return orderList;
    }

    public List<Order> findAllOrdersByStatus(Order order) throws ConnectionPoolException, JdbcDaoException, PropertyManagerException {
        List<Order> orderList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            parameters.add(ORDERS_STATUS_UNCONFIRMED);
            orderList = orderDao.findByParameters(order, parameters, FIND_ALL_ORDERS_BY_STATUS_KEY);
        }
        return orderList;
    }
}
