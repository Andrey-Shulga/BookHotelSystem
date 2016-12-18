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
    private static final String INSERT_ORDER_KEY = "insert.order";

    private static final String UPDATE_ORDER_ROOM_ID_KEY = "update.order.room.id";
    private static final String UPDATE_ORDER_STATUS_KEY = "change.order.status";
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
        List<List<Object>> resultList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            parameters.add(String.valueOf(order.getUser().getId()));
            resultList = orderDao.findByParameters(order, parameters, FIND_ORDER_BY_USER_ID_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        List<Order> orderList = new ArrayList<>();
        setRowsToOrder(order, resultList, orderList);
        return orderList;
    }

    private void assembleOrder(Order order, List<Object> rows, Order foundOrder) {
        foundOrder.setId((Integer) rows.get(0));
        foundOrder.setUser(order.getUser());
        foundOrder.setFirstName((String) rows.get(2));
        foundOrder.setLastName((String) rows.get(3));
        foundOrder.setEmail((String) rows.get(4));
        foundOrder.setPhone((String) rows.get(5));
        foundOrder.setBed(new Bed((Integer) rows.get(6)));
        foundOrder.setRoomType(new RoomType((String) rows.get(7)));
        Date checkInDate = (Date) rows.get(8);
        Date checkOutDate = (Date) rows.get(9);
        foundOrder.setCheckIn(checkInDate);
        foundOrder.setCheckOut(checkOutDate);
        foundOrder.setStatus(new OrderStatus((String) rows.get(10)));
    }

    public List<Order> findAllOrders(Order order) throws ServiceException {

        List<List<Object>> resultList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            resultList = orderDao.findByParameters(order, parameters, FIND_ALL_ORDERS_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        List<Order> orderList = new ArrayList<>();
        for (List<Object> rows : resultList) {
            Order foundOrder = new Order();
            assembleOrder(order, rows, foundOrder);
            Room room = new Room();
            if (rows.get(11) != null) {
                room.setId((Integer) rows.get(11));
                room.setNumber((Integer) rows.get(12));
                room.setPrice((BigDecimal) rows.get(13));
                foundOrder.setRoom(room);
                foundOrder.setFullCost((BigDecimal) rows.get(14));
            }
            orderList.add(foundOrder);
            logger.debug("Found entity: {}", foundOrder);
        }
        return orderList;
    }

    public List<Order> findAllOrdersByStatusUnconfirmed(Order order) throws ServiceException {

        List<List<Object>> resultList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            parameters.add(ORDERS_STATUS_UNCONFIRMED);
            resultList = orderDao.findByParameters(order, parameters, FIND_ALL_ORDERS_BY_STATUS_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        List<Order> orderList = new ArrayList<>();
        setRowsToOrder(order, resultList, orderList);
        return orderList;
    }

    private void setRowsToOrder(Order order, List<List<Object>> resultList, List<Order> orderList) {
        for (List<Object> rows : resultList) {
            Order foundOrder = new Order();
            assembleOrder(order, rows, foundOrder);
            orderList.add(foundOrder);
            logger.debug("Found entity: {}", foundOrder);
        }
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
