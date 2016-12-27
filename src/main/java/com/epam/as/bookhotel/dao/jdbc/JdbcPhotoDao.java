package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.PhotoDao;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.Photo;
import com.epam.as.bookhotel.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

class JdbcPhotoDao extends JdbcDao<Photo> implements PhotoDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcPhotoDao.class);
    private static final String QUERY_PROPERTY_FILE = getQueryPropertyFile();
    private static final String INSERT_PHOTO_KEY = "insert.photo";
    private static final int INDEX_1 = 1;
    private Connection connection;

    JdbcPhotoDao(Connection connection) {

        super(connection);
        this.connection = connection;
    }

    @Override
    Photo setRsToField(ResultSet rs, Photo entity) throws SQLException {

        Photo newPhoto = new Photo();
        newPhoto.setId(rs.getInt(INDEX_1));
        logger.debug("Found entity: {}", newPhoto);
        return newPhoto;
    }

    @Override
    public Photo addPhoto(Photo photo) throws JdbcDaoException {

        try {
            PropertyManager pm = new PropertyManager(QUERY_PROPERTY_FILE);
            String query = pm.getPropertyKey(INSERT_PHOTO_KEY);
            if (photo.getId() == null) {
                logger.debug("{} trying to INSERT entity \"{}\" to database...", this.getClass().getSimpleName(), photo);
                try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setBinaryStream(INDEX_1, photo.getImageStream());
                    ps.execute();
                    setId(photo, ps);
                } catch (SQLException e) {
                    throw new JdbcDaoException(e);
                }
            }
        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(e);
        }
        return photo;
    }
}
