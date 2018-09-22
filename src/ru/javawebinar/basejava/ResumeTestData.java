package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.model.Resume;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {


    public static  Resume fillResume(Resume resume) {

        resume.addContact(ContactType.PHONE, "8-999-999-99-99");
        resume.addContact(ContactType.MAIL, "paulslp@yandex.ru");
        resume.addContact(ContactType.SKYPE, "paulslp");

        resume.addSection(SectionType.OBJECTIVE, new TextSection("Junior Java Developer"));
        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума"));

        List<String> listAchievement = new ArrayList<>();
        listAchievement.add("с 2013 года: внедрение и сопровождение ПО IBM WAS, MQ, DB2.");
        listAchievement.add("с 2011 года: программист SQL (MS SQL Server 2008,2000).");
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(listAchievement));

        List<String> listQualifications = new ArrayList<>();
        listQualifications.add("Java Frameworks: Struts 1.3, Spring 3.0");
        listQualifications.add("MS SQL, PostgreSQL, DB2");
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(listQualifications));

        List<Organization> listExperience = new ArrayList<>();
        List<Organization.PersonalData> listPersonalData = new ArrayList<>();

        listExperience.add(new Organization("ООО Фирма", "www.firma.ru", LocalDate.parse("2013-03-01"), LocalDate.now(), "Главный специалист", "Реализация утилиты резервного копирования"));
        listExperience.get(listExperience.size() - 1).addPersonalData(LocalDate.parse("2005-11-01"), LocalDate.parse("2005-11-20"), "Программирование баз данных DB2", "");

        listPersonalData.add(new Organization.PersonalData(LocalDate.parse("2011-03-01"), LocalDate.parse("2013-03-01"), "Инженер-программист", "Реализация записи в базу данных MS SQL Server 2008 данных журналов контроллеров"));
        listExperience.add(new Organization("ООО Радио", "www.radio.ru", LocalDate.parse("2011-03-01"), LocalDate.parse("2013-03-01"), "Инженер-программист", "Реализация записи в базу данных MS SQL Server 2008 данных журналов контроллеров"));
        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(listExperience));

        List<Organization> listEducation = new ArrayList<>();
        listPersonalData.add(new Organization.PersonalData(LocalDate.parse("2016-11-01"), LocalDate.parse("2016-11-20"), "ITIL", ""));
        listPersonalData.add(new Organization.PersonalData(LocalDate.parse("2006-11-01"), LocalDate.parse("2006-11-20"), "Программирование баз данных SQL Server 2000", ""));


        listEducation.add(new Organization("Академия АйТи", "www.it.ru", LocalDate.parse("2016-11-01"), LocalDate.parse("2016-11-20"), "ITIL", ""));
        listEducation.get(listEducation.size() - 1).addPersonalData(LocalDate.parse("2006-11-01"), LocalDate.parse("2006-11-20"), "Программирование баз данных SQL Server 2000", "");


        listPersonalData.add(new Organization.PersonalData(LocalDate.parse("2001-09-01"), LocalDate.parse("2006-07-31"), "Факультет Прикладная математика. Квалификация инженер-математик", ""));
        listEducation.add(new Organization("Ижевский Государственный Технический Университет", "www.istu.ru", LocalDate.parse("2001-09-01"), LocalDate.parse("2006-07-31"), "Факультет Прикладная математика. Квалификация инженер-математик", ""));

        resume.addSection(SectionType.EDUCATION, new OrganizationSection(listEducation));

        return resume;
    }

    public static void printResume(Resume resume) {
        System.out.println(resume.getFullName());

        for (ContactType contactType : ContactType.values()) {
            if (resume.getContact(contactType) != null) {
                System.out.print(contactType.getTitle() + "  ");
                System.out.println(resume.getContact(contactType));
            }
        }

        System.out.println("");
        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
            System.out.println(resume.getSection(type).toString());
            System.out.println("");
        }

    }

    public static void main(String[] args) throws IOException {
        Resume resume = fillResume(new Resume("Селютин Павел Сергеевич"));
        printResume(resume);
    }
}
