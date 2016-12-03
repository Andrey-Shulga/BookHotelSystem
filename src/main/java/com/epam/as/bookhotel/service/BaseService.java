package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;

public abstract class BaseService {

    protected DaoFactory daoFactory;

    protected BaseService() {
    }

    protected BaseService(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
