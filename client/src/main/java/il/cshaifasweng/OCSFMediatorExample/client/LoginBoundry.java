/**
 * Sample Skeleton for 'LoginController.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.LoginController;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;


public class LoginBoundry {
    @FXML
    private Label userLogin;
    @FXML
    private ComboBox<String> userType;
    @FXML
    private ImageView logo;

    @FXML
    private CheckBox checkbox;

    @FXML
    private DatePicker DatePicker;

    @FXML
    private Label timeLabel;
    private AnimationTimer animationTimer;

    @FXML
    private ImageView userpassword;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordHidden;

    @FXML
    private TextField passwordTxt;

    @FXML
    private TextField usernameTxt;

    @FXML
    private ImageView users;
    @FXML
    private AnchorPane anchorPane;

    private LoginController controller;

    @FXML
    void loginAction(ActionEvent event)
    {
        controller.login(usernameTxt.getText(), passwordHidden.getText());
        System.out.println("hhh");
    }

    @FXML
    void passwordAction(ActionEvent event)
    {

    }
    @FXML
    void userTypeAction(ActionEvent event) {

    }

    @FXML
    void usernameAction(ActionEvent event)
    {

    }
    @FXML
    void changeVisibility(ActionEvent event){
        if(checkbox.isSelected()){
            passwordTxt.setText(passwordHidden.getText());
            passwordTxt.setVisible(true);
            passwordHidden.setVisible(false);
            return;
        }

        passwordHidden.setText(passwordTxt.getText());
        passwordHidden.setVisible(true);
        passwordTxt.setVisible(false);

    }
    private void handleUserTypeSelection(String selectedUserType) {
        loginBtn.getStyleClass().clear();
        usernameTxt.getStyleClass().clear();
        passwordTxt.getStyleClass().clear();
        passwordHidden.getStyleClass().clear();
        userType.getStyleClass().removeAll("student","teacher","principle");
        anchorPane.getStyleClass().clear();
        checkbox.getStyleClass().clear();
        timeLabel.getStyleClass().clear();
        userLogin.getStyleClass().clear();
        // Set different colors for "Student" and "Teacher" options
        if (selectedUserType.equals("Student"))
        {
            loginBtn.getStyleClass().add("login-button-student");
            usernameTxt.getStyleClass().add("text-field-student");
            passwordHidden.getStyleClass().add("password-field-custom-student");
            passwordTxt.getStyleClass().add("text-field-student");
            anchorPane.getStyleClass().add("anchor-pane-student");
            checkbox.getStyleClass().add("check-box-student");
            timeLabel.getStyleClass().add("label-student");
            userLogin.getStyleClass().add("label-student");
            userType.getStyleClass().add("student");
            Image image1 = new Image(getClass().getResourceAsStream("/images/usersOrange.png"));
            users.setImage(image1);
            Image image2 = new Image(getClass().getResourceAsStream("/images/userkeyOrange.png"));
            userpassword.setImage(image2);
        }
        else if (selectedUserType.equals("Teacher"))
        {
            loginBtn.getStyleClass().add("login-button-teacher");
            usernameTxt.getStyleClass().add("text-field-teacher");
            passwordHidden.getStyleClass().add("password-field-custom-teacher");
            passwordTxt.getStyleClass().add("text-field-teacher");
            anchorPane.getStyleClass().add("anchor-pane-teacher");
            checkbox.getStyleClass().add("check-box-teacher");
            timeLabel.getStyleClass().add("label-teacher");
            userLogin.getStyleClass().add("label-teacher");
            userType.getStyleClass().add("teacher");
            Image image1 = new Image(getClass().getResourceAsStream("/images/users.png"));
            users.setImage(image1);
            Image image2 = new Image(getClass().getResourceAsStream("/images/userpas.png"));
            userpassword.setImage(image2);
        }
        else if (selectedUserType.equals("Principle"))
        {
            loginBtn.getStyleClass().add("login-button-principle");
            usernameTxt.getStyleClass().add("text-field-principle");
            passwordHidden.getStyleClass().add("password-field-custom-principle");
            passwordTxt.getStyleClass().add("text-field-principle");
            anchorPane.getStyleClass().add("anchor-pane-principle");
            checkbox.getStyleClass().add("check-box-principle");
            timeLabel.getStyleClass().add("label-principle");
            userLogin.getStyleClass().add("label-principle");
            userType.getStyleClass().add("principle");
            Image image1 = new Image(getClass().getResourceAsStream("/images/usersPurple.png"));
            users.setImage(image1);
            Image image2 = new Image(getClass().getResourceAsStream("/images/userkeyPurple.png"));
            userpassword.setImage(image2);
        }
        Preferences prefs = Preferences.userNodeForPackage(LoginBoundry.class);
        prefs.put(("userType"),selectedUserType);
    }
    private String getSelectedStyle(){
        String selectedStyle = userType.getValue();
        return selectedStyle.toLowerCase();
    }
    public void setController(LoginController controller) {
        this.controller = controller;
    }
    @FXML
    public void initialize()
    {
        controller = new LoginController(this);
        this.setController(controller);
        Image logoImage = new Image(getClass().getResourceAsStream("/images/logoWhite.png"));
        logo.setImage(logoImage);
        Image image1 = new Image(getClass().getResourceAsStream("/images/users.png"));
        users.setImage(image1);
        Image image2 = new Image(getClass().getResourceAsStream("/images/userpas.png"));
        userpassword.setImage(image2);
//        DatePicker.setValue(LocalDate.now());



        // Start the AnimationTimer to update the time label periodically
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateDateTime();
            }
        };
        animationTimer.start();
        // Add event listener for "Enter" key press on the username field
        usernameTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginAction(null);
            }
        });


        userType.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginAction(null);
            }
        });
        // Add event listener for "Enter" key press on the password field
        passwordHidden.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginAction(null);
            }
        });
        userType.getItems().addAll("Student", "Teacher","Principle");
        userType.setValue("Teacher");
        userType.setOnAction(event -> {
            handleUserTypeSelection(userType.getSelectionModel().getSelectedItem());
        });
        Preferences prefs = Preferences.userNodeForPackage(LoginBoundry.class);
        String storedUserType = prefs.get("userType",null);
        if(storedUserType != null)
        {
            userType.setValue(storedUserType);
            handleUserTypeSelection(storedUserType);
        }
        // Set the initial color for the login button
//        Preferences prefs = Preferences.userNodeForPackage(LoginBoundry.class);
////        String storedStyle = prefs.get("button",null);
//        if(storedStyle!=null){
//            userType.setValue(storedStyle);
//            handleUserTypeSelection(storedStyle);
//        }
//        else{
//            userType.setValue("Student");
//            userType.setOnAction(event -> {
//                handleUserTypeSelection(userType.getSelectionModel().getSelectedItem());
//            });
//        }
    }





    private void updateDateTime() {
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();



        // Format the date and time as desired (change the pattern as needed)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd \n" +
                "HH:mm:ss");
        String dateTimeString = currentDateTime.format(formatter);



        // Update the label text
        timeLabel.setText(dateTimeString);
    }

    public ComboBox<String> getUserType() {
        return userType;
    }

}


