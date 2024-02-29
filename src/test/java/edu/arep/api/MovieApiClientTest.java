package edu.arep.api;

import edu.arep.myspring.example.Test;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class MovieApiClientTest extends TestCase {
    @Test
    public void testingGetMovies() throws Exception {

        Map<String, String> correctInfo = new HashMap<>();
        correctInfo.put("It", "{\"Title\":\"It\",\"Year\":\"2017\",\"Rated\":\"R\",\"Released\":\"08 Sep 2017\",\"Runtime\":\"135 min\",\"Genre\":\"Horror\",\"Director\":\"Andy Muschietti\",\"Writer\":\"Chase Palmer, Cary Joji Fukunaga, Gary Dauberman\",\"Actors\":\"Bill Skarsgård, Jaeden Martell, Finn Wolfhard\",\"Plot\":\"In the summer of 1989, a group of bullied kids band together to destroy a shape-shifting monster, which disguises itself as a clown and preys on the children of Derry, their small Maine town.\",\"Language\":\"English, Hebrew\",\"Country\":\"United States, Canada\",\"Awards\":\"10 wins & 49 nominations\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BZDVkZmI0YzAtNzdjYi00ZjhhLWE1ODEtMWMzMWMzNDA0NmQ4XkEyXkFqcGdeQXVyNzYzODM3Mzg@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"7.3/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"85%\"},{\"Source\":\"Metacritic\",\"Value\":\"69/100\"}],\"Metascore\":\"69\",\"imdbRating\":\"7.3\",\"imdbVotes\":\"597,951\",\"imdbID\":\"tt1396484\",\"Type\":\"movie\",\"DVD\":\"19 Dec 2017\",\"BoxOffice\":\"$328,874,981\",\"Production\":\"N/A\",\"Website\":\"N/A\",\"Response\":\"True\"}");

        String searchedInfo = String.valueOf(MovieApiClient.fetchMovieData("It"));
        assertEquals(correctInfo.get("It"), searchedInfo);
    }
}