package com.epam.bookhotel.service;

import com.epam.bookhotel.dao.DaoFactory;
import com.epam.bookhotel.dao.OrderDao;
import com.epam.bookhotel.dao.RoomDao;
import com.epam.bookhotel.entity.Order;
import com.epam.bookhotel.entity.Room;
import com.epam.bookhotel.exception.DaoException;
import com.epam.bookhotel.exception.ServiceException;
import com.epam.bookhotel.exception.UnableConfirmOrderException;
import com.epam.bookhotel.exception.UnableUpdateFieldException;
import com.epam.bookhotel.util.DateConverter;

import java.util.List;

/**
 * Service serves operation with entity Order
 */
public class OrderService extends ParentService {

    private static final String ORDER_STATUS_UNCONFIRMED = "unconfirmed";
    private static final String FIND_ORDERS_BY_USER_ID = "find.orders.by.user.id";
    private static final String FIND_ALL_ORDERS_KEY = "find.all.orders";
    private static final String FIND_ALL_ORDERS_BY_STATUS_KEY = "find.all.orders.by.status";
    private static final String INSERT_ORDER_KEY = "insert.order";
    private static final String FIND_CONFIRMED_ORDERS_BY_USER_ID_KEY = "find.conf.orders.by.user.id";
    private static final String FIND_ROOM_BY_NUMBER_KEY = "find.room.by.number";
    private static final String FIND_ORDER_BY_ORDER_ID_KEY = "find.order.by.order.id";
    private static final String UPDATE_ORDER_NUMBER_AND_STATUS_KEY = "update.order.number.and.status";
    private static final String UPDATE_ORDER_FULL_COST_KEY = "update.order.full.cost";

    /**
     * Save new order
     *
     * @param order entity for saving
     * @return saved order with id
     * @throws ServiceException if any exception in service occurred.
     */
    public Order makeOrder(Order order) throws ServiceException {

        parameters.add(order.getUser().getId());
        parameters.add(order.getFirstName());
        parameters.add(order.getLastName());
        parameters.add(order.getEmail());
        parameters.add(order.getPhone());
        parameters.add(order.getBed().getBed());
        parameters.add(order.getRoomType().getRoomType());
        parameters.add(DateConverter.getDateToStr(order.getCheckIn()));
        parameters.add(DateConverter.getDateToStr(order.getCheckOut()));
        Order newOrder;
        try (DaoFactory daoFactory = DaoFactory.createJdbcDaoFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            newOrder = orderDao.save(order, parameters, INSERT_ORDER_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return newOrder;
    }

    /**
     * Search all order by user id
     *
     * @param order entity for searching
     * @return the list of found orders
     * @throws ServiceException if any exception in service occurred
     */
    public List<Order> findOrdersByUserId(Order order) throws ServiceException {

        parameters.add(order.getUser().getId());
        return getOrdersList(order, parameters, FIND_ORDERS_BY_USER_ID);
    }

    /**
     * Search all orders
     *
     * @param order entity for searching
     * @return the list of found orders
     * @throws ServiceException if any exception in service occurred
     */
    public List<Order> findAllOrders(Order order) throws ServiceException {

        return getOrdersList(order, parameters, FIND_ALL_ORDERS_KEY);

    }

    /**
     * Search all orders by status "unconfirmed"
     *
     * @param order entity for searching
     * @return the list of found orders
     * @throws ServiceException if any exception in service occurred
     */
    public List<Order> findAllOrdersByStatusUnconfirmed(Order order) throws ServiceException {

        parameters.add(order.getStatus().getStatus());
        return getOrdersList(order, parameters, FIND_ALL_ORDERS_BY_STATUS_KEY);
    }

    /**
     * Search all confirmed orders by user id
     *
     * @param order entity for searching
     * @return the list of found orders
     * @throws ServiceException if any exception in service occurred
     */
    public List<Order> findConfirmedOrdersByUserId(Order order) throws ServiceException {

        parameters.add(order.getUser().getId());
        parameters.add(order.getStatus().getStatus());
        return getOrdersList(order, parameters, FIND_CONFIRMED_ORDERS_BY_USER_ID_KEY);
    }

    /**
     * General method for searching orders
     *
     * @param order      entity for searching
     * @param parameters values for which will be search
     * @param key        property key for query
     * @return the list of found orders
     * @throws ServiceException if any exception in service occurred
     */
    private List<Order> getOrdersList(Order order, List<Object> parameters, String key) throws ServiceException {

        List<Order> orderList;
        try (DaoFactory daoFactory = DaoFactory.createJdbcDaoFactory()) {
            OrderDao orderDao = daoFactory.getOrderDao();
            orderList = orderDao.findAllByParameters(order, parameters, key, order.getUser().getLocale().getLocaleName());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orderList;
    }

    /**
     * Confirm user order by room number
     *
     * @param order entity for updating
     * @throws ServiceException if any exception in service occurred
     */
    public void confirmRoomForOrder(Order order) throws ServiceException {

        try (DaoFactory daoFactory = DaoFactory.createJdbcDaoFactory()) {
            Room room = order.getRoom();
            OrderDao orderDao = daoFactory.getOrderDao();
            RoomDao roomDao = daoFactory.getRoomDao();

            daoFactory.beginTx();

            //search room by its number
            parameters.add(room.getNumber());
            room = roomDao.findByParameters(room, parameters, FIND_ROOM_BY_NUMBER_KEY);

            if (room == null) throw new UnableConfirmOrderException();

            //search order by its id and check order status
            parameters.add(order.getId());
            order = orderDao.findByParameters(order, parameters, FIND_ORDER_BY_ORDER_ID_KEY);
            if (!ORDER_STATUS_UNCONFIRMED.equals(order.getStatus().getStatus()))
                throw new UnableConfirmOrderException();
            order.setRoom(room);

            //set order room number and status
            parameters.add(order.getRoom().getNumber());
            parameters.add(order.getId());
            orderDao.update(order, parameters, UPDATE_ORDER_NUMBER_AND_STATUS_KEY);

            //update order full cost for select room number on all booking days
            parameters.add(order.getCheckOut());
            parameters.add(order.getCheckIn());
            parameters.add(order.getRoom().getPrice());
            parameters.add(order.getId());
            orderDao.update(order, parameters, UPDATE_ORDER_FULL_COST_KEY);

            daoFactory.commit();

        } catch (UnableUpdateFieldException e) {

            throw new UnableConfirmOrderException(e);

        } catch (DaoException e) {

            throw new ServiceException(e);
        }
    }

}