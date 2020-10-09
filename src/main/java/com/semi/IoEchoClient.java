package com.semi;

import java.io.BufferedInputStream;
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

        IoEchoClient ioEchoClient = new IoEchoClient();
        ioEchoClient.readFromServer(inputStream);
        ioEchoClient.writeToServer(outputStream);

        socket.close();

    }

    public void readFromServer(InputStream inputStream) {
        new MsgReader(inputStream);
    }

    public void writeToServer (OutputStream outputStream) {
        InputStream inputFromConsole = new BufferedInputStream(System.in);
        try {
            while (true) {

                int read;
                byte[] bytes = new byte[4];
                while ((read = inputFromConsole.read(bytes)) != -1) {
                    String input = new String(bytes);
                    System.out.println("byte: " + read + " bytes: " + input);
                    outputStream.write(input.getBytes());
                    if (input.toLowerCase().equals("exit")) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class MsgReader extends Thread {

        private final InputStream inputStream;
        public MsgReader (InputStream inputStream) {
            super();
            this.inputStream = inputStream;
            start();
        }

        @Override
        public void run() {
            try {
                byte[] bytes = new byte[2];
                while (inputStream.read(bytes) != 1) {
                    System.out.println("Msg from server: " + new String(bytes));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
