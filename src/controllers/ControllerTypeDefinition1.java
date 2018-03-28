package controllers;

import com.sun.istack.internal.NotNull;
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

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;

import static model.InfoModel.filterInfoModelByType;

public class ControllerTypeDefinition1 implements Initializable{
    @FXML
    public VBox typeDefinition;
    @FXML
    private ListView listViewOfficial;

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<TableViewChooseCategory, String> tableColumn1;
    @FXML
    private TableColumn<TableViewChooseCategory, Boolean> tableColumn2;

    private LinkedHashSet linkedHashSetOfficial = new LinkedHashSet();
    private static ObservableList<TableViewChooseCategory> observableListIsChange = FXCollections.observableArrayList();
    private static ObservableList<TableViewChooseCategory> observableListForTable = FXCollections.observableArrayList();
    private ObservableList<InfoModel> changingList = FXCollections.observableArrayList();
    private ObservableList<InfoModel> changingListAnother = FXCollections.observableArrayList();
    private LinkedHashSet arraySetTypeDef = new LinkedHashSet();

    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/choose_category_of_official_1.fxml"));
        typeDefinition.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/second_frame.fxml"));
        typeDefinition.getChildren().setAll(vBox);
    }

    @FXML
    private void theNext() throws IOException{
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/type_definition_2.fxml"));
        typeDefinition.getChildren().setAll(vBox);

    }

    public void setTrueIsChangeList(LinkedHashSet s){
        this.linkedHashSetOfficial = s;
        observableListIsChange.addAll(linkedHashSetOfficial);
        listViewOfficial.setItems(observableListIsChange);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        changingList.clear();
        changingList.addAll(XMLsaver.loadFromXML(InfoModel.FILENAME_INFOMODELS));

        listViewOfficial.setItems(observableListIsChange);
        listViewOfficial.getSelectionModel().selectedItemProperty().addListener(new javafx.beans.value.ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                changingListAnother.clear();
                observableListForTable.clear();

                changingListAnother.addAll(filterInfoModelByType("DS", changingList));
                changingListAnother.addAll(filterInfoModelByType("ZAS", changingList));
                changingListAnother.addAll(filterInfoModelByType("ARM", changingList));

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
                        arraySetTypeDef.add(tableViewChooseCategory.getFullName());
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
}
