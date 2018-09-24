package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.CategoryOfManagePoint;
import model.DB;
import model.MilitaryPart;
import model.XMLsaver;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static controllers.ControllerChooseCategoryScheme.chooseData;
import static controllers.ControllerChooseCategoryScheme.filterChooseModelByType;

public class ControllerDataCategory implements Initializable {

    @FXML
    public VBox VboxChooseData;

    @FXML
    private TextField textField;

    @FXML
    private ComboBox comboChooseDate;

    public String description = "";

    private String buttonId = "";

    @FXML
    public ListView listViewChooseDate;
    private ArrayList arrayOfList;

    public void buttonApply() throws IOException {

        XMLsaver.saveToXML(chooseData, "ChooseModels.xml");

        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/choose_category_scheme.fxml"));
        VboxChooseData.getChildren().setAll(vBox);
    }

    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/choose_category_scheme.fxml"));
        VboxChooseData.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/second_frame.fxml"));
        VboxChooseData.getChildren().setAll(vBox);
    }

    @FXML
    private void AddData() {

        if (textField.getText() != null && !textField.getText().isEmpty()) {

            switch (buttonId) {
                case "type_of_military_part":
                    DB.saveNewMilitaryPart(textField.getText());
                    break;
                case "category_of_manage_point":
                    DB.saveNewCategoryOfManagePoint(textField.getText(),((MilitaryPart) comboChooseDate.getSelectionModel().getSelectedItem()).getId());
                    break;

                default:
                    break;
            }
            updateList();
            textField.clear();
        }


    }

    @FXML
    private void DeleteData() {
        //chooseData.remove(listViewChooseDate.getSelectionModel().getSelectedItem());

        if (listViewChooseDate.getSelectionModel().getSelectedItem() != null) {

            Object selectedObj = listViewChooseDate.getSelectionModel().getSelectedItem();

            switch (buttonId) {
                case "type_of_military_part":
                    DB.deleteMilitaryPartById(((MilitaryPart) selectedObj).getId());
                    break;
                case "category_of_manage_point":
                    DB.deleteCategoryOfManagePointById(((CategoryOfManagePoint) selectedObj).getId());
                    break;

                default:
                    break;
            }
            updateList();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void comboChooseAction() {
        listViewChooseDate.setItems(filterChooseModelByType(comboChooseDate.getSelectionModel().getSelectedItem().toString()));
    }

    public void setSelectToComboBox(String s) {
        comboChooseDate.getSelectionModel().select(1);
        listViewChooseDate.setItems(filterChooseModelByType(comboChooseDate.getSelectionModel().getSelectedItem().toString()));
        this.description = s;
    }

    public void setData(String s) {
        buttonId = s;

        switch (buttonId) {
            case "type_of_military_part":
                comboChooseDate.setVisible(false);
                listViewChooseDate.setItems(DB.getMilitaryParts());
                break;
            case "category_of_manage_point":
                comboChooseDate.getItems().addAll(DB.getMilitaryParts());
                comboChooseDate.getSelectionModel().select(0);
                comboChooseDate.setVisible(true);

                comboChooseDate.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observableValue, Object o, Object t1) {
                        updateList();
                    }
                });

                updateList();

                break;

            default:
                break;
        }

    }

    private void updateList(){
        switch (buttonId) {
            case "type_of_military_part":
                listViewChooseDate.setItems(DB.getMilitaryParts());
                break;
            case "category_of_manage_point":
                listViewChooseDate.setItems(DB.getManagePointsFromMilitaryPartById(
                        ((MilitaryPart) comboChooseDate.getSelectionModel().getSelectedItem()).getId()
                ));

                break;

            default:
                break;
        }
    }

}
