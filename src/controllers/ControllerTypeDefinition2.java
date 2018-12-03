package controllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerTypeDefinition2 implements Initializable {
    @FXML
    public VBox typeDefinition;
    public Button _btn_next;

    @FXML
    private TableView<TableViewApparatus> _tableView;
    @FXML
    private TableColumn<TableViewApparatus, String> _tableColumn1;
    @FXML
    private TableColumn<TableViewApparatus, Integer> _tableColumn2;
    @FXML
    private TableColumn<TableViewApparatus, Boolean> _tableColumn3;
    @FXML
    private Label label;

    //TODO Статическая переменная ниже передается и используется далее(чтобы изменения там сразу отражались и здесь)
    public static ObservableList<CatalogItem> apparatusData = FXCollections.observableArrayList();
    public static ObservableList<TableViewApparatus> choosedApparatus = FXCollections.observableArrayList();

//    private static ObservableList<TableViewApparatus> tableData = FXCollections.observableArrayList();


    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/type_definition_1_result_table.fxml"));
        typeDefinition.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/second_frame.fxml"));
        typeDefinition.getChildren().setAll(vBox);
    }

    @FXML
    private void theNext() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/type_definition_3.fxml"));
        typeDefinition.getChildren().setAll(vBox);
    }

    public void readData() {
        apparatusData.clear();

        apparatusData.addAll(DB.getCatalogTitlesByType("AOZU"));
        apparatusData.addAll(DB.getCatalogTitlesByType("ATZU"));
    }

    private void setupTable() {
        // Название
        _tableColumn1.setCellValueFactory(new PropertyValueFactory<TableViewApparatus, String>("fullName"));

        // Колчество
        _tableColumn2.setCellValueFactory(new PropertyValueFactory<TableViewApparatus, Integer>("count"));
        _tableColumn2.setCellFactory(TextFieldTableCell.<TableViewApparatus, Integer>forTableColumn(new IntegerStringConverter()));
        _tableColumn2.setMinWidth(10);
        _tableColumn2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableViewApparatus, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableViewApparatus, Integer> event) {
                TablePosition<TableViewApparatus, Integer> pos = event.getTablePosition();

                Integer newCount = event.getNewValue();

                // Делаем кол-во положительным
                if (newCount < 1) newCount = 1;

                int row = pos.getRow();
                TableViewApparatus apparatus = event.getTableView().getItems().get(row);

                apparatus.setCount(newCount);

                // Обновляем таблицу
                //TODO: а надо?
//                event.getTableView().refresh();
            }
        });

        // Checkbox
        _tableColumn3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableViewApparatus, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<TableViewApparatus, Boolean> param) {
                final TableViewApparatus tableViewApparatus = param.getValue();

                SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(tableViewApparatus.isChoose());

                booleanProperty.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (newValue) {
                            if (!choosedApparatus.contains(tableViewApparatus)) {
                                choosedApparatus.add(tableViewApparatus);
                            }
                        } else {
                            if (choosedApparatus.contains(tableViewApparatus)) {
                                choosedApparatus.remove(tableViewApparatus);
                            }
                        }
                        tableViewApparatus.setChoose(newValue);

                    }
                });
                return booleanProperty;
            }
        });
        _tableColumn3.setCellFactory(new Callback<TableColumn<TableViewApparatus, Boolean>, TableCell<TableViewApparatus, Boolean>>() {
            @Override
            public TableCell<TableViewApparatus, Boolean> call(TableColumn<TableViewApparatus, Boolean> param) {
                CheckBoxTableCell<TableViewApparatus, Boolean> cell = new CheckBoxTableCell<TableViewApparatus, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        readData();

        setupTable();

        _tableView.getItems().clear();

        _tableView.setItems(getTableViewApparatusList());

        choosedApparatus.addListener(new ListChangeListener<TableViewApparatus>() {
            @Override
            public void onChanged(Change<? extends TableViewApparatus> c) {
                if (choosedApparatus.isEmpty()) {
                    disableButton();
                } else {
                    enableButton();
                }
            }
        });
    }

    private ObservableList<TableViewApparatus> getTableViewApparatusList() {

        //TableViewChooseCategory person1 = new TableViewChooseCategory("Susan Smith",  false);
        //TableViewChooseCategory person2 = new TableViewChooseCategory("Anne McNeil",  false);
        //TableViewChooseCategory person3 = new TableViewChooseCategory("Kenvin White", false);

        ObservableList<TableViewApparatus> tableData = FXCollections.observableArrayList();
        for (int i = 0; i < apparatusData.size(); i++) {

            Integer apparatusId = apparatusData.get(i).getId();
            String apparatusTitle = apparatusData.get(i).getTitle();

            if (isApparatusWasChoosed(apparatusTitle)) {
                tableData.add(new TableViewApparatus(
                        apparatusId,
                        apparatusTitle,
                        getApparatusFromChoosed(apparatusTitle).getCount(),
                        true
                ));

                enableButton();
            }
            else
                tableData.add(new TableViewApparatus(
                        apparatusId,
                        apparatusTitle
                ));
        }
        return tableData;
    }

    private boolean isApparatusWasChoosed(String apparatus_title) {
        boolean result = false;

        for (TableViewApparatus apparatus : choosedApparatus) {
            if (apparatus.getFullName().equals(apparatus_title))
                result = true;
        }

        return result;
    }

    private TableViewApparatus getApparatusFromChoosed(String apparatus_title) {
        TableViewApparatus result = null;

        for (TableViewApparatus apparatus : choosedApparatus) {
            if (apparatus.getFullName().equals(apparatus_title))
                result = apparatus;
        }

        return result;
    }

    private void enableButton(){
        _btn_next.setDisable(false);
    }

    private void disableButton(){
        _btn_next.setDisable(true);
    }
}
