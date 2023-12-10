package com.example.softwareconcept;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ActivateDeactivateController implements Initializable {
    public TextField username_foractivate_field;
    public TextField password_foractivate_field;
    public Button activate_button_real;
    public Button deactivate_button_real;
    public ComboBox combobox_room_names;
    public Button get_back;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        activate_button_real.setOnAction(event -> {
            String selectedRoom = (String) combobox_room_names.getValue();

            try {
                DBUtils.activateUser(event, username_foractivate_field.getText(), password_foractivate_field.getText(), selectedRoom);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        deactivate_button_real.setOnAction(event -> {
           String selectedRoom = (String) combobox_room_names.getValue();

            try {
                DBUtils.deactiveUser(event, username_foractivate_field.getText(), password_foractivate_field.getText(), selectedRoom);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


        get_back.setOnAction(event -> {
            DBUtils.getBack(event, "rooms.fxml");
        });

        try {
            loadRoomNames();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadRoomNames() throws SQLException {
        Connection connection = null;
        PreparedStatement psSelect = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login-demo", "root", "derya12sude34");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObservableList<String> roomNames = FXCollections.observableArrayList();

        psSelect = connection.prepareStatement("SELECT room_name FROM rooms");
        resultSet = psSelect.executeQuery();

        while (resultSet.next()) {
            roomNames.add(resultSet.getString("room_name"));
        }

        combobox_room_names.setItems(roomNames);
    }
}
