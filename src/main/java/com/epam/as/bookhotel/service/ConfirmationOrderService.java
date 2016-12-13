package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.ConfirmationOrderDao;
import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.OrderDao;
import com.epam.as.bookhotel.dao.RoomDao;
import com.epam.as.bookhotel.dao.jdbc.JdbcDaoFactory;
import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.ConfirmationOrder;

import java.sql.SQLException;


public class ConfirmationOrderService extends ParentService {

    private static final String INSERT_CONF_ORDER_KEY = "insert.conf.order";
    private static final String UPDATE_ORDER_STATUS_KEY = "change.order.status";
    private static final String UPDATE_ROOM_STATUS_KEY = "change.room.status";

    public ConfirmationOrder confirmOrder(ConfirmationOrder confOrder) throws ConnectionPoolException, JdbcDaoException, PropertyManagerException {

        try (DaoFactory daoFactory = JdbcDaoFactory.createFactory()) {
            daoFactory.beginTx();
            ConfirmationOrderDao confOrderDao = daoFactory.getConfirmationOrderDao();
            confOrderDao.save(confOrder, INSERT_CONF_ORDER_KEY);
            OrderDao orderDao = daoFactory.getOrderDao();
            orderDao.save(confOrder.getOrder(), UPDATE_ORDER_STATUS_KEY);
            RoomDao roomDao = daoFactory.getRoomDao();
            roomDao.save(confOrder.getRoom(), UPDATE_ROOM_STATUS_KEY);
            daoFactory.commit();
        } catch (SQLException e) {
            try {
                daoFactory.rollback();
            } catch (SQLException ex) {
                throw new JdbcDaoException(ex);
            }
            throw new JdbcDaoException(e);
        }
        return confOrder;
    }
}
