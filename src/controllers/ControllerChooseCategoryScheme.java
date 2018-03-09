package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ControllerChooseCategoryScheme {

    @FXML
    public VBox VboxChooseSheme;

    public void changeData(ActionEvent actionEvent){

    }

    public void theNext(ActionEvent actionEvent){

    }

    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/choose_category.fxml"));
        VboxChooseSheme.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/second_frame.fxml"));
        VboxChooseSheme.getChildren().setAll(vBox);
    }
}
