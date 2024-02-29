package edu.arep.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MovieApiClient {
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String API_KEY = "b7232f2";
    private static final String BASE_URL = "http://www.omdbapi.com/?apikey=" + API_KEY + "&t=";
    private static final CacheMovie cache = CacheMovie.getInstance();


    private static HttpURLConnection makeGetRequest(String movieTitle) throws IOException {
        URL obj = new URL(BASE_URL + movieTitle);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        return connection;
    }

    private static StringBuffer buildResponse(HttpURLConnection connection) throws IOException {
        StringBuffer response = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }

    public static String fetchMovieData(String movieTitle) throws Exception {
        if(cache.isCached(movieTitle)){
            System.out.println("Estaba en Cache");
            return cache.getMovie(movieTitle);
        }

        HttpURLConnection connection = makeGetRequest(movieTitle);

        StringBuffer response = new StringBuffer();
        int responseCode = connection.getResponseCode();
        System.out.println("GET Response Code:: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            response = buildResponse(connection);
            System.out.println(response);
        } else {
            System.out.println("GET request not worked");
        }

        System.out.println("GET DONE");
        cache.save(movieTitle, response.toString());
        return response.toString();
    }

    public static JsonObject fetchMovieData2(String movieTitle) throws Exception {
        if (cache.isCached(movieTitle)) {
            System.out.println("Estaba en Cache");
            String cachedMovieInfo = cache.getMovie(movieTitle);
            return new Gson().fromJson(cachedMovieInfo, JsonObject.class);
        }

        HttpURLConnection connection = makeGetRequest(movieTitle);

        StringBuffer response = new StringBuffer();
        int responseCode = connection.getResponseCode();
        System.out.println("GET Response Code:: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            response = buildResponse(connection);
            System.out.println(response);
        } else {
            System.out.println("GET request not worked");
        }

        System.out.println("GET DONE");
        String responseString = response.toString();
        cache.save(movieTitle, responseString);
        return new Gson().fromJson(responseString, JsonObject.class);
    }
}