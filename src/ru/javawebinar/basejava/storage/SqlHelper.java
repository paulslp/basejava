package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper<T> {

    private ConnectionFactory connectionFactory;
    private String sqlQuery;

    public SqlHelper( ConnectionFactory connectionFactory, String sqlQuery) {
        this.connectionFactory = connectionFactory;
        this.sqlQuery = sqlQuery;
    }


    public T callSQLFunction(SqlAction<T> sqlAction) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            return sqlAction.doAction(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public void callSQLMethod(SqlAction sqlAction) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
             sqlAction.doAction(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }


    }

}
