package com.epam.bookhotel.action;

import com.epam.bookhotel.entity.Order;
import com.epam.bookhotel.entity.User;
import com.epam.bookhotel.exception.ServiceException;
import com.epam.bookhotel.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action show page for current authorized user with its orders.
 */

public class ShowOrdersUserAction implements Action {

    private static final String USER = "user";
    private static final String ORDER_LIST_ATTRIBUTE = "orders";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String USER_ORDER_LIST = "user_order_list";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {

        boolean isCreated = false;
        final User user = (User) req.getSession(isCreated).getAttribute(USER);
        final Order order = new Order();
        order.setUser(user);
        OrderService orderService = new OrderService();
        try {
            final List<Order> orderList = orderService.findOrdersByUserId(order);
            req.setAttribute(ORDER_LIST_ATTRIBUTE, orderList);
        } catch (ServiceException e) {
            req.setAttribute(ORDER_LIST_ATTRIBUTE + ERROR_MESSAGE_SUFFIX, e.getMessage());
        }

        return USER_ORDER_LIST;
    }
}
