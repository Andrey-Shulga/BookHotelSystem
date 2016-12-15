package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.OrderDao;
import com.epam.as.bookhotel.dao.RoomDao;
import com.epam.as.bookhotel.dao.jdbc.JdbcDaoFactory;
import com.epam.as.bookhotel.exception.*;
import com.epam.as.bookhotel.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderService extends ParentService {

    private static final String FIND_ORDER_BY_USER_ID_KEY = "find.orders.by.user.id";
    private static final String FIND_ALL_ORDERS_KEY = "find.all.orders";
    private static final String FIND_ALL_ORDERS_BY_STATUS_KEY = "find.all.orders.by.status";
    private static final String ORDERS_STATUS_UNCONFIRMED = "unconfirmed";
    private static final String INSERT_ORDER_KEY = "insert.order";

    private static final String UPDATE_ORDER_ROOM_ID_KEY = "update.order.room.id";
    private static final String UPDATE_ORDER_STATUS_KEY = "change.order.status";
    private static final String UPDATE_ROOM_STATUS_KEY = "change.room.status";
    private static final String UPDATE_ORDER_FULL_COST_KEY = "update.order.full.cost";
    private static final String FIND_CONFIRMED_ORDERS_BY_USER_ID_KEY = "find.conf.orders.by.user.id";
    private List<String> parameters = new ArrayList<>();

    public Order makeOrder(Order order) throws ServiceException {
        Order newOrder;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            newOrder = orderDao.save(order, INSERT_ORDER_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return newOrder;
    }

    public List<Order> findOrdersByUserId(Order order) throws ServiceException {
        List<Order> orderList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            parameters.add(String.valueOf(order.getUser().getId()));
            orderList = orderDao.findByParameters(order, parameters, FIND_ORDER_BY_USER_ID_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orderList;
    }

    public List<Order> findAllOrders(Order order) throws ServiceException {
        List<Order> orderList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            orderList = orderDao.findByParameters(order, parameters, FIND_ALL_ORDERS_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orderList;
    }

    public List<Order> findAllOrdersByStatus(Order order) throws ServiceException {
        List<Order> orderList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            parameters.add(ORDERS_STATUS_UNCONFIRMED);
            orderList = orderDao.findByParameters(order, parameters, FIND_ALL_ORDERS_BY_STATUS_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orderList;
    }

    public Order confirmRoomForOrder(Order order) throws ServiceException {

        try (DaoFactory daoFactory = JdbcDaoFactory.createFactory()) {
            daoFactory.beginTx();

            OrderDao orderDao = daoFactory.getOrderDao();
            parameters.add(String.valueOf(order.getRoom().getId()));
            parameters.add(String.valueOf(order.getId()));
            orderDao.update(order, parameters, UPDATE_ORDER_ROOM_ID_KEY);

            parameters.add(String.valueOf(order.getId()));
            orderDao.update(order, parameters, UPDATE_ORDER_STATUS_KEY);

            parameters.add(String.valueOf(order.getId()));
            parameters.add(String.valueOf(order.getId()));
            parameters.add(String.valueOf(order.getRoom().getId()));
            parameters.add(String.valueOf(order.getId()));
            orderDao.update(order, parameters, UPDATE_ORDER_FULL_COST_KEY);

            RoomDao roomDao = daoFactory.getRoomDao();
            parameters.add(String.valueOf(order.getRoom().getId()));
            roomDao.update(order.getRoom(), parameters, UPDATE_ROOM_STATUS_KEY);

            daoFactory.commit();
        } catch (UnableUpdateFieldException e) {

            throw new UnableConfirmOrderException(e);

        } catch (DaoException e) {

            try {
                daoFactory.rollback();
            } catch (JdbcDaoException ex) {
                throw new ServiceException(ex);
            }
            throw new ServiceException(e);
        }
        return order;
    }
}
