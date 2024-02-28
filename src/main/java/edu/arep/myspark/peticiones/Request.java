package edu.arep.myspark.peticiones;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class Request {
    private String verb;
    private JSONObject body;
    private String path;
    private String query;


    /**
     * Constructs a new Request object from the raw request string.
     *
     * @param  rawRequest   the raw request string
     * @throws URISyntaxException if the URI is not formatted strictly according to RFC 2396 and cannot be created
     * @return              void
     */
    public Request(String rawRequest) throws URISyntaxException {
        this.verb = rawRequest.split(" ")[0];
        URI uri = new URI(rawRequest.split(" ")[1]);
        this.path = uri.getPath();
        this.query = uri.getQuery();
        buildBody(rawRequest);
    }

    /**
     * Builds the body of the request from the raw request string.
     *
     * @param  rawRequest   the raw request string
     * @return              void
     */
    private void buildBody(String rawRequest){
        try {
            String[] requestLines = rawRequest.split("\n");
            this.body = new JSONObject(requestLines[requestLines.length - 1]);
        } catch (JSONException e){
            this.body = null;
        }
    }

    public String getVerb() {
        return verb;
    }

    public JSONObject getBody() {
        return body;
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return query;
    }



}
