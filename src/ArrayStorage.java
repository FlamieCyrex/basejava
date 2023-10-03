import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    int count = 0;

    void clear() {
        Arrays.fill(storage,0,count-1,null);
        count = 0;
    }

    void save(Resume r) {
        storage[count] = r;
        count++;
    }

    Resume get(String uuid) {
        for (int i = 0; i<count; i++){
            if (storage [i].toString().equals(uuid))
                return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        int count = 0;
        for (Resume resume : storage) {
            if (Objects.equals(resume.uuid, uuid)) {
                break;
            } else count++;
        }
        storage[count] = storage[this.count - 1];
        storage[this.count - 1] = null;
        this.count--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    int size() {
        return count;
    }
}
