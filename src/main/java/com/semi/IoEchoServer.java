package com.semi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class IoEchoServer {

    public static void main(String[] args) throws IOException {
        int port = 2020;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Started server on port " + port);

        while (true) {
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            int read;
            byte[] bytes = new byte[2];
            while ((read = inputStream.read(bytes)) != -1) {
                System.out.println(read);
                outputStream.write(bytes, 0, read);
            }
            System.out.println(read);

            socket.close();
            break;
        }

        serverSocket.close();
    }
}

