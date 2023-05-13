package com.Client.Networking;

import com.Client.GUI.Client_Controller;
import com.Client.Portfolio.Portfolio;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.io.*;
import java.net.Socket;

public class Network {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private static boolean flowallow = true; //флаг остановки потока чтения с сервера

    public Network(Socket socket) {
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

    public void receiveMessageFromServer(String string, Portfolio portfolio, LineChart<Number, Number> stock_chart, XYChart.Series<Number, Number> series) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()&&flowallow) {
                    try {
                        String messageFromServer = bufferedReader.readLine();

                        // переменная для хранения результата от сервера в виде, подготовленном для обработки(строку от сервера парсим, отделяя строки пробелами)
                        String[] parsedData = new String[3];
                           parsedData = messageFromServer.split(" ");

                        // вызов статических функций класса контроллра в зависимости от данных переданных сервером
                        if (parsedData[0].equals("-b")) {
                            Client_Controller.updatePortfolioInfo(parsedData, portfolio, "-b");
                        }
                        if (parsedData[0].equals("-s")) {
                            Client_Controller.updatePortfolioInfo(parsedData, portfolio, "-s");
                        }
                        if (parsedData[0].equals("-P")) {
                            System.out.println("Server accepted new Portfolio");
                            System.out.println("Message from server:" + parsedData[2]);
                        }
                        if (parsedData[0].equals("-c")) {
                            Client_Controller.updateChartData(parsedData, stock_chart, series);
                        }
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

        }).start();
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
