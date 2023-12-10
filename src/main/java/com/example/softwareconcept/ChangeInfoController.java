package com.example.softwareconcept;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeInfoController implements Initializable {
    public TextField change_username_field;
    public TextField change_password_field;
    public TextField change_auth_field;
    public Button change_name_button;
    public Button change_password_button;
    public Button change_auth_button;
    public Button get_back;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        change_name_button.setOnAction(event -> {
            String newName = change_username_field.getText();
            String oldName = UserInfo.username;

            DBUtils.changeUserInfo(newName, null, null, oldName);
        });

        change_password_button.setOnAction(event -> {
            String newPassword = change_password_field.getText();
            String username = UserInfo.username;

            DBUtils.changeUserInfo(null, newPassword, null, username);
        });

        change_auth_button.setOnAction(event -> {
            String newAuthInfo = change_auth_field.getText();
            String username = UserInfo.username;

            DBUtils.changeUserInfo(null, null, newAuthInfo, username);
        });

        get_back.setOnAction(event -> {
            DBUtils.getBack(event, "profile.fxml");
        });
    }
}
