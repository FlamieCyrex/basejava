package com.javaops.webapp.storage;

import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.ContactType;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

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
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ? ")) {
                setStringQueue(ps, new String[]{r.getFullname(), r.getUuid()});
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(r);
            createContact(conn, r);
            return null;
        });
    }


    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume(uuid, full_name) VALUES (?,?)")) {
                setStringQueue(ps, new String[]{r.getUuid(), r.getFullname()});
                ps.execute();
            }
            createContact(conn, r);
            return null;
        });
    }


    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "   SELECT * " +
                        "     FROM resume r " +
                        "LEFT JOIN contact c " +
                        "       ON r.uuid = c.resume_uuid " +
                        "    WHERE r.uuid = ? ",
                preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        getContact(rs, r);
                    } while (rs.next());
                    return r;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid =? "
                , preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    if (preparedStatement.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume r " +
                "                             JOIN contact c on r.uuid = c.resume_uuid " +
                "                         ORDER BY r.full_name,r.uuid", preparedStatement -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                Resume resume = map.get(uuid);
                if (resume == null) {
                    resume = new Resume(uuid, rs.getString("full_name"));
                    map.put(uuid, resume);
                }
                getContact(rs, resume);
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            return !rs.next() ? 0 : rs.getInt(1);
        });
    }

    private void setStringQueue(PreparedStatement ps, String[] arr) throws SQLException {
        for (int i = 0; i < arr.length; i++) {
            ps.setString(i + 1, arr[i]);
        }
    }

    public void createContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (value,resume_uuid,type) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                setStringQueue(ps, new String[]{e.getValue(), r.getUuid(), e.getKey().name()});
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void deleteContacts(Resume r) {
        sqlHelper.execute("DELETE FROM contact WHERE resume_uuid=?", preparedStatement -> {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.execute();
            return null;
        });
    }

    public void getContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            r.setContact(type, value);
        }
    }
}
