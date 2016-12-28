package com.epam.as.bookhotel.servlet;

import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.Photo;
import com.epam.as.bookhotel.service.ImageService;
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

@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ImageServlet.class);
    private static final String ID_PREFIX_ATTR = "id";
    private static final String ZERO = "0";


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!ZERO.equals(req.getParameter(ID_PREFIX_ATTR))) {
            String id = req.getParameter(ID_PREFIX_ATTR);
            Photo photo = new Photo(Integer.parseInt(id));
            ImageService service = new ImageService();
            try {
                service.findPhotoById(photo);
                if (photo.getImageStream() != null) {
                    resp.reset();
                    resp.setContentType(photo.getContentType());
                    resp.setContentLength((int) photo.getContentLength());
                    writeImage(photo.getImageStream(), resp);
                }
            } catch (ServiceException e) {
                logger.error("Exception in ImageServlet occurred", e);
            }
        }
    }

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
