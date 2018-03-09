package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerChooseCategory{

    @FXML
    public VBox VboxChoose;

    public void changeData(ActionEvent actionEvent) throws IOException {

            VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/choose_data_category.fxml"));
            VboxChoose.getChildren().setAll(vBox);
            /*
        try {

            final Node source = (Node) actionEvent.getSource();
            final Stage stage1 = (Stage) source.getScene().getWindow();
            stage1.close();

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/choose_data_category.fxml"));
            stage.setResizable(true);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

    }

    public void theNext(ActionEvent actionEvent) throws IOException {
            VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/choose_category_scheme.fxml"));
            VboxChoose.getChildren().setAll(vBox);

    }


    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/second_frame.fxml"));
        VboxChoose.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/second_frame.fxml"));
        VboxChoose.getChildren().setAll(vBox);
    }
}



