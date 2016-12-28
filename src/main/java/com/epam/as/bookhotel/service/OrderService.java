package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.OrderDao;
import com.epam.as.bookhotel.exception.*;
import com.epam.as.bookhotel.model.Order;
import com.epam.as.bookhotel.util.DateConverter;

import java.util.ArrayList;
import java.util.List;

public class OrderService extends ParentService {

    private static final String FIND_ORDERS_BY_USER_ID = "find.orders.by.user.id";
    private static final String FIND_ALL_ORDERS_KEY = "find.all.orders";
    private static final String FIND_ALL_ORDERS_BY_STATUS_KEY = "find.all.orders.by.status";
    private static final String INSERT_ORDER_KEY = "insert.order";
    private static final String UPDATE_ORDER_ROOM_NUMBER_KEY = "update.order.room.number";
    private static final String FIND_CONFIRMED_ORDERS_BY_USER_ID_KEY = "find.conf.orders.by.user.id";
    private final List<Object> parameters = new ArrayList<>();

    public Order makeOrder(Order order) throws ServiceException {

        parameters.add(order.getUser().getId());
        parameters.add(order.getFirstName());
        parameters.add(order.getLastName());
        parameters.add(order.getEmail());
        parameters.add(order.getPhone());
        parameters.add(order.getBed().getBed());
        parameters.add(order.getRoomType().getRoomType());
        DateConverter converter = new DateConverter();
        parameters.add(converter.getDateToStr(order.getCheckIn()));
        parameters.add(converter.getDateToStr(order.getCheckOut()));
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            orderDao.save(order, parameters, INSERT_ORDER_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return order;
    }

    public List<Order> findOrdersByUserId(Order order) throws ServiceException {

        parameters.add(order.getUser().getId());
        List<Order> orderList;
        orderList = getOrdersList(order, parameters, FIND_ORDERS_BY_USER_ID);
        return orderList;
    }

    public List<Order> findAllOrders(Order order) throws ServiceException {

        return getOrdersList(order, parameters, FIND_ALL_ORDERS_KEY);

    }

    public List<Order> findAllOrdersByStatusUnconfirmed(Order order) throws ServiceException {

        parameters.add(order.getStatus().getStatus());
        return getOrdersList(order, parameters, FIND_ALL_ORDERS_BY_STATUS_KEY);
    }

    public List<Order> findConfirmedOrdersByUserId(Order order) throws ServiceException {

        parameters.add(order.getUser().getId());
        parameters.add(order.getStatus().getStatus());
        return getOrdersList(order, parameters, FIND_CONFIRMED_ORDERS_BY_USER_ID_KEY);
    }

    private List<Order> getOrdersList(Order order, List<Object> parameters, String key) throws ServiceException {

        List<Order> orderList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            orderList = orderDao.findByParameters(order, parameters, key, order.getUser().getLocale().getLocaleName());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orderList;
    }

    public Order confirmRoomForOrder(Order order) throws ServiceException {

        parameters.add(order.getRoom().getNumber());
        parameters.add(order.getId());
        parameters.add(order.getId());
        parameters.add(order.getRoom().getNumber());
        parameters.add(order.getRoom().getNumber());
        parameters.add(order.getId());
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            daoFactory.beginTx();
            orderDao.update(order, parameters, UPDATE_ORDER_ROOM_NUMBER_KEY);
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