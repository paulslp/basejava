package ru.javawebinar.basejava.storage;

import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;

public class FillResumeTest {

    @Test
    public void fillResume() {
        Resume resume = new Resume("Селютин Павел");
        resume.addContact("Тел.:", "8-999-999-99-99");
        resume.addContact("Skype:", "paulslp");
        resume.setObjective("Специалист отдела внедрения");
        resume.setPersonal("Аналитический склад ума");
        resume.addAchievement("с 2013 года: внедрение и сопровождение ПО IBM WAS, MQ, DB2.");
        resume.addAchievement("с 2011 года: программист SQL (MS SQL Server 2008,2000).");
        resume.addQualification("Java Frameworks: Struts 1.3, Spring 3.0");
        resume.addQualification("MS SQL, PostgreSQL, DB2");
        resume.addExperience("ООО Фирма", "03-2013-Сейчас", "Главный специалист", "Реализация утилиты резервного копирования");
        resume.addExperience("ООО Радио", "03-2013-Сейчас", "Инженер-программист", "Реализация записи в базу данных MS SQL Server 2008 данных журналов контроллеров");
        resume.addEducation("Академия АйТи", "11/2016        ", "IT Service Management");
        resume.addEducation("Академия АйТи", "11/2006        ", "Программирование баз данных SQL Server 2000");
        resume.addEducation("Ижевский Государственный Технический Университет", "09/2001-07/2006", "Факультет Прикладная математика. Квалификация инженер-математик");

        SectionType.valueOf("CONTACTS").setContent(resume.getContacts().getAll());
        SectionType.valueOf("OBJECTIVE").setContent(resume.getObjective());
        SectionType.valueOf("PERSONAL").setContent(resume.getPersonal());
        SectionType.valueOf("ACHIEVEMENT").setContent(resume.getAchievement().getAll());
        SectionType.valueOf("QUALIFICATIONS").setContent(resume.getQualification().getAll());
        SectionType.valueOf("EXPERIENCE").setContent(resume.getExperience().getAll());
        SectionType.valueOf("EDUCATION").setContent(resume.getEducation().getAll());

        System.out.println(resume.getFullName());
        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
            System.out.println(type.getContent());
        }
    }
}
