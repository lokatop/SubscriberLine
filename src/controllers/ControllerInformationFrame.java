package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.InfoModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

public class ControllerInformationFrame implements Initializable{

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

    /**
     * Данные, в виде наблюдаемого списка адресатов.
     */
    private ObservableList<InfoModel> infoData = FXCollections.observableArrayList();

    public void toShow(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/information_frame_decription.fxml"));

        //Получаем выбранную модель
        Button b =(Button)actionEvent.getTarget();
        HBox vb = (HBox) b.getParent();
        ComboBox cb = (ComboBox) vb.getChildren().get(0);
        InfoModel model = (InfoModel) cb.getSelectionModel().getSelectedItem();

        try{
            VBox vBox = (VBox)loader.load();
            ControllerInformationDescription controller = loader.getController();
            controller.setModel(model);

            //отображаем
            Vbox.getChildren().setAll(vBox);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void change(ActionEvent actionEvent) throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/information_frame_change.fxml"));
        Vbox.getChildren().setAll(vBox);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addTele();
    }

    private void addTele(){
        // В качестве образца добавляем некоторые данные
        infoData.add(new InfoModel("ТА-57","DS","Описание"));
        infoData.add(new InfoModel("ТА-88","DS","Описание"));
        infoData.add(new InfoModel("П-380 ТА","DS","Описание"));
        infoData.add(new InfoModel("Селенит","DS","Описание"));

        infoData.add(new InfoModel("П-170","ZAS","Описание"));
        infoData.add(new InfoModel("П-171Д","ZAS","Описание"));
        infoData.add(new InfoModel("АТ-3031","ZAS","Описание"));
        infoData.add(new InfoModel("Селенит","ZAS","Описание"));

        infoData.add(new InfoModel("Рамек-2","ARM","Описание"));

        infoData.add(new InfoModel("П-274М","CableAndOther","Описание"));
        infoData.add(new InfoModel("П-269 4х2+2х4","CableAndOther","Описание"));
        infoData.add(new InfoModel("ПРК 5х2","CableAndOther","Описание"));
        infoData.add(new InfoModel("ПТРК 5х2","CableAndOther","Описание"));
        infoData.add(new InfoModel("Витая пара","CableAndOther","Описание"));
        infoData.add(new InfoModel("ВП","CableAndOther","Описание"));
        infoData.add(new InfoModel("РМ2","CableAndOther","Описание"));

        infoData.add(new InfoModel("П-240И","AOZU","Описание"));
        infoData.add(new InfoModel("МП-1И","AOZU","Описание"));
        infoData.add(new InfoModel("МП-2И","AOZU","Описание"));

        infoData.add(new InfoModel("П-260-О","ATZU","Описание"));
        infoData.add(new InfoModel("П-260-У","ATZU","Описание"));
        infoData.add(new InfoModel("П-260-Т","ATZU","Описание"));
        infoData.add(new InfoModel("П-244И","ATZU","Описание"));
        infoData.add(new InfoModel("П-244И-4","ATZU","Описание"));
        infoData.add(new InfoModel("П-242И","ATZU","Описание"));


        // Добавляем фильтрованный по "типу" список в ComboBox
        DC.getItems().addAll(filterInfoModelByType("DS"));
        // Выбираем первый эл-т для отображения
        DC.getSelectionModel().select(0);
        ZAS.getItems().addAll(filterInfoModelByType("ZAS"));
        ZAS.getSelectionModel().select(0);
        ARM.getItems().addAll(filterInfoModelByType("ARM"));
        ARM.getSelectionModel().select(0);
        telCable.getItems().addAll(filterInfoModelByType("CableAndOther"));
        telCable.getSelectionModel().select(0);
        AOZU.getItems().addAll(filterInfoModelByType("AOZU"));
        AOZU.getSelectionModel().select(0);
        ATZU.getItems().addAll(filterInfoModelByType("ATZU"));
        ATZU.getSelectionModel().select(0);
    }

    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/second_frame.fxml"));
        Vbox.getChildren().setAll(vBox);
    }

    /**
     * Фильтрует модели по типу
     * @param type
     * @return FilteredList<InfoModel>
     */
    private FilteredList<InfoModel> filterInfoModelByType(String type){
        return infoData.filtered(new Predicate<InfoModel>() {
            @Override
            public boolean test(InfoModel infoModel) {
                if (infoModel.getType() == type) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}
