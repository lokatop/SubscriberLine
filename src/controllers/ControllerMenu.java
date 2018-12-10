package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class ControllerMenu {

    public static void dialogAbout (Window window_to_show){
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(window_to_show.getClass().getResource("/fxml/menu_about.fxml"));
            VBox page = (VBox) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();

            // Заголовок окна
            dialogStage.setTitle("О программе");

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(window_to_show);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
//            ControllerInformationFrameChangeDialog controller = loader.getController();
//            controller.setDialogStage(dialogStage);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void dialogDevelopers (Window window_to_show) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(window_to_show.getClass().getResource("/fxml/menu_developers.fxml"));
            VBox page = (VBox) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();

            // Заголовок окна
            dialogStage.setTitle("Разработики");

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(window_to_show);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
//            ControllerInformationFrameChangeDialog controller = loader.getController();
//            controller.setDialogStage(dialogStage);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void dialogMethodic(Window window_to_show){
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(window_to_show.getClass().getResource("/fxml/menu_methodic.fxml"));
            VBox page = (VBox) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();

            // Заголовок окна
            dialogStage.setTitle("Методика");

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(window_to_show);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
//            ControllerInformationFrameChangeDialog controller = loader.getController();
//            controller.setDialogStage(dialogStage);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
