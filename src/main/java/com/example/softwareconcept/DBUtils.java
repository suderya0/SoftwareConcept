package com.example.softwareconcept;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DBUtils {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/login-demo";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "derya12sude34";
    static String sql;


    public static java.sql.Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }

    public static void changeScene(ActionEvent event, String fxmlFile) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            root = loader.load();
            System.out.println("hata");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 800.0, 500.0));
        stage.show();
    }

    public static void getBack(ActionEvent event, String fxml){
        DBUtils.changeScene(event, fxml);
    }


    public static void signUpUser(ActionEvent event, String username, String password, String authentication) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement psCheckUserExist = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            psCheckUserExist = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExist.setString(1, username);
            resultSet = psCheckUserExist.executeQuery();


            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exist!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username already exist!");
                alert.show();
            } else {
                preparedStatement = connection.prepareStatement("INSERT INTO users (username, password, dogrulama) VALUES (?,?,?)");
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, authentication);
                preparedStatement.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Signed Up Succesfully!");
                alert.show();
                changeScene(event, "sign-in.fxml");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (psCheckUserExist != null) {
                    psCheckUserExist.close();
                }

                if (preparedStatement != null) {
                    preparedStatement.close();
                }

                if (connection != null) {
                    connection.close();
                }

        } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = getConnection();

            preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Password or username not found!");
            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)) {
                        UserInfo.username = username;
                        UserInfo.password = password;

                        System.out.println(UserInfo.username);
                        System.out.println(UserInfo.password);

                        changeScene(event, "home-page.fxml");
                    } else {
                        System.out.println("Password did not match");
                        alert.show();
                    }
                }
            }

    } catch (Exception e) {
              throw new RuntimeException(e);
          }finally {
              try{
              if(resultSet != null){
                  resultSet.close();
              }
              if(preparedStatement != null){
                  preparedStatement.close();
              }
              if(connection != null){
                  connection.close();
              }
          } catch (SQLException e) {
                  throw new RuntimeException(e);
              }
          }
    }
    public static void activateUser(ActionEvent event, String username, String password, String roomName) throws SQLException {
        if(roomName == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Choose Room!");
            alert.show();
        }else {
            Connection connection = null;
            PreparedStatement psInsert = null;
            PreparedStatement psCheckCustomerExist = null;
            PreparedStatement psCheckUserActive = null;
            ResultSet resultSet = null;
            ResultSet resultSet2 = null;

            connection = getConnection();
            psCheckCustomerExist = connection.prepareStatement("SELECT password FROM users WHERE username = ?");
            psCheckCustomerExist.setString(1, username);
            resultSet = psCheckCustomerExist.executeQuery();

            sql = "SELECT * FROM " + roomName + " WHERE active_users = ?";
            psCheckUserActive = connection.prepareStatement(sql);
            psCheckUserActive.setString(1, username);
            resultSet2 = psCheckUserActive.executeQuery();

            try {
                Alert alert;
                if (resultSet.isBeforeFirst()) {
                    if (!resultSet2.isBeforeFirst())
                        sql = "INSERT INTO " + roomName + " (active_users) VALUES (?)";

                    psInsert = connection.prepareStatement(sql);
                    psInsert.setString(1, username);

                    psInsert.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Activated Succesfully!");
                    alert.show();

                }
            } catch (SQLException var20) {
                var20.printStackTrace();
            } finally {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }

                    if (psInsert != null) {
                        psInsert.close();
                    }
                } catch (SQLException var19) {
                    var19.printStackTrace();
                }

            }
        }
    }




//    public static void changeUserInfo(String newname, String password, String authInfo, String username) {
//        try {
//            Connection connection = getConnection();
//
//            String query = "UPDATE users SET password = COALESCE(?, password), dogrulama = COALESCE(?, dogrulama) , username = COALESCE(?, username) WHERE username=? ";
//            PreparedStatement statement = connection.prepareStatement(query);
//            if (password == null) {
//                statement.setNull(1, Types.VARCHAR);
//            } else {
//                statement.setString(1, password);
//            }
//            if (authInfo == null) {
//                statement.setNull(2, Types.VARCHAR);
//            } else {
//                statement.setString(2, authInfo);
//                UserInfo.auth = authInfo;
//            }
//            if (username == null) {
//                statement.setNull(3, Types.VARCHAR);
//            } else {
//                statement.setString(3, newname);
//                UserInfo.username = newname;
//            }
//            if (username == null) {
//                statement.setNull(4, Types.VARCHAR);
//            } else {
//                statement.setString(4, username);
//            }
//
//            int rowsAffected = statement.executeUpdate();
//
//            if (rowsAffected > 0) {
//                System.out.println("Kullanıcı bilgileri güncellendi.");
//            } else {
//                System.out.println("Kullanıcı adı bulunamadı veya değiştirilemedi.");
//            }
//
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    //}
public static void changeUserInfo(String newname, String password, String authInfo, String username) {
    try {
        Connection connection = getConnection();

        if (newname != null) {
            String query = "UPDATE users SET password = COALESCE(?, password), dogrulama = COALESCE(?, dogrulama), username = ? WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            if (newname.equals(username)) {
                // Kullanıcı adı değişmediyse (yani yeni ve eski kullanıcı adları aynıysa)
                // Diğer alanları güncelle
                statement.setString(1, password);
                statement.setString(2, authInfo);
                statement.setString(3, newname);
                statement.setString(4, username); // Eski kullanıcı adıyla güncelleme yap

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Kullanıcı bilgileri güncellendi.");
                } else {
                    System.out.println("Kullanıcı adı bulunamadı veya değiştirilemedi.");
                }
            } else {
                // Kullanıcı adı değiştiyse sadece kullanıcı adını güncelle
                String usernameUpdateQuery = "UPDATE users SET username = ? WHERE username = ?";
                PreparedStatement usernameStatement = connection.prepareStatement(usernameUpdateQuery);
                usernameStatement.setString(1, newname);
                usernameStatement.setString(2, username);

                int usernameRowsAffected = usernameStatement.executeUpdate();

                if (usernameRowsAffected > 0) {
                    System.out.println("Kullanıcı adı güncellendi.");
                } else {
                    System.out.println("Kullanıcı adı değiştirilemedi.");
                }
            }
        } else {
            System.out.println("Yeni kullanıcı adı null.");
        }

        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // Bu metod kullanıcı şifresini güncellemek için kullanılabilir
    public static void updatePassword(String username, String authenticationText, String newPassword) {

        try {
            Connection connection = getConnection();

            // Kullanıcı adı ve doğrulama değeriyle veritabanında eşleşme kontrolü
            String query = "UPDATE users SET password = ? WHERE username = ? AND dogrulama = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newPassword);
            statement.setString(2, username);
            statement.setString(3, authenticationText);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Şifre güncellendi.");
                // Şifre başarıyla güncellendiğine dair mesaj veya işlem yapılabilir
            } else {
                System.out.println("Kullanıcı adı veya doğrulama değeri yanlış.");
                // Eşleşme bulunamadığı için şifre güncellenemedi
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deactiveUser(ActionEvent event, String username, String password, String roomName) throws SQLException {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckCustomerExist = null;
        PreparedStatement psCheckUserActive = null;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;

        connection = getConnection();
        psCheckCustomerExist = connection.prepareStatement("SELECT password FROM users WHERE username = ?");
        psCheckCustomerExist.setString(1, username);
        resultSet = psCheckCustomerExist.executeQuery();

        sql = "SELECT * FROM "+ roomName +" WHERE active_users = ?";
        psCheckUserActive = connection.prepareStatement(sql);
        psCheckUserActive.setString(1,username);
        resultSet2 = psCheckUserActive.executeQuery();

        try {
            Alert alert;
            if (resultSet.isBeforeFirst()) {
                if(resultSet2.isBeforeFirst())
                    sql ="DELETE FROM " + roomName +  " WHERE active_users = ?";

                psInsert = connection.prepareStatement(sql);
                psInsert.setString(1, username);

                psInsert.executeUpdate();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Deactivated Succesfully!");
                alert.show();

            }
        } catch (SQLException var20) {
            var20.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (psInsert != null) {
                    psInsert.close();
                }
            } catch (SQLException var19) {
                var19.printStackTrace();
            }

        }
    }
}