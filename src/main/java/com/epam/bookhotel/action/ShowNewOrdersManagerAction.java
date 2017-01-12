package com.epam.bookhotel.action;

import com.epam.bookhotel.entity.Order;
import com.epam.bookhotel.entity.OrderStatus;
import com.epam.bookhotel.entity.User;
import com.epam.bookhotel.exception.ServiceException;
import com.epam.bookhotel.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.epam.bookhotel.constant.ConstantsHolder.*;

/**
 * Action show page with new unconfirmed orders for user with manager rights.
 */

public class ShowNewOrdersManagerAction implements Action {

    private static final String MANAGER_ORDER_LIST = "manager_order_list";
    private static final String ORDERS_STATUS_UNCONFIRMED = "unconfirmed";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {

        final User user = (User) req.getSession().getAttribute(USER);
        final Order order = new Order();
        order.setUser(user);
        order.setStatus(new OrderStatus(ORDERS_STATUS_UNCONFIRMED));
        OrderService orderService = new OrderService();
        try {
            final List<Order> orderList = orderService.findAllOrdersByStatusUnconfirmed(order);
            req.setAttribute(ORDERS, orderList);
        } catch (ServiceException e) {
            req.setAttribute(ORDERS + ERROR_MESSAGES_POSTFIX, e.getMessage());
        }

        return MANAGER_ORDER_LIST;
    }
}
