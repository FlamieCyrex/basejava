package com.javaops.webapp.storage.serialization;

import com.javaops.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                String sectionClass = entry.getValue().getClass().getSimpleName();
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(sectionClass);
                switch (entry.getValue().getClass().getSimpleName()) {
                    case "TextSection":
                        dos.writeUTF(((TextSection) entry.getValue()).getTitle());
                        break;
                    case "ListSection":
                        List<String> list = ((ListSection) entry.getValue()).getElements();
                        dos.writeInt(list.size());
                        for (String str : list) {
                            dos.writeUTF(str);
                        }
                        break;
                    case "CompanySection":
                        List<Company> companies = ((CompanySection) entry.getValue()).getCompanies();
                        dos.writeInt(companies.size());
                        for (Company company : companies) {
                            dos.writeUTF(company.getName());
                            dos.writeUTF(company.getWebsite());
                            List<Period> periods = company.getPeriods();
                            dos.writeInt(periods.size());
                            for (Period period : periods) {
                                dos.writeUTF(period.getStartDate().toString());
                                dos.writeUTF(period.getEndDate().toString());
                                dos.writeUTF(period.getTitle());
                                dos.writeUTF(period.getDescription());
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
                String sectionClass = dis.readUTF();
                switch (sectionClass) {
                    case "TextSection":
                        resume.setSection(type, new TextSection(dis.readUTF()));
                        break;
                    case "ListSection":
                        int listSectionSize = dis.readInt();
                        List<String> elementsList = new ArrayList<>(listSectionSize);
                        for (int j = 0; j < listSectionSize; j++) {
                            elementsList.add(dis.readUTF());
                        }
                        resume.setSection(type, new ListSection(elementsList));
                        break;
                    case "CompanySection":
                        int companiesSize = dis.readInt();
                        List<Company> companies = new ArrayList<>(companiesSize);
                        for (int j = 0; j < companiesSize; j++) {
                            Company company = new Company();
                            company.setName(dis.readUTF());
                            company.setWebsite(dis.readUTF());
                            List<Period> periods = new ArrayList<>();
                            int periodSize = dis.readInt();
                            for (int k = 0; k < periodSize; k++) {
                                Period period = new Period();
                                period.setStartDate(LocalDate.parse(dis.readUTF()));
                                period.setEndDate(LocalDate.parse(dis.readUTF()));
                                period.setTitle(dis.readUTF());
                                period.setDescription(dis.readUTF());
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
}

