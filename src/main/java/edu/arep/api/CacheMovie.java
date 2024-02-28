package edu.arep.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheMovie {
    private final Map<String, String> cacheMemory;
    private static CacheMovie _cache = new CacheMovie();

    private CacheMovie() {
        cacheMemory = new ConcurrentHashMap<>();
    }

    public static CacheMovie getInstance() {
        return _cache;
    }

    /**
     * Saves the movie information in the cache memory.
     *
     * @param  title      the title of the movie
     * @param  movieInfo  the information about the movie
     */
    public void save(String title, String movieInfo) {
        cacheMemory.put(title, movieInfo);
    }

    /**
     * Check if the given title has been searched before.
     *
     * @param  title  the title to be checked
     * @return       true if the title has been searched before, false otherwise
     */
    public boolean isCached(String title) {
        return cacheMemory.containsKey(title);
    }

    /**
     * Get a movie from the cache memory by title.
     *
     * @param  title  the title of the movie to retrieve
     * @return       the movie corresponding to the provided title
     */
    public String getMovie(String title){
        return cacheMemory.get(title);
    }

    /**
     * Retrieves the cache memory as a map of strings.
     *
     * @return         	the cache memory as a map of strings
     */
    public Map<String, String> getCacheMemory() {
        return cacheMemory;
    }
}
