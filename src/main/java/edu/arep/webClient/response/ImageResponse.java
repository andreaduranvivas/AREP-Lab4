package edu.arep.webClient.response;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

public class ImageResponse extends SuccessResponse {
    public ImageResponse(Socket clientSocket, URI requestedFile, String fileType) throws IOException, URISyntaxException {
        super(clientSocket, requestedFile, fileType);
    }

    @Override
    public void sendResponse() throws IOException {
        OutputStream out = clientSocket.getOutputStream();
        System.out.println(requestedFile);
        BufferedImage img = ImageIO.read(new File(System.getProperty("user.dir") + requestedFile));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(img, fileType, byteArrayOutputStream);

        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
        out.write(header().getBytes());
        out.write(byteArrayOutputStream.toByteArray());

        out.close();
        clientSocket.close();
    }

    private String header(){
        return "HTTP/1.1 200 OK \r\n" +
                "Content-Type: image/" + fileType + " \r\n" +
                "\r\n";
    }
}
