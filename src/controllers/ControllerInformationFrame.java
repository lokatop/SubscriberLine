package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerInformationFrame {

    @FXML
    public VBox Vbox;

    public void toShow(ActionEvent actionEvent) throws IOException {

        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/information_frame_decription.fxml"));
        Vbox.getChildren().setAll(vBox);

    }

    public void change(ActionEvent actionEvent) throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/information_frame_change.fxml"));
        Vbox.getChildren().setAll(vBox);
    }
}
