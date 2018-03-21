package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import model.InfoModel;

public class ControllerInformationFrameChangeDialog {

    public TextField __title;
    public ImageView __image;
    public HTMLEditor __description;


    private Stage dialogStage;
    private InfoModel infoModel = null;
    private boolean okClicked = false;

    /**
     * Инициализирует класс-контроллер. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
    }

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
//        __image.setImage(infoModel.getImageURL()); //TODO изменить на image
        }
    }

    public InfoModel getInfoModel(){
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
        if (__description.getHtmlText() == null || __description.getHtmlText().length() == 0) {
            errorMessage += "Пустое описание!\n";
        }
//        if (__image.getImage() == null) { //TODO: вкл. проверку на налиличе image
//            errorMessage += "Изображение не выбрано!\n";
//        }

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
//            infoModel.setImageURL(__image.getImage()); //TODO изменить на image

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
}
