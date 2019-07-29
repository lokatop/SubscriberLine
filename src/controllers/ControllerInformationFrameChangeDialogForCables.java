package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import model.CatalogItem;
import model.DB;
import model.InfoModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerInformationFrameChangeDialogForCables implements Initializable {

    @FXML
    public TextField __title;
    @FXML
    public ImageView __image;
    @FXML
    public HTMLEditor __description;
    @FXML
    public TableView<CatalogItem.Wire> _wire_table;
    @FXML
    public ComboBox<String> _material_list;
    @FXML
    public TextField _material_count;
    @FXML
    public TableColumn<CatalogItem.Wire, String> _wire_column_1;
    @FXML
    public TableColumn<CatalogItem.Wire, Integer> _wire_column_2;
    public TextField __cable_mass;
    public TextField __cable_length;
    public VBox _additional_data_block;
    public TextField __work_pair;

    // Для свременного сохранения изображения и отображения иконки DragAndDrop
    private Image dropIconTemp;

    private Stage dialogStage;
    private InfoModel infoModel = null;
    private boolean okClicked = false;


    private Integer cableId = null;
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
            CatalogItem item = DB.getCableById(id);
            cableId = id;
            itemType = item.getType();

            enable_additional_data_block();

            // Заполняем
            __title.setText(item.getTitle());
            __description.setHtmlText(item.getDescription());
            __image.setImage(item.getImage());
            __cable_mass.setText(Float.toString(item.getMass()));
            __cable_length.setText(Float.toString(item.getCable_length()));
            __work_pair.setText(Integer.toString(item.getWork_pair()));

            fillWireTable();
            fillWireList();

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
        try {
            Float.parseFloat(__cable_mass.getText());
            Float.parseFloat(__cable_length.getText());
            Integer.parseInt(__work_pair.getText());
        } catch (Exception e) {
            errorMessage += "Введено неправильное число\n";
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
            if (cableId != null)
                if (DB.saveCableById(
                        cableId,
                        __title.getText(),
                        itemType,
                        __description.getHtmlText(),
                        __image.getImage(),
                        Float.parseFloat(__cable_mass.getText()),
                        Float.parseFloat(__cable_length.getText()),
                        Integer.parseInt(__work_pair.getText())
                )) {
                    okClicked = true;
                    dialogStage.close();
                } else {
                    // TODO: Ошибка сохранения
                }
            else if (DB.saveNewCableItem(
                    __title.getText(),
                    itemType,
                    __description.getHtmlText(),
                    __image.getImage(),
                    Float.parseFloat(__cable_mass.getText()),
                    Float.parseFloat(__cable_length.getText()),
                    Integer.parseInt(__work_pair.getText())
            )) {
                okClicked = true;
                try {
                    cableId = DB.getCatalogItemByTitle(__title.getText()).getId();
                    enable_additional_data_block();
                } catch (SQLException e) {
                    e.printStackTrace();
                    dialogStage.close();
                }
            } else {
                // TODO: Ошибка сохранения
            }
        } else {
            // TODO: Ошибка ввода
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

    private void setupWireTable() {
        // Очищение
        _wire_table.getItems().clear();

        // Настройка
        _wire_column_1.setCellValueFactory(new PropertyValueFactory<CatalogItem.Wire, String>("material"));
        _wire_column_2.setCellValueFactory(new PropertyValueFactory<CatalogItem.Wire, Integer>("count"));
        _wire_column_2.setCellFactory(TextFieldTableCell.<CatalogItem.Wire, Integer>forTableColumn(new IntegerStringConverter()));
        _wire_column_2.setMinWidth(10);
        _wire_column_2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CatalogItem.Wire, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CatalogItem.Wire, Integer> event) {
                TablePosition<CatalogItem.Wire, Integer> pos = event.getTablePosition();

                Integer newCount = event.getNewValue();

                // Делаем кол-во положительным
                if (newCount < 1) newCount = 1;

                int rowId = pos.getRow();
                CatalogItem.Wire row = event.getTableView().getItems().get(rowId);

                DB.updateCountWiresInCableById(cableId, row.getMaterial(), newCount);

                fillWireTable();
            }
        });
    }


    private void fillWireTable() {
        ObservableList<CatalogItem.Wire> wires = DB.getWiresFromCableById(cableId);

        _wire_table.getItems().clear();

        _wire_table.setItems(wires);
    }

    private void fillWireList() {

        _material_list.getItems().add("Сталь");
        _material_list.getItems().add("Медь");

        if (_material_list.getItems().size() != 0)
            _material_list.getSelectionModel().select(0);
    }

    public void _material_add(ActionEvent actionEvent) {
        Integer count;
        String material;

        try {
            count = Integer.parseInt(_material_count.getText());
            material = _material_list.getSelectionModel().getSelectedItem();

            if (count <= 0) count = 1;

            DB.addWireToCable(cableId, material, count);

            fillWireTable();

        } catch (Exception e) {

        }
    }

    private void setupMassLengthTextFields() {
        // Установка ввода только десятичных
        __cable_mass.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    __cable_mass.setText(oldValue);
                }
            }
        });
        __cable_length.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    __cable_length.setText(oldValue);
                }
            }
        });
        // Только положительные целые числа
        __work_pair.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("[0-9]*")) {
                    __work_pair.setText(oldValue);
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Настройка таблицы
        setupWireTable();
        setupMassLengthTextFields();

        fillWireList();
    }

    public void _material_del(ActionEvent actionEvent) {
        try {
            String material = _wire_table.getSelectionModel().getSelectedItem().getMaterial();
            DB.deleteWireInCable(cableId, material);

            fillWireTable();
            fillWireList();

        } catch (Exception e) {
        }
    }

    public void __desc_text_changed(KeyEvent keyEvent) {
        if (keyEvent.isControlDown() && (keyEvent.getText().equals("v") || keyEvent.getText().equals("V") || keyEvent.getText().equals("м") || keyEvent.getText().equals("М"))) {

            String raw_string = __description.getHtmlText();

            String modded_string = raw_string.replaceAll("href", "nohref");
            modded_string = modded_string.replaceAll("<a", "<font");
            modded_string = modded_string.replaceAll("a>", "font>");

            __description.setHtmlText(modded_string);
            __description.requestFocus();
        }
    }

    public void enable_additional_data_block(){
        _additional_data_block.setDisable(false);
    }
}
