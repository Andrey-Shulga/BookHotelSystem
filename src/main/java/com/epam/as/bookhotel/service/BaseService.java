package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;

public abstract class BaseService {

    private DaoFactory daoFactory;

    public BaseService() {
    }

    public BaseService(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
