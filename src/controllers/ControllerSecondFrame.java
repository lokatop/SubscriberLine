package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSecondFrame implements Initializable{

    @FXML
    public VBox VBoxRoot;

    public  void buttonInformation(javafx.event.ActionEvent actionEvent) throws IOException {


        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/information_frame_first.fxml"));
        VBoxRoot.getChildren().setAll(vBox);

        /*
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
        */
    }

    public void FormingADocument(javafx.event.ActionEvent actionEvent) throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/choose_category.fxml"));
        VBoxRoot.getChildren().setAll(vBox);
        /*
        try {

            final Node source = (Node) actionEvent.getSource();
            final Stage stage1 = (Stage) source.getScene().getWindow();
            stage1.close();

                Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/choose_category.fxml"));
            stage.setResizable(true);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
