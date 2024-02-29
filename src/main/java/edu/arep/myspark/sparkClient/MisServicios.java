package edu.arep.myspark.sparkClient;
import edu.arep.api.MovieApiClient;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

import static edu.arep.myspark.peticiones.MiniSpark.*;

public class MisServicios {

    public static void main(String[] args) throws IOException, URISyntaxException, IllegalAccessException, ClassNotFoundException, InvocationTargetException {

        get("/movie", (req) -> {
            try {
                String movieTitle = req.getQuery().split("=")[1];
                return String.valueOf(MovieApiClient.fetchMovieData(movieTitle));
            } catch (Exception e){
                return "Movie not found :(";
            }
        });

        post("/movie", (req) -> {
            JSONObject body = req.getBody();
            try {
                String movieTitle = (String) body.get("title");
                return String.valueOf(MovieApiClient.fetchMovieData(movieTitle));
            } catch (Exception e){
                return "Movie not found :(";
            }
        });

        start();
    }
}
