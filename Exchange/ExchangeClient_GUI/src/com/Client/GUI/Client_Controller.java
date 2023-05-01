package com.Client.GUI;

import com.Client.Networking.Networking;
import com.Client.Portfolio.Portfolio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.Socket;

public class Client_Controller {

    private Networking networking;
    private Portfolio portfolio;
    private double currentStockProice = 0;

    @FXML
    private Button buy_button;

    @FXML
    private TextArea deposit;

    @FXML
    private Button sell_button;

    @FXML
    private LineChart<Number, Number> stock_chart;

    @FXML
    void initialize() throws IOException {

        // не понял зачем
        assert buy_button != null : "fx:id=\"buy_button\" was not injected: check your FXML file 'client_GUI.fxml'.";
        assert deposit != null : "fx:id=\"deposit\" was not injected: check your FXML file 'client_GUI.fxml'.";
        assert sell_button != null : "fx:id=\"sell_button\" was not injected: check your FXML file 'client_GUI.fxml'.";
        assert stock_chart != null : "fx:id=\"stock_chart\" was not injected: check your FXML file 'client_GUI.fxml'.";
        // до этого момента

        portfolio = new Portfolio(1000, 0);

        Socket socket = new Socket("localhost", 1234);
        networking = new Networking(socket, "username");
        networking.listenForMessage();
        networking.sendMessage(portfolio.getDeposit() + " " + portfolio.getStockCount());
    }

    @FXML
    void buy_processing(ActionEvent event) {
        networking.sendMessage("-b" + " " + currentStockProice);
    }

    @FXML
    void sell_processing(ActionEvent event) {
        networking.sendMessage("-s" + " " + currentStockProice);
    }
}