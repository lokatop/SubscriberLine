package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.TheLastTable;
import treads.FirstTreadOnCalc;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controllers.ControllerTypeDefinition1.theLastTableList;

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

    private static ObservableList<TheLastTable> theLastTableObservableList = FXCollections.observableArrayList();

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
        try {
            createWordTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        officialPerson.setCellValueFactory(new PropertyValueFactory<>("officialPerson"  ));
        typeAbon.setCellValueFactory(      new PropertyValueFactory<>("typeAbon"        ));
        appFrom1.setCellValueFactory(      new PropertyValueFactory<>("appFrom1"        ));
        typeCable.setCellValueFactory(     new PropertyValueFactory<>("typeCable"       ));
        appFrom2.setCellValueFactory(      new PropertyValueFactory<>("appFrom2"        ));
        lengthCable.setCellValueFactory(   new PropertyValueFactory<>("lengthCable"     ));

        tableView.setItems(theLastTableList);

    }

    public void createWordTable() throws IOException {
        FirstTreadOnCalc tread = new FirstTreadOnCalc();
        tread.run(theLastTableList);
    }

}
