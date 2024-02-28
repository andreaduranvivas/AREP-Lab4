package edu.arep.myspring.components;

import edu.arep.api.MovieApiClient;
import edu.arep.myspark.peticiones.Request;
import edu.arep.myspring.runtime.Component;
import edu.arep.myspring.runtime.GetMapping;
import edu.arep.myspring.runtime.PostMapping;
import org.json.JSONObject;

import java.io.IOException;

@Component
public class WebComponent {

    @GetMapping("/hello")
    public static String getMovie(Request req){
        try {
            String movieTitle = req.getQuery().split("=")[1];
            return MovieApiClient.fetchMovieData(movieTitle);
        } catch (Exception e){
            return "Movie not found";
        }
    }

    @PostMapping("/hello")
    public static String postMovie(Request req){
        JSONObject body = req.getBody();
        try {
            String movieTitle = (String) body.get("name");
            return MovieApiClient.fetchMovieData(movieTitle);
        } catch (Exception e){
            return "Movie not found";
        }
    }



}
