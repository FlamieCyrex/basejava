package com.javaops.webapp.storage.serialization;

import com.javaops.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataStreamSerializer implements SerializationStorage {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullname());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
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
                        List<String> list = ((ListSection) section).getElements();
                        dos.writeInt(list.size());
                        for (String str : list) {
                            dos.writeUTF(str);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Company> companies = ((CompanySection) section).getCompanies();
                        dos.writeInt(companies.size());
                        for (Company company : companies) {
                            dos.writeUTF(company.getName());
                            checkNullAndWrite(dos, company.getWebsite());
                            List<Period> periods = company.getPeriods();
                            dos.writeInt(periods.size());
                            for (Period period : periods) {
                                dos.writeUTF(period.getStartDate().toString());
                                dos.writeUTF(period.getEndDate().toString());
                                dos.writeUTF(period.getTitle());
                                checkNullAndWrite(dos, period.getDescription());
                            }
                        }
                        break;

                }


            }
        }
    }


    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullname = dis.readUTF();
            Resume resume = new Resume(uuid, fullname);
            int contactSize = dis.readInt();
            for (int i = 0; i < contactSize; i++) {
                resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sectionSize = dis.readInt();
            for (int i = 0; i < sectionSize; i++) {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.setSection(type, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        int listSectionSize = dis.readInt();
                        List<String> elementsList = new ArrayList<>(listSectionSize);
                        for (int j = 0; j < listSectionSize; j++) {
                            elementsList.add(dis.readUTF());
                        }
                        resume.setSection(type, new ListSection(elementsList));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        int companiesSize = dis.readInt();
                        List<Company> companies = new ArrayList<>(companiesSize);
                        for (int j = 0; j < companiesSize; j++) {
                            Company company = new Company();
                            company.setName(dis.readUTF());
                            company.setWebsite(checkNullAndRead(dis));
                            List<Period> periods = new ArrayList<>();
                            int periodSize = dis.readInt();
                            for (int k = 0; k < periodSize; k++) {
                                Period period = new Period();
                                period.setStartDate(LocalDate.parse(dis.readUTF()));
                                period.setEndDate(LocalDate.parse(dis.readUTF()));
                                period.setTitle(dis.readUTF());
                                period.setDescription(checkNullAndRead(dis));
                                periods.add(period);
                            }
                            company.setPeriods(periods);
                            companies.add(company);
                        }
                        resume.setSection(type, new CompanySection(companies));
                }
            }
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
}
