package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class ControllerSecondFrame{

    @FXML
    public VBox VBoxRoot;
    @FXML
    private Label label1;

    VBox vBox = null;

    @FXML
    public  void buttonInformation(ActionEvent actionEvent) {
        try {
            vBox = FXMLLoader.load(getClass().getResource("../fxml/information_frame_first.fxml"));
            VBoxRoot.getChildren().setAll(vBox);
        } catch (IOException e) {
            e.printStackTrace();
            label1.setText("Эээ, я хзЭ \n" + e +"\n");

        }

        label1.setText("Всё ок, хуй знает что творится");
    }

    @FXML
    public void FormingADocument(ActionEvent actionEvent) throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/choose_category_scheme.fxml"));
        VBoxRoot.getChildren().setAll(vBox);
    }
}
