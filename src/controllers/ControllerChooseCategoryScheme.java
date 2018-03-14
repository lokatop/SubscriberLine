package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerChooseCategoryScheme implements Initializable{

    @FXML
    public VBox VboxChooseSheme;
    @FXML
    public ComboBox comboChooseCategory;

    public void changeData(ActionEvent actionEvent) throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/choose_data_category.fxml"));
        VboxChooseSheme.getChildren().setAll(vBox);
    }

    public void theNext(ActionEvent actionEvent) throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/choose_category_of_official_1.fxml"));
        VboxChooseSheme.getChildren().setAll(vBox);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboChooseCategory.getItems().addAll(
                "Командный пункт полка",
                "Запасный командный пункт полка",
                "Командный пункт бригады",
                "Запасный командный пункт бригады",
                "Передовой пункт управления бригады",
                "Командный пункт армии",
                "Запасный командный пункт армии",
                "Передовой пункт управления армии",
                "Вспомогательный пункт управления армии",
                "Командный пункт ГВ (с) на ТВД",
                "Запасный командный пункт ГВ (с) на ТВД",
                "Передовой пункт управления ГВ (с) на ТВД",
                "Вспомогательный пункт управления ГВ (с) на ТВД"
        );

        comboChooseCategory.getSelectionModel().select(0);
    }
}
