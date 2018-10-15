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
            dos.writeInt(contacts.size());
            writeCollection(dos, contacts.entrySet(), object -> {
                Map.Entry<ContactType, String> entry = (Map.Entry<ContactType, String>) object;
                System.out.println("name before write " + entry.getKey().name());
                System.out.println("value before write " + entry.getValue());
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = r.getSections();
            writeCollection(dos, sections.entrySet(), entryObject -> {
                Map.Entry<SectionType, Section> entry = (Map.Entry<SectionType, Section>) entryObject;
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());

                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(String.valueOf(entry.getValue()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> stringList = ((ListSection) entry.getValue()).getItems();
                        writeCollection(dos, stringList, object -> dos.writeUTF(String.valueOf(object)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizationList = ((OrganizationSection) entry.getValue()).getOrganizations();
                        writeCollection(dos, organizationList, object -> {
                            Organization org = (Organization) object;
                            dos.writeUTF(org.getHomePage().getName());
                            if (org.getHomePage().getUrl() != null) {
                                dos.writeUTF(org.getHomePage().getUrl());
                            } else {
                                dos.writeUTF("");
                            }
                            writeCollection(dos, org.getPositions(), positionObject -> {
                                Organization.Position pos = (Organization.Position) positionObject;
                                dos.writeInt(pos.getStartDate().getYear());
                                dos.writeInt(pos.getStartDate().getMonth().getValue());
                                dos.writeInt(pos.getEndDate().getYear());
                                dos.writeInt(pos.getEndDate().getMonth().getValue());
                                dos.writeUTF(pos.getTitle());
                                if (pos.getDescription() != null) {
                                    dos.writeUTF(pos.getDescription());
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

    public void writeCollection(DataOutputStream dos, Collection collection, Writer writer) throws IOException {
        dos.writeInt(collection.size());
        for (Object item : collection) {
            try {
                writer.write(item);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private interface Writer {
        public void write(Object object) throws IOException;
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {

        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            System.out.println(uuid);
            System.out.println(fullName);

            int size = dis.readInt();
            System.out.println(size);

            doActions(size, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

                SectionType sectionType;
                sectionType = SectionType.valueOf(dis.readUTF());

                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> stringList = new ArrayList<>();
                        stringList = readListObjects(dis, dis::readUTF);
                        resume.addSection(sectionType, new ListSection(stringList));
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizationList = new ArrayList<>();
                        organizationList = readListObjects(dis, () -> {
                            return new Organization(new Link(dis.readUTF(), dis.readUTF()),
                                    (List<Organization.Position>) readListObjects(dis,()->{
                                        return new Organization.Position(dis.readInt(), Month.of(dis.readInt())
                                                , dis.readInt(), Month.of(dis.readInt())
                                                , dis.readUTF(), dis.readUTF());
                                      })
                                    );
                        });
                        resume.addSection(sectionType, new OrganizationSection(organizationList));
                }
            return resume;

        }

    }

    private interface Reader<T> {
        T readSectionItem() throws IOException;
    }

    public <T> List<T> readListObjects(DataInputStream dis, Reader<T> reader) throws IOException {
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


    public void doActions(Integer countActions, Action action) throws IOException {
        for (int i = 0; i < countActions; i++) {
            action.doAction();
        }
    }
}

