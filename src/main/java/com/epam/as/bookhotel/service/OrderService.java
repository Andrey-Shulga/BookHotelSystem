package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.OrderDao;
import com.epam.as.bookhotel.exception.*;
import com.epam.as.bookhotel.model.Order;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderService extends ParentService {

    private static final String FIND_ORDER_BY_USER_ID_KEY = "find.orders.by.user.id";
    private static final String FIND_ALL_ORDERS_KEY = "find.all.orders";
    private static final String FIND_ALL_ORDERS_BY_STATUS_KEY = "find.all.orders.by.status";
    private static final String INSERT_ORDER_KEY = "insert.order";
    private static final String UPDATE_ORDER_ROOM_NUMBER_KEY = "update.order.room.number";
    private static final String UPDATE_ORDER_STATUS_KEY = "change.order.status";
    private static final String UPDATE_ORDER_FULL_COST_KEY = "update.order.full.cost";
    private static final String FIND_CONFIRMED_ORDERS_BY_USER_ID_KEY = "find.conf.orders.by.user.id";
    private static final List<String> parameters = new ArrayList<>();

    public Order makeOrder(Order order) throws ServiceException {

        parameters.add(order.getUser().getId().toString());
        parameters.add(order.getFirstName());
        parameters.add(order.getLastName());
        parameters.add(order.getEmail());
        parameters.add(order.getPhone());
        parameters.add(String.valueOf(order.getBed().getBed()));
        parameters.add(order.getRoomType().getRoomType());
        parameters.add(getDateToStr(order.getCheckIn()));
        parameters.add(getDateToStr(order.getCheckOut()));
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            orderDao.save(order, parameters, INSERT_ORDER_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return order;
    }

    private String getDateToStr(Date date) {
        final String DATE_PATTERN = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(DATE_PATTERN);
        return df.format(date);
    }

    public List<Order> findOrdersByUserId(Order order) throws ServiceException {

        parameters.add(String.valueOf(order.getUser().getId()));
        List<Order> orderList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
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

    public List<Order> findAllOrdersByStatusUnconfirmed(Order order) throws ServiceException {

        parameters.add(order.getStatus().getStatus());
        List<Order> orderList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            orderList = orderDao.findByParameters(order, parameters, FIND_ALL_ORDERS_BY_STATUS_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orderList;
    }

    public List<Order> findConfirmedOrdersByUserId(Order order) throws ServiceException {

        parameters.add(String.valueOf(order.getUser().getId()));
        parameters.add(order.getStatus().getStatus());
        List<Order> orderList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            orderList = orderDao.findByParameters(order, parameters, FIND_CONFIRMED_ORDERS_BY_USER_ID_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orderList;
    }

    public Order confirmRoomForOrder(Order order) throws ServiceException {

        parameters.add(String.valueOf(order.getRoom().getNumber()));
        parameters.add(String.valueOf(order.getId()));
        parameters.add(String.valueOf(order.getId()));
        parameters.add(String.valueOf(order.getRoom().getNumber()));
        parameters.add(String.valueOf(order.getRoom().getNumber()));
        parameters.add(String.valueOf(order.getId()));
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
