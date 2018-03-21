package controllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.TableViewChooseCategory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerChooseCategoryOfOfficial implements Initializable{
    @FXML
    private VBox selectionOfOfficials;

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<TableViewChooseCategory, String> tableColumn1;
    @FXML
    private TableColumn<TableViewChooseCategory, Boolean> tableColumn2;


    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/choose_category_scheme.fxml"));
        selectionOfOfficials.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/second_frame.fxml"));
        selectionOfOfficials.getChildren().setAll(vBox);
    }

    @FXML
    private void theNext() throws IOException{
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/type_definition_1.fxml"));
        selectionOfOfficials.getChildren().setAll(vBox);
    }

    @FXML
    private void EditOriginalData() throws IOException{

        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/choose_category_of_official_apple.fxml"));
        selectionOfOfficials.getChildren().setAll(vBox);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableColumn1.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tableColumn2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableViewChooseCategory, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<TableViewChooseCategory, Boolean> param) {
                TableViewChooseCategory tableViewChooseCategory = param.getValue();

                SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(tableViewChooseCategory.isChoose());

                booleanProperty.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        tableViewChooseCategory.setChoose(newValue);
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

        ObservableList<TableViewChooseCategory> list = getTableViewChooseCategoryList();
        tableView.setItems(list);

        tableView.getColumns().addAll(tableColumn1, tableColumn2);
    }

    private ObservableList<TableViewChooseCategory> getTableViewChooseCategoryList() {

        TableViewChooseCategory person1 = new TableViewChooseCategory("Susan Smith",  false);
        TableViewChooseCategory person2 = new TableViewChooseCategory("Anne McNeil",  false);
        TableViewChooseCategory person3 = new TableViewChooseCategory("Kenvin White", false);

        ObservableList<TableViewChooseCategory> list = FXCollections.observableArrayList(person1, person2, person3);
        return list;
    }

}
