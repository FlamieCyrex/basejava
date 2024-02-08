package com.javaops.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecuteCode<T> {
    T execute(PreparedStatement preparedStatement) throws SQLException;
}
