package edu.arep.myspring.components;

import edu.arep.api.MovieApiClient;
import edu.arep.myspark.peticiones.Request;
import edu.arep.myspring.runtime.Component;
import edu.arep.myspring.runtime.GetMapping;
import edu.arep.myspring.runtime.PostMapping;
import edu.arep.myspring.runtime.RequestParam;
import org.json.JSONObject;

@Component
public class WebComponent {

    @GetMapping("/movie")
    public static String getMovie(@RequestParam("title") String movieTitle){
        try {
            return MovieApiClient.fetchMovieData(movieTitle);
        } catch (Exception e){
            return "Movie not found";
        }
    }

    @PostMapping("/movie")
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
