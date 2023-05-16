package com.Server;

import com.Portfolio.Portfolio;
import com.StockPackage.Stock;
import com.StockPackage.StockPriceGenerator;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ClientHandler implements Runnable {
    static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private int ClientID = 0;
    private int NullRequestCounter = 0;
    private static int IDcounter = 0;


    // все последующие переменные должны относится к классу биржи
    // пока данные лежат к лассе ClientHandler. небходимо дописать для них отдельный класс Exchange (класс биржи)
    private Portfolio portfolio;
    private static Stock stock;
    // ----------------------------------------------------- //

    // следующие переменные используются для реализации логики генерации цены акции и ее отправлению всем подключенным клиентам
    static Timer timer;
    static TimerTask timerTask;
    private static long currentTick = 0;
    // ----------------------------------------------------- //

    // реализация отправки новой цены акции каждые 2 сек
    // идея - реализовать поток, общий для всех объектов класс ClientHandler, для отправления новой цены акции каждому клиенту
    // через определенныйпромежуток времени. Подумао, что это можно реазизовать через статические Timer and TimerTask,
    // т.к. они одинаковые для всех объектов этого класса
    // не проверял работу. Не уверен, что сработает.
    static {
        stock = new Stock("stock1", 1000, 0.01);

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                currentTick++;
                double newPrice = stock.GenerateNewPrice();
                broadcastMessageToAllConnectedClients("-c" + " " + currentTick + " " + newPrice);
            }
        };

        timer.scheduleAtFixedRate(timerTask, 0, 2000);
    }
    public static void broadcastMessageToAllConnectedClients(String messageToSend) {
        System.out.println("---Connecled---"+clientHandlers.size()+" users");
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                clientHandler.bufferedWriter.write(messageToSend);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
            } catch (IOException e) {
                clientHandler.closeEverything(clientHandler.socket, clientHandler.bufferedReader, clientHandler.bufferedWriter);
            }
        }
    }

    public ClientHandler(Socket socket) {
        // очень плохая реализация (все, что связано с ID), т.к. при удалении не сбрасывается счетчик IDcounter
        ClientID = this.hashCode();//IDcounter;
        System.out.println("new client ID = "+ClientID);
        //IDcounter++;

        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            clientHandlers.add(this);
            System.out.println(ClientID+" == try ok!");

        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);

            // возвращаем значение IDcounter, если объект ClientHandler не был сооздан
            //IDcounter--;
        }
    }

    @Override
    public void run() {
        String messageFromClient="";

        while (socket.isConnected()&&!(NullRequestCounter>=5)){
            System.out.println(clientHandlers.size());
            try {
                messageFromClient = bufferedReader.readLine();
                if (messageFromClient==null) {NullRequestCounter++; System.out.println("Strike "+NullRequestCounter);/*Thread.sleep(500);*/continue;}
                String answerToClient = parseMessageFromClient(messageFromClient);
                sendMessageToClient(answerToClient);
                NullRequestCounter = 0;
            } catch (IOException e) {
                removeClientHandler();
                NullRequestCounter++;
                //closeEverything(socket, bufferedReader, bufferedWriter);
                System.out.println("Error reading message from client. ClientID=" + this.ClientID);
            }
        }
        closeEverything(socket, bufferedReader, bufferedWriter);
        System.out.println(ClientID+" == is closing");
        removeClientHandler();
    }

    public String parseMessageFromClient(String messageFromClient) {
        String[]  parsedMessage;
        parsedMessage = messageFromClient.split(" ");
        if (parsedMessage[0].equals("-P")) {
            portfolio = new Portfolio(Double.parseDouble(parsedMessage[1]), Integer.parseInt(parsedMessage[2]));
            System.out.println("Server accepted new client's portfolio");
            return "-P" + " " + "accepted" + " " + "reservedInfo";
        }
        if (parsedMessage[0].equals("-b")) {
            clientBuySellStock("-b");
            System.out.println("Server accepted bue signal");
            return "-b" + " " + this.portfolio.getDeposit() + " " + this.portfolio.getStockCount();
        }
        if (parsedMessage[0].equals("-s")) {
            clientBuySellStock("-s");
            System.out.println("Server accepted sell signal");
            return "-s" + " " + this.portfolio.getDeposit() + " " + this.portfolio.getStockCount();
        }
        System.out.println("Unknown message from client");
        return "unknown operation";
    }

    // функция реализующая логику покупки и продажжи одной акции
    public void clientBuySellStock(String flag) {
        // не реализовано изменнение количества покупаемых и продаваемых акций. Пока "захардкожено" 1
        int stockAmount = 1;

        double transactionAmount =  stock.getCurrentPrice() * stockAmount;

        if (flag.equals("-b")) {
            this.portfolio.setDeposit(this.portfolio.getDeposit() - transactionAmount);
            this.portfolio.setStockCount(this.portfolio.getStockCount() + stockAmount);
            return;
        }
        if (flag.equals("-s")) {
            this.portfolio.setDeposit(this.portfolio.getDeposit() + transactionAmount);
            this.portfolio.setStockCount(this.portfolio.getStockCount() - stockAmount);
            return;
        }
    }

    public void sendMessageToClient(String messageToSend) {
            try {
                this.bufferedWriter.write(messageToSend);
                this.bufferedWriter.newLine();
                this.bufferedWriter.flush();
            } catch (IOException e) {
                System.out.println("Error sending message to client. ClientID=" + this.ClientID);
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
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
