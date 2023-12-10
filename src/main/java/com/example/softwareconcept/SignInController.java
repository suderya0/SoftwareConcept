package com.example.softwareconcept;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignInController implements Initializable {


    public TextField sign_in_username_field;
    public PasswordField sign_in_password_field;
    public Button log_in_home_button;
    public Button sign_up_home_button;
    public Button log_in_google_button;
    public Button forget_password_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log_in_home_button.setOnAction(event ->
                DBUtils.logInUser(event, sign_in_username_field.getText(), sign_in_password_field.getText())
        );


        sign_up_home_button.setOnAction(event ->
                DBUtils.changeScene(event, "sign-up.fxml"));


        forget_password_button.setOnAction(event -> {
            DBUtils.changeScene(event, "forget-password.fxml");


        });
        log_in_google_button.setOnAction(event -> {
            DBUtils.changeScene(event, "LoginGoogle.fxml");
        });
    }
}
