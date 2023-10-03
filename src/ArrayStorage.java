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
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return new Resume[0];
    }

    int size() {
        return 0;
    }
}
