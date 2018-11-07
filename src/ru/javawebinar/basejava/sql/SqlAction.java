package ru.javawebinar.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlAction<T> {

    T doAction(PreparedStatement ps) throws SQLException;
}
