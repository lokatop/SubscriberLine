package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeSet;

import static controllers.ControllerChooseCategoryScheme.setOfList;

public class ControllerDataCategory implements Initializable{

    @FXML
    public VBox VboxChooseData;

    @FXML
    public ListView<String> listViewChooseDate;
    private ObservableList observableList = FXCollections.observableArrayList();
    private TreeSet setOfList;

    public void buttonApply(){

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

    public void setSetOfList(TreeSet setOfList1){
        this.setOfList = setOfList1;

        observableList.addAll(setOfList);
        listViewChooseDate.setItems(observableList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


}
