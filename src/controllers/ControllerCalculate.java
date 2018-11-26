package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
import model.CatalogItem;
import model.DB;
import model.TableViewApparatus;
import model.TheLastTable;
import thread.FirstTreadOnCalc_2;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerCalculate implements Initializable {
    @FXML
    public VBox vbox;
    public ListView<CatalogItem> typeCable_choose_list;
    public Label calculated_time;
    public TextField _putting_speed;
    public TextField _people_count;
    public RadioButton _rb_1, _rb_2, _rb_3, _rb_w_1, _rb_w_2, _rb_w_3, _rb_w_4;
    public CheckBox _cb_1, _cb_2, _cb_3, _cb_4, _cb_5, _cb_6, _cb_7;
    public ToggleGroup locationRadioButtonsGroup, weatherRadioButtonsGroup;
    @FXML
    private TableView<TheLastTable> tableView, tableViewAbon, tableViewCable;
    @FXML
    private TableView<TableViewApparatus> tableViewApp;
    @FXML
    private TableColumn<TheLastTable, String> officialPerson,
            typeAbon, appFrom1;
    @FXML
    private TableColumn<TheLastTable, String> typeAbon2, typeCable2;
    @FXML
    private TableColumn<TableViewApparatus, String> appFrom12;
    @FXML
    private TableColumn<TheLastTable, Integer> lengthCable;
    @FXML
    private TableColumn<TheLastTable, Integer> amounfOfAbon, lengthOfCable;
    @FXML
    private TableColumn<TableViewApparatus, Integer> amountOfApp;
    @FXML
    private TableColumn<TheLastTable, String> typeCable;

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
    private void calculate() throws IOException {
        createWordTable();
    }

    private void setupTable() {

        ((TableView<TheLastTable>) tableView).getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TheLastTable>() {
            @Override
            public void changed(ObservableValue<? extends TheLastTable> observableValue, TheLastTable theLastTable, TheLastTable t1) {
                TheLastTable selectedItem = (TheLastTable) tableView.getSelectionModel().getSelectedItem();
                String appTitle = selectedItem.getAppFrom1();

                // Читаем из БД

                try {
                    CatalogItem app = DB.getCatalogItemByTitle(appTitle);

                    ObservableList<CatalogItem> cableList = DB.getCablesInApparatousById(app.getId());

                    typeCable_choose_list.getItems().clear();
                    typeCable_choose_list.getItems().addAll(cableList);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        officialPerson.setCellValueFactory(new PropertyValueFactory<TheLastTable, String>("officialPerson"));
        typeAbon.setCellValueFactory(new PropertyValueFactory<TheLastTable, String>("typeAbon"));
        appFrom1.setCellValueFactory(new PropertyValueFactory<TheLastTable, String>("appFrom1"));
        appFrom1.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TheLastTable, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TheLastTable, String> event) {
                TablePosition<TheLastTable, String> pos = event.getTablePosition();

                int row = pos.getRow();
                TheLastTable lastTable = event.getTableView().getItems().get(row);

            }
        });

        typeCable.setCellValueFactory(new PropertyValueFactory<TheLastTable, String>("typeCable"));

        lengthCable.setCellValueFactory(new PropertyValueFactory<TheLastTable, Integer>("lengthCable"));
        lengthCable.setCellFactory(TextFieldTableCell.<TheLastTable, Integer>forTableColumn(new IntegerStringConverter()));
        lengthCable.setMinWidth(10);
        lengthCable.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TheLastTable, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TheLastTable, Integer> event) {
                TablePosition<TheLastTable, Integer> pos = event.getTablePosition();

                Integer newLength = event.getNewValue();

                // Делаем кол-во положительным
                if (newLength <= 0) newLength = 1;

                int row = pos.getRow();
                TheLastTable lastTable = event.getTableView().getItems().get(row);

                lastTable.setLengthCable(newLength);

                // Обновляем таблицу
                //TODO: а надо?
//                event.getTableView().refresh();
            }
        });
    }

    private void setupTableAbon() {
        typeAbon2.setCellValueFactory(new PropertyValueFactory<TheLastTable, String>("typeAbon"));
        amounfOfAbon.setCellValueFactory(new PropertyValueFactory<TheLastTable, Integer>("amountAbon"));
        amounfOfAbon.setCellFactory(TextFieldTableCell.<TheLastTable, Integer>forTableColumn(new IntegerStringConverter()));
    }

    private void setupTableCable() {
        typeCable2.setCellValueFactory(new PropertyValueFactory<TheLastTable, String>("typeCable"));
        lengthOfCable.setCellValueFactory(new PropertyValueFactory<TheLastTable, Integer>("lengthCable"));
        lengthOfCable.setCellFactory(TextFieldTableCell.<TheLastTable, Integer>forTableColumn(new IntegerStringConverter()));
        lengthOfCable.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TheLastTable, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TheLastTable, Integer> event) {
                TablePosition<TheLastTable, Integer> pos = event.getTablePosition();
                Integer newLength = event.getNewValue();
                // Делаем кол-во положительным
                if (newLength <= 0) newLength = 1;
                int row = pos.getRow();
                TheLastTable lastTable = event.getTableView().getItems().get(row);
                lastTable.setLengthCable(newLength);
            }
        });
    }

    private void setupTableApp() {
        appFrom12.setCellValueFactory(new PropertyValueFactory<TableViewApparatus, String>("fullName"));
        amountOfApp.setCellValueFactory(new PropertyValueFactory<TableViewApparatus, Integer>("count"));
        amountOfApp.setCellFactory(TextFieldTableCell.<TableViewApparatus, Integer>forTableColumn(new IntegerStringConverter()));
    }

    private void setupRadioButtons() {
        // userData соосв. индексу в массиве
        _rb_1.setUserData(0);
        _rb_2.setUserData(1);
        _rb_3.setUserData(2);

        // а здесь - просто для индефикациии радиокнопки
        _rb_w_1.setUserData(1);
        _rb_w_2.setUserData(2);
        _rb_w_3.setUserData(3);
        _rb_w_4.setUserData(4);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupTable();
        setupTableAbon();
        setupTableCable();
        setupTableApp();
        setupRadioButtons();

        tableView.setItems(ControllerTypeDefinition3.theLastTableListUpdatedD3);
        tableViewCable.setItems(ControllerTypeDefinition3.theLastTableListUpdatedD3);
//        tableViewApp.setItems(ControllerTypeDefinition3.theLastTableListUpdatedD3);

        fillTableViewAbon();
        fillTableViewApp();
    }

    private void fillTableViewApp() {
        tableViewApp.setItems(ControllerTypeDefinition2.choosedApparatus);
    }

    private void fillTableViewAbon() {
        ObservableList<TheLastTable> newList = FXCollections.observableArrayList();

        for (TheLastTable theLastTable : ControllerTypeDefinition3.theLastTableListUpdatedD3) {

            theLastTable.setAmountAbon(1);

            if (newList.size() == 0) {
                newList.add(theLastTable);
                continue;
            }

            boolean exist = false;

            for (TheLastTable existedItem : newList) {
                if (existedItem.getTypeAbon().equals(theLastTable.getTypeAbon())) {
                    Integer newCount = existedItem.getAmountAbon() + theLastTable.getAmountAbon();

                    existedItem.setAmountAbon(newCount);

                    exist = true;
                }
            }

            if (!exist) newList.add(theLastTable);
        }

        tableViewAbon.getItems().clear();
        tableViewAbon.getItems().addAll(newList);
    }

    public void createWordTable() throws IOException {
        FirstTreadOnCalc_2 tread = new FirstTreadOnCalc_2();
        tread.run(
                ControllerTypeDefinition3.theLastTableListUpdatedD3,
                tableViewAbon.getItems(),
                tableViewCable.getItems(),
                tableViewApp.getItems(),
                calculated_time.getText());
    }

    public void _typeCable_choose_list_add(ActionEvent actionEvent) {
        if (!typeCable_choose_list.getItems().isEmpty()) {
            CatalogItem selectedCable = typeCable_choose_list.getSelectionModel().getSelectedItem();
            TheLastTable selectedTheLastTableItem = (TheLastTable) tableView.getSelectionModel().getSelectedItem();

            if (selectedCable != null) {
                selectedTheLastTableItem.setTypeCable(selectedCable.getTitle());
            }
        }
    }


    /**
     * @param V             скорость прокладки кабеля абонентских линий по поверхности земли<br><br>
     * @param cables_length массив длин кабелей по типам кабелей<br><br>
     * @param location      параметры местности <br>
     *                      <table>
     *                      <tr>
     *                      <th>id</th>
     *                      <th>Характер местности развертывания</th>
     *                      <th>Значения коэффициента k_местн</th>
     *                      </tr>
     *                      <tr>
     *                      <td>0</td>
     *                      <td>Местность равнинная, мало и среднепересеченная</td>
     *                      <td>0,05</td>
     *                      </tr>
     *                      <tr>
     *                      <td>1</td>
     *                      <td>Местность холмистая и сильнопересеченная</td>
     *                      <td>0,1</td>
     *                      </tr>
     *                      <tr>
     *                      <td>2</td>
     *                      <td>Горная и другая труднодоступная для прокладки кабелей, сильнопересеченная (изрезанная) местность</td>
     *                      <td>0,15</td>
     *                      </tr>
     *                      </table>
     *                      <br><br>
     * @param weather       массив параметров погоды <br>
     *
     *                      <table>
     *                      <tr>
     *                      <th>Характер местности</th>
     *                      <th>–7 ÷ +35°С</th>
     *                      <th>более +35°С</th>
     *                      <th>снег до 30см</th>
     *                      <th>–20 ÷–7°С или снег 30-80 см</th>
     *                      <th>ниже –20°С или снег более 80 см</th>
     *                      <th>ветер 10-20 м/с</th>
     *                      <th>ветер более20 м/с</th>
     *                      </tr>
     *                      <tr>
     *                      <td>Равнинная и среднепересеченная</td>
     *                      <td>(0) 0,0</td>
     *                      <td>(1) 0,1</td>
     *                      <td>(2) 0,1</td>
     *                      <td>(3) 0,2</td>
     *                      <td>(3) 0,25</td>
     *                      <td>(4) 0,2</td>
     *                      <td>(6) 0,3</td>
     *                      </tr>
     *                      <tr>
     *                      <td>Лесисто-болотистая</td>
     *                      <td>(7) 0,1</td>
     *                      <td>(8) 0,2</td>
     *                      <td>(9) 0,2</td>
     *                      <td>(10) 0,25</td>
     *                      <td>(11) 0,3</td>
     *                      <td>(12) 0,2</td>
     *                      <td>(13) 0,3</td>
     *                      </tr>
     *                      <tr>
     *                      <td>Пустынно-песчаная</td>
     *                      <td>(14) 0,2</td>
     *                      <td>(15) 0,3</td>
     *                      <td>(16) 0,2</td>
     *                      <td>(17) 0,25</td>
     *                      <td>(18) 0,3</td>
     *                      <td>(19) 0,25</td>
     *                      <td>(20) 0,35</td>
     *                      </tr>
     *                      <tr>
     *                      <td>Гористая</td>
     *                      <td>(21) 0,3</td>
     *                      <td>(22) 0,35</td>
     *                      <td>(23) 0,35</td>
     *                      <td>(24) 0,35</td>
     *                      <td>(25) 0,4</td>
     *                      <td>(26) 0,25</td>
     *                      <td>(27) 0,35</td>
     *                      </tr>
     *                      </table>
     *                      <br><br>
     * @param N             количество устанавливаемых (подключаемых) оконечных терминальных устройств (оконечного оборудования) <br><br>
     * @param n             количество личного состава, задействованного для развертывания абонентской сети <br><br>
     * @param omega         коэффициент использования ресурса кабеля абонентских линий (отношение количества задействованных пар кабеля к общему числу пар кабеля);
     * @return
     */
    public double time_calculate(double V, ObservableList<Integer> cables_length, Integer location, ObservableList<Integer> weather, Integer N, Integer n, double omega) {
        double t_razv = 0;
        double t_prokl = 0;
        double t_prov = 0;
        double t_pereh = 0;

        double[] location_params = {0.05, 0.1, 0.15};
        double[] weather_params = {0.0, 0.1, 0.1, 0.2, 0.25, 0.2, 0.3, 0.1, 0.2, 0.2, 0.25, 0.3, 0.2, 0.3, 0.2, 0.3, 0.2, 0.25, 0.3, 0.25, 0.35, 0.3, 0.35, 0.35, 0.35, 0.4, 0.25, 0.35};

        // Расчёт К_вр (по параметрам погоды)
        double K_vr = 0;
        for (Integer k_w_index : weather) {
            K_vr += weather_params[k_w_index];
        }

        // Расчёт К_местн (по параметрам местности)
        double K_mestn = location_params[location];

        // Расчёт общей длины кабелей
        double l = 0;
        for (Integer cable_length : cables_length) {
            l += cable_length + cable_length * K_mestn;
        }

        // Расчёт t_прокл
        t_prokl = l / (V * Math.sqrt(n) * Math.sqrt(omega)) * (1 + K_vr);

        // длина линии от распределительного (выносного) щита до оконечного терминального оборудования
        double d = 0; // т.к. выносных щитов у нас нет

        // время прокладки кабеля типа П-274М (STP- или UTP-кабеля) от распределительного (выносного) щита до оконечного оборудования длиной более 5 м
        double t_podkl = 2 * (d + K_mestn * d) / V;

        // среднее время подключения и проверки оконечного терминального оборудования
        double t_oo = 1; // TODO: или 2, или 4

        // Расчёт t_пров
        t_prov = N / n + t_oo + t_podkl;

        // среднее время подключения переходных устройств (разветви тельных муфт, выносных щитков, переходников с одного типа кабеля на другой, автоматических конверторов сигнала и т.п.), при проведении расчётов принимают (t_пу ) ̅ = 0,5 мин
        double t_py = 0.5;

        // количество используемых переходных устройств
        double m = 0; // TODO: ваще хз

        // Расчёт t_перех
        t_pereh = 3; //t_py * m;

        // Расчёт t_разв
        t_razv = t_prokl + t_prov + t_pereh;

        return t_razv;
    }

    public void _calc_time(ActionEvent actionEvent) {
        if (
                !_putting_speed.getText().isEmpty() &&
                !tableViewCable.getItems().isEmpty() &&
                locationRadioButtonsGroup.getSelectedToggle() != null &&
                !tableView.getItems().isEmpty() &&
                !_people_count.getText().isEmpty()
                ) {

            double result = 0;

            // Скорость прокладки
            double speed = Double.valueOf(_putting_speed.getText());

            // Длины кабелей
            ObservableList<Integer> cables_length = FXCollections.observableArrayList();
            for (TheLastTable item : tableViewCable.getItems()) {
                cables_length.add(item.getLengthCable());
            }

            // Параметры местности
            Integer location = 0;
            location = (Integer) locationRadioButtonsGroup.getSelectedToggle().getUserData();


            // Параметры местности/погоды
            ObservableList<Integer> weather = FXCollections.observableArrayList();

            switch ((Integer) weatherRadioButtonsGroup.getSelectedToggle().getUserData()) {
                case 1:
                    if (_cb_1.isSelected()) weather.add(0);
                    if (_cb_2.isSelected()) weather.add(1);
                    if (_cb_3.isSelected()) weather.add(2);
                    if (_cb_4.isSelected()) weather.add(3);
                    if (_cb_5.isSelected()) weather.add(4);
                    if (_cb_6.isSelected()) weather.add(5);
                    if (_cb_7.isSelected()) weather.add(6);
                    break;
                case 2:
                    if (_cb_1.isSelected()) weather.add(7);
                    if (_cb_2.isSelected()) weather.add(8);
                    if (_cb_3.isSelected()) weather.add(9);
                    if (_cb_4.isSelected()) weather.add(10);
                    if (_cb_5.isSelected()) weather.add(11);
                    if (_cb_6.isSelected()) weather.add(12);
                    if (_cb_7.isSelected()) weather.add(13);
                    break;
                case 3:
                    if (_cb_1.isSelected()) weather.add(14);
                    if (_cb_2.isSelected()) weather.add(15);
                    if (_cb_3.isSelected()) weather.add(16);
                    if (_cb_4.isSelected()) weather.add(17);
                    if (_cb_5.isSelected()) weather.add(18);
                    if (_cb_6.isSelected()) weather.add(19);
                    if (_cb_7.isSelected()) weather.add(20);
                    break;
                case 4:
                    if (_cb_1.isSelected()) weather.add(21);
                    if (_cb_2.isSelected()) weather.add(22);
                    if (_cb_3.isSelected()) weather.add(23);
                    if (_cb_4.isSelected()) weather.add(24);
                    if (_cb_5.isSelected()) weather.add(25);
                    if (_cb_6.isSelected()) weather.add(26);
                    if (_cb_7.isSelected()) weather.add(27);
                    break;
            }

            // Количество оконечных устройств
            Integer abonCount = 0;
            for (TheLastTable item : tableView.getItems()) {
                abonCount += item.getAmountAbon();
            }

            // Количество личного состава
            Integer people_count = Integer.parseInt(_people_count.getText());

            result = time_calculate(speed, cables_length, location, weather, abonCount, people_count, 1);

            result = (double) Math.round(result * 100) / 100;

            calculated_time.setText(Double.toString(result));
        } else {
            calculated_time.setText("Ошибка расчёта");
        }
    }
}
