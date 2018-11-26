package controllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.ChooseModel;
import model.DB;
import model.Official;
import model.TableViewChooseCategory;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;

public class ControllerChooseCategoryOfOfficial implements Initializable {
    @FXML
    private VBox selectionOfOfficials;

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<TableViewChooseCategory, String> tableColumn1;
    @FXML
    private TableColumn<TableViewChooseCategory, Boolean> tableColumn2;
    @FXML
    private Label label;
    @FXML
    public Button _next_btn;

    private static String nameForFindFromXml;
    private static String viewForFindFromXml;

    private int idMilitaryPart;
    private int idCategoryOfManagePoint;

    private ObservableList<ChooseModel> list2;

    private ObservableList<Official> listOfficial;

    private static ObservableList<TableViewChooseCategory> listSetTable;

    public static LinkedHashSet arraySetOfficial = new LinkedHashSet();
    public static LinkedHashSet arrayOfficialsId = new LinkedHashSet();

    private static ObservableList<TableViewChooseCategory> observableList = FXCollections.observableArrayList();


    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass()
                .getResource("/fxml/choose_category_scheme.fxml"));
        selectionOfOfficials.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass()
                .getResource("/fxml/second_frame.fxml"));
        selectionOfOfficials.getChildren().setAll(vBox);
    }

    @FXML
    private void theNext() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/fxml/type_definition_1.fxml"));
        try {
            VBox vBox = (VBox) loader.load();

            // Передаём выбранную модель в контроллер фрейма Описание
//            ControllerTypeDefinition1 controller = loader.getController();
//            controller.setTrueIsChangeList(arraySetOfficial);

            // Оотображаем
            selectionOfOfficials.getChildren().setAll(vBox);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void EditOriginalData(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/fxml/choose_data_category.fxml"));
        try {
            VBox vBox = (VBox) loader.load();

            // Передаём выбранную модель в контроллер фрейма Описание
            ControllerDataCategory controller = loader.getController();
            controller.setData(((Button) actionEvent.getSource()).getId());

            // Оотображаем
            selectionOfOfficials.getChildren().setAll(vBox);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setChooseCategory(String s, String a, int ss, int aa) {
        this.nameForFindFromXml = s;
        this.viewForFindFromXml = a;

        this.idCategoryOfManagePoint = ss;
        this.idMilitaryPart = aa;

        listSetTable = getTableViewChooseCategoryList();
        tableView.setItems(listSetTable);

//        label.setText(nameForFindFromXml);
        label.setText(DB.getMilitaryPartById(idMilitaryPart).getTitle());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        arraySetOfficial.clear();
        arrayOfficialsId.clear();
        tableColumn1.setCellValueFactory(new PropertyValueFactory<TableViewChooseCategory, String>("fullName"));
        tableColumn2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableViewChooseCategory, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<TableViewChooseCategory, Boolean> param) {
                final TableViewChooseCategory tableViewChooseCategory = param.getValue();

                SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(tableViewChooseCategory.isChoose());

                booleanProperty.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        tableViewChooseCategory.setChoose(newValue);

                        if (newValue) {
                            //observableList.add(new TableViewChooseCategory(tableViewChooseCategory.getFullName(),newValue));
                            arraySetOfficial.add(tableViewChooseCategory.getFullName());
                            arrayOfficialsId.add(tableViewChooseCategory.getId());

                            enableNextButton();
                        } else {
                            arraySetOfficial.remove(tableViewChooseCategory.getFullName());
                            arrayOfficialsId.remove(tableViewChooseCategory.getId());

                            if (arraySetOfficial.size() == 0 || arrayOfficialsId.size() == 0) {
                                diableNextButton();
                            }
                        }
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

        listSetTable = getTableViewChooseCategoryList();
        tableView.setItems(listSetTable);

        diableNextButton();
    }

    private void diableNextButton() {
        _next_btn.setDisable(true);
    }

    private void enableNextButton() {
        _next_btn.setDisable(false);
    }

    private ObservableList<TableViewChooseCategory> getTableViewChooseCategoryList() {

        ObservableList<TableViewChooseCategory> list = FXCollections.observableArrayList();

        listOfficial = DB.getOfficialsFromMilitaryPartById(idMilitaryPart);

        for (Official official : listOfficial) {
            list.add(new TableViewChooseCategory(
                    official.getTitle()
            ));
        }

//        list2 = filterChooseModelByDescription(viewForFindFromXml);
//        for (int i = 0; i<list2.size();i++){
//
//         list.add( new TableViewChooseCategory(
//                 list2.get(i).getTitle()
//         ));
//        }
        return list;
    }
}
