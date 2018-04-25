package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ControllerFirstFrame {

    @FXML
    public VBox VBoxRoot1;

    public void loginToTheProgram(ActionEvent actionEvent) throws IOException {


        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/second_frame.fxml"));
        VBoxRoot1.getChildren().setAll(vBox);
    }

}
