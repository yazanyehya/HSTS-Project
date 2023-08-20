package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.AlreadyLoggedIn;
import il.cshaifasweng.OCSFMediatorExample.client.LoginBoundry;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

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
//                                handleUserTypeSelection("Student");
                                SimpleChatClient.switchScreen("studentBoundry");
                                Message newMessage = new Message("getStudentNotificationList", SimpleClient.getClient().getUser());
                                SimpleClient.getClient().sendToServer(newMessage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (user.getWhoAreYou() == 1) {
                            try {
//                                handleUserTypeSelection("Teacher");
                                SimpleChatClient.switchScreen("teacherBoundry");
                                Message newMessage = new Message("getTeacherNotificationList", SimpleClient.getClient().getUser());
                                SimpleClient.getClient().sendToServer(newMessage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (user.getWhoAreYou() == 2) {
                            try {
//                                handleUserTypeSelection("Principle");

                                SimpleChatClient.switchScreen("PrincipleBoundry");
                                Message newMessage = new Message("getPrincipleNotificationList", SimpleClient.getClient().getUser());
                                SimpleClient.getClient().sendToServer(newMessage);
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
            else if(message.getTitle().equals("wrongType")){
                Platform.runLater(() -> {
                    // Login failure
                    showAlertDialog(AlertType.ERROR, "Error", "Incorrect User Type");
                });
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
        //EventBus.getDefault().unregister(this);
    }
    public void login(String username, String password)
    {
        int s=0;
        String type = loginBoundry.getUserType().getSelectionModel().getSelectedItem();
        if(type == "Student"){
            s=0;

        }
        else if(type == "Teacher"){
            s=1;
        }
        else {
            s=2;
        }
        Object[] obj = {username, password,s};
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
//    private void handleUserTypeSelection(String selectedUserType) {
//
//        // Set different colors for "Student" and "Teacher" options
//        if (selectedUserType.equals("Student"))
//        {
//            loginBoundry.getLoginBtn().getStyleClass().add("login-button-student");
//            loginBoundry.getUsernameTxt().getStyleClass().add("text-field-student");
//            loginBoundry.getPasswordHidden().getStyleClass().add("password-field-custom-student");
//            loginBoundry.getPasswordTxt().getStyleClass().add("text-field-student");
//            loginBoundry.getAnchorPane().getStyleClass().add("anchor-pane-student");
//            loginBoundry.getCheckbox().getStyleClass().add("check-box-student");
//            loginBoundry.getTimeLabel().getStyleClass().add("label-student");
//            loginBoundry.getUserLogin().getStyleClass().add("label-student");
//            loginBoundry.getUserType().getStyleClass().add("combo-box-custom-student");
//            Image image1 = new Image(getClass().getResourceAsStream("/images/usersOrange.png"));
//            loginBoundry.getLogo().setImage(image1);
//            Image image2 = new Image(getClass().getResourceAsStream("/images/userkeyOrange.png"));
//            loginBoundry.getUserpassword().setImage(image2);
//        }
//        else if (selectedUserType.equals("Teacher"))
//        {
//            loginBoundry.getLoginBtn().getStyleClass().add("login-button-teacher");
//            loginBoundry.getUsernameTxt().getStyleClass().add("text-field-teacher");
//            loginBoundry.getPasswordHidden().getStyleClass().add("password-field-custom-teacher");
//            loginBoundry.getPasswordTxt().getStyleClass().add("text-field-teacher");
//            loginBoundry.getAnchorPane().getStyleClass().add("anchor-pane-teacher");
//            loginBoundry.getCheckbox().getStyleClass().add("check-box-teacher");
//            loginBoundry.getTimeLabel().getStyleClass().add("label-teacher");
//            loginBoundry.getUserLogin().getStyleClass().add("label-teacher");
//            loginBoundry.getUserType().getStyleClass().add("combo-box-custom-teacher");
//            Image image1 = new Image(getClass().getResourceAsStream("/images/users.png"));
//            loginBoundry.getLogo().setImage(image1);
//            Image image2 = new Image(getClass().getResourceAsStream("/images/userpas.png"));
//            loginBoundry.getUserpassword().setImage(image2);
//        }
//        else if (selectedUserType.equals("Principle"))
//        {
//            loginBoundry.getLoginBtn().getStyleClass().add("login-button-principle");
//            loginBoundry.getUsernameTxt().getStyleClass().add("text-field-principle");
//            loginBoundry.getPasswordHidden().getStyleClass().add("password-field-custom-principle");
//            loginBoundry.getPasswordTxt().getStyleClass().add("text-field-principle");
//            loginBoundry.getAnchorPane().getStyleClass().add("anchor-pane-principle");
//            loginBoundry.getCheckbox().getStyleClass().add("check-box-principle");
//            loginBoundry.getTimeLabel().getStyleClass().add("label-principle");
//            loginBoundry.getUserLogin().getStyleClass().add("label-principle");
//            loginBoundry.getUserType().getStyleClass().add("combo-box-custom-principle");
//            Image image1 = new Image(getClass().getResourceAsStream("/images/usersPurple.png"));
//            loginBoundry.getLogo().setImage(image1);
//            Image image2 = new Image(getClass().getResourceAsStream("/images/userkeyPurple.png"));
//            loginBoundry.getUserpassword().setImage(image2);
//        }
//    }
}
