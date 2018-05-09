package controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import model.InfoModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
    public ComboBox<InfoModel> _ta_list;
    @FXML
    public TextField _ta_count;
    @FXML
    public TableColumn<TableModel, String> _ta_column_1;
    @FXML
    public TableColumn<TableModel, Integer> _ta_column_2;

    // Для свременного сохранения изображения и отображения иконки DragAndDrop
    private Image dropIconTemp;

    private Stage dialogStage;
    private InfoModel infoModel = null;
    private boolean okClicked = false;
    private ObservableList<InfoModel> TA = FXCollections.observableArrayList();
    private ObservableList<TableModel> TATable = FXCollections.observableArrayList();

    /**
     * Устанавливает сцену для этого окна.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setInfoModel(InfoModel infoModel) {
        this.infoModel = infoModel;

        if (infoModel != null) {
            // Заполняем
            __title.setText(infoModel.getTitle());
            __description.setHtmlText(infoModel.getDescription());
            __image.setImage(infoModel.getImage());
        }

        // Читаем данные
        readData();

        // Заполняем таблицу
        fillTable();
    }

    public void setTAList(ObservableList<InfoModel> TA) {
        this.TA = TA;

        // Настройка выпадающего списка
        setupTAList();
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
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Поля с ошибками");
            alert.setHeaderText("Пожалуйста, исправте ошибки");
            alert.setContentText(errorMessage);

            alert.showAndWait();

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
            for (TableModel ta : TATable) {
                data += ta.getName() + ":" + ta.getCount() + ";";
            }
            infoModel.setData(data);

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

    private void setupTable() {
        // Очищение
        _ta_table.getItems().clear();

        // Настройка
        _ta_column_1.setCellValueFactory(new PropertyValueFactory<TableModel, String>("name"));
        _ta_column_2.setCellValueFactory(new PropertyValueFactory<TableModel, Integer>("count"));
        _ta_column_2.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        _ta_column_2.setMinWidth(10);
        _ta_column_2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableModel, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableModel, Integer> event) {
                TablePosition<TableModel, Integer> pos = event.getTablePosition();

                Integer newCount = event.getNewValue();

                // Делаем кол-во положительным
                if (newCount < 1) newCount = 1;

                int rowId = pos.getRow();
                TableModel row = event.getTableView().getItems().get(rowId);

                row.setCount(newCount);
            }
        });
    }

    private void fillTable() {
        _ta_table.setItems(TATable);
    }

    private void readData() {
        if (infoModel.getData() != null) {
            TATable.clear();

            String[] substrs;
            substrs = infoModel.getData().split(";");
            for (String substr : substrs) {
                String strTA[] = substr.split(":");
                TATable.add(new TableModel(strTA[0], Integer.parseInt(strTA[1])));
            }
        }
    }

    private void setupTAList() {
        _ta_list.setItems(TA);
        if (_ta_list.getItems().size() != 0)
            _ta_list.getSelectionModel().select(0);
    }

    public void _ta_add(ActionEvent actionEvent) {
        String name;
        String count;

        name = _ta_list.getSelectionModel().getSelectedItem().getTitle();
        count = _ta_count.getText();

        try {
            if (!name.isEmpty() && !count.isEmpty()) {
                TATable.add(new TableModel(name, Integer.parseInt(count)));
                fillTable();
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Настройка таблицы
        setupTable();
    }

    public void _ta_del(ActionEvent actionEvent) {
        try {
            TATable.remove(_ta_table.getSelectionModel().getSelectedItem());
        } catch (Exception e){}
    }


    public class TableModel {

        private SimpleStringProperty name;
        private SimpleIntegerProperty count;

        public TableModel(String name, Integer count) {
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
}
