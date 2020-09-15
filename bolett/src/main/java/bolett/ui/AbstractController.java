package bolett.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.EventListener;
import java.util.ResourceBundle;

public abstract class AbstractController {
    
/*
    @FXML
    Button createAccount;

    @FXML
    Button createAccount2;

    @FXML
    Button LogInn;*/


    public void changeScreen(String URLName, Button button) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        Parent root=FXMLLoader.load(getClass().getResource(URLName));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

   /* @FXML
    public void LogInToCreatAccount() throws IOException {
        changeScreen("ui/CreateUser.fxml", createAccount);
    }

    @FXML
    public void CreateAccountToWelcome() throws IOException {
        changeScreen("ui/Welcome.fxml", createAccount2);
    }

*/


 /*  @FXML
    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
       Stage stage = (Stage) createAccount.getScene().getWindow();
       Parent root=FXMLLoader.load(getClass().getResource("ui/CreateUser.fxml"));
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.show();
    }*/



}
