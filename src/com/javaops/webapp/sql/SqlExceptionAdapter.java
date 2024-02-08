package com.javaops.webapp.sql;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.StorageException;

import java.sql.SQLException;

public class SqlExceptionAdapter {
    private SqlExceptionAdapter() {
    }

    public static StorageException convertException(SQLException e) {
        if (e.getSQLState().equals("23505")) {
            return new ExistStorageException(null);
        }
        return new StorageException(e);

    }
}

