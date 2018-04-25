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
import model.InfoModel;
import model.XMLsaver;

import java.io.IOException;
import java.net.URL;
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

        addTele();

        // Заполняем список редактирования категорий
        list_for_edit.getItems().clear();
        list_for_edit.getItems().addAll(CATEGORIES_DESC);
        list_for_edit.getSelectionModel().select(0);
    }

    private void addTele() {

        //Пробуем сохранить  в xml
//        XMLsaver.saveToXML(infoData, "InfoModels.xml");
        infoData.clear();
        infoData.addAll(XMLsaver.loadFromXML(InfoModel.FILENAME_INFOMODELS));
        System.out.println(infoData);

        if (infoData.isEmpty()) {
            // В качестве образца добавляем некоторые данные
            Image noImage = new Image("resource/noimage.png");
            infoData.add(new InfoModel("ТА-57", "DS", "Описание", noImage));
            infoData.add(new InfoModel("ТА-88", "DS", "Описание", noImage));
            infoData.add(new InfoModel("П-380 ТА", "DS", "Описание", noImage));
            infoData.add(new InfoModel("Селенит", "DS,ZAS", "Описание", noImage));

            infoData.add(new InfoModel("П-170", "ZAS", "Описание", noImage));
            infoData.add(new InfoModel("П-171Д", "ZAS", "Описание", noImage));
            infoData.add(new InfoModel("АТ-3031", "ZAS", "Описание", noImage));

            infoData.add(new InfoModel("Рамек-2", "ARM", "Описание", noImage));

            infoData.add(new InfoModel("П-274М", "CableAndOther", "Описание", noImage));
            infoData.add(new InfoModel("П-269 4х2+2х4", "CableAndOther", "Описание", noImage));
            infoData.add(new InfoModel("ПРК 5х2", "CableAndOther", "Описание", noImage));
            infoData.add(new InfoModel("ПТРК 5х2", "CableAndOther", "Описание", noImage));
            infoData.add(new InfoModel("Витая пара", "CableAndOther", "Описание", noImage));
            infoData.add(new InfoModel("ВП", "CableAndOther", "Описание", noImage));
            infoData.add(new InfoModel("РМ2", "CableAndOther", "Описание", noImage));

            infoData.add(new InfoModel("П-240И", "AOZU", "Описание", noImage));
            infoData.add(new InfoModel("МП-1И", "AOZU", "Описание", noImage));
            infoData.add(new InfoModel("МП-2И", "AOZU", "Описание", noImage));

            infoData.add(new InfoModel("П-260-О", "ATZU", "Описание", noImage));
            infoData.add(new InfoModel("П-260-У", "ATZU", "Описание", noImage));
            infoData.add(new InfoModel("П-260-Т", "ATZU", "Описание", noImage));
            infoData.add(new InfoModel("П-244И", "ATZU", "Описание", noImage));
            infoData.add(new InfoModel("П-244И-4", "ATZU", "Описание", noImage));
        }

        // Добавляем фильтрованный по "типу" список в ComboBox
        DC.getItems().addAll(filterInfoModelByType("DS", infoData));
        // Выбираем первый эл-т для отображения
        DC.getSelectionModel().select(0);
        ZAS.getItems().addAll(filterInfoModelByType("ZAS", infoData));
        ZAS.getSelectionModel().select(0);
        ARM.getItems().addAll(filterInfoModelByType("ARM", infoData));
        ARM.getSelectionModel().select(0);
        telCable.getItems().addAll(filterInfoModelByType("CableAndOther", infoData));
        telCable.getSelectionModel().select(0);
        AOZU.getItems().addAll(filterInfoModelByType("AOZU", infoData));
        AOZU.getSelectionModel().select(0);
        ATZU.getItems().addAll(filterInfoModelByType("ATZU", infoData));
        ATZU.getSelectionModel().select(0);
    }

    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/second_frame.fxml"));
        Vbox.getChildren().setAll(vBox);
    }
}
