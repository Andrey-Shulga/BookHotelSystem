package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.model.Bed;
import com.epam.as.bookhotel.model.Photo;
import com.epam.as.bookhotel.model.Room;
import com.epam.as.bookhotel.model.RoomType;
import com.epam.as.bookhotel.service.RoomService;
import com.epam.as.bookhotel.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Add new room to the list of hotel's rooms.
 */

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

        try {
            //validate form's fields
            FormValidator validator = new FormValidator();
            Map<String, List<String>> fieldErrors = validator.validate(ROOM_FORM_JSP, req);

            //check if form's dropdown list item not selected
            validator.checkDropDownListOnSelect(ROOM_BED_ATTR, req);
            validator.checkDropDownListOnSelect(ROOM_TYPE_ATTR, req);
            //check if chooses file has suitable content type
            validator.checkImageContentType(ROOM_PHOTO_ATTR, req);
            //check if size of chooses file not over the limit
            validator.checkFileMaxSize(ROOM_PHOTO_ATTR, req);
            //if errors found return to page again with output errors
            if (validator.hasFieldsErrors(req, fieldErrors)) return REDIRECT;
        } catch (ValidatorException e) {
            throw new ActionException(e);
        }
        //if form validation ok continue...
        logger.debug("Form's parameters are valid.");

        String roomNumber = req.getParameter(ROOM_NUMBER_ATTR);
        String roomBed = req.getParameter(ROOM_BED_ATTR);
        String roomType = req.getParameter(ROOM_TYPE_ATTR);
        String roomPrice = req.getParameter(ROOM_PRICE_ATTR);
        Double roomPriceDouble = Double.parseDouble(roomPrice);
        InputStream in = null;
        Room room;
        try {
            Part photoPart = req.getPart(ROOM_PHOTO_ATTR);
            //if choose photo for room assemble room with photo
            if (photoPart.getSize() != ZERO_SIZE) {
                in = photoPart.getInputStream();
                String contentType = photoPart.getContentType();
                Long contentLength = photoPart.getSize();
                room = new Room(new RoomType(roomType), new Bed(Integer.parseInt(roomBed)), Integer.parseInt(roomNumber),
                        new BigDecimal(roomPriceDouble), new Photo(in, contentType, contentLength));

            } else
                room = new Room(new RoomType(roomType), new Bed(Integer.parseInt(roomBed)), Integer.parseInt(roomNumber),
                        new BigDecimal(roomPriceDouble));
            RoomService roomService = new RoomService();
            try {
                roomService.addRoom(room);
            } catch (ServiceException e) {
                //set error to session for output on jsp
                req.getSession().setAttribute(ROOM_FORM_JSP + ERROR_MESSAGE_SUFFIX, e.getMessage());
                return REDIRECT;

            } finally {
                if (in != null) in.close();
            }

        } catch (IOException | ServletException e) {

            throw new ActionException(e);
        } finally {
            if (in != null) try {
                in.close();
            } catch (IOException e) {
                throw new ActionException(e);
            }
        }

        logger.debug("Add room action success.");
        return REDIRECT;
    }
}
