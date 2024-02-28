package edu.arep.myspring.components;

import edu.arep.myspark.peticiones.Request;
import edu.arep.myspring.runtime.Component;
import edu.arep.myspring.runtime.GetMapping;
import edu.arep.myspring.runtime.PostMapping;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ComponentLoader {

    private static final Map<String, Method> GET_SERVICES = new HashMap<>();
    private static final Map<String, Method> POST_SERVICES = new HashMap<>();

    private static final String PACKAGE_NAME = "edu/arep/myspring/components";


    public static void loadComponents() throws ClassNotFoundException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URL packageURL = classLoader.getResource(PACKAGE_NAME);

        loadMethods(packageURL);
    }

    private static void loadMethods(URL packageURL) throws ClassNotFoundException {
        if (packageURL != null) {
            String packagePath = packageURL.getPath();
            if (packagePath != null) {
                File packageDir = new File(packagePath);
                if (packageDir.isDirectory()) {
                    File[] files = packageDir.listFiles();
                    assert files != null;
                    checkComponent(files);
                }
            }
        }
    }

    private static void checkComponent(File[] files) throws ClassNotFoundException {
        for (File file : files) {
            String className = file.getName();
            if (className.endsWith(".class")) {
                className = PACKAGE_NAME + "/" + className.substring(0, className.length() - 6);
                Class<?> clazz = Class.forName(className.replace("/", "."));
                if(clazz.isAnnotationPresent(Component.class)){
                    saveComponentMethods(clazz);
                }
            }
        }
    }

    private static void saveComponentMethods(Class<?> clazz){
        Method[] methods = clazz.getDeclaredMethods();
        for(Method m : methods){
            if(m.isAnnotationPresent(GetMapping.class)){
                String endpoint = m.getAnnotation(GetMapping.class).value();
                GET_SERVICES.put(endpoint, m);
            } else if(m.isAnnotationPresent(PostMapping.class)){
                String endpoint = m.getAnnotation(PostMapping.class).value();
                POST_SERVICES.put(endpoint, m);
            }
        }
    }

    public static Method search(String endpoint, String verb){
        switch (verb){
            case "GET":
                return GET_SERVICES.get(endpoint);
            case "POST":
                return POST_SERVICES.get(endpoint);
        }
        return null;
    }

    public static String execute(Method method, Request param) throws InvocationTargetException, IllegalAccessException {
        return (String) method.invoke(null, param);
    }

}
