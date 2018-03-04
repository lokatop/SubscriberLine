package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerFirstFrame {
    public void loginToTheProgram(ActionEvent actionEvent){
        try {

            final Node source = (Node) actionEvent.getSource();
            final Stage stage1 = (Stage) source.getScene().getWindow();
            stage1.close();

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/second_frame.fxml"));
            stage.setResizable(true);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
