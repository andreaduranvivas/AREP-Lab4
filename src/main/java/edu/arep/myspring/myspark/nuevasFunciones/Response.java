package edu.arep.myspring.myspark.nuevasFunciones;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private String contentType = "text/html";

    public Response() {
    }

    /**
     * Sets the content type to the specified type.
     *
     * @param type the content type to set
     */
    public void type(String type) {
        this.contentType = type;
    }

    public String getContentType() {
        return contentType;
    }

    /**
     * Parses the query string and returns a map of query parameters.
     *
     * @param query the query string to parse
     * @return a map of query parameters
     */
    public Map<String, String> readQueryParams(String query) {
        Map<String, String> params = new HashMap<>();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                String key = URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8);
                String value = URLDecoder.decode(pair.substring(idx +  1), StandardCharsets.UTF_8);
                params.put(key, value);
            }
        }
        return params;
    }
}

