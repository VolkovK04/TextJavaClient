package org.example;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public Client(String ip, int port) {
        try{
            socket = new Socket(ip, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Connected");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void send(String message) throws IOException {
        writer.write(message + "\n");
        writer.flush();
    }
    public void start() throws IOException {
        while (!socket.isClosed()) {
            String message = reader.readLine();
            System.out.println(message);
        }
        System.out.println("Disconnected");
    }
    public void close() throws IOException {
        socket.close();
        reader.close();
        writer.close();
    }
}
