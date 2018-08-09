import java.util.Arrays;

public class ArrayStorage {

    int size = 0;
    final int STORAGE_LENGTH = 10000;
    Resume[] storage = new Resume[STORAGE_LENGTH];

    void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    void update(Resume resume) {
        int indexResume = findResume(resume.uuid);
        if (indexResume > -1) {
            storage[indexResume] = resume;
        } else {
            System.out.println("Resume is missing in the storage");
        }
    }


    void save(Resume resume) {
        if (size < STORAGE_LENGTH) {
            if (findResume(resume.uuid) == -1) {
                storage[size] = resume;
                size++;
            } else {
                System.out.println("Resume already present in the storage");
            }
        } else {
            System.out.println("ArrayIndexOutOfBoundException");
        }
    }

    Resume get(String uuid) {
        int indexResume = findResume(uuid);
        if (indexResume > -1) {
            return storage[indexResume];
        } else {
            System.out.println("Resume is missing in the storage");
            return null;
        }
    }

    void delete(String uuid) {
        int indexResume = findResume(uuid);
        if (indexResume > -1) {
            storage[indexResume] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume is missing in the storage");
        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }

    private int findResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return i;
            }
        }
        return -1;
    }
}
