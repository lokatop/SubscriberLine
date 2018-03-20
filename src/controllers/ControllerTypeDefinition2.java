package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ControllerTypeDefinition2 {
    @FXML
    public VBox typeDefinition;

    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/type_definition_1.fxml"));
        typeDefinition.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/second_frame.fxml"));
        typeDefinition.getChildren().setAll(vBox);
    }

    @FXML
    private void theNext() throws IOException{
        //VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/.fxml"));
        //typeDefinition.getChildren().setAll(vBox);

    }
    @FXML
    private void EditOriginalData(){}
}
