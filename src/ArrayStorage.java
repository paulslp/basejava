import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i=0;i<this.size();i++)
        {
            storage[i]=null;
        }
    }


    void save(Resume r) {
        for (int i=0;i<10000;i++)
        {
            if (storage[i]==null) {
                storage[i]=r;
                break;
            }
        }


    }

    Resume get(String uuid) {
        for (int i=0;i<this.size();i++)
        {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int i;
        for (i=0;i<this.size();i++)
        {
            if (storage[i].uuid.equals(uuid)) {
                for (int j=i;j<this.size();j++) {
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
        int i=0;
        while (storage[i]!=null)
        {
            i=i+1;
        }
        return i;
    }
}
