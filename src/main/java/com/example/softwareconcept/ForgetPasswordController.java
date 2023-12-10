package com.example.softwareconcept;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ForgetPasswordController implements Initializable {
    public TextField username_forget_field;
    public TextField oauth_forget_field;
    public TextField new_password_field;
    public Button update_forgetten_password;
    public Button get_back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update_forgetten_password.setOnAction(event -> {
            DBUtils.updatePassword(username_forget_field.getText(), oauth_forget_field.getText(), new_password_field.getText());
        });
        get_back.setOnAction(event -> {
            DBUtils.getBack(event, "sign-in.fxml");
        });
    }
}
