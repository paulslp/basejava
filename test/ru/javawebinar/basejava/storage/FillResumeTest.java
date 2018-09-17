package ru.javawebinar.basejava.storage;

import org.junit.Test;
import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;

public class FillResumeTest {

    @Test
    public void fillResume() {

        Resume resume = new Resume("Селютин Павел \r\n");


        resume.addContact(ContactType.PHONE, "8-999-999-99-99");
        resume.addContact(ContactType.EMAIL, "paulslp@yandex.ru");
        resume.addContact(ContactType.SKYPE, "paulslp");
        resume.addSection(SectionType.OBJECTIVE, new SectionValue("Junior Java Developer"));
        resume.addSection(SectionType.PERSONAL, new SectionValue("Аналитический склад ума"));
        resume.addSection(SectionType.ACHIEVEMENT, new SectionValue("с 2013 года: внедрение и сопровождение ПО IBM WAS, MQ, DB2."));
        resume.addSection(SectionType.ACHIEVEMENT, new SectionValue("с 2011 года: программист SQL (MS SQL Server 2008,2000)."));
        resume.addSection(SectionType.QUALIFICATIONS, new SectionValue("Java Frameworks: Struts 1.3, Spring 3.0"));
        resume.addSection(SectionType.QUALIFICATIONS, new SectionValue("MS SQL, PostgreSQL, DB2"));
        resume.addSection(SectionType.EXPERIENCE, new SectionPlaceInfo("ООО Фирма", "www.firma.ru", LocalDate.parse("2013-03-01"), LocalDate.now(), "Главный специалист", "Реализация утилиты резервного копирования"));
        resume.addSection(SectionType.EXPERIENCE, new SectionPlaceInfo("ООО Фирма", "www.firma.ru", LocalDate.parse("2013-03-01"), LocalDate.now(), "Главный специалист", "Реализация утилиты резервного копирования"));
        resume.addSection(SectionType.EXPERIENCE, new SectionPlaceInfo("ООО Радио", "www.radio.ru", LocalDate.parse("2011-03-01"), LocalDate.parse("2013-03-01"), "Инженер-программист", "Реализация записи в базу данных MS SQL Server 2008 данных журналов контроллеров"));
        resume.addSection(SectionType.EDUCATION, new SectionPlaceInfo("Академия АйТи", "www.it.ru", LocalDate.parse("2016-11-01"), LocalDate.parse("2016-11-20"), "ITIL", ""));
        resume.addSection(SectionType.EDUCATION, new SectionPlaceInfo("Академия АйТи", "www.it.ru", LocalDate.parse("2006-11-01"), LocalDate.parse("2006-11-20"), "Программирование баз данных SQL Server 2000", ""));
        resume.addSection(SectionType.EDUCATION, new SectionPlaceInfo("Ижевский Государственный Технический Университет", "www.istu.ru", LocalDate.parse("2001-09-01"), LocalDate.parse("2006-07-31"), "Факультет Прикладная математика. Квалификация инженер-математик", ""));

        System.out.println(resume.getFullName());

        for (ContactType contactType : ContactType.values()) {
            System.out.print(contactType.getTitle() + "  ");
            System.out.println(resume.getMapContacts().get(contactType));
        }
        System.out.println("");
        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
            for (Object sectionValue : resume.getMapSections().get(type)) {
                System.out.println(sectionValue.toString());
            }
            System.out.println("");
        }
    }
}
