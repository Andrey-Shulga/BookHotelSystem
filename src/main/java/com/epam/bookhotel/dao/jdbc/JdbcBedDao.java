package com.epam.bookhotel.dao.jdbc;

import com.epam.bookhotel.dao.BedDao;
import com.epam.bookhotel.entity.Bed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.bookhotel.constant.ConstantsHolder.COLUMN_INDEX_1;

/**
 * Jdbc DAO for entity Bed.
 */

class JdbcBedDao extends JdbcDao<Bed> implements BedDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcBedDao.class);

    JdbcBedDao(Connection connection) {
        super(connection);
    }

    @Override
    Bed setRsToField(ResultSet rs, Bed bed) throws SQLException {

        Bed foundBed = new Bed();
        foundBed.setBed(rs.getInt(COLUMN_INDEX_1));
        logger.debug("Found entity: \"{}\" {}", foundBed.getClass().getSimpleName(), foundBed);
        return foundBed;
    }
}