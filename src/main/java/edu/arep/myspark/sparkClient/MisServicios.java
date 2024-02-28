package edu.arep.myspark.sparkClient;
import edu.arep.api.MovieApiClient;
import edu.arep.myspark.LBSpark;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import static edu.arep.myspark.peticiones.MiniSpark.get;
import static edu.arep.myspark.peticiones.MiniSpark.post;

public class MisServicios {
    public static void main(String[] args) throws IOException, URISyntaxException, IllegalAccessException {

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

    }
}
