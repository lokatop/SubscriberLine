package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
import model.CatalogItem;
import model.DB;
import model.TheLastTable;
import thread.FirstTreadOnCalc_2;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.ResourceBundle;

public class ControllerCalculate implements Initializable {
    @FXML
    public VBox vbox;
    public ListView<CatalogItem> typeCable_choose_list;
    @FXML
    private TableView tableView, tableViewAbon, tableViewCable, tableViewApp;

    @FXML
    private TableColumn<TheLastTable, String> officialPerson,
            typeAbon, appFrom1;
    @FXML
    private TableColumn<TheLastTable, String> typeAbon2, typeCable2, appFrom12;
    @FXML
    private TableColumn<TheLastTable, Integer> lengthCable;
    @FXML
    private TableColumn<TheLastTable, Integer> amounfOfAbon, lengthOfCable, amountOfApp;
    @FXML
    private TableColumn<TheLastTable, String> typeCable;

    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/type_definition_3.fxml"));
        vbox.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/second_frame.fxml"));
        vbox.getChildren().setAll(vBox);
    }

    @FXML
    private void calculate() throws IOException {
        createWordTable();
    }

    private void setupTable() {

        ((TableView<TheLastTable>)tableView).getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TheLastTable>() {
            @Override
            public void changed(ObservableValue<? extends TheLastTable> observableValue, TheLastTable theLastTable, TheLastTable t1) {
                TheLastTable selectedItem = (TheLastTable) tableView.getSelectionModel().getSelectedItem();
                String appTitle = selectedItem.getAppFrom1();

                // Читаем из БД

                try {
                    CatalogItem app = DB.getCatalogItemByTitle(appTitle);

                    ObservableList<CatalogItem> cableList = DB.getCablesInApparatousById(app.getId());

                    typeCable_choose_list.getItems().clear();
                    typeCable_choose_list.getItems().addAll(cableList);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        officialPerson.setCellValueFactory(new PropertyValueFactory<TheLastTable, String>("officialPerson"));
        typeAbon.setCellValueFactory(new PropertyValueFactory<TheLastTable, String>("typeAbon"));
        appFrom1.setCellValueFactory(new PropertyValueFactory<TheLastTable, String>("appFrom1"));
        appFrom1.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TheLastTable, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TheLastTable, String> event) {
                TablePosition<TheLastTable, String> pos = event.getTablePosition();

                int row = pos.getRow();
                TheLastTable lastTable = event.getTableView().getItems().get(row);

            }
        });

        typeCable.setCellValueFactory(new PropertyValueFactory<TheLastTable, String>("typeCable"));

        lengthCable.setCellValueFactory(new PropertyValueFactory<TheLastTable, Integer>("lengthCable"));
        lengthCable.setCellFactory(TextFieldTableCell.<TheLastTable, Integer>forTableColumn(new IntegerStringConverter()));
        lengthCable.setMinWidth(10);
        lengthCable.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TheLastTable, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TheLastTable, Integer> event) {
                TablePosition<TheLastTable, Integer> pos = event.getTablePosition();

                Integer newLength = event.getNewValue();

                // Делаем кол-во положительным
                if (newLength <= 0) newLength = 1;

                int row = pos.getRow();
                TheLastTable lastTable = event.getTableView().getItems().get(row);

                lastTable.setLengthCable(newLength);

                // Обновляем таблицу
                //TODO: а надо?
//                event.getTableView().refresh();
            }
        });
    }

    private void setupTableAbon() {
        typeAbon2.setCellValueFactory(new PropertyValueFactory<TheLastTable, String>("typeAbon"));
        amounfOfAbon.setCellValueFactory(new PropertyValueFactory<TheLastTable, Integer>("amountAbon"));
        amounfOfAbon.setCellFactory(TextFieldTableCell.<TheLastTable, Integer>forTableColumn(new IntegerStringConverter()));
    }

    private void setupTableCable() {
        typeCable2.setCellValueFactory(new PropertyValueFactory<TheLastTable, String>("typeCable"));
        lengthOfCable.setCellValueFactory(new PropertyValueFactory<TheLastTable, Integer>("lengthCable"));
        lengthOfCable.setCellFactory(TextFieldTableCell.<TheLastTable, Integer>forTableColumn(new IntegerStringConverter()));
        lengthOfCable.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TheLastTable, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TheLastTable, Integer> event) {
                TablePosition<TheLastTable, Integer> pos = event.getTablePosition();
                Integer newLength = event.getNewValue();
                // Делаем кол-во положительным
                if (newLength <= 0) newLength = 1;
                int row = pos.getRow();
                TheLastTable lastTable = event.getTableView().getItems().get(row);
                lastTable.setLengthCable(newLength);
            }
        });
    }

    private void setupTableApp() {
        appFrom12.setCellValueFactory(new PropertyValueFactory<TheLastTable, String>("appFrom1"));
        amountOfApp.setCellValueFactory(new PropertyValueFactory<TheLastTable, Integer>("lengthCable"));
        amountOfApp.setCellFactory(TextFieldTableCell.<TheLastTable, Integer>forTableColumn(new IntegerStringConverter()));
        amountOfApp.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TheLastTable, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TheLastTable, Integer> event) {
                TablePosition<TheLastTable, Integer> pos = event.getTablePosition();
                Integer newLength = event.getNewValue();
                // Делаем кол-во положительным
                if (newLength <= 0) newLength = 1;
                int row = pos.getRow();
                TheLastTable lastTable = event.getTableView().getItems().get(row);
                lastTable.setLengthCable(newLength);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupTable();
        setupTableAbon();
        setupTableCable();
        setupTableApp();

        tableView.setItems(ControllerTypeDefinition3.theLastTableListUpdatedD3);
        tableViewCable.setItems(ControllerTypeDefinition3.theLastTableListUpdatedD3);
        tableViewApp.setItems(ControllerTypeDefinition3.theLastTableListUpdatedD3);

        fillTableViewAbon();
    }

    private void fillTableViewAbon(){
        ObservableList<TheLastTable> newList = FXCollections.observableArrayList();

        for (TheLastTable theLastTable : ControllerTypeDefinition3.theLastTableListUpdatedD3) {

            theLastTable.setAmountAbon(1);

            if (newList.size() == 0){
                newList.add(theLastTable);
                continue;
            }

            boolean exist = false;

            for (TheLastTable existedItem : newList) {
                if (existedItem.getTypeAbon().equals(theLastTable.getTypeAbon())){
                    Integer newCount = existedItem.getAmountAbon() + theLastTable.getAmountAbon();

                    existedItem.setAmountAbon(newCount);

                    exist = true;
                }
            }

            if (!exist) newList.add(theLastTable);
        }

        tableViewAbon.getItems().clear();
        tableViewAbon.getItems().addAll(newList);
    }

    public void createWordTable() throws IOException {
        FirstTreadOnCalc_2 tread = new FirstTreadOnCalc_2();
        tread.run(ControllerTypeDefinition3.theLastTableListUpdatedD3);
    }

    public void _typeCable_choose_list_add(ActionEvent actionEvent) {
        if (!typeCable_choose_list.getItems().isEmpty()){
            CatalogItem selectedCable = typeCable_choose_list.getSelectionModel().getSelectedItem();
            TheLastTable selectedTheLastTableItem = (TheLastTable) tableView.getSelectionModel().getSelectedItem();

            if (selectedCable != null){
                selectedTheLastTableItem.setTypeCable(selectedCable.getTitle());
            }
        }
    }
}
