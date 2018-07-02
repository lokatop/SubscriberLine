package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.DB;
import model.InfoModel;
import model.XMLsaver;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static model.InfoModel.CATEGORIES_DESC;
import static model.InfoModel.filterInfoModelByType;

public class ControllerInformationFrame implements Initializable {

    @FXML
    public VBox Vbox;

    @FXML
    public ComboBox DC;
    @FXML
    public ComboBox ZAS;
    @FXML
    public ComboBox ARM;
    @FXML
    public ComboBox telCable;
    @FXML
    public ComboBox AOZU;
    @FXML
    public ComboBox ATZU;
    @FXML
    public ComboBox list_for_edit;

    /**
     * Данные, в виде наблюдаемого списка адресатов.
     */
    public static ObservableList<InfoModel> infoData = FXCollections.observableArrayList();

    public void toShow(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/information_frame_decription.fxml"));

        //Получаем выбранную модель
        Button b = (Button) actionEvent.getTarget();
        HBox vb = (HBox) b.getParent();
        ComboBox cb = (ComboBox) vb.getChildren().get(0);
        InfoModel model = (InfoModel) cb.getSelectionModel().getSelectedItem();

        try {
            VBox vBox = (VBox) loader.load();

            // Передаём выбранную модель в контроллер фрейма Описание
            ControllerInformationDescription controller = loader.getController();
            controller.setModel(model);

            // Оотображаем
            Vbox.getChildren().setAll(vBox);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void change(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/information_frame_change.fxml"));

        //Получаем id выбранного типа
        Integer typeId = list_for_edit.getSelectionModel().getSelectedIndex();

        try {
            VBox vBox = (VBox) loader.load();

            // Передаём выбранный тип, список типов и список моделей в контроллер фрейма Описание
            ControllerInformationChange controller = loader.getController();
            controller.setChangingTypeAndInfoData(typeId, infoData);

            // Оотображаем
            Vbox.getChildren().setAll(vBox);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            addTele();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Заполняем список редактирования категорий
        list_for_edit.getItems().clear();
        list_for_edit.getItems().addAll(CATEGORIES_DESC);
        list_for_edit.getSelectionModel().select(0);
    }

    private void addTele() throws SQLException {

        // Добавляем фильтрованный по "типу" список в ComboBox
        DC.getItems().addAll(DB.getCatalogTitlesByType("DS"));
        // Выбираем первый эл-т для отображения
        DC.getSelectionModel().select(0);
        ZAS.getItems().addAll(DB.getCatalogTitlesByType("ZAS"));
        ZAS.getSelectionModel().select(0);
        ARM.getItems().addAll(DB.getCatalogTitlesByType("ARM"));
        ARM.getSelectionModel().select(0);
        telCable.getItems().addAll(DB.getCatalogTitlesByType("CableAndOther"));
        telCable.getSelectionModel().select(0);
        AOZU.getItems().addAll(DB.getCatalogTitlesByType("AOZU"));
        AOZU.getSelectionModel().select(0);
        ATZU.getItems().addAll(DB.getCatalogTitlesByType("ATZU"));
        ATZU.getSelectionModel().select(0);
    }

    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/second_frame.fxml"));
        Vbox.getChildren().setAll(vBox);
    }
}
