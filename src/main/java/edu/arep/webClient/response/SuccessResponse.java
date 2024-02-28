package edu.arep.webClient.response;

import edu.arep.webClient.response.ResponseInterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

public class SuccessResponse implements ResponseInterface{

    protected Socket clientSocket;
    protected URI requestedFile;
    protected String fileType;

    public SuccessResponse(Socket clientSocket, URI requestedFile, String fileType) {
        this.clientSocket = clientSocket;
        this.fileType = fileType;
        try {
            this.requestedFile = new URI("." + requestedFile);
        }catch (URISyntaxException e){
            this.requestedFile = requestedFile;
        }
    }

    @Override
    public void sendResponse() throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        String outputLine;

        outputLine = "HTTP/1.1 200 OK \r\n" +
                "Content-Type: " + getMimeType() + " \r\n" +
                "\r\n";
        outputLine += ResponseInterface.readFile(requestedFile.getPath());

        out.println(outputLine);

        out.close();
        clientSocket.close();
    }

    private String getMimeType() {
        switch (fileType){
            case "js":
                return "text/javascript";
            case "css":
                return "text/css";
            case "html":
                return "text/html";
        }
        return "";
    }


}
