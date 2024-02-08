package com.javaops.webapp.sql;

import com.javaops.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String sqlRequest) {
        execute(sqlRequest, PreparedStatement::execute);
    }

    public <T> T execute(String sqlRequest, SqlExecuteCode<T> executeCode) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlRequest)) {
            return executeCode.execute(ps);
        } catch (SQLException e) {
            throw SqlExceptionAdapter.convertException(e);
        }
    }
}
