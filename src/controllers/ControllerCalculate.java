package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.TheLastTable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCalculate implements Initializable{
    @FXML
    public VBox vbox;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<TheLastTable,String> officialPerson,
            typeAbon,appFrom1,typeCable,
            appFrom2;
    @FXML
    private TableColumn<TheLastTable, Integer> lengthCable;


    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/type_cable.fxml"));
        vbox.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/second_frame.fxml"));
        vbox.getChildren().setAll(vBox);
    }

    @FXML
    private void calculate() throws IOException{

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        officialPerson.setCellValueFactory(new PropertyValueFactory<TheLastTable,String>("officialPerson"  ));
        typeAbon.setCellValueFactory(      new PropertyValueFactory<TheLastTable,String>("typeAbon"        ));
        appFrom1.setCellValueFactory(      new PropertyValueFactory<TheLastTable,String>("appFrom1"        ));
        typeCable.setCellValueFactory(     new PropertyValueFactory<TheLastTable,String>("typeCable"       ));
        appFrom2.setCellValueFactory(      new PropertyValueFactory<TheLastTable, String>("appFrom2"        ));
        lengthCable.setCellValueFactory(   new PropertyValueFactory<TheLastTable, Integer>("lengthCable"     ));

        tableView.setItems(ControllerTypeDefinition1.theLastTableList);
    }
}
