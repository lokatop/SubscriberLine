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
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.InfoModel;
import model.TableViewChooseCategory;
import model.TableViewTypeDef1;
import model.XMLsaver;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static model.InfoModel.filterInfoModelByType;

public class ControllerTypeDefinition1 implements Initializable{
    @FXML
    public VBox typeDefinition;
    @FXML
    private ListView listViewOfficial;

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<TableViewTypeDef1, String> tableColumn1;
    @FXML
    private TableColumn<TableViewTypeDef1, Boolean> tableColumn2;

    private static ObservableList<TableViewChooseCategory> observableListIsChange = FXCollections.observableArrayList();
    private static ObservableList<TableViewTypeDef1> observableListTypeDef1 = FXCollections.observableArrayList();
    private static ObservableList<TableViewTypeDef1> observableListTypeDef3 = FXCollections.observableArrayList();
    private ObservableList<InfoModel> changingList = FXCollections.observableArrayList();
    private ObservableList<InfoModel> changingListAnother = FXCollections.observableArrayList();
    private static LinkedHashMap<String, ObservableList<TableViewTypeDef1>> callback = new LinkedHashMap<>();
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

    //Тут получаем массим с выбранными людьми из прошлого фрейма
    //И ставим tableView пустым
    public void setTrueIsChangeList(LinkedHashSet s){
        observableListIsChange.clear();
        observableListIsChange.addAll(s);
        listViewOfficial.setItems(observableListIsChange);
        tableView.setItems(null);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (changingList.size() == 0){
            changingList.addAll(XMLsaver.loadFromXML(InfoModel.FILENAME_INFOMODELS));
        }

        listViewOfficial.setItems(observableListIsChange);
        listViewOfficial.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        //Подчищаем
                        observableListTypeDef1.clear();
                        changingListAnother.clear();

                        //Добавляем в переменную из infoModels
                        if(changingList!= null) {
                            changingListAnother.addAll(filterInfoModelByType("DS", changingList));
                            changingListAnother.addAll(filterInfoModelByType("ZAS", changingList));
                            changingListAnother.addAll(filterInfoModelByType("ARM", changingList));
                        }

                        //Создаем наши объекты из числа тех переменных
                        for (int i = 0; i < changingListAnother.size();i++) {
                            observableListTypeDef1.add
                                    (new TableViewTypeDef1(i,listViewOfficial.getSelectionModel()
                                            .getSelectedItems().toString(),
                                            changingListAnother.get(i).getTitle()));
                        }


                        tableView.setItems(observableListTypeDef1);
                    }
                });


        tableColumn1.setCellValueFactory(new PropertyValueFactory<TableViewTypeDef1,String>("equipment"));
        tableColumn2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableViewTypeDef1, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<TableViewTypeDef1, Boolean> param) {
                final TableViewTypeDef1 tableViewTypeDef1 = param.getValue();

                SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(tableViewTypeDef1.isBoolen());
                booleanProperty.addListener(new javafx.beans.value.ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        tableViewTypeDef1.setBoolen(newValue);
                    }
                });
                return booleanProperty;
            }
        });
        tableColumn2.setCellFactory(new Callback<TableColumn<TableViewTypeDef1, Boolean>, TableCell<TableViewTypeDef1, Boolean>>() {
            @Override
            public TableCell<TableViewTypeDef1, Boolean> call(TableColumn<TableViewTypeDef1, Boolean> param) {
                CheckBoxTableCell<TableViewTypeDef1, Boolean> cell = new CheckBoxTableCell<TableViewTypeDef1, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
    }
}
