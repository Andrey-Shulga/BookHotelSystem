package com.epam.bookhotel.dao.jdbc;

import com.epam.bookhotel.dao.PhotoDao;
import com.epam.bookhotel.entity.Photo;
import com.epam.bookhotel.exception.JdbcDaoException;
import com.epam.bookhotel.exception.PropertyManagerException;
import com.epam.bookhotel.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

import static com.epam.bookhotel.constant.Constants.*;

/**
 * Jdbc DAO for entity Photo.
 */

class JdbcPhotoDao extends JdbcDao<Photo> implements PhotoDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcPhotoDao.class);
    private static final String QUERY_PROPERTY_FILE = getQueryPropertyFile();
    private Connection connection;

    JdbcPhotoDao(Connection connection) {

        super(connection);
        this.connection = connection;
    }

    @Override
    Photo setRsToField(ResultSet rs, Photo photo) throws SQLException {

        photo.setImageStream(rs.getBinaryStream(COLUMN_INDEX_1));
        photo.setContentType(rs.getString(COLUMN_INDEX_2));
        photo.setContentLength(rs.getLong(COLUMN_INDEX_3));
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
                    ps.setBinaryStream(COLUMN_INDEX_1, photo.getImageStream());
                    ps.setString(COLUMN_INDEX_2, photo.getContentType());
                    ps.setLong(COLUMN_INDEX_3, photo.getContentLength());
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
