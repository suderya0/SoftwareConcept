package com.example.softwareconcept;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {


    public Button profile_button_home_page;
    public Button map_button_home_page;
    public Button wallet_button_home_page;
    public Button rooms_button_home_page;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rooms_button_home_page.setOnAction(event -> {
            try{
                DBUtils.changeScene(event , "rooms.fxml");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        profile_button_home_page.setOnAction(event -> {
            DBUtils.changeScene(event, "profile.fxml");
        });

    }
}
