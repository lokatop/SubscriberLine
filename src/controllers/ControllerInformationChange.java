package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ControllerInformationChange {
    @FXML
    public VBox VboxInfFrame;

    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/information_frame_first.fxml"));
        VboxInfFrame.getChildren().setAll(vBox);
    }
}
