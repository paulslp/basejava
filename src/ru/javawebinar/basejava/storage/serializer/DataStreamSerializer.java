package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeCollection(dos, contacts.entrySet(), entryObject -> {
                dos.writeUTF(entryObject.getKey().name());
                dos.writeUTF(entryObject.getValue());
            });

            Map<SectionType, Section> sections = r.getSections();
            writeCollection(dos, sections.entrySet(), entryObject -> {
                SectionType sectionType = entryObject.getKey();
                dos.writeUTF(sectionType.name());

                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(String.valueOf(entryObject.getValue()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> stringList = ((ListSection) entryObject.getValue()).getItems();
                        writeCollection(dos, stringList, object -> dos.writeUTF(String.valueOf(object)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizationList = ((OrganizationSection) entryObject.getValue()).getOrganizations();
                        writeCollection(dos, organizationList, organization -> {
                            dos.writeUTF(organization.getHomePage().getName());
                            if (organization.getHomePage().getUrl() != null) {
                                dos.writeUTF(organization.getHomePage().getUrl());
                            } else {
                                dos.writeUTF("");
                            }
                            writeCollection(dos, organization.getPositions(), positionObject -> {
                                dos.writeInt(positionObject.getStartDate().getYear());
                                dos.writeInt(positionObject.getStartDate().getMonth().getValue());
                                dos.writeInt(positionObject.getEndDate().getYear());
                                dos.writeInt(positionObject.getEndDate().getMonth().getValue());
                                dos.writeUTF(positionObject.getTitle());
                                if (positionObject.getDescription() != null) {
                                    dos.writeUTF(positionObject.getDescription());
                                } else {
                                    dos.writeUTF("");
                                }
                            });
                        });
                        break;
                }
            });
        }
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, Writer<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
                writer.write(item);
        }
    }


    private interface Writer<T> {
        void write(T object) throws IOException;
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {

        try (DataInputStream dis = new DataInputStream(is)) {

            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            int size = dis.readInt();
            doActions(size, () -> {
                String contactType = dis.readUTF();
                String contactValue = dis.readUTF();
                resume.addContact(ContactType.valueOf(contactType), contactValue);

            });
            size = dis.readInt();
            if (size == 0) {
                return resume;
            }
            doActions(size, () -> {
                SectionType sectionType;
                sectionType = SectionType.valueOf(dis.readUTF());

                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> stringList = readListObjects(dis, dis::readUTF);
                        resume.addSection(sectionType, new ListSection(stringList));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizationList = readListObjects(dis, () -> new Organization(new Link(dis.readUTF(), dis.readUTF()),
                                readListObjects(dis, () -> new Organization.Position(dis.readInt(), Month.of(dis.readInt())
                                        , dis.readInt(), Month.of(dis.readInt())
                                        , dis.readUTF(), dis.readUTF()))
                        ));
                        resume.addSection(sectionType, new OrganizationSection(organizationList));
                        break;
                }
            });
            return resume;

        }

    }

    private interface Reader<T> {
        T readSectionItem() throws IOException;
    }

    private <T> List<T> readListObjects(DataInputStream dis, Reader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> listObject = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            listObject.add(reader.readSectionItem());
        }
        return listObject;
    }

    private interface Action {
        void doAction() throws IOException;
    }


    private void doActions(Integer countActions, Action action) throws IOException {
        for (int i = 0; i < countActions; i++) {
            action.doAction();
        }
    }
}

