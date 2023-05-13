package com.Client.GUI;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.chart.LineChart;
        import javafx.scene.control.Button;
        import javafx.scene.control.MenuButton;
        import javafx.scene.control.TextArea;
        import javafx.scene.control.TextField;
        import javafx.scene.input.InputMethodEvent;

public class Fresh_controller {
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
    private Button buy_button;

    @FXML
    private Button sell_button;

    @FXML
    private LineChart<?, ?> stock_chart;

    @FXML
    private TextArea username;

    @FXML
    private TextArea wallet;

    @FXML
    void Buy_deal(ActionEvent event) {

    }

    @FXML
    void Buy_text_edited(InputMethodEvent event) {

    }

    @FXML
    void Sell_deal(ActionEvent event) {

    }

    @FXML
    void Sell_text_edited(InputMethodEvent event) {

    }

    @FXML
    void Ticket_button_pushed(ActionEvent event) {

    }


}

