package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper<T> {

    SqlAction<T> sqlAction;
    ConnectionFactory connectionFactory;
    String sqlQuery;


    public SqlHelper(SqlAction<T> sqlAction, ConnectionFactory connectionFactory, String sqlQuery) {
        this.sqlAction = sqlAction;
        this.connectionFactory = connectionFactory;
        this.sqlQuery = sqlQuery;

    }

    public T callSQLMethod(ConnectionFactory connectionFactory, String sqlQuery) {

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            return sqlAction.doAction();
        } catch (SQLException e) {
            throw new StorageException(e);
        }


    }


}
