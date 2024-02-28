package edu.arep.webClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class RestResponse {

    /**
     * Sends a response to the client socket.
     *
     * @param  clientSocket   the client socket to send the response to
     * @param  response       the response to send
     */
    public static void sendResponse(Socket clientSocket, String response) throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        String outputLine;


        outputLine = "HTTP/1.1 200 OK \r\n" +
                "Content-Type: application/json \r\n" +
                "\r\n";
        outputLine += response;

        out.println(outputLine);

        out.close();
        clientSocket.close();
    }
}
