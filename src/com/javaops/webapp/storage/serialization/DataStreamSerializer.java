package com.javaops.webapp.storage.serialization;

import com.javaops.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements SerializationStorage {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullname());
            Map<ContactType, String> contacts = r.getContacts();
            writeWithException(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });


            writeWithException(dos, r.getSections().entrySet(), entry -> {
                SectionType type = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(type.name());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getTitle());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeWithException(dos, ((ListSection) section).getElements(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeWithException(dos, ((CompanySection) section).getCompanies(), company -> {
                            dos.writeUTF(company.getName());
                            checkNullAndWrite(dos, company.getWebsite());
                            writeWithException(dos, company.getPeriods(), period -> {
                                dos.writeUTF(period.getStartDate().toString());
                                dos.writeUTF(period.getEndDate().toString());
                                dos.writeUTF(period.getTitle());
                                checkNullAndWrite(dos, period.getDescription());
                            });
                        });
                        break;

                }
            });
        }
    }


    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullname = dis.readUTF();
            Resume resume = new Resume(uuid, fullname);
            readWithException(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, () -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switchSection(dis, type, resume);
            });
            return resume;
        }
    }

    private void checkNullAndWrite(DataOutputStream dos, String str) throws IOException {
        if (Objects.isNull(str)) {
            dos.writeUTF("null");
        } else dos.writeUTF(str);
    }

    private String checkNullAndRead(DataInputStream dis) throws IOException {
        String str = dis.readUTF();
        if (str.equals("null")) {
            return null;
        }
        return str;
    }

    private interface SerializerWriter<T> {
        void write(T t) throws IOException;
    }

    private interface SerializerReader {
        void read() throws IOException;
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, SerializerWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.write(element);
        }
    }

    private void readWithException(DataInputStream dis, SerializerReader reader) throws IOException {
        int collectionLength = dis.readInt();
        for (int i = 0; i < collectionLength; i++) {
            reader.read();
        }

    }

    private void switchSection(DataInputStream dis, SectionType type, Resume resume) throws IOException {
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                resume.setSection(type, new TextSection(dis.readUTF()));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                List<String> elementsList = new ArrayList<>();
                readWithException(dis, () -> elementsList.add(dis.readUTF()));
                resume.setSection(type, new ListSection(elementsList));
                break;
            case EXPERIENCE:
            case EDUCATION:
                List<Company> companies = new ArrayList<>();
                readWithException(dis, () -> {
                    Company company = new Company();
                    company.setName(dis.readUTF());
                    company.setWebsite(checkNullAndRead(dis));
                    List<Period> periods = new ArrayList<>();
                    readWithException(dis, () -> {
                        Period period = new Period();
                        period.setStartDate(LocalDate.parse(dis.readUTF()));
                        period.setEndDate(LocalDate.parse(dis.readUTF()));
                        period.setTitle(dis.readUTF());
                        period.setDescription(checkNullAndRead(dis));
                        periods.add(period);
                    });
                    company.setPeriods(periods);
                    companies.add(company);
                });
                resume.setSection(type, new CompanySection(companies));
                break;
        }
    }
}

