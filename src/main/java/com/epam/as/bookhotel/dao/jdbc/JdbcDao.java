package com.epam.as.bookhotel.dao.jdbc;


import com.epam.as.bookhotel.dao.Dao;
import com.epam.as.bookhotel.model.BaseEntity;

import java.sql.Connection;

abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {

    private Connection connection;

    JdbcDao(Connection connection) {
        this.connection = connection;
    }
}
