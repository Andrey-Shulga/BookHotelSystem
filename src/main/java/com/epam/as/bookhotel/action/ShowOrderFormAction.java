package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.Bed;
import com.epam.as.bookhotel.model.RoomType;
import com.epam.as.bookhotel.model.User;
import com.epam.as.bookhotel.service.BedService;
import com.epam.as.bookhotel.service.RoomTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action show page with form for the order
 */

public class ShowOrderFormAction implements Action {

    private static final String BOOK_ORDER_JSP = "order_form";
    private static final String BED_LIST_ATTRIBUTE = "bedList";
    private static final String ROOM_TYPE_LIST_ATTRIBUTE = "roomTypeList";
    private static final String USER_ATTR_NAME = "user";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        User user = (User) req.getSession().getAttribute(USER_ATTR_NAME);

        BedService service = new BedService();
        try {
            List<Bed> bedList = service.findAllBeds(new Bed());
            req.setAttribute(BED_LIST_ATTRIBUTE, bedList);
        } catch (ServiceException e) {
            throw new ActionException(e);
        }

        RoomTypeService roomTypeService = new RoomTypeService();
        try {
            List<RoomType> roomTypeList = roomTypeService.findAllRoomTypes(new RoomType(), user.getLocale().getLocaleName());
            req.setAttribute(ROOM_TYPE_LIST_ATTRIBUTE, roomTypeList);
        } catch (ServiceException e) {
            throw new ActionException(e);
        }

        return BOOK_ORDER_JSP;
    }
}
