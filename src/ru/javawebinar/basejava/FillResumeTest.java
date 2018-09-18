package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FillResumeTest {

    public static void fillResume() {

        Resume resume = new Resume("Селютин Павел \r\n");
        resume.addContact(ContactType.PHONE, "8-999-999-99-99");
        resume.addContact(ContactType.EMAIL, "paulslp@yandex.ru");
        resume.addContact(ContactType.SKYPE, "paulslp");
        resume.addSection(SectionType.OBJECTIVE,new TextSection("Junior Java Developer"));
        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума"));
        List<String> listValues = new ArrayList<>();
        listValues.add("с 2013 года: внедрение и сопровождение ПО IBM WAS, MQ, DB2.");
        listValues.add("с 2011 года: программист SQL (MS SQL Server 2008,2000).");
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(listValues));
        listValues.clear();
        listValues.add("Java Frameworks: Struts 1.3, Spring 3.0");
        listValues.add("MS SQL, PostgreSQL, DB2");
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(listValues));
        listValues.clear();
        listValues.add(String.valueOf(new Organization("ООО Фирма", "www.firma.ru", LocalDate.parse("2013-03-01"), LocalDate.now(), "Главный специалист", "Реализация утилиты резервного копирования")));
        listValues.add(String.valueOf(new Organization("ООО Радио", "www.radio.ru", LocalDate.parse("2011-03-01"), LocalDate.parse("2013-03-01"), "Инженер-программист", "Реализация записи в базу данных MS SQL Server 2008 данных журналов контроллеров")));
        listValues.clear();
        resume.addSection(SectionType.EXPERIENCE, new ListSection(listValues));
        listValues.add(String.valueOf(new Organization("Академия АйТи", "www.it.ru", LocalDate.parse("2016-11-01"), LocalDate.parse("2016-11-20"), "ITIL", "")));
        listValues.add(String.valueOf(new Organization("Академия АйТи", "www.it.ru", LocalDate.parse("2006-11-01"), LocalDate.parse("2006-11-20"), "Программирование баз данных SQL Server 2000", "")));
        listValues.add(String.valueOf(new Organization("Ижевский Государственный Технический Университет", "www.istu.ru", LocalDate.parse("2001-09-01"), LocalDate.parse("2006-07-31"), "Факультет Прикладная математика. Квалификация инженер-математик", "")));
        resume.addSection(SectionType.EDUCATION, new ListSection(listValues));
        System.out.println(resume.getFullName());

        for (ContactType contactType : ContactType.values()) {
            System.out.print(contactType.getTitle() + "  ");
            System.out.println(resume.getContact(contactType));
        }

        System.out.println("");
        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
            System.out.println(resume.getSection(type).getSection().toString());
            System.out.println("");
        }
    }

    public static void main(String[] args) throws IOException {
        fillResume();
    }
}
