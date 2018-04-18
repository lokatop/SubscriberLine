package controllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.InfoModel;
import model.TableViewChooseCategory;
import model.XMLsaver;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;

import static model.InfoModel.filterInfoModelByType;

public class ControllerTypeCable implements Initializable{
    @FXML
    public VBox vbox;
    @FXML
    private ListView listViewOfficial;

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<TableViewChooseCategory, String> tableColumn1;
    @FXML
    private TableColumn<TableViewChooseCategory, Boolean> tableColumn2;

    private static ObservableList<TableViewChooseCategory> observableListIsChange = FXCollections.observableArrayList();
    private static ObservableList<TableViewChooseCategory> observableListForTable = FXCollections.observableArrayList();
    private ObservableList<InfoModel> changingList = FXCollections.observableArrayList();
    private ObservableList<InfoModel> changingListAnother = FXCollections.observableArrayList();
    private LinkedHashSet arraySetTypeDef = new LinkedHashSet();

    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/type_definition_3.fxml"));
        vbox.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/second_frame.fxml"));
        vbox.getChildren().setAll(vBox);
    }

    @FXML
    private void theNext() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/calculate.fxml"));
        vbox.getChildren().setAll(vBox);
    }

    public void setList(LinkedHashSet s){
        //observableListIsChange.addAll(s);
        //listViewOfficial.setItems(observableListIsChange);
        //demo();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        demo();//TODO удалить и раскомментить setList
        changingList.clear();
        changingList.addAll(XMLsaver.loadFromXML(InfoModel.FILENAME_INFOMODELS));

        listViewOfficial.setItems(observableListIsChange);
        listViewOfficial.getSelectionModel().selectedItemProperty().addListener(new javafx.beans.value.ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                changingListAnother.clear();
                observableListForTable.clear();

                changingListAnother.addAll(filterInfoModelByType("CableAndOther", changingList));

                for (int i = 0; i < changingListAnother.size();i++) {
                    observableListForTable.add(new TableViewChooseCategory(changingListAnother.get(i).getTitle()));
                }
            }
        });

        tableColumn1.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tableColumn2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableViewChooseCategory, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<TableViewChooseCategory, Boolean> param) {
                TableViewChooseCategory tableViewChooseCategory = param.getValue();

                SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(tableViewChooseCategory.isChoose());
                booleanProperty.addListener(new javafx.beans.value.ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        tableViewChooseCategory.setChoose(newValue);
                        //observableList.add(new TableViewChooseCategory(tableViewChooseCategory.getFullName(),newValue));
                        //arraySetTypeDef.add(tableViewChooseCategory.getFullName());
                    }
                });
                return booleanProperty;
            }
        });
        tableColumn2.setCellFactory(new Callback<TableColumn<TableViewChooseCategory, Boolean>, TableCell<TableViewChooseCategory, Boolean>>() {
            @Override
            public TableCell<TableViewChooseCategory, Boolean> call(TableColumn<TableViewChooseCategory, Boolean> param) {
                CheckBoxTableCell<TableViewChooseCategory, Boolean> cell = new CheckBoxTableCell<TableViewChooseCategory, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });

        tableView.setItems(observableListForTable);
    }

    public void demo(){
        LinkedHashSet s = new LinkedHashSet();
        s.add("что-то 1");
        s.add("что-то 2");
        s.add("что-то 3");
        observableListIsChange.addAll(s);
        listViewOfficial.setItems(observableListIsChange);
    }

    public static void getTypeCable(){

    }
}
