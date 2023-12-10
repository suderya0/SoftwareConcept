package com.example.softwareconcept;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    public TextField sign_up_username_field;
    public TextField sign_up_password_field;
    public Button sign_up_real;
    public Button get_back;
    public TextField authentication_field;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sign_up_real.setOnAction(event -> {
            if(!sign_up_username_field.getText().trim().isEmpty() &&
            !sign_up_password_field.getText().trim().isEmpty() &&
            !authentication_field.getText().trim().isEmpty()){
                try {
                    DBUtils.signUpUser(event, sign_up_username_field.getText(), sign_up_password_field.getText(), authentication_field.getText());

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Empty fields!");
                alert.show();
            }
        });

        get_back.setOnAction(event -> {
            DBUtils.getBack(event, "sign-in.fxml");
        });



    }
}
