package il.cshaifasweng.OCSFMediatorExample.server;

import com.mysql.cj.xdevapi.Client;
import il.cshaifasweng.OCSFMediatorExample.client.AlreadyLoggedIn;
import il.cshaifasweng.OCSFMediatorExample.client.LoginBoundry;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

import static il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient.loadFXML;

public class LoginController
{
    private LoginBoundry loginBoundry;

    public LoginController(LoginBoundry loginBoundry)
    {
        EventBus.getDefault().register(this);
        this.loginBoundry = loginBoundry;
    }
    public void showAlertDialog(AlertType alertType, String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
    @Subscribe
    public void checkLogin(Message message)
    {
        System.out.println("imheere");
        if (message!= null) {
            if (message.getTitle().equals("Login")) {
                User user = (User) message.getBody();
                // Validate the password

                if (user != null && user.isLoggedIn())
                {
                    try {
                        SimpleClient.getClient().sendToServer(new Message("NewClient", null));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Platform.runLater(() -> {
                        // Login success
                        showAlertDialog(AlertType.INFORMATION, "Login Successful", "Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");
                        if (user.getWhoAreYou() == 0) {
                            try {
                                SimpleChatClient.switchScreen("StudentBoundry");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (user.getWhoAreYou() == 1) {
                            try {
                                SimpleChatClient.switchScreen("TeacherBoundry");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Stage stage;
                    });
                    EventBus.getDefault().unregister(this);
                }
                else {
                    Platform.runLater(() -> {
                        // Login failure
                        showAlertDialog(AlertType.ERROR, "Login Failed", "Invalid username or password.");
                    });
                }

            }
        }
    }

// ...



    @Subscribe
    public void checkLoginI(AlreadyLoggedIn alreadyLoggedIn)
    {
        Platform.runLater(() -> {
            // Login failure
            showAlertDialog(AlertType.ERROR, "Login Failed", "you are already logged in.");
        });
        EventBus.getDefault().unregister(this);
    }
    public void login(String username, String password)
    {
        Object[] obj = {username, password};
        Message msg = new Message("Login", obj);
        try {
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public LoginBoundry getLoginBoundry() {
        return loginBoundry;
    }

    public void setLoginBoundry(LoginBoundry loginBoundry) {
        this.loginBoundry = loginBoundry;
    }
}
