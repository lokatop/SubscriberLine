package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class ControllerSecondFrame {

    public  void buttonInformation(javafx.event.ActionEvent actionEvent){
        try {

            final Node source = (Node) actionEvent.getSource();
            final Stage stage1 = (Stage) source.getScene().getWindow();
            stage1.close();

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/information_frame_first.fxml"));
            stage.setResizable(true);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void FormingADocument(){

    }

}
