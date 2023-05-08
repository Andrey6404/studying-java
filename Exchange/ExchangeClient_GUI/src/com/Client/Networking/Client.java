package com.Client.Networking;

import com.Client.GUI.Client_Controller;
import com.Client.Portfolio.Portfolio;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Error creating client");
            closeEverything(socket, bufferedReader, bufferedWriter);
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
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void receiveMessageFromServer(String string, Portfolio portfolio, LineChart<?, ?> stock_chart, XYChart.Series<Number, Number> series) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        String messageFromServer = bufferedReader.readLine();

                        // переменная для хранения результата от сервера в виде, подготовленном для обработки(строку от сервера парсим, отделяя строки пробелами)
                        String[] parsedData = new String[3];
                        parsedData = messageFromServer.split(" ");

                        // вызов статических функций класса контроллра в зависимости от данных переданных сервером
                        if (parsedData[0] == "-b") {
                            Client_Controller.updatePortfolioInfo(parsedData, portfolio, "-b");
                        }
                        if (parsedData[0] == "-s") {
                            Client_Controller.updatePortfolioInfo(parsedData, portfolio, "-s");
                        }
                        if (parsedData[0] == "-P") {
                            System.out.println("Server accepted new Portfolio");
                            System.out.println("Message from server:" + parsedData[2]);
                        }
                        if (parsedData[0].equals("-c")) {
                            Client_Controller.updateChartData(parsedData, stock_chart, series);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error receiving message from the client");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
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
