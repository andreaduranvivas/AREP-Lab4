package edu.arep.myspark.sparkClient;
import edu.arep.api.MovieApiClient;
import edu.arep.myspark.LBSpark;
import edu.arep.myspark.peticiones.MiniSpark;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

import static edu.arep.myspark.peticiones.MiniSpark.*;

public class MisServicios {

    public static void main(String[] args) throws IOException, URISyntaxException, IllegalAccessException, ClassNotFoundException, InvocationTargetException {

        get("/hola", (req) -> {
            try {
                String movieTitle = req.getQuery().split("=")[1];
                return MovieApiClient.fetchMovieData(movieTitle);
            } catch (Exception e){
                return "Movie not found :(";
            }
        });


        post("/hello", (req) -> {
            JSONObject body = req.getBody();
            try {
                String movieTitle = (String) body.get("name");
                return MovieApiClient.fetchMovieData(movieTitle);
            } catch (Exception e){
                return "Movie not found :(";
            }
        });

        start();
    }
}
