import java.io.File;
import java.util.Objects;

public class FileRecursion {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\flami\\OneDrive\\Рабочий стол\\Code\\JavaOPS\\basejava");
        doRecursionFiles(file);
    }

    static void doRecursionFiles(File file) {
        for (File file1 : Objects.requireNonNull(file.listFiles())) {
            if (file1.isDirectory()) {
                System.out.println(file1.getName());
                doRecursionFiles(file1);
            } else {
                System.out.println("  " + file1.getName());
            }

        }
    }
}
