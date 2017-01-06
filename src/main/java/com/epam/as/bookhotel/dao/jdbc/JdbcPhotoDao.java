package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.PhotoDao;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.Photo;
import com.epam.as.bookhotel.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

/**
 * Jdbc DAO for entity Photo.
 */

class JdbcPhotoDao extends JdbcDao<Photo> implements PhotoDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcPhotoDao.class);
    private static final String QUERY_PROPERTY_FILE = getQueryPropertyFile();
    private static final int INDEX_1 = 1;
    private static final int INDEX_2 = 2;
    private static final int INDEX_3 = 3;
    private Connection connection;

    JdbcPhotoDao(Connection connection) {

        super(connection);
        this.connection = connection;
    }

    @Override
    Photo setRsToField(ResultSet rs, Photo photo) throws SQLException {

        photo.setImageStream(rs.getBinaryStream(INDEX_1));
        photo.setContentType(rs.getString(INDEX_2));
        photo.setContentLength(rs.getLong(INDEX_3));
        logger.debug("Found entity: {}", photo);
        return photo;
    }

    /**
     * Inserts entity Photo to database.
     *
     * @param photo      the entity which need inserts in database
     * @param parameters the list of parameters for prepare PrepareStatements
     * @param queryKey   property key for reading insert query from property file
     * @return inserted entity with received id
     * @throws JdbcDaoException if any exceptions occurred with jdbc operations
     */
    @Override
    public Photo addPhoto(Photo photo, List<Object> parameters, String queryKey) throws JdbcDaoException {

        try {
            PropertyManager pm = new PropertyManager(QUERY_PROPERTY_FILE);
            if (photo.getId() == null) {
                logger.debug("{} trying to INSERT entity \"{}\" to database...", this.getClass().getSimpleName(), photo);
                try (PreparedStatement ps = connection.prepareStatement(pm.getPropertyKey(queryKey), Statement.RETURN_GENERATED_KEYS)) {
                    ps.setBinaryStream(INDEX_1, photo.getImageStream());
                    ps.setString(INDEX_2, photo.getContentType());
                    ps.setLong(INDEX_3, photo.getContentLength());
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
