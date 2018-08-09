/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.uuid = "uuid1";
        Resume r2 = new Resume();
        r2.uuid = "uuid2";
        Resume r3 = new Resume();
        r3.uuid = "uuid3";

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.uuid));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        System.out.println("Тестирование метода update...");
        printAll();
        Resume r4 = new Resume();
        r4.uuid = "uuid2";
        System.out.println("Вызываем update со значением uuid, уже существующем в хранилище...");
        ARRAY_STORAGE.update(r4);
        printAll();
        Resume r5 = new Resume();
        r5.uuid = "uuid5";
        System.out.println("Вызываем update со значением uuid, которого нет в хранилище...");
        ARRAY_STORAGE.update(r5);
        printAll();
        System.out.println("Тестирование метода update завершено.");

        printAll();
        ARRAY_STORAGE.delete(r1.uuid);
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
