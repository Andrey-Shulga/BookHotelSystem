package com.epam.bookhotel.action;

import com.epam.bookhotel.entity.Bed;
import com.epam.bookhotel.entity.RoomType;
import com.epam.bookhotel.entity.User;
import com.epam.bookhotel.exception.ActionException;
import com.epam.bookhotel.exception.ServiceException;
import com.epam.bookhotel.service.BedService;
import com.epam.bookhotel.service.RoomTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.epam.bookhotel.constant.Constants.USER;

/**
 * Action show page with form for the order
 */

public class ShowOrderFormAction implements Action {

    private static final String BOOK_ORDER_FORM = "order_form";
    private static final String BED_LIST = "bedList";
    private static final String ROOM_TYPE_LIST = "roomTypeList";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        final User user = (User) req.getSession().getAttribute(USER);

        BedService service = new BedService();
        try {
            final List<Bed> bedList = service.findAllBeds(new Bed());
            req.getSession().setAttribute(BED_LIST, bedList);
        } catch (ServiceException e) {
            return BOOK_ORDER_FORM;

        }

        RoomTypeService roomTypeService = new RoomTypeService();
        try {
            final List<RoomType> roomTypeList = roomTypeService.findAllRoomTypes(new RoomType(), user.getLocale().getLocaleName());
            req.getSession().setAttribute(ROOM_TYPE_LIST, roomTypeList);
        } catch (ServiceException e) {
            return BOOK_ORDER_FORM;
        }

        return BOOK_ORDER_FORM;
    }
}
