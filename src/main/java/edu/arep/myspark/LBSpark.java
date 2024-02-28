package edu.arep.myspark;

import edu.arep.myspark.handle.Function;
import edu.arep.myspark.nuevasFunciones.StaticFiles;
import edu.arep.myspark.peticiones.MiniSpark;
import edu.arep.myspark.peticiones.Request;
import edu.arep.myspring.components.ComponentLoader;
import edu.arep.webClient.FileIIdentifier;
import edu.arep.webClient.RestResponse;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LBSpark {

    private static boolean running = false;
    private static StaticFiles staticFiles = new StaticFiles();
    private static LBSpark _instance = new LBSpark();

    public LBSpark() {}

    public static LBSpark getInstance() {
        return _instance;
    }


    /**
     * Runs the server to receive requests, it accepts the request, processes the specific action, and sends the corresponding response.
     *
     * @param  args  The arguments to the function
     * @throws IOException when an I/O exception occurs
     * @throws URISyntaxException when there is an invalid syntax
     */
    public static void runServer(String[] args) throws IOException, URISyntaxException, IllegalAccessException, InvocationTargetException {

        ServerSocket serverSocket = startServer(35000);
        running = true;

        while (running) {
            Socket clientSocket = startClientSocket(serverSocket);

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            StringBuilder rawRequest = getRawResponse(in);
            sendSpringResponse(clientSocket, rawRequest.toString());

            in.close();
        }
    }

    /**
     * Starts a server on the specified port.
     *
     * @param  port  the port number to start the server on
     * @return       the server socket object
     */

    public static ServerSocket startServer(int port) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(1);
        }

        return serverSocket;
    }


    /**
     * Starts a client socket using the provided server socket.
     *
     * @param  serverSocket   the server socket to accept connections from
     * @return                the client socket for the accepted connection
     */
    private static Socket startClientSocket(ServerSocket serverSocket){
        Socket clientSocket = null;
        try {
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        return clientSocket;
    }


    /**
     * Retrieves the raw response from the BufferedReader and creates a String to use it.
     *
     * @param  in   the BufferedReader input
     * @return      the raw response as a StringBuilder
     */
    private static StringBuilder getRawResponse(BufferedReader in) throws IOException {
        StringBuilder rawRequest = new StringBuilder();
        rawRequest.append(in.readLine()).append("\n");
        while (in.ready()) {
            rawRequest.append((char) in.read());
        }
        return rawRequest;
    }


    /**
     * Sends a Spark response using the specified client socket and raw request.
     *
     * @param  clientSocket   the client socket to send the response to
     * @param  rawRequest     the raw request received from the client
     * @throws IOException    if an I/O error occurs
     * @throws URISyntaxException if a string could not be parsed as a URI reference
     */
    private static void sendSparkResponse(Socket clientSocket, String rawRequest) throws IOException, URISyntaxException {
        System.out.println("Received: " + rawRequest.split("\n")[0]);
        String method = rawRequest.split(" ")[0];
        String path = rawRequest.split(" ")[1];
        URI restPath = new URI(path);
        URI resourcePath = new URI("/target/classes/public" + path);
        Function service = MiniSpark.search(restPath.getPath(), method);

        if(service != null){
            Request req = new Request(rawRequest);
            String response = service.handle(req);
            RestResponse.sendResponse(clientSocket, response);
        } else {
            FileIIdentifier.sendResponse(resourcePath, clientSocket);
        }
    }


    /**
     * Sends a Spring response based on the client socket and raw request.
     *
     * @param  clientSocket  the client socket to send the response to
     * @param  rawRequest    the raw request received from the client
     * @throws IOException if an I/O error occurs
     * @throws URISyntaxException if a string could not be parsed as a URI reference
     * @throws InvocationTargetException if the underlying method throws an exception
     * @throws IllegalAccessException if the class or its nullary constructor is not accessible
     */
    private static void sendSpringResponse(Socket clientSocket, String rawRequest) throws IOException, URISyntaxException, InvocationTargetException, IllegalAccessException {
        System.out.println("Received: " + rawRequest.split("\n")[0]);
        String method = rawRequest.split(" ")[0];
        String path = rawRequest.split(" ")[1];
        URI restPath = new URI(path);
        URI resourcePath = new URI("/target/classes/public" + path);

        Method service = ComponentLoader.search(restPath.getPath(), method);

        if(service != null){
            Request req = new Request(rawRequest);
            String response = ComponentLoader.execute(service, req);
            RestResponse.sendResponse(clientSocket, response);
        } else {
            FileIIdentifier.sendResponse(resourcePath, clientSocket);
        }


    }

}
