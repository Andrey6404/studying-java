package com.Client.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Client_App extends Application {

    private Client_Controller controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("client_GUI.fxml")); // нельзя опустошать загрузчик
        Parent root;
        try {
            root = loader.load();
            controller = loader.getController();
            stage.setScene(new Scene(root));
            //Scene scene = new Scene(controller.getStock_chart(), 500, 500);
            //stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch();
    }


}
