package ru.javawebinar.basejava.storage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlAction<T> {

    public  T doAction(PreparedStatement ps) throws SQLException;
}
