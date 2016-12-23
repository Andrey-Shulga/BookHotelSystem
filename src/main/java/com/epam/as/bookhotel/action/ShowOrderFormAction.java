package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.Bed;
import com.epam.as.bookhotel.service.BedService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action show page with form for the order
 */

public class ShowOrderFormAction implements Action {

    private static final String BOOK_ORDER_JSP = "order_form";
    private static final String BED_LIST_ATTRIBUTE = "bedList";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        BedService service = new BedService();
        try {
            List<Bed> bedList = service.findAllBeds(new Bed());
            req.setAttribute(BED_LIST_ATTRIBUTE, bedList);
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
        return BOOK_ORDER_JSP;
    }
}
