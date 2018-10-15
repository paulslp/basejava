package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainString {
    public static void main(String[] args) throws IOException {
      /*  String[] strArray = new String[]{"1", "2", "3", "4", "5"};
//        String result = "";
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str).append(", ");
        }
        System.out.println(sb.toString());

        String str1 = "abc";
        String str3 = "c";
        String str2 = ("ab" + str3).intern();
        System.out.println(str1 == str2);
*/
     /*   DataInputStream dis = null;
        try {
            dis = new DataInputStream(new BufferedInputStream(Files.newInputStream(Paths.get("C:/projects/storage/uuid1"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
            Resume resume = new Resume("uuid", "ff");
        try {
            int size = dis.readInt();
            System.out.println(size);
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(Files.newInputStream(Paths.get("C:/projects/basejava/storage/uuid1"))))) {

            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            ContactType.valueOf("PHONE");

            System.out.println(uuid);
            System.out.println(fullName);
            int size = dis.readInt();
            System.out.println(size);

        }
    }
}