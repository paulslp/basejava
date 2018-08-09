import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    private int findResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if ((uuid.equals(storage[i].uuid)) && (uuid != null)) {
                return i;
            }
        }
        return -1;
    }

    void update(Resume r) {
        //TODO check if resume present
        int indexResume = findResume(r.uuid);
        if (indexResume > -1) {
            storage[indexResume] = r;
        } else {
            System.out.println("Resume is missing in the storage");
        }
    }


    void save(Resume r) {
        //TODO check if storage ArrayIndexOutOfBound
        if (size < 10000) {
            //TODO check if resume not present
            if (findResume(r.uuid) == -1) {
                storage[size] = r;
                size++;
            } else {
                System.out.println("Resume already present in the storage");
            }
        } else {
            System.out.println("ArrayIndexOutOfBoundException");
        }
    }

    Resume get(String uuid) {
        //TODO check if resume present
        int indexResume = findResume(uuid);
        if (indexResume > -1) {
            return storage[indexResume];
        } else {
            System.out.println("Resume is missing in the storage");
            return null;
        }
    }

    void delete(String uuid) {
        //TODO check if resume present
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
}
