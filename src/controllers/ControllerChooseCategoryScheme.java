package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import model.XMLsaver;
import model.ChooseModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.function.Predicate;

public class ControllerChooseCategoryScheme implements Initializable{

    @FXML
    public VBox VboxChooseSheme;
    @FXML
    public ComboBox comboChooseCategory;

    public static ArrayList arrayOfList = new ArrayList();

    //TODO Статическая переменная ниже передается и используется в ChooseDataCategory(чтобы изменения там сразу отражались и здесь)
    public static ObservableList<ChooseModel> chooseData = FXCollections.observableArrayList();

    public void changeData(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/choose_data_category.fxml"));
        try{
            VBox vBox = (VBox)loader.load();

            // Передаём выбранную модель в контроллер фрейма Описание
            ControllerDataCategory controller = loader.getController();
           // controller.setSetOfList(chooseData);

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
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/second_frame.fxml"));
        VboxChooseSheme.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/second_frame.fxml"));
        VboxChooseSheme.getChildren().setAll(vBox);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { addData(); }

    public void addData(){
        chooseData.clear();
        chooseData.addAll(XMLsaver.loadFromXML("ChooseModels.xml"));

        if (!chooseData.isEmpty()) {
            // Добавляем фильтрованный по "типу" список в ComboBox
            comboChooseCategory.getItems().addAll(filterChooseModelByType("Пункты управления"));
            // Выбираем первый эл-т для отображения
            comboChooseCategory.getSelectionModel().select(0);
        }
    }

    /**
     * Фильтрует модели по типу
     * @param type
     * @return FilteredList<ChooseModel>
     */
    public static FilteredList<ChooseModel> filterChooseModelByType(String type){
        return chooseData.filtered(new Predicate<ChooseModel>() {
            @Override
            public boolean test(ChooseModel chooseModel) {
                if (chooseModel.getType().equals(type)) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

}
