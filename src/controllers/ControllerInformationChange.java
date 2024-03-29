package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.InfoModel;
import model.XMLsaver;

import java.io.IOException;

import static model.InfoModel.CATEGORIES;
import static model.InfoModel.CATEGORIES_DESC;

public class ControllerInformationChange{
    @FXML
    public VBox VboxInfFrame;
    @FXML
    public ListView __list_of_items;
    @FXML
    public ComboBox __list_of_categories;
    @FXML
    public Button __btn_edit;
    public Button __btn_delete;
    public Button __btn_copy_past;
    public Button __btn_copy_past_cancel;

    /**
     * Изменяемая категория
     */
    private Integer changingTypeId;
    /**
     * Список всех моделей
     */
    private ObservableList<InfoModel> infoData = FXCollections.observableArrayList();
    /**
     * Список изменяемых моделей
     */
    private ObservableList<InfoModel> changingList = FXCollections.observableArrayList();

    // Копирование/вставка
    private Integer modelCopyPastID = null;

    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/information_frame_first.fxml"));
        VboxInfFrame.getChildren().setAll(vBox);
    }

    public void setChangingTypeAndInfoData(Integer changingTypeId, ObservableList<InfoModel> infoData) {
        this.changingTypeId = changingTypeId;

        this.infoData.clear();
        this.infoData = infoData;

        // Заполняем категорию изменяемых моделей
        __list_of_categories.getItems().clear();
        __list_of_categories.getItems().addAll(CATEGORIES_DESC);
        __list_of_categories.getSelectionModel().select(CATEGORIES_DESC[changingTypeId]);

        updateLists();

        __list_of_items.getItems().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                if (__list_of_items.getSelectionModel().isEmpty())
                    disable_buttons();
                else
                    enable_buttons();
            }
        });
    }

    public void info_model_add(ActionEvent actionEvent) {
        showAddDialog();
        updateLists();
    }

    public void info_model_save(ActionEvent actionEvent) throws IOException {
        if (!infoData.isEmpty()) {
            XMLsaver.saveToXML(infoData, InfoModel.FILENAME_INFOMODELS);
        } else {
            // TODO: Удаление файла, если удалили все модели?
        }

        this.btnBackClick();
    }

    public void info_model_edit(ActionEvent actionEvent) {
        showEditDialog();
        updateLists();
    }

    public void enable_btns(MouseEvent mouseEvent) {
        enable_buttons();
    }

    private void enable_buttons() {
        __btn_edit.setDisable(false);
        __btn_delete.setDisable(false);
        __btn_copy_past.setDisable(false);
    }

    private void disable_buttons() {
        __btn_edit.setDisable(true);
        __btn_delete.setDisable(true);
        if (modelCopyPastID == null) {
            __btn_copy_past.setDisable(true);
        }
    }

    public boolean showEditDialog() {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/information_frame_change_dialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();

            // Заголовок окна
            dialogStage.setTitle("Редактирование");

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(VboxInfFrame.getParent().getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            ControllerInformationFrameChangeDialog controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Отправляем модель
            InfoModel model = changingList.get(__list_of_items.getSelectionModel().getSelectedIndex());
            controller.setInfoModel(model);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showAddDialog() {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/information_frame_change_dialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();

            // Заголовок окна
            dialogStage.setTitle("Добавление");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(VboxInfFrame.getParent().getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            ControllerInformationFrameChangeDialog controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Отправляем модель
            InfoModel model = new InfoModel("",CATEGORIES[changingTypeId],"",new Image("resource/noimage.png"));

            controller.setInfoModel(model);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            // Добавляем новую модель
            if (controller.isOkClicked()){
                infoData.add(controller.getInfoModel());
            }

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void updateLists(){

        // Меняем id категории
        changingTypeId = __list_of_categories.getSelectionModel().getSelectedIndex();

        // Фильтруем по типу
        changingList = null;
        changingList = InfoModel.filterInfoModelByType(CATEGORIES[changingTypeId], infoData);

        // Выбираем категорию изменяемых моделей
        __list_of_categories.getSelectionModel().select(CATEGORIES_DESC[changingTypeId]);

        // Заполняем список изменяемых моделей
        __list_of_items.getItems().clear();
        __list_of_items.getItems().addAll(changingList);
    }

    public void info_model_delete(ActionEvent actionEvent) {
        int id = __list_of_items.getSelectionModel().getSelectedIndex();
        infoData.remove(changingList.get(id));

        updateLists();
    }

    public void info_model_copy_past(ActionEvent actionEvent) {
        int id = __list_of_items.getSelectionModel().getSelectedIndex();

        // Если буфер пуст - копируем, если нет - вставляем
        if (modelCopyPastID == null){
            modelCopyPastID = id;
            __btn_copy_past_cancel.setDisable(false);
            __btn_copy_past.setText("Вставить");
        } else {
            // Модифицируем тип модели
            infoData.get(modelCopyPastID).setType(infoData.get(modelCopyPastID).getType()+","+InfoModel.CATEGORIES[changingTypeId]);
            modelCopyPastID = null;
            __btn_copy_past_cancel.setDisable(true);
            __btn_copy_past.setText("Копировать");
            updateLists();
        }
    }

    public void info_model_copy_past_cancel(ActionEvent actionEvent) {
        modelCopyPastID = null;

        __btn_copy_past.setDisable(true);
        __btn_copy_past.setText("Копировать");
        __btn_copy_past_cancel.setDisable(true);
    }
}
