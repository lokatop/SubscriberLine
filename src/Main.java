import javafx.application.Application;
import javafx.stage.Stage;
import model.DB;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        DB.createDB();

//        Stage window = primaryStage;
//        Parent root = FXMLLoader.load(getClass().getResource("fxml/first_frame.fxml"));
//        primaryStage.setTitle("Расчёт абонентской сети на пункте управления");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
