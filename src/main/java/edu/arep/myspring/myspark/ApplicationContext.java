package edu.arep.myspring.myspark;

import edu.arep.myspring.myspark.handle.Function;
import edu.arep.myspring.myspark.handle.PostFunction;
import edu.arep.myspring.runtime.Component;
import edu.arep.myspring.runtime.GetMapping;
import edu.arep.myspring.runtime.PostMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {

    private Map<String, Function> getRoutes = new HashMap<>();
    private Map<String, PostFunction> postRoutes = new HashMap<>();
    private static ApplicationContext _instance = new ApplicationContext();

    public static ApplicationContext getInstance() {
        return _instance;
    }

    public void scan(String packageName) {
        // Implementar la lógica para escanear el paquete y cargar las clases anotadas con @Component
        // Aquí se puede usar reflexión para cargar las clases y buscar métodos anotados
    }

    public void registerComponent(Class<?> componentClass) {
        if (componentClass.isAnnotationPresent(Component.class)) {
            for (Method method : componentClass.getMethods()) {
                if (method.isAnnotationPresent(GetMapping.class)) {
                    GetMapping mapping = method.getAnnotation(GetMapping.class);
                    getRoutes.put(mapping.value(), (request) -> {
                        try {
                            return (String) method.invoke(null, request);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                } else if (method.isAnnotationPresent(PostMapping.class)) {
                    PostMapping mapping = method.getAnnotation(PostMapping.class);
                    postRoutes.put(mapping.value(), (body) -> {
                        try {
                            return (String) method.invoke(null, body);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        }
    }

    public Map<String, Function> getGetRoutes() {
        return getRoutes;
    }

    public Map<String, PostFunction> getPostRoutes() {
        return postRoutes;
    }
}
