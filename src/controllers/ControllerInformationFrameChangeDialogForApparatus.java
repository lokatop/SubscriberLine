package controllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import model.Catalog;
import model.DB;
import model.InfoModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class ControllerInformationFrameChangeDialogForApparatus implements Initializable {

    @FXML
    public TextField __title;
    @FXML
    public ImageView __image;
    @FXML
    public HTMLEditor __description;
    @FXML
    public TableView _ta_table;
    @FXML
    public ComboBox<Catalog> _ta_list;
    @FXML
    public TextField _ta_count;
    @FXML
    public TableColumn<Catalog, String> _ta_column_1;
    @FXML
    public TableColumn<Catalog, Integer> _ta_column_2;
    public TableView _cable_table;
    public ComboBox<Catalog> _cable_list;
    public TextField _cable_count;
    public TableColumn<Catalog, String> _cable_column_1;
    public TableColumn<TableCableModel, Integer> _cable_column_2;

    // Для свременного сохранения изображения и отображения иконки DragAndDrop
    private Image dropIconTemp;

    private Stage dialogStage;
    private InfoModel infoModel = null;
    private boolean okClicked = false;
    private ObservableList<InfoModel> TA = FXCollections.observableArrayList();
    private ObservableList<TableTAModel> TATable = FXCollections.observableArrayList();
    private ArrayList<TableCableModel> CableTable = new ArrayList<>();


    private Integer itemId = null;
    private String itemType;

    /**
     * Устанавливает сцену для этого окна.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void setType(String type) {
        itemType = type;
    }


    public void setId(Integer id) {
        try {
            Catalog item = DB.getCatalogItemById(id);
            itemId = id;

            // Заполняем
            __title.setText(item.getTitle());
            __description.setHtmlText(item.getDescription());
            __image.setImage(item.getImage());

            fillCableTable();
            fillTaTable();
            fillTAList();
            fillCableList();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public InfoModel getInfoModel() {
        return this.infoModel;
    }

    /**
     * Returns true, если пользователь кликнул OK, в другом случае false.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Проверяет пользовательский ввод в текстовых полях.
     *
     * @return true, если пользовательский ввод корректен
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (__title.getText() == null || __title.getText().length() == 0) {
            errorMessage += "Пустой заголовок!\n";
        }
        if (__description.getHtmlText().equals("<html dir=\"ltr\"><head></head><body contenteditable=\"true\"></body></html>")) {
            errorMessage += "Пустое описание!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // TODO: отображение ошибок во всплывающем окне
            // Показываем сообщение об ошибке.
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.initOwner(dialogStage);
//            alert.setTitle("Поля с ошибками");
//            alert.setHeaderText("Пожалуйста, исправте ошибки");
//            alert.setContentText(errorMessage);
//
//            alert.showAndWait();

            return false;
        }
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке OK.
     */
    @FXML
    public void __save(ActionEvent actionEvent) {
        if (isInputValid()) {
            infoModel.setTitle(__title.getText());
            infoModel.setDescription(__description.getHtmlText());
            infoModel.setImage(__image.getImage());

            // Записываем доп информацию в data
            String data = "";
            for (TableTAModel ta : TATable) {
                data += ta.getName() + ":" + ta.getCount() + ";";
            }
            infoModel.setData(data);

            // Записываем доп информацию в cables
            String cables = "";
            for (TableCableModel cm : CableTable) {
                cables += cm.getName() + ":" + cm.getCount() + ";";
            }
            infoModel.setCables(cables);

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке Cancel.
     */
    @FXML
    public void __cancel(ActionEvent actionEvent) {
        dialogStage.close();
    }

    @FXML
    public void __drag_dropped(DragEvent dragEvent) {
        Dragboard board = dragEvent.getDragboard();
        List<File> phil = board.getFiles();
        FileInputStream fis;

        set_image_from_file(phil.get(0));
    }

    @FXML
    public void __select_image(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Изображения", "*.jpg", "*.jpeg", "*.png", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог загрузки файла
        File file = fileChooser.showOpenDialog(this.dialogStage);

        if (file != null) {
            set_image_from_file(file);
        }
    }

    private void set_image_from_file(File file) {
        // Проверка файла на бытие изображением
        try {
            FileInputStream fis = new FileInputStream(file);
            Image img = new Image(fis);
            if (!img.isError()) {
                __image.setImage(img);
                dropIconTemp = img;
            } else {
                __image.setImage(new Image("resource/noimage.png"));
            }
        } catch (FileNotFoundException e) {
            __image.setImage(new Image("resource/noimage.png"));
        }
    }

    @FXML
    public void __drag_over(DragEvent dragEvent) {

        // Разрешаем класть файлы
        Dragboard board = dragEvent.getDragboard();
        if (board.hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    public void __drag_enter(DragEvent dragEvent) {
        // Меняем фон
        dropIconTemp = __image.getImage();
        __image.setImage(new Image("resource/dropimage.jpg"));
    }

    @FXML
    public void __drag_exit(DragEvent dragEvent) {
        __image.setImage(dropIconTemp);
    }

    @FXML
    public void __drag_done(DragEvent dragEvent) {
    }

    private void setupTaTable() {
        // Очищение
        _ta_table.getItems().clear();

        // Настройка
        _ta_column_1.setCellValueFactory(new PropertyValueFactory<Catalog, String>("title"));
        _ta_column_2.setCellValueFactory(new PropertyValueFactory<Catalog, Integer>("count"));
        _ta_column_2.setCellFactory(TextFieldTableCell.<Catalog, Integer>forTableColumn(new IntegerStringConverter()));
        _ta_column_2.setMinWidth(10);
        _ta_column_2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Catalog, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Catalog, Integer> event) {
                TablePosition<Catalog, Integer> pos = event.getTablePosition();

                Integer newCount = event.getNewValue();

                // Делаем кол-во положительным
                if (newCount < 1) newCount = 1;

                int rowId = pos.getRow();
                Catalog row = event.getTableView().getItems().get(rowId);

                DB.updateCountTaInApparatousById(itemId, row.getId(), newCount);

                fillTaTable();
            }
        });
    }

    private void setupCableTable() {
        // Очищение
        _cable_table.getItems().clear();

        // Настройка
        _cable_column_1.setCellValueFactory(new PropertyValueFactory<Catalog, String>("title"));
    }

    private void fillTaTable() {
        ObservableList<Catalog> Ta = DB.getTaInApparatousById(itemId);

        _ta_table.getItems().clear();
        ;
        _ta_table.setItems(Ta);
    }

    private void fillCableTable() {

        ObservableList<Catalog> Cables = DB.getCablesInApparatousById(itemId);

        _cable_table.setItems(Cables);
    }

    private void fillTAList() {
        ObservableList<Catalog> Ta = DB.getTaNotInApparatousById(itemId);

        _ta_list.setItems(Ta);

        if (_ta_list.getItems().size() != 0)
            _ta_list.getSelectionModel().select(0);
    }

    private void fillCableList() {

        ObservableList<Catalog> Cables = DB.getCablesNotInApparatousById(itemId);

        _cable_list.getItems().clear();
        _cable_list.setItems(Cables);

        if (_cable_list.getItems().size() != 0)
            _cable_list.getSelectionModel().select(0);
    }

    public void _ta_add(ActionEvent actionEvent) {
        Integer count;
        Integer ta_id;

        try {
            count = Integer.parseInt(_ta_count.getText());
            ta_id = _ta_list.getSelectionModel().getSelectedItem().getId();

            if (count <= 0) count = 1;

            DB.addTaInApparatous(itemId, ta_id, count);

            fillTaTable();
            fillTAList();

        } catch (Exception e) {

        }
    }

    public void _cable_add(ActionEvent actionEvent) {
        Integer cable_id;

        cable_id = _cable_list.getSelectionModel().getSelectedItem().getId();

        try {

            DB.addCableInApparatous(itemId, cable_id);

            fillCableTable();
            fillCableList();

        } catch (Exception e) {

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Настройка таблицы
        setupTaTable();
        setupCableTable();
    }

    public void _ta_del(ActionEvent actionEvent) {
        try {
            TATable.remove(_ta_table.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
        }
    }

    public void _cable_del(ActionEvent actionEvent) {
        try {
            CableTable.remove(_cable_table.getSelectionModel().getSelectedItem());
            fillCableTable();
        } catch (Exception e) {
        }
    }

    public class TableTAModel {

        private SimpleStringProperty name;
        private SimpleIntegerProperty count;

        public TableTAModel(String name, Integer count) {
            this.name = new SimpleStringProperty(name);
            this.count = new SimpleIntegerProperty(count);
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public int getCount() {
            return count.get();
        }

        public SimpleIntegerProperty countProperty() {
            return count;
        }

        public void setCount(int count) {
            this.count.set(count);
        }
    }

    public class TableCableModel {
        private final StringProperty name;
        private final IntegerProperty count;

        public TableCableModel(String name, Integer count) {
            this.name = new SimpleStringProperty(name);
            this.count = new SimpleIntegerProperty(count);
        }

        public String getName() {
            return name.get();
        }

        public StringProperty nameProperty() {
            return name;
        }

        public int getCount() {
            return count.get();
        }

        public void setCount(Integer count) {
            this.count.set(count);
        }

        public IntegerProperty countProperty() {
            return count;
        }

        @Override
        public boolean equals(Object obj) {

            boolean condition_1 = false;
            boolean condition_2 = false;

            if (this.getName().equals(((TableCableModel) obj).getName()))
                condition_1 = true;
            if (this.getCount() == ((TableCableModel) obj).getCount())
                condition_2 = true;

            return condition_1 && condition_2;
        }
    }
}
