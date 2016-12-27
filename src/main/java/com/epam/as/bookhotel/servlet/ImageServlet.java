package com.epam.as.bookhotel.servlet;

import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.Photo;
import com.epam.as.bookhotel.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ImageServlet.class);
    private static final String ID_PREFIX_ATTR = "id";
    private static final String ZERO = "0";


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            String id = req.getParameter(ID_PREFIX_ATTR);
            logger.debug("id = {}", id);
            Photo photo = new Photo(Integer.parseInt(id));
            ImageService service = new ImageService();
            try {
                service.findPhotoById(photo);
                if (photo.getImageStream() != null) {
                    boolean readAll = true;
                    byte[] bytes = IOUtils.readFully(photo.getImageStream(), Integer.MAX_VALUE, readAll);
                    photo.getImageStream().close();
                    resp.reset();
                    resp.setContentType("image/jpeg");
                    resp.setContentLength(bytes.length);
                    resp.getOutputStream().write(bytes);
                }

            } catch (ServiceException e) {
                logger.error("Exception in ImageServlet occurred", e);
            }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

}
