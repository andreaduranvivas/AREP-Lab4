package edu.arep.myspring.runtime;

import edu.arep.myspring.example.Test;
import edu.arep.myspring.runtime.Component;
import edu.arep.myspring.runtime.GetMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {

    public static Map<String, Method> componentes = new HashMap<String, Method>();

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {

        //1. Cargar los componentes anotados con @Component
        //Para el primer prototipo lo leeré de la linea de comados
        //Para la entrega final lo debemos leer del disco

        Class c = Class.forName(args[0]);

        if (c.isAnnotationPresent(Component.class)){

            //2. Almacenar todos los métodos en una estrucura <K,V>
            // La llave será el path del webservice y el valor son métodos
            // OJO: Todos los métodos deben ser estáticos

            for (Method m : Class.forName(args[0]).getMethods()) {
                if (m.isAnnotationPresent(GetMapping.class)) {
                    componentes.put(m.getAnnotation(GetMapping.class).value(), m);
                }
            }

        }
        //3. Si llega una ruta que está enlazada a un componente
        // ejecutar el componente. No olvidar los encabezados
        // NOTA: Implemente pasar parámetros


        // Simulador: Lo que hace el web server
        String pathDelGet = "/components/movie";
        String queryValue = "it";

        Method m = componentes.get(pathDelGet.substring(11));

        if (m != null){
            System.out.println("Salida: " + m.invoke(null, queryValue));
        }
    }
}