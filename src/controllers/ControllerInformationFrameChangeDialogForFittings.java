package controllers;

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
import javafx.scene.input.*;
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

public class ControllerInformationFrameChangeDialogForFittings implements Initializable {

    @FXML
    public TextField __title;
    @FXML
    public ImageView __image;
    @FXML
    public HTMLEditor __description;
    @FXML
    public TableView<CatalogItem.FittingCable> _enter_table;
    @FXML
    public TableView<CatalogItem.FittingCable> _exit_table;
    @FXML
    public ComboBox<CatalogItem> _enter_cable_list;
    @FXML
    public ComboBox<CatalogItem> _exit_cable_list;
    @FXML
    public TextField _enter_cable_count;
    @FXML
    public TextField _exit_cable_count;
    @FXML
    public TableColumn<CatalogItem.FittingCable, String> _enter_column_1;
    @FXML
    public TableColumn<CatalogItem.FittingCable, Integer> _enter_column_2;
    @FXML
    public TableColumn<CatalogItem.FittingCable, String> _exit_column_1;
    @FXML
    public TableColumn<CatalogItem.FittingCable, Integer> _exit_column_2;

    public VBox _additional_data_block_1;
    public VBox _additional_data_block_2;

    // Для свременного сохранения изображения и отображения иконки DragAndDrop
    private Image dropIconTemp;

    private Stage dialogStage;
    private InfoModel infoModel = null;
    private boolean okClicked = false;


    private Integer fittingId = null;
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
            fittingId = id;
            itemType = item.getType();

            enable_additional_data_block();

            // Заполняем
            __title.setText(item.getTitle());
            __description.setHtmlText(item.getDescription());
            __image.setImage(item.getImage());

            fillCableTable();
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
            if (fittingId != null)
                if (DB.saveCatalogItemById(
                        fittingId,
                        __title.getText(),
                        itemType,
                        __description.getHtmlText(),
                        __image.getImage()
                )) {
                    okClicked = true;
                    dialogStage.close();
                } else {
                    // TODO: Ошибка сохранения
                }
            else if (DB.saveNewCatalogItem(
                    __title.getText(),
                    itemType,
                    __description.getHtmlText(),
                    __image.getImage()
            )) {
                okClicked = true;
                try {
                    fittingId = DB.getCatalogItemByTitle(__title.getText()).getId();
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

    private void setuCableTable() {
        // Очищение
        _enter_table.getItems().clear();

        // Настройка
        _enter_column_1.setCellValueFactory(new PropertyValueFactory<CatalogItem.FittingCable, String>("title"));
        _enter_column_2.setCellValueFactory(new PropertyValueFactory<CatalogItem.FittingCable, Integer>("count"));
        _enter_column_2.setCellFactory(TextFieldTableCell.<CatalogItem.FittingCable, Integer>forTableColumn(new IntegerStringConverter()));
        _enter_column_2.setMinWidth(10);
        _enter_column_2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CatalogItem.FittingCable, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CatalogItem.FittingCable, Integer> event) {
                TablePosition<CatalogItem.FittingCable, Integer> pos = event.getTablePosition();

                Integer newCount = event.getNewValue();

                // Делаем кол-во положительным`
                if (newCount < 1) newCount = 1;

                int rowId = pos.getRow();
                CatalogItem.FittingCable row = event.getTableView().getItems().get(rowId);

                DB.updateFittingCable(fittingId, row.getCable_id(), 0, newCount);

                fillCableTable();
            }
        });
        _exit_column_1.setCellValueFactory(new PropertyValueFactory<CatalogItem.FittingCable, String>("title"));
        _exit_column_2.setCellValueFactory(new PropertyValueFactory<CatalogItem.FittingCable, Integer>("count"));
        _exit_column_2.setCellFactory(TextFieldTableCell.<CatalogItem.FittingCable, Integer>forTableColumn(new IntegerStringConverter()));
        _exit_column_2.setMinWidth(10);
        _exit_column_2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CatalogItem.FittingCable, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CatalogItem.FittingCable, Integer> event) {
                TablePosition<CatalogItem.FittingCable, Integer> pos = event.getTablePosition();

                Integer newCount = event.getNewValue();

                // Делаем кол-во положительным`
                if (newCount < 1) newCount = 1;

                int rowId = pos.getRow();
                CatalogItem.FittingCable row = event.getTableView().getItems().get(rowId);

                DB.updateFittingCable(fittingId, row.getCable_id(), 1, newCount);

                fillCableTable();
            }
        });
    }


    private void fillCableTable() {
        ObservableList<CatalogItem.FittingCable> enter_cables = DB.getFittingCableIn(fittingId);
        ObservableList<CatalogItem.FittingCable> exit_cables = DB.getFittingCableOut(fittingId);

        _enter_table.getItems().clear();
        _enter_table.setItems(enter_cables);

        _exit_table.getItems().clear();
        _exit_table.setItems(exit_cables);
    }

    private void fillCableList() {

        ObservableList<CatalogItem> enterCables = FXCollections.observableArrayList();
        ObservableList<CatalogItem> exitCables = FXCollections.observableArrayList();
        if (fittingId != null) {
            enterCables.addAll(DB.getCablesNotInFittingById(fittingId, 0));
            exitCables.addAll(DB.getCablesNotInFittingById(fittingId, 1));
        } else {
            enterCables.addAll(DB.getCatalogTitlesByType("CableAndOther"));
            exitCables.addAll(DB.getCatalogTitlesByType("CableAndOther"));
        }

        _enter_cable_list.getItems().clear();
        _enter_cable_list.setItems(enterCables);
        _exit_cable_list.getItems().clear();
        _exit_cable_list.setItems(exitCables);

        if (_enter_cable_list.getItems().size() != 0)
            _enter_cable_list.getSelectionModel().select(0);
        if (_exit_cable_list.getItems().size() != 0)
            _exit_cable_list.getSelectionModel().select(0);

    }

    public void _enter_cable_add(ActionEvent actionEvent) {
        Integer count;
        Integer cable_id;

        try {
            count = Integer.parseInt(_enter_cable_count.getText());
            cable_id = _enter_cable_list.getSelectionModel().getSelectedItem().getId();

            if (count <= 0) count = 1;

            DB.addFittingCable(fittingId, cable_id, 0, count);

            fillCableTable();
            fillCableList();

        } catch (Exception e) {

        }
    }

    public void _exit_cable_add(ActionEvent actionEvent) {
        Integer count;
        Integer cable_id;

        try {
            count = Integer.parseInt(_exit_cable_count.getText());
            cable_id = _exit_cable_list.getSelectionModel().getSelectedItem().getId();

            if (count <= 0) count = 1;

            DB.addFittingCable(fittingId, cable_id, 1, count);

            fillCableTable();
            fillCableList();

        } catch (Exception e) {

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Настройка таблицы
        setuCableTable();

        fillCableList();
    }

    public void _enter_cable_del(ActionEvent actionEvent) {
        try {
            Integer cable_id = _enter_table.getSelectionModel().getSelectedItem().getCable_id();
            DB.deleteFittingCable(fittingId, cable_id, 0);

            fillCableTable();
            fillCableList();

        } catch (Exception e) {
        }
    }

    public void _exit_cable_del(ActionEvent actionEvent) {
        try {
            Integer cable_id = _exit_table.getSelectionModel().getSelectedItem().getCable_id();
            DB.deleteFittingCable(fittingId, cable_id, 1);

            fillCableTable();
            fillCableList();

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
        _additional_data_block_1.setDisable(false);
        _additional_data_block_2.setDisable(false);
    }
}
