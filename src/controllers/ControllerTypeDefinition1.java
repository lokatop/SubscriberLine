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
import javafx.scene.web.WebView;
import javafx.util.Callback;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.InfoModel.filterInfoModelByType;
import static model.TableViewTypeDef1.filterByNameOfOfficial;

public class ControllerTypeDefinition1 implements Initializable {
    @FXML
    public VBox typeDefinition;
    @FXML
    private ListView<String> listViewOfficial;

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<TableViewTypeDef1, String> tableColumn1;
    @FXML
    private TableColumn<TableViewTypeDef1, Boolean> tableColumn2;
    @FXML
    public Button _next_btn;

    /**
     * Список коненых данных (Для конечной таблицы)
     */
    public static ObservableList<TheLastTable> theLastTableList = FXCollections.observableArrayList();


    /**
     * Список выбранных должносей
     */
    private ObservableList<String> choosedOfficialList = FXCollections.observableArrayList();


    /**
     * Список всех ТА для должностей
     */
    private ObservableList<InfoModel> infoModelsList = FXCollections.observableArrayList();
    private ObservableList<CatalogItem> catalogItems = FXCollections.observableArrayList();

    /**
     * Список строк таблицы с чекбоксами
     */
    private ObservableList<TableViewTypeDef1> tableViewTypeDef1s = FXCollections.observableArrayList();


    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/choose_category_of_official_1.fxml"));
        typeDefinition.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/second_frame.fxml"));
        typeDefinition.getChildren().setAll(vBox);
    }

    @FXML
    private void theNext() throws IOException {

        //*****
        //  Создаем объекты и добавляем в них результат из getFinalData()
        theLastTableList.clear();
        for (int i = 0; i < getFinalData().size(); i++) {
            theLastTableList.add(new TheLastTable(getFinalData().get(i).getNameOfOfficial(),getFinalData().get(i).getEquipment()));
        }


        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/type_definition_1_result_table.fxml"));
        typeDefinition.getChildren().setAll(vBox);
    }

    private void readData() {
//        infoModelsList.clear();
        catalogItems.clear();

        catalogItems.addAll(DB.getCatalogTitlesByType("DS"));
        catalogItems.addAll(DB.getCatalogTitlesByType("ZAS"));
        catalogItems.addAll(DB.getCatalogTitlesByType("ARM"));



        // Чтение из файла
//        ObservableList unfilterred = FXCollections.observableArrayList();
//        unfilterred.addAll(XMLsaver.loadFromXML(InfoModel.FILENAME_INFOMODELS));
//
//        infoModelsList.addAll(filterInfoModelByType("DS", unfilterred));
//        infoModelsList.addAll(filterInfoModelByType("ZAS", unfilterred));
//        infoModelsList.addAll(filterInfoModelByType("ARM", unfilterred));

        // Генерация объектов для таблицы
        for (int officialIndex = 0; officialIndex < choosedOfficialList.size(); officialIndex++) {
            for (int catalogItemIndex = 0; catalogItemIndex < catalogItems.size(); catalogItemIndex++) {
                tableViewTypeDef1s.add(
                        new TableViewTypeDef1(
                                choosedOfficialList.get(officialIndex),
                                catalogItems.get(catalogItemIndex).getTitle()
                        )
                );
            }
        }
    }

    private void setupTable() {

        // Очищение
        tableView.setItems(null);

        // Настройка
        tableColumn1.setCellValueFactory(new PropertyValueFactory<TableViewTypeDef1, String>("equipment"));
        tableColumn2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableViewTypeDef1, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<TableViewTypeDef1, Boolean> param) {
                final TableViewTypeDef1 tableViewTypeDef1 = param.getValue();

                SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(tableViewTypeDef1.isChecked());
                booleanProperty.addListener(new javafx.beans.value.ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        tableViewTypeDef1.setChecked(newValue);
                        if (newValue)
                            enableNextButton();
                        else {
                            boolean hasEnabled = false;
                            for (TableViewTypeDef1 item : tableViewTypeDef1s) {
                                if (item.isChecked())
                                    hasEnabled = true;
                            }
                            if (!hasEnabled)
                                disableNextButton();
                        }
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

    private void setupListView() {
        // Очищение
        listViewOfficial.getItems().clear();

        // Заполнение listView
        listViewOfficial.setItems(choosedOfficialList);

        // Слушатель выбранного пункта списка
        listViewOfficial.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                fillTable(newValue);
            }
        });

        // Выбор первого пункта списка
        listViewOfficial.getSelectionModel().select(0);
    }

    /**
     * Заполнение таблицы
     **/
    private void fillTable(String official) {
        tableView.setItems(filterByNameOfOfficial(official, tableViewTypeDef1s));
    }

    /**
     * Метод выдаёт данные для финальной таблицы
     * @return ObservableList<TableViewTypeDef1>
     */
    private ObservableList<TableViewTypeDef1> getFinalData(){
        ObservableList<TableViewTypeDef1> result = FXCollections.observableArrayList();

        for (TableViewTypeDef1 tableViewTypeDef1 : tableViewTypeDef1s)
            if (tableViewTypeDef1.isChecked())
                result.add(tableViewTypeDef1);

        return result;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        disableNextButton();

        // Получаем ранее выбранные должности
        choosedOfficialList.clear();
        choosedOfficialList.addAll(ControllerChooseCategoryOfOfficial.arraySetOfficial);

        // Получаем список ТА
        readData();

        // Настройка таблицы
        setupTable();

        // Настройка listView
        setupListView();
    }

    private void disableNextButton() {
        _next_btn.setDisable(true);
    }

    private void enableNextButton() {
        _next_btn.setDisable(false);
    }
}
