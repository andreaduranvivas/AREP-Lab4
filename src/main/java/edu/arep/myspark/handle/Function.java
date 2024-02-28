package edu.arep.myspark.handle;

import edu.arep.myspark.peticiones.Request;

public interface Function {

    /**
     * A function that takes a request and returns a response
     *
     * @param  req	description of parameter
     * @return      description of return value
     */
    public String handle(Request req);
}
