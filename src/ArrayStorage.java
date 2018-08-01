import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];


    void clear() {
        int size = size();
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
    }


    void save(Resume r) {
        if ((r != null) || (!r.uuid.equals("null"))) {
            int size = size();
            storage[size] = r;
        }
    }

    Resume get(String uuid) {
        int size = size();
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int i;
        int size = size();
        for (i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                for (int j = i; j < size; j++) {
                    storage[j] = storage[j + 1];
                }
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, this.size());
        //return new Resume[0];
    }

    int size() {
        int i = 0;
        while (storage[i] != null) {
            i = i + 1;
        }
        return i;
    }
}
