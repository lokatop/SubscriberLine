package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import javax.swing.text.html.ListView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerChooseCategoryOfOfficial implements Initializable{
    @FXML
    public VBox selectionOfOfficials;

 /*   @FXML
    public ListView listViewOfficial;
*/
    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/choose_category_scheme.fxml"));
        selectionOfOfficials.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/second_frame.fxml"));
        selectionOfOfficials.getChildren().setAll(vBox);
    }

    @FXML
    private void theNext() throws IOException{
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/type_definition_1.fxml"));
        selectionOfOfficials.getChildren().setAll(vBox);
    }

    @FXML
    private void EditOriginalData() throws IOException{

        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/choose_category_of_official_apple.fxml"));
        selectionOfOfficials.getChildren().setAll(vBox);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //ListViewOfficial
    }
}
