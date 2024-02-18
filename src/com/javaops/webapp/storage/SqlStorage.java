package com.javaops.webapp.storage;

import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.ContactType;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            try (PreparedStatement ps = conn.prepareStatement("UPDATE contact SET value = ? WHERE resume_uuid = ? AND type = ?")) {
                for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                    setStringQueue(ps, new String[]{e.getValue(), r.getUuid(), e.getKey().name()});
                    ps.addBatch();
                }
                ps.executeBatch();
            }
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
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                    setStringQueue(ps, new String[]{r.getUuid(), e.getKey().name(), e.getValue()});
                    ps.addBatch();
                }
                ps.executeBatch();
            }
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
                        String value = rs.getString("value");
                        ContactType type = ContactType.valueOf(rs.getString("type"));
                        r.setContact(type, value);
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
        return sqlHelper.execute("SELECT * FROM public.resume r " +
                "                             JOIN public.contact c on r.uuid = c.resume_uuid " +
                "                         ORDER BY r.full_name,r.uuid", preparedStatement -> {
            List<Resume> list = new ArrayList<>();
            ResultSet rs = preparedStatement.executeQuery();
            int listIndex = -1;
            while (rs.next()) {
                String value = rs.getString("value");
                String uuid = rs.getString("uuid");
                String fullname = rs.getString("full_name");
                ContactType type = ContactType.valueOf(rs.getString("type"));
                if (list.isEmpty() || (!list.get(listIndex).getUuid().equals(uuid))) {
                    Resume r = new Resume(uuid, fullname);
                    r.setContact(type, value);
                    list.add(r);
                    listIndex++;
                } else {
                    Resume r = list.get(listIndex);
                    r.setContact(type, value);
                    list.set(listIndex, r);
                }
            }
            return list;
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

}
