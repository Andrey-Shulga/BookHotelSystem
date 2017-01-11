package com.epam.bookhotel.action;

import com.epam.bookhotel.entity.Order;
import com.epam.bookhotel.entity.OrderStatus;
import com.epam.bookhotel.entity.User;
import com.epam.bookhotel.exception.ActionException;
import com.epam.bookhotel.exception.ServiceException;
import com.epam.bookhotel.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action show page with user's confirmation orders with the amount to pay for them.
 */

public class ShowInvoiceUserAction implements Action {

    private static final String USER_INVOICE_LIST = "user_invoice_list";
    private static final String USER = "user";
    private static final String ORDER_LIST_ATTRIBUTE = "orders";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String ORDERS_STATUS_CONFIRMED = "confirmed";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        boolean isCreated = false;
        final User user = (User) req.getSession(isCreated).getAttribute(USER);
        final Order order = new Order();
        order.setUser(user);
        order.setStatus(new OrderStatus(ORDERS_STATUS_CONFIRMED));
        OrderService orderService = new OrderService();
        try {
            final List<Order> orderList = orderService.findConfirmedOrdersByUserId(order);
            req.setAttribute(ORDER_LIST_ATTRIBUTE, orderList);
        } catch (ServiceException e) {
            req.setAttribute(ORDER_LIST_ATTRIBUTE + ERROR_MESSAGE_SUFFIX, e.getMessage());
        }

        return USER_INVOICE_LIST;
    }
}