package com.semi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class IoEchoClient {
    public static void main(String[] args) throws IOException {
        int port = 2020;
        Socket socket = new Socket("localhost", port);

        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        String test = "test";
        outputStream.write(test.getBytes());
        while (true) {

            int read;
            byte [] bytes = new byte[3];
            while ((read = inputStream.read(bytes)) != -1) {
                System.out.println("byte: " + read + " bytes: " + new String(bytes));
            }
            socket.close();
        }
    }
}
