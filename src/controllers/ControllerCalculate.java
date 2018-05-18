package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
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

    }

    private void setupTable(){
        officialPerson.setCellValueFactory(new PropertyValueFactory<>("officialPerson"  ));
        typeAbon.setCellValueFactory(      new PropertyValueFactory<>("typeAbon"        ));
        appFrom1.setCellValueFactory(      new PropertyValueFactory<>("appFrom1"        ));
        typeCable.setCellValueFactory(     new PropertyValueFactory<>("typeCable"       ));
        lengthCable.setCellValueFactory(   new PropertyValueFactory<>("lengthCable"     ));
        lengthCable.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        lengthCable.setMinWidth(10);
        lengthCable.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TheLastTable, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TheLastTable, Integer> event) {
                TablePosition<TheLastTable, Integer> pos = event.getTablePosition();

                Integer newLength= event.getNewValue();

                // Делаем кол-во положительным
                if (newLength<=0) newLength = 1;

                int row = pos.getRow();
                TheLastTable lastTable = event.getTableView().getItems().get(row);

                lastTable.setLengthCable(newLength);

                // Обновляем таблицу
                event.getTableView().refresh();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupTable();

        tableView.setItems(ControllerTypeDefinition3.theLastTableListUpdatedD3);
    }
}
