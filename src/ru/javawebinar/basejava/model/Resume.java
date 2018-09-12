package ru.javawebinar.basejava.model;

import java.util.Objects;
import java.util.UUID;

/**
 * ru.javawebinar.basejava.model.Resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    private ListRecord<Record2Field> contacts;
    private String objective;
    private String personal;
    private ListRecord<String> achievement;
    private ListRecord<String> qualification;
    private ListRecord<Record4Field> experience;
    private ListRecord<Record3Field> education;

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

    public ListRecord<String> getAchievement() {
        return achievement;
    }

    public ListRecord<String> getQualification() {
        return qualification;
    }

    public ListRecord<Record2Field> getContacts() {
        return contacts;
    }

    public ListRecord<Record4Field> getExperience() {
        return experience;
    }

    public ListRecord<Record3Field> getEducation() {
        return education;
    }


    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public void addContact(String field1, String field2) {
        contacts.add(new Record2Field(field1, field2));
    }

    public String getUuid() {
        return uuid;
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

    public void addAchievement(String achievementString) {
        achievement.add(achievementString);
    }

    public void addQualification(String qualificationString) {
        qualification.add(qualificationString);
    }

    public void addExperience(String field1, String field2, String field3, String field4) {
        experience.add(new Record4Field(field1, field2, field3, field4));
    }

    public void addEducation(String field1, String field2, String field3) {
        education.add(new Record3Field(field1, field2, field3));
    }
}