package com.epam.bookhotel.service;

import com.epam.bookhotel.dao.DaoFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract parent service for other services
 */

abstract class ParentService {

    protected final List<Object> parameters = new ArrayList<>();
    DaoFactory daoFactory;

}
