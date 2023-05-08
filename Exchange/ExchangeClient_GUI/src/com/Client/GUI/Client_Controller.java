package com.Client.GUI;

import com.Client.Networking.Client;
import com.Client.Portfolio.Portfolio;
import com.sun.media.jfxmedia.events.PlayerStateEvent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Client_Controller implements Initializable {

    private Client client;
    private Portfolio portfolio;
    private double currentStockProice = 0;

    private String string;

    @FXML
    private Button buy_button;

    @FXML
    private TextArea deposit;

    @FXML
    private Button sell_button;

    @FXML
    private LineChart<Number, Number> stock_chart;
    private XYChart.Series series;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        // не понял зачем
//        assert buy_button != null : "fx:id=\"buy_button\" was not injected: check your FXML file 'client_GUI.fxml'.";
//        assert deposit != null : "fx:id=\"deposit\" was not injected: check your FXML file 'client_GUI.fxml'.";
//        assert sell_button != null : "fx:id=\"sell_button\" was not injected: check your FXML file 'client_GUI.fxml'.";
//        assert stock_chart != null : "fx:id=\"stock_chart\" was not injected: check your FXML file 'client_GUI.fxml'.";
//        // до этого момента

        // создания объека для хранения иформации о состоянии счета клиента(простейшая структура, как в C).
        portfolio = new Portfolio(1000, 0);

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        stock_chart = new LineChart<Number, Number>(xAxis, yAxis);
        series = new XYChart.Series();

        stock_chart.getData().add(series);

        try {
            client = new Client(new Socket("localhost", 1234));

            // идея - прислать данные о состоянии счета клиента серверу сразу же после подключения клиента к серверу
            client.sendMessageToServer("-P" + " " + portfolio.getDeposit() + " " + portfolio.getStockCount());

        } catch (IOException e) {
            System.out.println("Error in creating Client class");
            //e.printStackTrace();
        }

        client.receiveMessageFromServer(string, portfolio, stock_chart, series);

    }

    @FXML
    void buy_processing(ActionEvent event) {
        client.sendMessageToServer("-b" + " " + currentStockProice);
        System.out.println("buy signal was sent");
    }

    @FXML
    void sell_processing(ActionEvent event) {
        client.sendMessageToServer("-s" + " " + currentStockProice);
        System.out.println("sell signal was sent");
    }

    public static void updatePortfolioInfo(String[] parsedData, Portfolio portfolio, String flag) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                double DepositTmp = portfolio.getDeposit();
                int StockCountTmp = portfolio.getStockCount();
                if (flag == "-s") {
                    portfolio.setDeposit(DepositTmp + Double.parseDouble(parsedData[1]));
                    portfolio.setStockCount(StockCountTmp - Integer.parseInt(parsedData[2]));
                } else if (flag == "-b") {
                    portfolio.setDeposit(DepositTmp - Double.parseDouble(parsedData[1]));
                    portfolio.setStockCount(StockCountTmp + Integer.parseInt(parsedData[2]));
                } else {
                    System.out.println("Unknown operation");
                }
            }
        });
    }

    public static void updateChartData(String[] parsedData, LineChart<?, ?> chart, XYChart.Series DataSeries) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                DataSeries.getData().add(new XYChart.Data<Integer, Double>(Integer.parseInt(parsedData[1]), Double.parseDouble(parsedData[2])));
                System.out.println("chart has been updated");
            }
        });
    }

}