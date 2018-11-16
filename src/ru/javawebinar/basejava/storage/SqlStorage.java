package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ru.javawebinar.basejava.model.SectionType.ACHIEVEMENT;
import static ru.javawebinar.basejava.model.SectionType.QUALIFICATIONS;

// TODO implement Section (except OrganizationSection)
// TODO Join and split ListSection by `\n`
public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {

        return sqlHelper.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement("    SELECT * FROM resume r " +
                    " LEFT JOIN contact c " +
                    "        ON r.uuid = c.resume_uuid " +
                    "     WHERE r.uuid =? ")) {

                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
                do {
                    addContact(rs, r);
                } while (rs.next());

            }
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT * FROM section s " +
                    "     WHERE s.resume_uuid =? ")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    addSection(rs, r);
                }

            }

            return r;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() != 1) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(conn, r);
            insertContact(conn, r);

            deleteSections(conn, r);
            insertSection(conn, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContact(conn, r);
            insertSection(conn, r);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {

        Map<String, Resume> resumes = new LinkedHashMap<>();
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r ORDER BY full_name,uuid")) {
                        ResultSet rs = ps.executeQuery();

                        while (rs.next()) {
                            resumes.put(rs.getString("uuid"), new Resume(rs.getString("uuid"), rs.getString("full_name")));
                        }
                    }

                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact c ")) {
                        ResultSet rs = ps.executeQuery();

                        if (!rs.next()) {
                            return null;
                        }
                        do {
                            String uuid = rs.getString("resume_uuid");
                            String value = rs.getString("value");
                            ContactType type = ContactType.valueOf(rs.getString("type"));
                            resumes.get(uuid).addContact(type, value);
                        } while (rs.next());
                    }

                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section s ")) {
                        ResultSet rs = ps.executeQuery();

                        if (!rs.next()) {
                            return null;
                        }
                        do {
                            String uuid = rs.getString("resume_uuid");
                            String value = rs.getString("value");
                            String type = rs.getString("type");
                            SectionType sectionType = SectionType.valueOf(type);
                            resumes.get(uuid).addSection(sectionType, stringToSection(type, value));
                        } while (rs.next());
                    }
                    return null;
                }
        );
        return new ArrayList<>(resumes.values());
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", st -> {
            ResultSet rs = st.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSection(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());

                String typeSection = e.getKey().name();
                ps.setString(2, typeSection);

                String sectionValue = e.getValue().toString();
                ps.setString(3, sectionValue);

                ps.addBatch();
            }
            if (r.getSections().size() > 0) {
                ps.executeBatch();
            }
        }
    }
//
//    private String sectionToString(String typeSection, Map.Entry<SectionType, Section> e) {
//
//        String sectionValue = null;
//        switch (typeSection) {
//            case "PERSONAL":
//            case "OBJECTIVE":
//                sectionValue = ((TextSection) e.getValue()).getContent();
//                break;
//            case "ACHIEVEMENT":
//            case "QUALIFICATIONS":
//                sectionValue = ((ListSection) e.getValue()).getItems().stream().reduce("", (x, y) -> (new StringBuilder(x).append("\\n").append(y)).toString()).substring(2);
//        }
//        return sectionValue;
//    }


    private void deleteContacts(Connection conn, Resume r) {
        sqlHelper.execute("DELETE  FROM contact WHERE resume_uuid=?", ps -> {
            ps.setString(1, r.getUuid());
            ps.execute();
            return null;
        });
    }

    private void deleteSections(Connection conn, Resume r) {
        sqlHelper.execute("DELETE  FROM section WHERE resume_uuid=?", ps -> {
            ps.setString(1, r.getUuid());
            ps.execute();
            return null;
        });
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            r.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        String sectionValue = rs.getString("value");
        String type = rs.getString("type");
        if (sectionValue != null) {
            r.addSection(SectionType.valueOf(type), stringToSection(type, sectionValue));
        }
    }

    private Section stringToSection(String typeSection, String sectionValue) {

        switch (typeSection) {
            case "PERSONAL":
            case "OBJECTIVE":
                return new TextSection(sectionValue);
            default:
                return new ListSection((sectionValue).replace("\\n", ";").split(";"));
        }
    }
}