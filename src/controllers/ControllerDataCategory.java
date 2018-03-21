package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.TreeSet;

import static controllers.ControllerChooseCategoryScheme.arrayOfList;
import static controllers.ControllerChooseCategoryScheme.chooseData;
import static controllers.ControllerChooseCategoryScheme.filterChooseModelByType;

public class ControllerDataCategory implements Initializable{

    @FXML
    public VBox VboxChooseData;

    @FXML
    private TextField textField;

    @FXML
    private ComboBox comboChooseDate;


    @FXML
    public ListView<ChooseModel> listViewChooseDate;
    private ObservableList observableList = FXCollections.observableArrayList();
    private ArrayList arrayOfList;

    public void buttonApply() throws IOException {

        XMLsaver.saveToXML(chooseData, "ChooseModels.xml");

        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/choose_category_scheme.fxml"));
        VboxChooseData.getChildren().setAll(vBox);
    }

    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/choose_category_scheme.fxml"));
        VboxChooseData.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/second_frame.fxml"));
        VboxChooseData.getChildren().setAll(vBox);
    }

    @FXML
    private void AddData(){
        if(textField.getText()!= null){
            chooseData.add(new ChooseModel(textField.getText(),"Категории пункта управления",""));
            textField.setText("");
        }
    }

    @FXML
    private void DeleteData(){
        chooseData.remove(listViewChooseDate.getSelectionModel().getSelectedItem());

    }
/*
    public void setSetOfList(ArrayList arrayOfList){
        this.arrayOfList = arrayOfList;

        observableList.addAll(arrayOfList);
        listViewChooseDate.setItems(observableList);
    }
*/

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


}
