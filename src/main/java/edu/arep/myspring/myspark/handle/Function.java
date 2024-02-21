package edu.arep.myspring.myspark.handle;

public interface Function {
    /**
     * A functional interface for handling requests.
     *
     * @param requestQuery the query string of the request
     * @return the response to the request
     * @throws Exception if an error occurs while handling the request
     */
    public String handle(String requestQuery) throws Exception;
}
