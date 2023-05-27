package com.Client.GUI;

import com.Client.Networking.Network;
import com.Client.Portfolio.Portfolio;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Client_Controller implements Initializable {

    private Network network;
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

//    public LineChart<Number, Number> getStock_chart()
//    {
//        return stock_chart;
//    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // создание объека для хранения иформации о состоянии счета клиента(простейшая структура, как в C).
        portfolio = new Portfolio(1000.0, 0);
        //NumberAxis xAxis = new NumberAxis();
        //NumberAxis yAxis = new NumberAxis();
        //stock_chart = new LineChart<Number, Number>(xAxis, yAxis);
        XYChart.Series series = new XYChart.Series();
        series.setName("stock1");
        stock_chart.getData().add(series);
        try {
            network = new Network(new Socket("localhost", 1234));
            // идея - прислать данные о состоянии счета клиента серверу сразу же после подключения клиента к серверу
            network.sendMessageToServer("-P" + " " + portfolio.getDeposit() + " " + portfolio.getStockCount());
        } catch (IOException e) {
            System.out.println("Error in creating Client class");
            //e.printStackTrace();
        }
        network.receiveMessageFromServer(string, portfolio, stock_chart, series);
    }

    @FXML
    void buy_processing(ActionEvent event) {
        network.sendMessageToServer("-b" + " " + portfolio.getDeposit() + " " + portfolio.getStockCount());
        System.out.println("buy signal was sent");
    }

    @FXML
    void sell_processing(ActionEvent event) {
        network.sendMessageToServer("-s" + " " + portfolio.getDeposit() + " " + portfolio.getStockCount());
        System.out.println("sell signal was sent");
    }

    public static void updatePortfolioInfo(String[] parsedData, Portfolio portfolio, String flag) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                double DepositTmp = portfolio.getDeposit();
                int StockCountTmp = portfolio.getStockCount();
                if (flag.equals("-s")) {
                    portfolio.setDeposit(DepositTmp + Double.parseDouble(parsedData[1]));
                    portfolio.setStockCount(StockCountTmp - Integer.parseInt(parsedData[2]));
                } else if (flag.equals("-b")) {
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
                DataSeries.getData().add(new XYChart.Data((parsedData[1]), Double.parseDouble(parsedData[2])));

                System.out.println("chart has been updated");
            }
        });
    }
    public void Controller_stop(){
        network.closeEverything();
    }

}