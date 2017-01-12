package com.epam.bookhotel.action;

import com.epam.bookhotel.entity.Order;
import com.epam.bookhotel.entity.User;
import com.epam.bookhotel.exception.ServiceException;
import com.epam.bookhotel.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.epam.bookhotel.constant.ConstantsHolder.*;

/**
 * Action show page with all orders for user with manager rights in application
 */

public class ShowAllOrdersManagerAction implements Action {

    private static final String ALL_ORDER_LIST = "manager_allorder_list";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {

        final User user = (User) req.getSession().getAttribute(USER);
        final Order order = new Order();
        order.setUser(user);
        OrderService orderService = new OrderService();
        try {
            final List<Order> orderList = orderService.findAllOrders(order);
            req.setAttribute(ORDERS, orderList);
        } catch (ServiceException e) {
            req.setAttribute(ORDERS + ERROR_MESSAGES_POSTFIX, e.getMessage());
        }

        return ALL_ORDER_LIST;
    }
}
