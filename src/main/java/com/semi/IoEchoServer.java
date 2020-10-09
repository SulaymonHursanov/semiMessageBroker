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
        IoEchoServer echoServer = new IoEchoServer();

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                echoServer.addNewClient(socket);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        serverSocket.close();
    }

    public void addNewClient (Socket socket) {
        Client client = new Client(socket);
        System.out.println("Connected new client with id: " + socket.getRemoteSocketAddress().toString());
    }

    class Client extends Thread {

        private final Socket socket;
        public Client(Socket socket) {
            super();
            this.socket = socket;
            start();
        }

        @Override
        public void run() {

            try {
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                int read;
                byte[] bytes = new byte[2];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

