/**
 * Sample Skeleton for 'LoginController.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginBoundry {

    @FXML // fx:id="loginBtn"
    private Button loginBtn; // Value injected by FXMLLoader

    @FXML // fx:id="passwordTxt"
    private TextField passwordTxt; // Value injected by FXMLLoader

    @FXML // fx:id="usernameBtn"
    private TextField usernameTxt; // Value injected by FXMLLoader

    private LoginController controller;

    @FXML
    void loginAction(ActionEvent event)
    {
        controller.login(usernameTxt.getText(), passwordTxt.getText());
        System.out.println("hhh");
    }

    @FXML
    void passwordAction(ActionEvent event)
    {

    }

    @FXML
    void usernameAction(ActionEvent event)
    {

    }
    public void setController(LoginController controller) {
        this.controller = controller;
    }
    @FXML
    public void initialize()
    {
        controller = new LoginController(this);
        this.setController(controller);
    }

}
