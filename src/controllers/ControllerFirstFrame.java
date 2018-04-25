package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerFirstFrame implements Initializable {

    @FXML
    public VBox VBoxRoot1;

    public void loginToTheProgram(ActionEvent actionEvent) throws IOException {


        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/second_frame.fxml"));
        VBoxRoot1.getChildren().setAll(vBox);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
