package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.ConfirmationOrderDao;
import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.OrderDao;
import com.epam.as.bookhotel.dao.RoomDao;
import com.epam.as.bookhotel.dao.jdbc.JdbcDaoFactory;
import com.epam.as.bookhotel.exception.DaoException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.ConfirmationOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfirmationOrderService extends ParentService {

    private static final Logger logger = LoggerFactory.getLogger(ConfirmationOrderService.class);
    private static final String INSERT_CONF_ORDER_KEY = "insert.conf.order";
    private static final String UPDATE_ORDER_STATUS_KEY = "change.order.status";
    private static final String UPDATE_ROOM_STATUS_KEY = "change.room.status";

    public ConfirmationOrder confirmOrder(ConfirmationOrder confOrder) throws ServiceException {

        try (DaoFactory daoFactory = JdbcDaoFactory.createFactory()) {
            daoFactory.beginTx();
            ConfirmationOrderDao confOrderDao = daoFactory.getConfirmationOrderDao();
            confOrderDao.save(confOrder, INSERT_CONF_ORDER_KEY);
            OrderDao orderDao = daoFactory.getOrderDao();
            if (confOrder.getId() == 0) {
                daoFactory.rollback();
            }
            orderDao.save(confOrder.getOrder(), UPDATE_ORDER_STATUS_KEY);
            RoomDao roomDao = daoFactory.getRoomDao();
            roomDao.save(confOrder.getRoom(), UPDATE_ROOM_STATUS_KEY);
            daoFactory.commit();
        } catch (DaoException e) {

            try {
                daoFactory.rollback();
            } catch (JdbcDaoException ex) {
                throw new ServiceException(ex);
            }

            throw new ServiceException(e);
        }

        return confOrder;
    }
}
