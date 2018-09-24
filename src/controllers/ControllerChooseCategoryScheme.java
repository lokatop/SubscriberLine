package controllers;

import com.sun.org.apache.xml.internal.resolver.CatalogManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.ControllerInformationFrame.infoData;
import static model.ChooseModel.FILENAME_CHOOSEMODELS;

public class ControllerChooseCategoryScheme implements Initializable {

    @FXML
    public VBox VboxChooseSheme;
    @FXML
    public ComboBox comboChooseCategory;
    @FXML
    public ComboBox ComboBoxPart;
    //TODO Статическая переменная ниже передается и используется в ChooseDataCategory(чтобы изменения там сразу отражались и здесь)
    public static ObservableList<ChooseModel> chooseData = FXCollections.observableArrayList();

    public void changeData(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/fxml/choose_data_category.fxml"));
        try {
            VBox vBox = (VBox) loader.load();

            // Передаём выбранную модель в контроллер фрейма Описание
            ControllerDataCategory controller = loader.getController();
            controller.setData(((Button) actionEvent.getSource()).getId());

            // Оотображаем
            VboxChooseSheme.getChildren().setAll(vBox);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void theNext(ActionEvent actionEvent) throws IOException {

        if (!comboChooseCategory.getSelectionModel().isEmpty() && !ComboBoxPart.getSelectionModel().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/fxml/choose_category_of_official_1.fxml"));
            try {
                VBox vBox = (VBox) loader.load();

                // Передаём выбранную модель в контроллер фрейма Описание
                ControllerChooseCategoryOfOfficial controller = loader.getController();
                controller.setChooseCategory(comboChooseCategory.getSelectionModel()
                                .getSelectedItem().toString(), ComboBoxPart
                                .getSelectionModel().getSelectedItem().toString(),
                        ((CategoryOfManagePoint) comboChooseCategory.getSelectionModel()
                                .getSelectedItem()).getId(),
                        ((MilitaryPart) ComboBoxPart.getSelectionModel()
                                .getSelectedItem()).getId()
                );

                // Оотображаем
                VboxChooseSheme.getChildren().setAll(vBox);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/second_frame.fxml"));
        VboxChooseSheme.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/second_frame.fxml"));
        VboxChooseSheme.getChildren().setAll(vBox);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addData();
    }

    public void addData() {
        chooseData.clear();
        List pathToXml = XMLsaver.loadFromXML(FILENAME_CHOOSEMODELS);
        chooseData.addAll(pathToXml);
        if (!chooseData.isEmpty()) {
            // Добавляем фильтрованный по "типу" список в ComboBox
            //comboChooseCategory.getItems().addAll(filterChooseModelByType("Пункты управления"));
//            comboChooseCategory.getItems().addAll(DB.getManagePoints());
//            // Выбираем первый эл-т для отображения
//            comboChooseCategory.getSelectionModel().select(0);

            //ComboBoxPart.getItems().addAll(filterChooseModelByType("Вид воинской части"));
            ComboBoxPart.getItems().addAll(DB.getMilitaryParts());
            ComboBoxPart.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    comboChooseCategory.getItems().clear();
                    comboChooseCategory.getItems().addAll(DB.getManagePointsFromMilitaryPartById(
                            ((MilitaryPart) t1).getId()
                    ));
                    comboChooseCategory.getSelectionModel().select(0);
                }
            });
            ComboBoxPart.getSelectionModel().select(0);
        }
    }

    /**
     * Фильтрует модели по типу
     *
     * @param type
     * @return FilteredList<ChooseModel>
     */


    public static ObservableList<ChooseModel> filterChooseModelByType(String type) {

        ObservableList<ChooseModel> result = FXCollections.observableArrayList();

        for (ChooseModel chooseModel : chooseData) {
            if (chooseModel.getType().equals(type))
                result.add(chooseModel);
        }

        return result;

//        return chooseData.filtered(new Predicate<ChooseModel>() {
//            @Override
//            public boolean test(ChooseModel chooseModel) {
//                if (chooseModel.getType().equals(type)) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });
    }

    /**
     * Фильтрует модели по типу
     *
     * @param description
     * @return FilteredList<ChooseModel>
     */
    public static ObservableList<ChooseModel> filterChooseModelByDescription(String description) {

        ObservableList<ChooseModel> result = FXCollections.observableArrayList();

        for (ChooseModel chooseModel : chooseData) {
            if (chooseModel.getDescription().equals(description))
                result.add(chooseModel);
        }

        return result;

//        return chooseData.filtered(new Predicate<ChooseModel>() {
//            @Override
//            public boolean test(ChooseModel chooseModel) {
//                if (chooseModel.getDescription().equals(description)) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });
    }

    public static ObservableList<InfoModel> filterInfoModelByTypeSecond(String type) {
        ObservableList<InfoModel> result = FXCollections.observableArrayList();

        for (InfoModel infoModel : infoData) {
            if (infoModel.getType().equals(type))
                result.add(infoModel);
        }

        return result;

//        return infoData.filtered(new Predicate<InfoModel>() {
//            @Override
//            public boolean test(InfoModel infoModel) {
//                if (infoModel.getType().equals(type)) {
//                    return true;
//
//                } else {
//                    return false;
//                }
//            }
//        });
    }
}
