package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.InfoModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

public class ControllerChooseCategoryScheme implements Initializable{

    @FXML
    public VBox VboxChooseSheme;
    @FXML
    public ComboBox comboChooseCategory;

    public static TreeSet setOfList = new TreeSet();

    public void changeData(ActionEvent actionEvent) throws IOException {
        //VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/choose_data_category.fxml"));
        //VboxChooseSheme.getChildren().setAll(vBox);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/choose_data_category.fxml"));
        try{
            VBox vBox = (VBox)loader.load();

            // Передаём выбранную модель в контроллер фрейма Описание
            ControllerDataCategory controller = loader.getController();
            controller.setSetOfList(setOfList);

            // Оотображаем
            VboxChooseSheme.getChildren().setAll(vBox);
        } catch(Exception e){
            e.printStackTrace();
        }
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
        setOfList.add("Командный пункт полка");
        setOfList.add("Запасный командный пункт полка");
        setOfList.add("Командный пункт бригады");
        setOfList.add("Запасный командный пункт бригады");
        setOfList.add("Передовой пункт управления бригады");
        setOfList.add("Командный пункт армии");
        setOfList.add("Запасный командный пункт армии");
        setOfList.add("Передовой пункт управления армии");
        setOfList.add("Вспомогательный пункт управления армии");
        setOfList.add("Командный пункт ГВ (с) на ТВД");
        setOfList.add("Запасный командный пункт ГВ (с) на ТВД");
        setOfList.add("Передовой пункт управления ГВ (с) на ТВД");
        setOfList.add("Вспомогательный пункт управления ГВ (с) на ТВД");

        comboChooseCategory.getItems().addAll(setOfList);

        comboChooseCategory.getSelectionModel().select(0);
    }


}
