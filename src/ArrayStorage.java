import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    int ResumeCountering = 0;

    void clear() {
    }

    void save(Resume r) {
        storage[ResumeCountering] = r;
        ResumeCountering++;
    }

    Resume get(String uuid) {
        Resume NestedResume = null;
        for (Resume resume : storage) {
            if (resume.toString().equals(uuid)) {
                NestedResume = resume;
                break;
            }
        }
        return NestedResume;
    }

    void delete(String uuid) {
        int count = 0;
        for (Resume resume : storage) {
            if (Objects.equals(resume.uuid, uuid)) {
                break;
            } else count++;
        }
        storage[count] = storage[ResumeCountering - 1];
        storage[ResumeCountering - 1] = null;
        ResumeCountering--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage,size());
    }

    int size() {
        return ResumeCountering;
    }
}
