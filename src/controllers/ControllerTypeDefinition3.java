package controllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.Effect;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import model.TableViewAbonent;
import model.TableViewApparatus;
import model.TheLastTable;
import model.XMLsaver;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerTypeDefinition3 implements Initializable {
    @FXML
    public VBox typeDefinition;
    public TableView<TableViewAbonent> _abonent_tableView;
    public ListView<TableViewApparatus> _apparatus_list;
    public TextField _add_count;
    public TextField _add_name;
    public Button _add_btn;
    public Button _del_btn;
    @FXML
    private TableColumn<TableViewAbonent, String> _tableColumn1;
    @FXML
    private TableColumn<TableViewAbonent, Integer> _tableColumn2;
    @FXML
    private TableColumn<TableViewAbonent, Integer> _tableColumn3;
    @FXML
    private TableColumn<TableViewAbonent, Boolean> _tableColumn4;
    @FXML
    private Label label;

    // Статическая переменная ниже передается и используется далее(чтобы изменения там сразу отражались и здесь)
    public static ObservableList<TableViewAbonent> abonentsData = FXCollections.observableArrayList();

    public static ObservableList<TableViewApparatus> apparatusChoosedData = FXCollections.observableArrayList();

    private Integer apparatusChoosedId = null;
    private TableViewAbonent abonentChoosedModel = null;


    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/type_definition_2.fxml"));
        typeDefinition.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/second_frame.fxml"));
        typeDefinition.getChildren().setAll(vBox);
    }

    @FXML
    private void theNext() throws IOException{
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/type_cable.fxml"));
        typeDefinition.getChildren().setAll(vBox);

    }

    public void readData(){
        abonentsData.clear();

        // Берём данные для показа
        for (TableViewApparatus app : apparatusChoosedData) {
            for (TheLastTable item : ControllerTypeDefinition1.theLastTableList) {

                TableViewAbonent temp = new TableViewAbonent(item.getTypeAbon(),0,app.getFullName());

                boolean alredy_exist = false;
                for (TableViewAbonent t : abonentsData)
                    if (t.getFullName().equals(temp.getFullName()) && t.getParentApparatus().equals(temp.getParentApparatus()))
                        alredy_exist = true;

                if (!alredy_exist)
                    abonentsData.add(temp);
            }
        }
    }

    private void setupTable(){
        // Название
        _tableColumn1.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        // Колчество
        _tableColumn2.setCellValueFactory(new PropertyValueFactory<>("count"));

        // Колчество
        _tableColumn3.setCellValueFactory(new PropertyValueFactory<>("count_used"));
        _tableColumn3.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        _tableColumn3.setMinWidth(10);
        _tableColumn3.setOnEditCommit((TableColumn.CellEditEvent<TableViewAbonent, Integer> event) -> {
            TablePosition<TableViewAbonent, Integer> pos = event.getTablePosition();

            Integer newCount= event.getNewValue();

            int row = pos.getRow();
            TableViewAbonent apparatus = event.getTableView().getItems().get(row);

            apparatus.setCount_used(newCount);
        });

        // Checkbox
        _tableColumn4.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableViewAbonent, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<TableViewAbonent, Boolean> param) {
                TableViewAbonent tableViewApparatus = param.getValue();

                SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(tableViewApparatus.isChoose());

                booleanProperty.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        tableViewApparatus.setChoose(newValue);
                    }
                });
                return booleanProperty;
            }
        });
        _tableColumn4.setCellFactory(new Callback<TableColumn<TableViewAbonent, Boolean>, TableCell<TableViewAbonent, Boolean>>() {
            @Override
            public TableCell<TableViewAbonent, Boolean> call(TableColumn<TableViewAbonent, Boolean> param) {
                CheckBoxTableCell<TableViewAbonent, Boolean> cell = new CheckBoxTableCell<TableViewAbonent, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
    }

    private void setupList(){
        _apparatus_list.getItems().clear();
        _apparatus_list.setItems(apparatusChoosedData);
        // Слушатель выбранной моели в списке аппаратных
        _apparatus_list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TableViewApparatus>() {
            @Override
            public void changed(ObservableValue<? extends TableViewApparatus> observable, TableViewApparatus oldValue, TableViewApparatus newValue) {
                setAbonentTable(newValue);
                apparatusChoosedId = _apparatus_list.getSelectionModel().getSelectedIndex();

                // Активируем поля и кнопку добавления
                _add_btn.setDisable(false);
                _add_name.setDisable(false);
                _add_count.setDisable(false);
                // Деактивируем кнопку удаления
                _del_btn.setDisable(true);
            }
        });

        // Слушатель выбранной моели в списке абон. оборуд.
        _abonent_tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TableViewAbonent>(){
            @Override
            public void changed(ObservableValue<? extends TableViewAbonent> observable, TableViewAbonent oldValue, TableViewAbonent newValue) {
                abonentChoosedModel = _abonent_tableView.getSelectionModel().getSelectedItem();

                // Активируем кнопку удаления
                _del_btn.setDisable(false);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Инициализация переменных
        apparatusChoosedId = null;
        abonentChoosedModel = null;

        // Инициализация GUI
        _add_btn.setDisable(true);
        _del_btn.setDisable(true);
        _add_name.setDisable(true);
        _add_count.setDisable(true);

        // Получаем выбранные ранне аппаратные
        apparatusChoosedData = ControllerTypeDefinition2.choosedApparatus;

        readData();

        setupTable();
        setupList();
        }

    public void setAbonentTable(TableViewApparatus apparatus){

        _abonent_tableView.setItems(TableViewAbonent.filterInfoModelByApparatusName(apparatus.getFullName(),abonentsData));
    }

    public void _list_clicked(MouseEvent mouseEvent) {

    }

    public void _add(ActionEvent actionEvent) {
        if (!_add_name.getText().isEmpty() && !_add_count.getText().isEmpty() && _add_count.getText().matches("^-?\\d+$") && apparatusChoosedId != null){
            // TODO: предусмотреть совпадение имён абонентского оборудования

            abonentsData.add(new TableViewAbonent(_add_name.getText(),Integer.parseInt(_add_count.getText()),apparatusChoosedData.get(apparatusChoosedId).getFullName()));

            // Обновляем таблицу
            setAbonentTable(apparatusChoosedData.get(apparatusChoosedId));
        } else {
//            if (_add_count.getText().isEmpty() || !_add_count.getText().matches("^-?\\d+$"))
                // TODO: доабвить эффект с выделением красным цветом поля с ошибкой
        }
    }

    public void _delete(ActionEvent actionEvent) {
        if (abonentChoosedModel != null){
            abonentsData.remove(abonentChoosedModel);
            abonentChoosedModel = null;

            // Обновляем таблицу
            setAbonentTable(apparatusChoosedData.get(apparatusChoosedId));
        }
    }
}
