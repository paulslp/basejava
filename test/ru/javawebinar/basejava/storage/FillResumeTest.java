package ru.javawebinar.basejava.storage;

import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionPlaceInfo;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.model.SectionValue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FillResumeTest {

    @Test
    public void fillResume() {

        List<SectionValue> listAchievement = new ArrayList<>();
        List<SectionValue> listQualification = new ArrayList<>();
        List<SectionPlaceInfo> listExperience = new ArrayList<>();
        List<SectionPlaceInfo> listEducation = new ArrayList<>();

        HashMap<SectionType,Object> mapSection= new HashMap<>();
        mapSection.put(SectionType.OBJECTIVE,"Junior Java Developer");
        mapSection.put(SectionType.PERSONAL,"Аналитический склад ума");
        listAchievement.add(new SectionValue("с 2013 года: внедрение и сопровождение ПО IBM WAS, MQ, DB2."));
        listAchievement.add(new SectionValue("с 2011 года: программист SQL (MS SQL Server 2008,2000)."));
        mapSection.put(SectionType.ACHIEVEMENT, listAchievement);
        listQualification.add(new SectionValue("Java Frameworks: Struts 1.3, Spring 3.0"));
        listQualification.add(new SectionValue("MS SQL, PostgreSQL, DB2"));
        mapSection.put(SectionType.QUALIFICATIONS, listQualification);
        listExperience.add(new SectionPlaceInfo("ООО Фирма", "www.firma.ru",LocalDate.parse("2013-03-01"),LocalDate.now(), "Главный специалист", "Реализация утилиты резервного копирования"));
        listExperience.add(new SectionPlaceInfo( "ООО Радио", "www.radio.ru",LocalDate.parse("2011-03-01"), LocalDate.parse("2013-03-01"),"Инженер-программист", "Реализация записи в базу данных MS SQL Server 2008 данных журналов контроллеров"));
        mapSection.put(SectionType.EXPERIENCE, listExperience);
        listEducation.add(new SectionPlaceInfo("Академия АйТи", "www.it.ru",LocalDate.parse("2016-11-01"), LocalDate.parse("2016-11-20"),"ITIL",""));
        listEducation.add(new SectionPlaceInfo("Академия АйТи","www.it.ru", LocalDate.parse("2006-11-01"), LocalDate.parse("2006-11-20"),"Программирование баз данных SQL Server 2000",""));
        listEducation.add(new SectionPlaceInfo("Ижевский Государственный Технический Университет","www.istu.ru",LocalDate.parse("2001-09-01"), LocalDate.parse("2006-07-31"), "Факультет Прикладная математика. Квалификация инженер-математик",""));
        mapSection.put(SectionType.EDUCATION, listEducation);

        Resume resume = new Resume("Селютин Павел");
        resume.addContact("Тел.:", "8-999-999-99-99");
        resume.addContact("Skype:", "paulslp");


        System.out.println(resume.getFullName());
        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
            System.out.println(mapSection.get(type).toString());
        }
    }
}
