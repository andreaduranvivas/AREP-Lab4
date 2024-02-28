package edu.arep.webClient;

import edu.arep.webClient.response.ErrorResponse;
import edu.arep.webClient.response.ImageResponse;
import edu.arep.webClient.response.ResponseInterface;
import edu.arep.webClient.response.SuccessResponse;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class FileIIdentifier {

    private static ResponseInterface responseInterface;

    private static final List<String> supportedImgFormats = Arrays.asList("jpg", "png", "jpeg");

    private static final List<String> supportedTextFormats = Arrays.asList("html", "css", "js");


    /**
     * Returns the file type based on the given URI path.
     *
     * @param  path   the URI path from which to extract the file type
     * @return       the file type extracted from the URI path
     */
    private static String getFileType(URI path) {
        String fileFormat = "";
        try {
            fileFormat = path.getPath().split("\\.")[1];
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        return fileFormat;
    }

    private static boolean isText(URI path) {
        String fileFormat = path.getPath().split("\\.")[1];
        return supportedTextFormats.contains(fileFormat);
    }

    private static boolean isImage(URI path) {
        String fileFormat = path.getPath().split("\\.")[1];
        return supportedImgFormats.contains(fileFormat);
    }


    /**
     * Sends a response based on the resourcePath and clientSocket.
     *
     * @param  resourcePath   the URI of the requested resource
     * @param  clientSocket   the socket for the client connection
     * @throws IOException    if an I/O error occurs
     * @throws URISyntaxException   if the URI is not well formed
     */
    public static void sendResponse(URI resourcePath, Socket clientSocket) throws IOException, URISyntaxException {
        String path = resourcePath.getPath();
        String fileType = getFileType(resourcePath);

        if (path.equals("/target/classes/public/")) {
            responseInterface = new SuccessResponse(clientSocket, new URI(resourcePath.getPath() + "/formulario.html"), "html");
        } else if (!fileExists(resourcePath)) {
            responseInterface = new ErrorResponse(clientSocket);
        } else if (isImage(resourcePath)) {
            responseInterface = new ImageResponse(clientSocket, resourcePath, fileType);
        } else if (isText(resourcePath)) {
            responseInterface = new SuccessResponse(clientSocket, resourcePath, fileType);
        } else {
            responseInterface = new ErrorResponse(clientSocket);
        }
        responseInterface.sendResponse();
    }

    /**
     * Checks if the file exists at the specified URI path.
     *
     * @param  path    the URI path to the file
     * @return        true if the file exists, false otherwise
     */
    public static boolean fileExists(URI path) {
        File file = new File(System.getProperty("user.dir") + path);
        return file.exists();
    }

    /**
     * Sends an error response to the client using the given resource path and client socket.
     *
     * @param  resourcePath  the URI of the requested resource
     * @param  clientSocket  the socket connected to the client
     */
    private static void sendError(URI resourcePath, Socket clientSocket) throws IOException {
        responseInterface = new ErrorResponse(clientSocket);
        responseInterface.sendResponse();
    }

}
