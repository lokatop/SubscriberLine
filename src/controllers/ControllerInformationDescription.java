package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import model.InfoModel;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

public class ControllerInformationDescription {
    @FXML
    public VBox VboxDesc;

    public Label lblLabel;
    public ImageView imageDescr;
    public WebView lblDesc;

    private InfoModel infoModel;

    public void setModel(InfoModel model){
        this.infoModel = model;

        // Заголовок
        lblLabel.setText(this.infoModel.getTitle());
        // Описание
        lblDesc.getEngine().loadContent(this.infoModel.getDescription());
    }

    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/information_frame_first.fxml"));
        VboxDesc.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/second_frame.fxml"));
        VboxDesc.getChildren().setAll(vBox);
    }
}
