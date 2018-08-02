import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    Integer size = size();

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
    }


    void save(Resume r) {
        if (r.uuid != null) {
            storage[size] = r;
            size++;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int i;
        for (i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                for (int j = i; j < size; j++) {
                    storage[j] = storage[j + 1];
                }
                size--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        int i = 0;
        while (storage[i] != null) {
            i++;
        }
        return i;
    }
}
