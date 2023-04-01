import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;

public class gui {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextFlow dialLOG;

    @FXML
    private TextField inputstream;

    @FXML
    private Button sendingbutton;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    @FXML
    void initialize() {
        sendingbutton.setOnAction(event -> {

        });

        assert dialLOG != null : "fx:id=\"dialLOG\" was not injected: check your FXML file 'drug-drop-test.fxml'.";
        assert inputstream != null
                : "fx:id=\"inputstream\" was not injected: check your FXML file 'drug-drop-test.fxml'.";
        assert sendingbutton != null
                : "fx:id=\"sendingbutton\" was not injected: check your FXML file 'drug-drop-test.fxml'.";
        assert x1 != null : "fx:id=\"x1\" was not injected: check your FXML file 'drug-drop-test.fxml'.";
        assert x2 != null : "fx:id=\"x2\" was not injected: check your FXML file 'drug-drop-test.fxml'.";
        assert x3 != null : "fx:id=\"x3\" was not injected: check your FXML file 'drug-drop-test.fxml'.";
        assert x4 != null : "fx:id=\"x4\" was not injected: check your FXML file 'drug-drop-test.fxml'.";

    }

}
