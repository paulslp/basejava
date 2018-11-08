package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.callSQLFunction("DELETE FROM resume", (ps) -> ps.execute());
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.callSQLFunction("SELECT * FROM resume r WHERE r.uuid =?", ps -> {
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
        sqlHelper.callSQLFunction("UPDATE resume SET full_name=? WHERE uuid=?", ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.callSQLFunction("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());

            if (ps.executeUpdate() == 0) {
                throw new ExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.callSQLFunction("DELETE FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.callSQLFunction("SELECT trim(uuid) as uuid,full_name FROM resume r ORDER BY full_name", ps -> {
            List<Resume> resumeList = new ArrayList<>();
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                resumeList.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }

            return resumeList;
        });

    }

    @Override
    public int size() {
        return sqlHelper.callSQLFunction("SELECT count(*) as resume_count FROM resume r ", ps -> {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new StorageException("Result set is empty");
            }
            return rs.getInt("resume_count");
        });
    }

}
