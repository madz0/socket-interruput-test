package com.github.madz0;

import java.io.*;
import java.net.Socket;

public class Client implements Closeable {
    private Socket client;
    private PrintWriter writer;
    private BufferedReader reader;

    public Client(String ip, int port) throws IOException {
        client = new Socket(ip, port);
        writer = new PrintWriter(client.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    public void sendAndWait() throws IOException {
        reader.readLine();
        System.out.println(Thread.interrupted());
    }

    public void close() throws IOException {
        reader.close();
        writer.close();
        client.close();
    }
}
