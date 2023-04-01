import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {
    int count = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Networking SocOverGUI = new Networking("127.0.0.1", 8000);

        // String[] LocalStrings = {};
        // Stream<String> localStream = Stream.of(LocalStrings);

        TextField textField1 = new TextField();
        // TextField textField2 = new TextField();
        Button button = new Button("Submit");
        button.setTranslateX(250);
        button.setTranslateY(75);
        // Creating labels
        Label label1 = new Label("Input: ");
        // Label label2 = new Label("Email: ");
        // Setting the message with read data
        Text text = new Text("");
        // Setting font to the label
        Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10);
        text.setFont(font);
        text.setTranslateX(15);
        text.setTranslateY(250);
        text.setFill(Color.BROWN);
        text.maxWidth(580);
        text.setWrappingWidth(580);
        // Displaying the message
        button.setOnAction(e -> {
            // Retrieving data
            String name = textField1.getText();
            // String email = textField2.getText();
            textField1.clear();
            // textField2.clear();
            SocOverGUI.SocketSending(name);
            text.setText(
                    // "Hello " + name + "Welcome to Tutorialspoint. From now, we will communicate
                    // with you at " + email);
                    SocOverGUI.datafromserver);
        });
        // Adding labels for nodes
        HBox box = new HBox(5);
        box.setPadding(new Insets(25, 5, 5, 50));
        box.getChildren().addAll(label1, textField1 /* ,label2 , textField2 */);
        Group root = new Group(box, button, text);
        // Setting the stage
        Scene scene = new Scene(root, 595, 150, Color.BEIGE);
        stage.setTitle("Text Field Example");
        stage.setScene(scene);
        stage.show();
    }
}
