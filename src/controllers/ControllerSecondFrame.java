package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class ControllerSecondFrame{

    @FXML
    public VBox VBoxRoot;

    @FXML
    public  void buttonInformation(ActionEvent actionEvent) throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/information_frame_first.fxml"));
        VBoxRoot.getChildren().setAll(vBox);
    }

    @FXML
    public void FormingADocument(ActionEvent actionEvent) throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/choose_category_scheme.fxml"));
        VBoxRoot.getChildren().setAll(vBox);
    }
}
