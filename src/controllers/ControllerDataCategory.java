package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.ChooseModel;
import model.XMLsaver;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import static controllers.ControllerChooseCategoryScheme.chooseData;
import static controllers.ControllerChooseCategoryScheme.filterChooseModelByType;

public class ControllerDataCategory implements Initializable{

    @FXML
    public VBox VboxChooseData;

    @FXML
    private TextField textField;

    @FXML
    private ComboBox comboChooseDate;

    public String description = "";

    @FXML
    public ListView<ChooseModel> listViewChooseDate;
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
    private void AddData(){
        if(textField.getText()!= null){
            chooseData.add(new ChooseModel(textField.getText(),comboChooseDate.getSelectionModel().getSelectedItem().toString(),description));
            textField.setText("");
        }
    }

    @FXML
    private void DeleteData(){
        chooseData.remove(listViewChooseDate.getSelectionModel().getSelectedItem());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        comboChooseDate.getItems().addAll(filterChooseModelByType("types"));
        comboChooseDate.getSelectionModel().select(0);

        listViewChooseDate.setItems(filterChooseModelByType(comboChooseDate.getSelectionModel().getSelectedItem().toString()));
    }

    @FXML
    public void comboChooseAction(){
        listViewChooseDate.setItems(filterChooseModelByType(comboChooseDate.getSelectionModel().getSelectedItem().toString()));
    }

    public void setSelectToComboBox(String s){
        comboChooseDate.getSelectionModel().select(1);
        listViewChooseDate.setItems(filterChooseModelByType(comboChooseDate.getSelectionModel().getSelectedItem().toString()));
        this.description = s;
    }

}
