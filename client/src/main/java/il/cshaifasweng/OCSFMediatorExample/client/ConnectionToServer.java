package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ConnectionToServer {

    @FXML
    private Button connectBtn;

    @FXML
    private TextField ipText;

    @FXML
    void connectAction(ActionEvent event) throws IOException {
        if (ipText.getText().equals("")) {
            showAlertDialog(Alert.AlertType.ERROR, "Error", "Please enter a valid IP address");
        } else {
            boolean connection = SimpleClient.newClinet(ipText.getText());
            if (connection) {
                SimpleChatClient.switchScreen("LoginController");
            } else {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "No server has been found with IP: " + ipText.getText());
            }
        }
    }

    @FXML
    void ipAction(ActionEvent event) {

    }

    public void showAlertDialog(Alert.AlertType alertType, String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
