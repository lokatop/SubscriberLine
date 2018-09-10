import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DB;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        DB.getConnection();

        Stage window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/first_frame.fxml"));
        primaryStage.setTitle("Расчёт абонентской сети на пункте управления");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {

        // Удаляем лишние изображения
        DB.clearImagesFolder();

        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
