package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import model.InfoModel;
import model.TableViewTypeDef1;
import model.TheLastTable;
import model.XMLsaver;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static controllers.ControllerTypeDefinition3.apparatusChoosedData;

public class ControllerTypeCable implements Initializable{
    @FXML
    public VBox vbox;
    @FXML
    private ListView listViewOfficial;
    @FXML
    private ListView listViewEquipment;

    @FXML
    private ListView listViewChoose;
    private ObservableList<InfoModel> infoData;

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
    private void theNext() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/calculate.fxml"));
        vbox.getChildren().setAll(vBox);


        /*
        //*****
        //  Создаем объекты и добавляем в них результат из getFinalData()
        for (int i = 0; i < getFinalData().size(); i++) {
            //theLastTableList.add(new TheLastTable(getFinalData().get(i).getNameOfOfficial(),getFinalData().get(i).getEquipment()));
        }
        */

    }

    /**
     * Список всех ТА для должностей
     */
    private ObservableList<InfoModel> infoModelsList = FXCollections.observableArrayList();
    private ObservableList<String> infoModelsListForListView = FXCollections.observableArrayList();
    private ObservableList unfilterred = FXCollections.observableArrayList();

    /**
     * Список выбранных должносей
     */
    private ObservableList<String> choosedOfficialList = FXCollections.observableArrayList();
    /**
     * Список выбранного оборудования
     */
    private ObservableList<String> choosedEquipmentList = FXCollections.observableArrayList();



    /**
     * Список строк таблицы с чекбоксами
     */
    private ObservableList<TableViewTypeDef1> tableViewTypeDef1s = FXCollections.observableArrayList();

    private void readData() {
        infoModelsList.clear();

        // Чтение из файла
       // ObservableList unfilterred = FXCollections.observableArrayList();
       // unfilterred.addAll(XMLsaver.loadFromXML(InfoModel.FILENAME_INFOMODELS));

        //infoModelsList.addAll(filterInfoModelByType("CableAndOther", unfilterred));

        /*
        // Генерация объектов для таблицы
        for (int officialIndex = 0; officialIndex < choosedOfficialList.size(); officialIndex++) {
            for (int infoModelIndex = 0; infoModelIndex < infoModelsList.size(); infoModelIndex++) {
                tableViewTypeDef1s.add(
                        new TableViewTypeDef1(
                                choosedOfficialList.get(officialIndex),
                                infoModelsList.get(infoModelIndex).getTitle()
                        )
                );
            }
        }
        */
    }


    private void setupListView() {
        // Очищение
        listViewOfficial.getItems().clear();
        //listViewEquipment.getItems().clear();

        // Заполнение listView
        listViewOfficial.setItems(choosedOfficialList);
        //listViewEquipment.setItems(choosedEquipmentList);

        // Слушатель выбранного пункта списка
        listViewOfficial.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //fillTable(newValue);
                //listViewEquipment.getItems().clear();
                choosedEquipmentList.clear();

                for (TheLastTable item : ControllerTypeDefinition3.theLastTableListUpdatedD3) {
                    if (item.getAppFrom1().contains(newValue)) {
                        if (item.getAppFrom1().equals(newValue)) {
                            choosedEquipmentList.addAll(item.getTypeAbon());

                            listViewEquipment.setItems(choosedEquipmentList);

                            }else { choosedEquipmentList.clear();}
                        }
                }


                if (unfilterred.isEmpty()){
                    // Чтение из файла
                    unfilterred.addAll(XMLsaver.loadFromXML(InfoModel.FILENAME_INFOMODELS));
                }
                infoModelsList.clear();
                infoModelsListForListView.clear();
                infoModelsList.addAll(filterInfoModelByTitle(newValue, unfilterred));

                for (InfoModel infoModel : infoModelsList) {
                    List<Map<String, String>> data = infoModel.parseCables();
                    for (Map<String,String> cable:data){
                        infoModelsListForListView.add(cable.get("name"));
                    }
                }

                listViewChoose.setItems(infoModelsListForListView);

            }
        });

        // Слушатель выбранного пункта списка
        listViewEquipment.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {


                for (TheLastTable item : ControllerTypeDefinition3.theLastTableListUpdatedD3) {
                    if (item.getTypeAbon().equals(newValue)) {
                        if (item.getTypeAbon().equals(newValue) && item.getTypeCable() != null) {

                            listViewChoose.getSelectionModel().select(item.getTypeCable());

                        } else {
                        }
                    }
                }


            }
        });

        listViewChoose.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                for (TheLastTable item : ControllerTypeDefinition3.theLastTableListUpdatedD3) {
                    if (item.getTypeAbon().equals(listViewEquipment.getSelectionModel().getSelectedItem()) && item.getAppFrom1().equals(listViewOfficial.getSelectionModel().getSelectedItem())){
                        item.setTypeCable(newValue);
                    }else {
                        String newString = listViewOfficial.getSelectionModel().toString()+ "  \n" + listViewEquipment.getSelectionModel().toString();
                        System.out.println(newString);
                    }
                }
            }
        });

        // Выбор первого пункта списка
        //listViewOfficial.getSelectionModel().select(0);
    }

    /**
     * Заполнение таблицы
     **/
    /*
    private void fillTable(String official) {
        tableView.setItems(filterByNameOfOfficial(official, tableViewTypeDef1s));
    }
    */
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

        //Создаем Set  и в него добавляем наш список
        Set<String> setForListView = new HashSet();
        for (int i = 0; i < apparatusChoosedData.size(); i++) {
            setForListView.add(apparatusChoosedData.get(i).getFullName());
            //setForEquipmentListView.add(ControllerTypeDefinition3.apparatusChoosedData.get(i).getDataApparatus());
        }

        // Получаем ранее выбранные должности
        choosedOfficialList.clear();
        choosedOfficialList.addAll(setForListView);

        //choosedEquipmentList.clear();
        //choosedEquipmentList.addAll(setForEquipmentListView);

        // Получаем список ТА
        readData();

        // Настройка listView
        setupListView();
    }


    /**
     * Фильтрует модели по типу
     * @param title
     * @param infoData
     * @return FilteredList&lt;InfoModel&gt;
     */
    public static ObservableList<InfoModel> filterInfoModelByTitle(String title, ObservableList<InfoModel> infoData){
        ObservableList<InfoModel> result = FXCollections.observableArrayList();

        for (InfoModel infoModel : infoData) {
            // Если несколько разделены запятой, то хренацим массив да цикл
            if(infoModel.getTitle().contains(",")){
                String[] titles = infoModel.getTitle().split(",");
                for (int i = 0; i < titles.length; i++) {
                    if (title.equals(titles[i])) {
                        result.add(infoModel);
                    }
                }
            } else {
                if (infoModel.getTitle().equals(title)) {
                    result.add(infoModel);
                }
            }
        }

        return result;

//        return infoData.filtered(new Predicate<InfoModel>() {
//            @Override
//            public boolean test(InfoModel infoModel) {
//                // Если несколько разделены запятой, то хренацим массив да цикл
//                if(infoModel.getTitle().contains(",")){
//                    String[] titles = infoModel.getTitle().split(",");
//                    for (int i = 0; i < titles.length; i++) {
//                        if (title.equals(titles[i])) {
//                            return true;
//                        }
//                    }
//                } else {
//                    if (infoModel.getTitle().equals(title)) {
//                        return true;
//                    } else {
//                        return false;
//                    }
//                }
//                return false;
//            }
//        });
    }
}
