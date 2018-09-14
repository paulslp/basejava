package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * ru.javawebinar.basejava.model.Resume class
 */
public class Resume implements Comparable<Resume> {

    private final String uuid;
    private final String fullName;
    private ListRecord<Contact> contacts;
    private String objective;
    private String personal;
    private List<String> achievement;
    private List<String> qualification;
    private List<SectionPlaceInfo> experience;
    private List<SectionPlaceInfo> education;


    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
        this.contacts = new ListRecord<>();
        this.achievement = new ArrayList<>();
        this.qualification = new ArrayList<>();
        this.experience = new ArrayList<>();
        this.education = new ArrayList<>();
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public List<String> getAchievement() {
        return achievement;
    }

    public List<String> getQualification() {
        return qualification;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public ListRecord<Contact> getContacts() {
        return contacts;
    }

    public List<SectionPlaceInfo> getExperience() {
        return experience;
    }

    public List<SectionPlaceInfo> getEducation() {
        return education;
    }


    public void addContact(String type, String value) {
        contacts.add(new Contact(type, value));
    }

    public void addExperience(String place, String site, LocalDate dateStart, LocalDate dateEnd, String position, String text) {
        experience.add(new SectionPlaceInfo(place, site, dateStart, dateEnd, position, text));
    }

    public void addEducation(String place, String site, LocalDate dateStart, LocalDate dateEnd, String position) {
        education.add(new SectionPlaceInfo(place, site, dateStart, dateEnd, position, ""));
    }

    public void addAchievement(String achievementString) {
        achievement.add(achievementString);
    }

    public void addQualification(String qualificationString) {
        qualification.add(qualificationString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);

    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }





}