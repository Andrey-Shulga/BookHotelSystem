package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;

public abstract class ParentService {

    DaoFactory daoFactory;

    ParentService() {
    }

    protected ParentService(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
