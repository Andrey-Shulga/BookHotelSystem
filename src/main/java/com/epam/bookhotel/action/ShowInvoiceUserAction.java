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
 * Action show page with user's confirmation orders with the amount to pay for them.
 */

public class ShowInvoiceUserAction implements Action {

    private static final String INVOICES_LIST = "user_invoice_list";
    private static final String ORDERS_STATUS_CONFIRMED = "confirmed";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {

        final User user = (User) req.getSession().getAttribute(USER);
        final Order order = new Order();
        order.setUser(user);
        order.setStatus(new OrderStatus(ORDERS_STATUS_CONFIRMED));
        OrderService orderService = new OrderService();
        try {
            final List<Order> orderList = orderService.findConfirmedOrdersByUserId(order);
            req.setAttribute(ORDERS, orderList);
        } catch (ServiceException e) {
            req.setAttribute(ORDERS + ERROR_MESSAGES_POSTFIX, e.getMessage());
        }

        return INVOICES_LIST;
    }
}
