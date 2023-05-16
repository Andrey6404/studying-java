package com.Client.GUI;
        import com.Client.Networking.Network;
        import com.Client.Portfolio.Portfolio;
        import javafx.animation.PauseTransition;
        import javafx.application.Platform;
        import javafx.beans.value.ChangeListener;
        import javafx.beans.value.ObservableValue;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.chart.LineChart;
        import javafx.scene.chart.XYChart;
        import javafx.scene.control.Button;
        import javafx.scene.control.MenuButton;
        import javafx.scene.control.TextArea;
        import javafx.scene.control.TextField;
        import javafx.scene.input.InputMethodEvent;
        import javafx.scene.input.KeyEvent;
        import javafx.util.Duration;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.net.Socket;
        import java.net.URL;
        import java.util.ResourceBundle;
        import java.util.Timer;
        import java.util.TimerTask;

public class Fresh_controller implements Initializable {
    @FXML
    private TextField Buy_count;
    @FXML
    private TextArea Buy_prise;
    @FXML
    private TextArea Buy_product;
    @FXML
    private TextArea Error_text;
    @FXML
    private TextField Sell_count;
    @FXML
    private TextArea Sell_prise;
    @FXML
    private TextArea Sell_product;
    @FXML
    private MenuButton Stok_list;
    @FXML
    private Button Ticket_button;
    @FXML
    private Button Buy_button;
    @FXML
    private Button Sell_button;
    @FXML
    private LineChart<Number, Number> stock_chart;
    private XYChart.Series<Number, Number> series;
    @FXML
    private TextArea username;
    @FXML
    private TextArea wallet;
    private Network network;
    private Portfolio portfolio;
    private boolean StupidFlagToAlive = true;
    private boolean NetworkConnected = false;
    private double currentStockPrice = 0;
    private String string;//вовоино наследие
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // создание объека для хранения иформации о состоянии счета клиента(простейшая структура, как в C).
        portfolio = new Portfolio(1000.0, 0);
        wallet.setText(String.valueOf(portfolio.getDeposit()));
        //NumberAxis xAxis = new NumberAxis();
        //NumberAxis yAxis = new NumberAxis();
        //stock_chart = new LineChart<Number, Number>(xAxis, yAxis);
        series = new XYChart.Series();
        series.setName("stock1");
        stock_chart.getData().add(series);
        try {
            network = new Network(new Socket("localhost", 1234),this);
            Thread nettread = new Thread(network);
            nettread.start();
            network.sendMessageToServer("-P" + " " + portfolio.getDeposit() + " " + portfolio.getStockCount());
        } catch (IOException e) {
            System.out.println("CONTROLLER initialize:: network is down");
        }


        // идея - прислать данные о состоянии счета клиента серверу сразу же после подключения клиента к серверу

        //network.receiveMessageFromServer(string, portfolio, stock_chart, series);

        //TryConnectToServ();
    }
        public void reciver(String[] word){
//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//                    String[] word;
//                    while (StupidFlagToAlive) {
//                        if (!network.Inbox) continue;
//                        network.Inbox = false;
//                        word = network.getServermessage().split(" ");
                        if(word.length!=3) return;
                        switch (word[0]){
                            case "-c":
//                                series = new XYChart.Series();
//                                series.setName("stock1");
//                                series.getData().add(new XYChart.Data(Integer.parseInt(vord[1]),Double.parseDouble(vord[2])));
//                                series.getData().add(new XYChart.Data(Integer.parseInt(word[1]),Double.parseDouble(word[2])));
//                                System.out.println(series.getData());
//                                stock_chart.getData().add(series);
                                updateChartData(word[1],Double.parseDouble(word[2]));
                                Buy_prise.setText(word[2]);
                                Sell_prise.setText(word[2]);
                                if(!Buy_count.getText().equals("")) Buy_product.setText(String.valueOf(Double.parseDouble(Buy_count.getText())*Double.parseDouble(Buy_prise.getText())));
                                else Buy_product.clear();
                                if(!Sell_count.getText().equals("")) Sell_product.setText(String.valueOf(Double.parseDouble(Sell_count.getText())*Double.parseDouble(Sell_prise.getText())));
                                else Sell_product.clear();
                                break;
                            case "-b":
                                portfolio.setDeposit(Double.parseDouble(word[1]));
                                portfolio.setStockCount(Integer.parseInt(word[2]));
                                wallet.setText(String.valueOf(portfolio.getDeposit()));
                                break;
                            case "-s":
                                portfolio.setDeposit(Double.parseDouble(word[1]));
                                portfolio.setStockCount(Integer.parseInt(word[2]));
                                wallet.setText(String.valueOf(portfolio.getDeposit()));
                                break;
                            case "-P":
                                System.out.println("ThreadRead:: server return portfolio");
                                break;
                            default:
                                System.out.println("ThreadRead:: unknown message::"+word);
                                break;
                        }
    }
//                    }
//                }
//            });//start;
//        }
//    private void TryConnectToServ(){
//        while(!NetworkConnected){
//            try {
//
//                NetworkConnected = true;
//            } catch (IOException e) {
//                System.out.println("Error in creating Client class");
//                //e.printStackTrace();
//            }
//        }
//    }

    private void updateChartData(String X, double Y){//(XYChart.Series<Number,Number> printdata){
        //series.getData().add(new XYChart.Series(String.valueOf(X),Y));
        //System.out.println("here could be data!!!!");
        //stock_chart.getData().add(series);
        series.getData().add(new XYChart.Data(X,Y));
    }
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                DataSeries.getData().add(new XYChart.Data((parsedData[1]), Double.parseDouble(parsedData[2])));
//
//                System.out.println("updateChartData:: chart has been updated");
//            }
//        });
    @FXML
    void Buy_deal(ActionEvent event) {
        network.sendMessageToServer("-b" + " " + portfolio.getDeposit() + " " + Buy_count.getText());
        Buy_count.clear();
        System.out.println("Buy_deal:: buy signal was sent to server");
    }

    @FXML
    void Buy_text_edited(KeyEvent event) {
        //Buy_prise.setText(stock_chart.);
        Buy_product.clear();
        if (!checkIsDigit(Buy_prise.getText(),"NND")) {
            System.out.println("Buy_text_edited:: Prise error");
            Buy_button.setDisable(true);
            return;
        }
        if (!checkIsDigit(Buy_count.getText(),"N")) {
            System.out.println("Buy_text_edited:: Count error");
            Buy_button.setDisable(true);
            return;
        }
        if(portfolio.getDeposit()<Double.parseDouble(Buy_prise.getText())*Double.parseDouble(Buy_count.getText())){
            System.out.println("Buy_text_edited:: Client is poor to this operation");
            Buy_button.setDisable(true);

        }else {
            Buy_button.setDisable(false);
            System.out.println("Buy_text_edited:: Correct data, allow");
            Buy_product.setText(Double.parseDouble(Buy_prise.getText()) * Double.parseDouble(Buy_count.getText()) + " $");
        }
    }

    @FXML
    void Sell_deal(ActionEvent event) {
        network.sendMessageToServer("-s" + " " + portfolio.getDeposit() + " " + Sell_count.getText());
        Sell_count.clear();
        System.out.println("Sell_deal:: sell signal was sent to server");
    }

    @FXML
    void Sell_text_edited(KeyEvent event) {
        if (!checkIsDigit(Sell_prise.getText(),"NND")) {
            System.out.println("Sell_text_edited:: Prise error");
            Sell_button.setDisable(true);
            return;
        }
        if (!checkIsDigit(Sell_count.getText(),"N")) {
            System.out.println("Sell_text_edited:: Count error");
            Sell_button.setDisable(true);
            return;
        }
        Sell_button.setDisable(false);
        System.out.println("Sell_text_edited:: Correct data, allow");
        Sell_product.setText(Double.parseDouble(Sell_prise.getText())*Double.parseDouble(Sell_count.getText())+" $");
    }

    @FXML
    void Ticket_button_pushed(ActionEvent event) {
    }

    private boolean checkIsDigit(String data, String type){
        try {
            double number = Double.parseDouble(data);
            if (type.equals("N")) //nature numb
                if(number >= 0 && (number % 1 == 0)) return true;
            if (type.equals("NND")) //not negative double
                if(number >= 0) return true;
            System.out.println("checkIsDigit:: number not correct. User was write: "+number);
            return false;
        } catch (NumberFormatException e) {
            System.out.println("checkIsDigit:: trouble in converting string->double. Input data: "+data);
            return false;
        }
    }
    /*private void SetErrorText(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //BufferedReader Err = new BufferedReader(new InputStreamReader(Runtime.getRuntime().getErrorStream()));
                while (StupidFlagToAlive){
                    if(true) {
                        Error_text.setVisible(true);
                        Error_text.setText(str);
                        PauseTransition visiblePause = new PauseTransition(
                                Duration.seconds(3)
                        );
                        visiblePause.setOnFinished(
                                event -> Error_text.setVisible(false)
                        );
                        visiblePause.play();
                    }
                }
            }
        }).start();
    }*/
    public void Controller_stop() {
        StupidFlagToAlive = false;
        try {
            network.closeEverything();
        } catch(Exception e) {
            System.out.println("CONTROLLER stop method:: network dont exist");
        }
    }
//    public static void updatePortfolioInfo() {
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                double DepositTmp = portfolio.getDeposit();
//                int StockCountTmp = portfolio.getStockCount();
//                if (flag.equals("-s")) {
//                    portfolio.setDeposit(DepositTmp + Double.parseDouble(parsedData[1]));
//                    portfolio.setStockCount(StockCountTmp - Integer.parseInt(parsedData[2]));
//                } else if (flag.equals("-b")) {
//                    portfolio.setDeposit(DepositTmp - Double.parseDouble(parsedData[1]));
//                    portfolio.setStockCount(StockCountTmp + Integer.parseInt(parsedData[2]));
//                } else {
//                    System.out.println("Unknown operation");
//                }
//            }
//        });
//    }

}

