package com.example.softwareconcept;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    public Button change_info_buton;
    public Button get_back;
    public Button log_out_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        change_info_buton.setOnAction(event -> {
            DBUtils.changeScene(event, "change_info.fxml");
        });
        get_back.setOnAction(event -> {
            DBUtils.getBack(event, "home-page.fxml");
        });
        log_out_button.setOnAction(event -> {
            DBUtils.changeScene(event, "sign-in.fxml");
            UserInfo.username = null;
            UserInfo.auth = null;
            UserInfo.password = null;
        });
    }
}
