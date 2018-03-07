package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

public class ControllerInformationFrame implements Initializable{

    @FXML
    public VBox Vbox;

    @FXML
    public ComboBox DC;
    @FXML
    public ComboBox ZAS;
    @FXML
    public ComboBox ARM;
    @FXML
    public ComboBox telCable;
    @FXML
    public ComboBox AOZU;
    @FXML
    public ComboBox ATZU;

    public void toShow(ActionEvent actionEvent) throws IOException {

        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/information_frame_decription.fxml"));
        Vbox.getChildren().setAll(vBox);

    }

    public void change(ActionEvent actionEvent) throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/information_frame_change.fxml"));
        Vbox.getChildren().setAll(vBox);
    }

    public static Set<String> telephoneSetsDS = new TreeSet<>();
    public static Set<String> telephoneSetsZAS = new TreeSet<>();
    public static Set<String> ARMset = new TreeSet<>();
    public static Set<String> telCableAndOther = new TreeSet<>();
    public static Set<String> ListAOZU = new TreeSet<>();
    public static Set<String> ListATZU = new TreeSet<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addTele();
    }

    private void addTele(){
        telephoneSetsDS.add("ТА-57");
        telephoneSetsDS.add("ТА-88");
        telephoneSetsDS.add("П-380 ТА");
        telephoneSetsDS.add("Селенит");

        telephoneSetsZAS.add("П-170");
        telephoneSetsZAS.add("П-171Д");
        telephoneSetsZAS.add("АТ-3031");
        telephoneSetsZAS.add("Селенит");

        ARMset.add("Рамек-2");

        telCableAndOther.add("П-274М");
        telCableAndOther.add("П-269 4х2+2х4");
        telCableAndOther.add("ПРК 5х2");
        telCableAndOther.add("ПТРК 5х2");
        telCableAndOther.add("Витая пара");
        telCableAndOther.add("ВП");
        telCableAndOther.add("РМ2");

        ListAOZU.add("П-240И");
        ListAOZU.add("МП-1И");
        ListAOZU.add("МП-2И");

        ListATZU.add("П-260-О");
        ListATZU.add("П-260-У");
        ListATZU.add("П-260-Т");
        ListATZU.add("П-244И");
        ListATZU.add("П-244И-4");
        ListATZU.add("П-242И");

        DC.getItems().addAll(telephoneSetsDS);
        ZAS.getItems().addAll(telephoneSetsZAS);
        ARM.getItems().addAll(ARMset);
        telCable.getItems().addAll(telCableAndOther);
        AOZU.getItems().addAll(ListAOZU);
        ATZU.getItems().addAll(ListATZU);
    }
}
