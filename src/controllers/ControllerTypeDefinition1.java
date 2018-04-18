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
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;

import static model.InfoModel.filterInfoModelByType;
import static model.TheLastTable.FILENAME_THELASTTABLE;

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

    private LinkedHashSet stringListFromListView = new LinkedHashSet();

    private static ObservableList<TableViewTypeDef1> observableListIsChange = FXCollections.observableArrayList();
    private static ObservableList<TableViewTypeDef1> observableListTypeDef1 = FXCollections.observableArrayList();
    private static ObservableList<TableViewTypeDef1> observableListTypeDef12 = FXCollections.observableArrayList();
    private static ObservableList<TableViewTypeDef1> observableTheLast = FXCollections.observableArrayList();
    private static ObservableList<TableViewTypeDef1> observableTheLast2 = FXCollections.observableArrayList();
    private ObservableList<InfoModel> changingList = FXCollections.observableArrayList();
    private ObservableList<InfoModel> changingListAnother = FXCollections.observableArrayList();
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

            changingListAnother.clear();

            //Добавляем в переменную из infoModels
            if(changingList!= null) {
                changingListAnother.addAll(filterInfoModelByType("DS", changingList));
                changingListAnother.addAll(filterInfoModelByType("ZAS", changingList));
                changingListAnother.addAll(filterInfoModelByType("ARM", changingList));
            }

        }

        listViewOfficial.setItems(observableListIsChange);

        tableColumn1.setCellValueFactory(new PropertyValueFactory<TableViewTypeDef1,String>("equipment"));
        tableColumn2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableViewTypeDef1, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<TableViewTypeDef1, Boolean> param) {
                final TableViewTypeDef1 tableViewTypeDef1 = param.getValue();

                SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(tableViewTypeDef1.isBoolen());
                booleanProperty.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        tableViewTypeDef1.setBoolen(newValue);
                        observableListTypeDef12.add(tableViewTypeDef1);

                        XMLsaver.saveToXML(tableViewTypeDef1, FILENAME_THELASTTABLE);
                        XMLsaver.saveToXML(observableListTypeDef1, "ChooseCategory.xml");
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


        listViewOfficial.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        //Подчищаем
                        observableListTypeDef1.clear();
                        String fromListViewName = "";
                        fromListViewName = listViewOfficial.getSelectionModel()
                                .getSelectedItems().toString();

                        if(stringListFromListView.contains(fromListViewName)){

                            observableTheLast2.addAll(XMLsaver.loadFromXML("ChooseCategory.xml"));
                            observableTheLast = TableViewTypeDef1.filterByDefCategoryName(fromListViewName,observableTheLast2);
                            for (int i = 0; i < observableTheLast2.size(); i++) {
                                observableListTypeDef1.addAll(observableTheLast);
                            }

                        }
                        else {
                            //Создаем наши объекты из числа тех переменных
                            for (int i = 0; i < changingListAnother.size();i++) {
                                observableListTypeDef1.add
                                        (new TableViewTypeDef1(fromListViewName,
                                                changingListAnother.get(i).getTitle()));
                            }

                            XMLsaver.saveToXML(observableListTypeDef1, "ChooseCategory.xml");

                            stringListFromListView.add(fromListViewName);
                        }

                        tableView.setItems(observableListTypeDef1);

                    }
                });

    }
}
