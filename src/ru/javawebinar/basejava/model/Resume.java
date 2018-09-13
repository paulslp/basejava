package ru.javawebinar.basejava.model;

import java.time.LocalDate;
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
    private ListRecord<String> achievement;
    private ListRecord<String> qualification;
    private ListRecord<PlaceInfo> experience;
    private ListRecord<PlaceInfo> education;


    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
        this.contacts = new ListRecord<>();
        this.achievement = new ListRecord<>();
        this.qualification = new ListRecord<>();
        this.experience = new ListRecord<>();
        this.education = new ListRecord<>();
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public ListRecord<String> getAchievement() {
        return achievement;
    }

    public ListRecord<String> getQualification() {
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

    public ListRecord<PlaceInfo> getExperience() {
        return experience;
    }

    public ListRecord<PlaceInfo> getEducation() {
        return education;
    }


    public void addContact(String type, String value) {
        contacts.add(new Contact(type, value));
    }

    public void addExperience(String place, String site, LocalDate dateStart, LocalDate dateEnd, String position, String text) {
        experience.add(new PlaceInfo(place, site, dateStart, dateEnd, position, text));
    }

    public void addEducation(String place, String site, LocalDate dateStart, LocalDate dateEnd, String position) {
        education.add(new PlaceInfo(place, site, dateStart, dateEnd, position, ""));
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