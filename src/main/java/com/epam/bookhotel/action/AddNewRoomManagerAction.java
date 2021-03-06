package com.epam.bookhotel.action;

import com.epam.bookhotel.entity.Bed;
import com.epam.bookhotel.entity.Photo;
import com.epam.bookhotel.entity.Room;
import com.epam.bookhotel.entity.RoomType;
import com.epam.bookhotel.exception.ActionException;
import com.epam.bookhotel.exception.ServiceException;
import com.epam.bookhotel.exception.ValidatorException;
import com.epam.bookhotel.service.RoomService;
import com.epam.bookhotel.util.SessionHelper;
import com.epam.bookhotel.validator.FormValidator;
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

import static com.epam.bookhotel.constant.ConstantsHolder.*;

/**
 * Add new room to the list of hotel's rooms.
 */

public class AddNewRoomManagerAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(AddNewRoomManagerAction.class);
    private static final String MANAGER_ROOM_LIST = "manager_room_list";
    private static final String REDIRECT_ROOM_LIST = "redirect:/do/?action=show-manager-room-list";
    private static final String ROOM_NUMBER = "roomNumber";
    private static final String ROOM_PRICE = "roomPrice";
    private static final String ROOM_PHOTO = "photo";
    private static final int ZERO_FILE_SIZE = 0;
    private InputStream in;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        try {
            if (hasFieldsError(req)) return REDIRECT_ROOM_LIST;

        } catch (ValidatorException e) {
            throw new ActionException(e);
        }
        //if form validation ok continue...
        logger.debug("Form's parameters are valid.");
        Room newRoom;
        try {
            final Room room = getRoom(req);
            RoomService roomService = new RoomService();
            try {
                newRoom = roomService.addRoom(room);
            } catch (ServiceException e) {
                //set error to session for output on jsp
                req.getSession().setAttribute(MANAGER_ROOM_LIST + ERROR_MESSAGES_POSTFIX, e.getMessage());
                return REDIRECT_ROOM_LIST;
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

        logger.debug("Add room action success. {}", newRoom);
        return REDIRECT_ROOM_LIST;
    }

    private boolean hasFieldsError(HttpServletRequest req) throws ValidatorException {

        //validate form's fields
        FormValidator validator = new FormValidator();
        Map<String, List<String>> fieldErrors = validator.validate(MANAGER_ROOM_LIST, req);

        //check if form's dropdown list item not selected
        validator.checkDropDownListOnSelect(ROOM_BED, req);
        validator.checkDropDownListOnSelect(ROOM_TYPE, req);
        //check if chooses file has suitable content type
        validator.checkImageContentType(ROOM_PHOTO, req);
        //check if size of chooses file not over the limit
        validator.checkFileMaxSize(ROOM_PHOTO, req);
        //if errors found return to page again with output errors
        return validator.hasFieldsErrors(req, fieldErrors);
    }

    private Room getRoom(HttpServletRequest req) throws IOException, ServletException {

        String roomNumber = req.getParameter(ROOM_NUMBER);
        SessionHelper.saveParamToSession(req, ROOM_NUMBER, roomNumber);
        String roomBed = req.getParameter(ROOM_BED);
        String roomType = req.getParameter(ROOM_TYPE);
        String roomPrice = req.getParameter(ROOM_PRICE);
        SessionHelper.saveParamToSession(req, ROOM_PRICE, roomPrice);
        Double roomPriceDouble = Double.parseDouble(roomPrice);

        Room room;
        Part photoPart = req.getPart(ROOM_PHOTO);
        //if choose photo for room assemble room with photo
        if (photoPart.getSize() != ZERO_FILE_SIZE) {
            in = photoPart.getInputStream();
            String contentType = photoPart.getContentType();
            Long contentLength = photoPart.getSize();
            room = new Room(new RoomType(roomType), new Bed(Integer.parseInt(roomBed)), Integer.parseInt(roomNumber),
                    new BigDecimal(roomPriceDouble), new Photo(in, contentType, contentLength));

        } else
            room = new Room(new RoomType(roomType), new Bed(Integer.parseInt(roomBed)), Integer.parseInt(roomNumber),
                    new BigDecimal(roomPriceDouble));
        return room;
    }

}
