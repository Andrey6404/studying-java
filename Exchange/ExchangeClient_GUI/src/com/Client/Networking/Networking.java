package com.Client.Networking;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Networking {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;;
    private String username;

    public Networking(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(String messageToSend) {
        try {
//            bufferedWriter.write(username);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();

            bufferedWriter.write(messageToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            System.out.println("Error: IOException in networking.sendMessage()");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromExchange;
                while (socket.isConnected()) {
                    try {
                        msgFromExchange =  bufferedReader.readLine();
                        System.out.println(msgFromExchange);
                        String tmp[] = msgFromExchange.split(" ");

                        if (tmp[0] == "-u")
                        {

                        }

                        if (tmp[0] == "-b")
                        {

                        }

                        if (tmp[0] == "-s")
                        {

                        }

                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
