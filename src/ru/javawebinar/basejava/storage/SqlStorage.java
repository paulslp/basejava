package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        new SqlHelper<>(connectionFactory, "DELETE FROM resume").callSQLMethod((ps) -> ps.execute());
    }

    @Override
    public Resume get(String uuid) {

        return new SqlHelper<Resume>(connectionFactory, "SELECT * FROM resume r WHERE r.uuid =?").callSQLFunction((ps) -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void update(Resume r) {
        new SqlHelper<>(connectionFactory, "UPDATE resume SET full_name=? WHERE uuid=?").callSQLMethod((ps) -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            ps.execute();
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        new SqlHelper<>(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?,?)").callSQLMethod((ps) -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        new SqlHelper<>(connectionFactory, "DELETE FROM resume r WHERE r.uuid =?").callSQLMethod((ps) -> {
            ps.setString(1, uuid);
            ps.execute();
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return new SqlHelper<List<Resume>>(connectionFactory, "SELECT trim(uuid) as uuid,full_name FROM resume r ").callSQLFunction((ps) -> {
            List<Resume> resumeList = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new StorageException("Resume list is empty");
            }
            do {
                resumeList.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            } while (rs.next());

            return resumeList;
        });

    }

    @Override
    public int size() {
        return new SqlHelper<Integer>(connectionFactory, "SELECT count(*) as resume_count FROM resume r ").callSQLFunction((ps) -> {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new StorageException("Result set is empty");
            }
            return rs.getInt("resume_count");
        });
    }

}
