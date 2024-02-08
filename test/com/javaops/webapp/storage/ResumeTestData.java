package com.javaops.webapp.storage;

import com.javaops.webapp.model.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    public static Resume fillResume(String uuid, String fullname) {
        /*String objective = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям";
        String personal = "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.";
        List<String> achievs = new ArrayList<>();
        achievs.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievs.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        achievs.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievs.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievs.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievs.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievs.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        List<String> qualification = new ArrayList<>();
        qualification.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualification.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualification.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        qualification.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualification.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualification.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualification.add("Python: Django.");
        qualification.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualification.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualification.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualification.add("Родной русский, английский \"upper intermediate\"");
        List<Period> periodListJavaOnline = new ArrayList<>();
        periodListJavaOnline.add(new Period(2016, Month.NOVEMBER, "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        periodListJavaOnline.add(new Period(2016, Month.NOVEMBER, 2016, Month.DECEMBER, "Инженер", "Example for 2."));
        List<Period> periodListWrike = new ArrayList<>();
        periodListWrike.add(new Period(2014, Month.MARCH, 2018, Month.JANUARY, "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company("Java Online Projects", null, periodListJavaOnline));
        companyList.add(new Company("Wrike", null, periodListWrike));
        List<Period> periodEducate1 = new ArrayList<>();
        periodEducate1.add(new Period(2013, Month.FEBRUARY, 2014, Month.FEBRUARY, "'Functional Programming Principles in Scala' by Martin Odersky", ""));
        List<Period> periodEducate2 = new ArrayList<>();
        periodEducate2.add(new Period(2012, Month.MARCH, 2013, Month.JANUARY, "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", ""));
        List<Company> learning = new ArrayList<>();
        learning.add(new Company("Coursera", null, periodEducate1));
        learning.add(new Company("Luxoft", null, periodEducate2));


         */

        Resume resumeTest = new Resume(uuid, fullname);
        /*resumeTest.setContact(ContactType.PHONE_NUMBER, "+7-922-111-44-44");
        resumeTest.setContact(ContactType.SKYPE, "skype:flamiecyrex");
        resumeTest.setContact(ContactType.EMAIL, "wave-5@yandex.ru");
        resumeTest.setContact(ContactType.PROFILE_ON_LINKEDIN, "");
        resumeTest.setContact(ContactType.PROFILE_ON_GITHUB, "");
        resumeTest.setContact(ContactType.PROFILE_ON_STACKOVERFLOW, "");
        resumeTest.setContact(ContactType.HOMEPAGE, "");
        resumeTest.setSection(SectionType.OBJECTIVE, new TextSection(objective));
        resumeTest.setSection(SectionType.PERSONAL, new TextSection(personal));
        resumeTest.setSection(SectionType.ACHIEVEMENT, new ListSection(achievs));
        resumeTest.setSection(SectionType.QUALIFICATIONS, new ListSection(qualification));
        resumeTest.setSection(SectionType.EXPERIENCE, new CompanySection(companyList));
        resumeTest.setSection(SectionType.EDUCATION, new CompanySection(learning));

         */
        return resumeTest;
    }


}
