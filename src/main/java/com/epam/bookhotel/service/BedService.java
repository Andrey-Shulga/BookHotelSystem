package com.epam.bookhotel.service;

import com.epam.bookhotel.dao.BedDao;
import com.epam.bookhotel.dao.DaoFactory;
import com.epam.bookhotel.entity.Bed;
import com.epam.bookhotel.exception.DaoException;
import com.epam.bookhotel.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Service serves operation with entity Bed
 */

public class BedService extends ParentService {

    private static final String FIND_ALL_BED = "find.all.bed";
    private static final String BLANK_LOCALE = "";
    private final List<Object> parameters = new ArrayList<>();

    /**
     * Search all exists beds
     *
     * @param bed entity for searching
     * @return the list of beds
     * @throws ServiceException if any exception in service occurred.
     */
    public List<Bed> findAllBeds(Bed bed) throws ServiceException {

        List<Bed> bedList;
        try (DaoFactory daoFactory = DaoFactory.createJdbcDaoFactory()) {
            BedDao bedDao = daoFactory.getBedDao();
            bedList = bedDao.findByParameters(bed, parameters, FIND_ALL_BED, BLANK_LOCALE);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return bedList;
    }
}