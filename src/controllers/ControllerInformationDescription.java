package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import model.Catalog;
import model.DB;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerInformationDescription {
    @FXML
    public VBox VboxDesc;

    public Label lblLabel;
    public ImageView imageDescr;
    public WebView lblDesc;

    private Catalog catalogItem;

    public void setModel(Integer id){
        try {
            this.catalogItem = DB.getCatalogItemById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Заголовок
        lblLabel.setText(catalogItem.getTitle());
        // Описание
        lblDesc.getEngine().loadContent(catalogItem.getDescription());
        // Изображение
        imageDescr.setImage(catalogItem.getImage());
    }

    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/information_frame_first.fxml"));
        VboxDesc.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/second_frame.fxml"));
        VboxDesc.getChildren().setAll(vBox);
    }
}
