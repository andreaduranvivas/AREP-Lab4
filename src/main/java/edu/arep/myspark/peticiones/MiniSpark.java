package edu.arep.myspark.peticiones;

import edu.arep.myspark.LBSpark;
import edu.arep.myspark.handle.Function;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class MiniSpark {
    private static final Map<String, Function> GET_SERVICES = new HashMap<>();
    private static final Map<String, Function> POST_SERVICES = new HashMap<>();

    /**
     * A method to get a resource from the specified endpoint.
     *
     * @param  endpoint   the endpoint for the resource
     * @param  lambda     the function to handle the response
     */
    public static void get(String endpoint, Function lambda){
        GET_SERVICES.put(endpoint, lambda);
    }

    /**
     * Add a new endpoint and associated lambda function for POST requests.
     *
     * @param  endpoint   the endpoint to handle POST requests
     * @param  lambda     the lambda function to execute for the endpoint
     * @return            void
     */
    public static void post(String endpoint, Function lambda){
        POST_SERVICES.put(endpoint, lambda);
    }

    /**
     * A function to search for a specific endpoint using the given verb.
     *
     * @param  endpoint  the endpoint to search for
     * @param  verb      the HTTP verb to use for the search
     * @return           the function that corresponds to the endpoint and verb, or null if not found
     */
    public static Function search(String endpoint, String verb){
        switch (verb){
            case "GET":
                return GET_SERVICES.get(endpoint);
            case "POST":
                return POST_SERVICES.get(endpoint);
        }
        return null;
    }

    /**
     * It starts the application by running the LBSpark server.
     *
     * @throws IOException              If an I/O error occurs.
     * @throws URISyntaxException        If a string could not be parsed as a URI reference.
     * @throws InvocationTargetException If the underlying method throws an exception.
     * @throws IllegalAccessException    If the class or its nullary constructor is not accessible.
     * @throws ClassNotFoundException    If the class definition was not found.
     */

    public static void start() throws IOException, URISyntaxException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        LBSpark.runServer(new String[]{});
    }


}
