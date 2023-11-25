import com.javaops.webapp.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume("name");
        Method method = r.getClass().getMethod("toString");
        System.out.println(method.invoke(r));
    }
}
