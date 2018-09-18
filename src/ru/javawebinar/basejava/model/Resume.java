package ru.javawebinar.basejava.model;

import java.util.EnumMap;
import java.util.Objects;
import java.util.UUID;


public class Resume implements Comparable<Resume> {

    private final String uuid;
    private final String fullName;
    private EnumMap<ContactType, String> mapContacts = new EnumMap<ContactType, String>(ContactType.class);
    private EnumMap<SectionType, Section> mapSections = new EnumMap<SectionType, Section>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getContact(ContactType type) {
        return mapContacts.get(type);
    }

    public Section getSection(SectionType type) {
        return mapSections.get(type);
    }



    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void addSection(SectionType type, Section value) {
        mapSections.put(type, value);
    }

    public void addContact(ContactType type, String value) {
        mapContacts.put(type, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (uuid != null ? !uuid.equals(resume.uuid) : resume.uuid != null) return false;
        if (fullName != null ? !fullName.equals(resume.fullName) : resume.fullName != null) return false;
        if (mapContacts != null ? !mapContacts.equals(resume.mapContacts) : resume.mapContacts != null) return false;
        return mapSections != null ? mapSections.equals(resume.mapSections) : resume.mapSections == null;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (mapContacts != null ? mapContacts.hashCode() : 0);
        result = 31 * result + (mapSections != null ? mapSections.hashCode() : 0);
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