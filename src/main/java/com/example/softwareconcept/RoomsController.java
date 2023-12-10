package com.example.softwareconcept;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class RoomsController implements Initializable {
    public Button valorant_button;
    public Button basketball_button;
    public Button tennis_button;
    public Button activate_deactivate_button;
    public Button send_request_button;
    public TableView<RoomsTableController> active_users_table_view;
    public TableColumn<RoomsTableController, String> active_users_column = new TableColumn<>("active_users");
    public Button get_back;


    String sql;

    ObservableList<RoomsTableController> activeUsers = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        activate_deactivate_button.setOnAction(event -> {
            DBUtils.changeScene(event, "activate-deactivate.fxml");
        });

        valorant_button.setOnAction(event -> {
            active_users_table_view.getItems().clear();

            sql = "SELECT active_users FROM valorant";
            try {
                getValues(active_users_table_view, sql);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


        basketball_button.setOnAction(event -> {

            active_users_table_view.getItems().clear();

            sql = "SELECT active_users FROM basketball";
            try {
                getValues(active_users_table_view, sql);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        tennis_button.setOnAction(event -> {

            active_users_table_view.getItems().clear();

            sql = "SELECT active_users FROM tennis";

            try {
                getValues(active_users_table_view, sql);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        get_back.setOnAction(event -> {
            DBUtils.getBack(event, "home-page.fxml");
        });

    }


    public void getValues(TableView<RoomsTableController> table, String sql) throws SQLException {
        Connection connection = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login-demo", "root", "derya12sude34");
        } catch (SQLException var7) {
            throw new RuntimeException(var7);
        }

        psInsert = connection.prepareStatement(sql);
        resultSet = psInsert.executeQuery();

        while(resultSet.next()) {
            this.activeUsers.add(new RoomsTableController(resultSet.getString("active_users")));
            this.active_users_column.setCellValueFactory(new PropertyValueFactory("active_users"));

            if (table != null) {
                table.setItems(this.activeUsers);
            } else {
                System.out.println("table null");
            }
        }


    }

}
