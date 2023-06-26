package org.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static ExecutorService thread = Executors.newCachedThreadPool();
    public static void main(String[] args) {

        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("connection.properties")) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String IP = properties.getProperty("ip");
        int PORT = Integer.parseInt(properties.getProperty("port"));

        try {
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            Client client = new Client(IP, PORT);
            client.send("Hello");
            client.send("world");
            client.send("!!!");

            thread.submit(() -> {
                try {
                    client.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            String str;
            do {
                str = consoleReader.readLine();
                client.send(str);
            } while (!str.equals("end"));
            client.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}