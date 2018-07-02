import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Stage window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/first_frame.fxml"));
        primaryStage.setTitle("Расчёт абонентской сети на пункте управления");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
