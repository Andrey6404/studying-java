import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

import javafx.event.ActionEvent;

public class Controller {
    private Networking netconnect;

    @FXML
    private ComboBox<String> inmeasure;

    @FXML
    private TextField inputspase;

    @FXML
    private ComboBox<String> outmeasure;

    @FXML
    private TextField outputvalue;

    @FXML
    private Button sendingbutton;

    @FXML
    private TextArea serverdataspase;

    @FXML
    void initialize() {
        netconnect = new Networking("127.0.0.1", 4004);

        // надо получить данные для списков
        this.setComboBox();
    }

    @FXML
    private void send(ActionEvent event) {
        outputvalue.clear();
        serverdataspase.clear();
        // String message = inputspase.getText();
        // inputspase.setLayoutX(inputspase.getLayoutX() + 4);
        System.out.println(inputspase.getText() + " " + inmeasure.getValue() + " to " + outmeasure.getValue());
        netconnect.SocketSending(inputspase.getText() + " " + inmeasure.getValue() + " to " + outmeasure.getValue());
        String message = netconnect.getmessage();

        if (Character.isDigit(message.charAt(0)))
            outputvalue.setText(message);
        else
            serverdataspase.setText(message);
        // SocOverGUI.SocketSending(message);
    }

    @FXML
    public void closer() {
        netconnect.CloseSocket();
        // System.out.println("null5");
    }

    private void setComboBox() {
        try {
            String measures = netconnect.getArrayMeasure();
            String[] measuARR = measures.split("/");
            for (String measure : measuARR) {
                inmeasure.getItems().add(measure);
                outmeasure.getItems().add(measure);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
