package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.OrderDao;
import com.epam.as.bookhotel.exception.*;
import com.epam.as.bookhotel.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OrderService extends ParentService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private static final String FIND_ORDER_BY_USER_ID_KEY = "find.orders.by.user.id";
    private static final String FIND_ALL_ORDERS_KEY = "find.all.orders";
    private static final String FIND_ALL_ORDERS_BY_STATUS_KEY = "find.all.orders.by.status";
    private static final String ORDERS_STATUS_UNCONFIRMED = "unconfirmed";
    private static final String ORDERS_STATUS_CONFIRMED = "confirmed";
    private static final String INSERT_ORDER_KEY = "insert.order";

    private static final String UPDATE_ORDER_ROOM_ID_KEY = "update.order.room.id";
    private static final String UPDATE_ORDER_STATUS_KEY = "change.order.status";
    private static final String UPDATE_ORDER_FULL_COST_KEY = "update.order.full.cost";
    private static final String FIND_CONFIRMED_ORDERS_BY_USER_ID_KEY = "find.conf.orders.by.user.id";
    private static final int INDEX_0 = 0;
    private static final int INDEX_2 = 2;
    private static final int INDEX_3 = 3;
    private static final int INDEX_4 = 4;
    private static final int INDEX_5 = 5;
    private static final int INDEX_6 = 6;
    private static final int INDEX_7 = 7;
    private static final int INDEX_8 = 8;
    private static final int INDEX_9 = 9;
    private static final int INDEX_10 = 10;
    private static final int INDEX_11 = 11;
    private static final int INDEX_12 = 12;
    private static final int INDEX_13 = 13;
    private static final int INDEX_14 = 14;
    private static final List<String> parameters = new ArrayList<>();

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

        parameters.add(String.valueOf(order.getUser().getId()));
        return getOrdersByParameter(order, parameters, FIND_ORDER_BY_USER_ID_KEY);
    }

    public List<Order> findConfirmedOrdersByUserId(Order order) throws ServiceException {

        parameters.add(String.valueOf(order.getUser().getId()));
        parameters.add(ORDERS_STATUS_CONFIRMED);
        return getOrdersByParameter(order, parameters, FIND_CONFIRMED_ORDERS_BY_USER_ID_KEY);
    }

    public List<Order> findAllOrders(Order order) throws ServiceException {

        return getOrdersByParameter(order, parameters, FIND_ALL_ORDERS_KEY);
    }

    public List<Order> findAllOrdersByStatusUnconfirmed(Order order) throws ServiceException {

        parameters.add(ORDERS_STATUS_UNCONFIRMED);
        return getOrdersByParameter(order, parameters, FIND_ALL_ORDERS_BY_STATUS_KEY);
    }

    private List<Order> getOrdersByParameter(Order order, List<String> parameters, String parameter) throws ServiceException {

        List<List<Object>> resultList;
        List<Order> orderList = new ArrayList<>();
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            resultList = orderDao.findByParameters(order, parameters, parameter);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        for (List<Object> rows : resultList) {
            Order foundOrder = new Order();
            setRowsToOrder(order, rows, foundOrder);
            if (rows.size() > INDEX_11 && rows.get(INDEX_11) != null) {
                Room room = new Room();
                room.setId((Integer) rows.get(INDEX_11));
                room.setNumber((Integer) rows.get(INDEX_12));
                room.setPrice((BigDecimal) rows.get(INDEX_13));
                foundOrder.setRoom(room);
                foundOrder.setFullCost((BigDecimal) rows.get(INDEX_14));
            }
            orderList.add(foundOrder);
            logger.debug("Found entity: {}", foundOrder);
        }
        return orderList;
    }


    private void setRowsToOrder(Order order, List<Object> rows, Order foundOrder) {

        foundOrder.setId((Integer) rows.get(INDEX_0));
        foundOrder.setUser(order.getUser());
        foundOrder.setFirstName((String) rows.get(INDEX_2));
        foundOrder.setLastName((String) rows.get(INDEX_3));
        foundOrder.setEmail((String) rows.get(INDEX_4));
        foundOrder.setPhone((String) rows.get(INDEX_5));
        foundOrder.setBed(new Bed((Integer) rows.get(INDEX_6)));
        foundOrder.setRoomType(new RoomType((String) rows.get(INDEX_7)));
        Date checkInDate = (Date) rows.get(INDEX_8);
        Date checkOutDate = (Date) rows.get(INDEX_9);
        foundOrder.setCheckIn(checkInDate);
        foundOrder.setCheckOut(checkOutDate);
        foundOrder.setStatus(new OrderStatus((String) rows.get(INDEX_10)));

    }

    public Order confirmRoomForOrder(Order order) throws ServiceException {

        try (DaoFactory daoFactory = DaoFactory.createFactory()) {

            daoFactory.beginTx();

            OrderDao orderDao = daoFactory.getOrderDao();
            parameters.add(String.valueOf(order.getRoom().getId()));
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
