package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ControllerChooseCategoryOfOfficialApply {

    @FXML
    public VBox selectionOfOfficials;

    public void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/choose_category_of_official_1.fxml"));
        selectionOfOfficials.getChildren().setAll(vBox);
    }

    public void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/second_frame.fxml"));
        selectionOfOfficials.getChildren().setAll(vBox);
    }

    public void applyTheChange() throws IOException {
        /*
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/choose_category_of_official_1.fxml"));
        selectionOfOfficials.getChildren().setAll(vBox);
        */

    }
}
