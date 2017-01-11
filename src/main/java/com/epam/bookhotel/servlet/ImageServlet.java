package com.epam.bookhotel.servlet;

import com.epam.bookhotel.entity.Photo;
import com.epam.bookhotel.exception.ServiceException;
import com.epam.bookhotel.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Image servlet for serving images
 */
@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ImageServlet.class);
    private static final String ID_PREFIX_ATTR = "id";
    private static final String ZERO = "0";


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //check if request contains attribute not 0
        if (!ZERO.equals(req.getParameter(ID_PREFIX_ATTR))) {
            String id = req.getParameter(ID_PREFIX_ATTR);
            final Photo photo = new Photo(Integer.parseInt(id));
            ImageService service = new ImageService();
            try {
                Photo foundPhoto = service.findPhotoById(photo);
                if (foundPhoto.getImageStream() != null) {
                    resp.reset();
                    resp.setContentType(foundPhoto.getContentType());
                    resp.setContentLengthLong(foundPhoto.getContentLength());
                    writeImage(foundPhoto.getImageStream(), resp);
                }
            } catch (ServiceException e) {
                logger.error("Exception in ImageServlet occurred", e);
            }
        }
    }

    /**
     * Write image on jsp, using buffer
     *
     * @param in       input stream consist image
     * @param response the response for writing image
     * @throws IOException if I/O exception occurred
     */
    private void writeImage(InputStream in, HttpServletResponse response) throws IOException {

        final int bufferSize = 10240;
        final int ZERO = 0;
        byte[] buffer = new byte[bufferSize];
        try (InputStream input = in;
             ServletOutputStream output = response.getOutputStream()) {
            int length;
            while ((length = input.read(buffer)) > ZERO) {
                output.write(buffer, ZERO, length);
            }
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
