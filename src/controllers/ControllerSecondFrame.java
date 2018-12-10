package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSecondFrame implements Initializable {

    @FXML
    public VBox VBoxRoot;
    public MenuItem _menu_about;
    public MenuItem _menu_methodic;
    public MenuItem _menu_developers;

    @FXML
    public  void buttonInformation(ActionEvent actionEvent) throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/information_frame_first.fxml"));
        VBoxRoot.getChildren().setAll(vBox);
    }

    @FXML
    public void FormingADocument(ActionEvent actionEvent) throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/choose_category_scheme.fxml"));
        VBoxRoot.getChildren().setAll(vBox);
    }

    public void setupMenus(){

        _menu_about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Window window = ((Node)VBoxRoot).getScene().getWindow();

                ControllerMenu.dialogAbout(window);
            }
        });

        _menu_developers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Window window = ((Node)VBoxRoot).getScene().getWindow();

                ControllerMenu.dialogDevelopers(window);
            }
        });

        _menu_methodic.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Window window = ((Node)VBoxRoot).getScene().getWindow();

                ControllerMenu.dialogMethodic(window);
            }
        });


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupMenus();
    }
}
