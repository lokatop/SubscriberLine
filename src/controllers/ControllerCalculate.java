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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.InfoModel;
import model.TheLastTable;
import model.XMLsaver;
import thread.FirstTreadOnCalc_2;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static controllers.ControllerTypeCable.filterInfoModelByTitle;

public class ControllerCalculate implements Initializable{
    @FXML
    public VBox vbox;
    @FXML
    private TableView tableView,tableViewAbon,tableViewCable,tableViewApp;

    @FXML
    private TableColumn<TheLastTable,String> officialPerson,
            typeAbon,appFrom1;
    @FXML
    private TableColumn<TheLastTable,String> typeAbon2,typeCable2,appFrom12;
    @FXML
    private TableColumn<TheLastTable, Integer> lengthCable;
    @FXML
    private TableColumn<TheLastTable, Integer> amounfOfAbon,lengthOfCable,amountOfApp;
    @FXML
    private TableColumn<TheLastTable, String> typeCable;

    private static ObservableList<String> cableData = FXCollections.observableArrayList();
    /**
     * Список всех ТА для должностей
     */
    private ObservableList<InfoModel> infoModelsList = FXCollections.observableArrayList();
    private ObservableList<String> infoModelsListForListView = FXCollections.observableArrayList();
    private ObservableList unfilterred = FXCollections.observableArrayList();

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
    private void calculate() throws IOException{
        createWordTable();
    }

    private void setupTable(){
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (unfilterred.isEmpty()){
                    // Чтение из файла
                    unfilterred.addAll(XMLsaver.loadFromXML(InfoModel.FILENAME_INFOMODELS));
                }
                TheLastTable selectedItem = (TheLastTable) tableView.getSelectionModel().getSelectedItem();
                String selectedItemAppFrom1 = selectedItem.getAppFrom1();
                infoModelsList.clear();
                infoModelsListForListView.clear();
                infoModelsList.addAll(filterInfoModelByTitle(selectedItemAppFrom1, unfilterred));

                for (InfoModel infoModel : infoModelsList) {
                    List<Map<String, String>> data = infoModel.parseCables();
                    for (Map<String,String> cable:data){
                        infoModelsListForListView.add(cable.get("name"));
                    }
                }
            }
        });

        officialPerson.setCellValueFactory(new PropertyValueFactory<TheLastTable,String>("officialPerson"  ));
        typeAbon.setCellValueFactory(      new PropertyValueFactory<TheLastTable,String>("typeAbon"        ));
        appFrom1.setCellValueFactory(      new PropertyValueFactory<TheLastTable,String>("appFrom1"        ));
        appFrom1.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TheLastTable, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TheLastTable, String> event) {
                TablePosition<TheLastTable,String> pos = event.getTablePosition();

                int row = pos.getRow();
                TheLastTable lastTable = event.getTableView().getItems().get(row);

            }
        });


        typeCable.setCellValueFactory(     new PropertyValueFactory<TheLastTable, String>("typeCable"       ));
        typeCable.setCellFactory(ComboBoxTableCell.<TheLastTable, String>forTableColumn(new DefaultStringConverter(),infoModelsListForListView));
        typeCable.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TheLastTable, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TheLastTable, String> event) {
                TablePosition<TheLastTable,String> pos = event.getTablePosition();

                int row = pos.getRow();
                TheLastTable lastTable = event.getTableView().getItems().get(row);

                lastTable.getAppFrom1();
                lastTable.setTypeCable(event.getNewValue());
            }
        });
        lengthCable.setCellValueFactory(   new PropertyValueFactory<TheLastTable, Integer>("lengthCable"     ));
        lengthCable.setCellFactory(TextFieldTableCell.<TheLastTable, Integer>forTableColumn(new IntegerStringConverter()));
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
                //TODO: а надо?
//                event.getTableView().refresh();
            }
        });
    }

    private void setupTableAbon(){
        typeAbon2.setCellValueFactory(      new PropertyValueFactory<TheLastTable,String>("typeAbon"        ));
        amounfOfAbon.setCellValueFactory(   new PropertyValueFactory<TheLastTable, Integer>("lengthCable"     ));
        amounfOfAbon.setCellFactory(TextFieldTableCell.<TheLastTable, Integer>forTableColumn(new IntegerStringConverter()));
        amounfOfAbon.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TheLastTable, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TheLastTable, Integer> event) {
                TablePosition<TheLastTable, Integer> pos = event.getTablePosition();
                Integer newLength= event.getNewValue();
                // Делаем кол-во положительным
                if (newLength<=0) newLength = 1;
                int row = pos.getRow();
                TheLastTable lastTable = event.getTableView().getItems().get(row);
                lastTable.setLengthCable(newLength);
            }
        });
    }
    private void setupTableCable(){
        typeCable2.setCellValueFactory(      new PropertyValueFactory<TheLastTable,String>("typeCable"        ));
        lengthOfCable.setCellValueFactory(   new PropertyValueFactory<TheLastTable, Integer>("lengthCable"     ));
        lengthOfCable.setCellFactory(TextFieldTableCell.<TheLastTable, Integer>forTableColumn(new IntegerStringConverter()));
        lengthOfCable.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TheLastTable, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TheLastTable, Integer> event) {
                TablePosition<TheLastTable, Integer> pos = event.getTablePosition();
                Integer newLength= event.getNewValue();
                // Делаем кол-во положительным
                if (newLength<=0) newLength = 1;
                int row = pos.getRow();
                TheLastTable lastTable = event.getTableView().getItems().get(row);
                lastTable.setLengthCable(newLength);
            }
        });
    }
    private void setupTableApp(){
        appFrom12.setCellValueFactory(      new PropertyValueFactory<TheLastTable,String>("appFrom1"        ));
        amountOfApp.setCellValueFactory(   new PropertyValueFactory<TheLastTable, Integer>("lengthCable"     ));
        amountOfApp.setCellFactory(TextFieldTableCell.<TheLastTable, Integer>forTableColumn(new IntegerStringConverter()));
        amountOfApp.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TheLastTable, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TheLastTable, Integer> event) {
                TablePosition<TheLastTable, Integer> pos = event.getTablePosition();
                Integer newLength= event.getNewValue();
                // Делаем кол-во положительным
                if (newLength<=0) newLength = 1;
                int row = pos.getRow();
                TheLastTable lastTable = event.getTableView().getItems().get(row);
                lastTable.setLengthCable(newLength);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupTable();setupTableAbon();setupTableCable();setupTableApp();

        tableView.setItems(ControllerTypeDefinition3.theLastTableListUpdatedD3);
        tableViewAbon.setItems(ControllerTypeDefinition3.theLastTableListUpdatedD3);
        tableViewCable.setItems(ControllerTypeDefinition3.theLastTableListUpdatedD3);
        tableViewApp.setItems(ControllerTypeDefinition3.theLastTableListUpdatedD3);
    }

    public void createWordTable() throws IOException {
        FirstTreadOnCalc_2 tread = new FirstTreadOnCalc_2();
        tread.run(ControllerTypeDefinition3.theLastTableListUpdatedD3);
    }
}
