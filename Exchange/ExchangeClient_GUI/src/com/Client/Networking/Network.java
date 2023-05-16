package com.Client.Networking;

import com.Client.GUI.Fresh_controller;
import com.Client.Portfolio.Portfolio;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.io.*;
import java.net.Socket;

public class Network implements Runnable{
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String servermessage;
    private Fresh_controller exsemplar;
    public boolean Inbox=false;

    private static boolean flowallow = true; //флаг остановки потока чтения с сервера

    public Network(Socket socket, Fresh_controller obj) {
        exsemplar =obj;
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Error creating client");
            closeEverything(/*socket, bufferedReader, bufferedWriter*/);
            e.printStackTrace();
        }
    }

    public void sendMessageToServer(String messageToSend) {
        try {
            bufferedWriter.write(messageToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println("Error sending message to server");
            e.printStackTrace();
            closeEverything(/*socket, bufferedReader, bufferedWriter*/);
        }
    }

//    public void receiveMessageFromServer(String string, Portfolio portfolio, LineChart<Number, Number> stock_chart, XYChart.Series<Number, Number> series) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (socket.isConnected()&&flowallow) {
//                    try {
//                        String messageFromServer = bufferedReader.readLine();
//
//                        // переменная для хранения результата от сервера в виде, подготовленном для обработки(строку от сервера парсим, отделяя строки пробелами)
//                        String[] parsedData = new String[3];
//                           parsedData = messageFromServer.split(" ");
//
//                        // вызов статических функций класса контроллра в зависимости от данных переданных сервером
//                        if (parsedData[0].equals("-b")) {
//                            Fresh_controller.updatePortfolioInfo(parsedData, portfolio, "-b");
//                        }
//                        if (parsedData[0].equals("-s")) {
//                            Fresh_controller.updatePortfolioInfo(parsedData, portfolio, "-s");
//                        }
//                        if (parsedData[0].equals("-P")) {
//                            System.out.println("Server accepted new Portfolio");
//                            System.out.println("Message from server:" + parsedData[2]);
//                        }
//                        if (parsedData[0].equals("-c")) {
//                            Fresh_controller.updateChartData(parsedData, stock_chart, series);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        System.out.println("Error receiving message from the client");
//                        closeEverything(/*socket, bufferedReader, bufferedWriter*/);
//                        //Thread.currentThread().interrupt();
//                        break;
//                    }
//                }
//                if(!flowallow) System.out.println("flag is down");
//                else System.out.println("Thread stopped with error");
//
//            }
//        }).start();
//    }
    public String getServermessage(){
        return this.servermessage;
    }
    public void run() {
        while (socket.isConnected()&&flowallow) {
            try {
                servermessage = bufferedReader.readLine();
                //System.out.println("RUN TRAY:: new message::"+servermessage);
                Inbox=true;
                exsemplar.reciver(servermessage.split(" "));
                // переменная для хранения результата от сервера в виде, подготовленном для обработки(строку от сервера парсим, отделяя строки пробелами)
                /*String[] parsedData = new String[3];
                parsedData = messageFromServer.split(" ");
                // вызов статических функций класса контроллра в зависимости от данных переданных сервером
                if (parsedData[0].equals("-b")) {
                    Fresh_controller.updatePortfolioInfo(parsedData, portfolio, "-b");
                }
                if (parsedData[0].equals("-s")) {
                    Fresh_controller.updatePortfolioInfo(parsedData, portfolio, "-s");
                }
                if (parsedData[0].equals("-P")) {
                    System.out.println("Server accepted new Portfolio");
                    System.out.println("Message from server:" + parsedData[2]);
                }
                if (parsedData[0].equals("-c")) {
                    Fresh_controller.updateChartData(parsedData, stock_chart, series);
                }*/
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error receiving message from the client");
                closeEverything(/*socket, bufferedReader, bufferedWriter*/);
                //Thread.currentThread().interrupt();
                break;
            }
        }
        if(!flowallow) System.out.println("flag is down");
        else System.out.println("Thread stopped with error");
    }

    public void closeEverything(/*Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter*/) {
        try {
            flowallow = false;
            //receiveMessageFromServer.close();
            if (this.bufferedReader != null) {
                this.bufferedReader.close();
            }
            if (this.bufferedWriter != null) {
                this.bufferedWriter.close();
            }
            if (this.socket != null) {
                this.socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
