package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.Bed;
import com.epam.as.bookhotel.model.Room;
import com.epam.as.bookhotel.model.RoomType;
import com.epam.as.bookhotel.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;

public class AddNewRoomManagerAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(AddNewRoomManagerAction.class);
    private static final String ROOM_FORM_JSP = "manager_room_list";
    private static final String REDIRECT = "redirect:/do/?action=show-manager-room-list";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String ROOM_BED_ATTR = "bed";
    private static final String ROOM_TYPE_ATTR = "roomType";
    private static final String ROOM_NUMBER_ATTR = "roomNumber";
    private static final String ROOM_PRICE_ATTR = "roomPrice";
    private static final String ROOM_PHOTO_ATTR = "photo";
    private static final int ZERO_SIZE = 0;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        OrderRoomAction action = new OrderRoomAction();
        if (action.checkForm(req, ROOM_FORM_JSP)) return REDIRECT;

        logger.debug("Form's parameters are valid.");

        String roomNumber = req.getParameter(ROOM_NUMBER_ATTR);
        String roomBed = req.getParameter(ROOM_BED_ATTR);
        String roomType = req.getParameter(ROOM_TYPE_ATTR);
        String roomPrice = req.getParameter(ROOM_PRICE_ATTR);
        Double roomPriceDouble = Double.parseDouble(roomPrice);
        Room room;
        try {
            Part photoPart = req.getPart(ROOM_PHOTO_ATTR);
            if (photoPart.getSize() == ZERO_SIZE) {
                photoPart = req.getPart(ROOM_PHOTO_ATTR);
                room = new Room(new RoomType(roomType), new Bed(Integer.parseInt(roomBed)), Integer.parseInt(roomNumber),
                        new BigDecimal(roomPriceDouble), photoPart);
            } else
                room = new Room(new RoomType(roomType), new Bed(Integer.parseInt(roomBed)), Integer.parseInt(roomNumber),
                        new BigDecimal(roomPriceDouble));
        } catch (IOException | ServletException e) {
            throw new ActionException(e);
        }

        RoomService service = new RoomService();
        try {
            service.addRoom(room);
        } catch (ServiceException e) {
            req.getSession().setAttribute(ROOM_FORM_JSP + ERROR_MESSAGE_SUFFIX, e.getMessage());
            return REDIRECT;
        }

        logger.debug("Add room action success.");
        return REDIRECT;
    }
}
