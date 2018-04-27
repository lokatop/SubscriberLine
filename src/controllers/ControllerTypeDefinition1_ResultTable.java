package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import model.TheLastTable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerTypeDefinition1_ResultTable implements Initializable {
    @FXML
    public VBox typeDefinition;
    @FXML
    public WebView _table_view;
    @FXML
    public WebView _table_view_abons;

    /**
     * Список данных Для промежуточной таблицы
     */
    public ObservableList<TheLastTable> theLastTableList = FXCollections.observableArrayList();

    /**
     * Промежуточная таблица в HTML
     */
    public String theLastTableListHTML;

    /**
     * Таблица кол-ва абонентов в HTML
     */
    public String abonsHTML;

    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/type_definition_1.fxml"));
        typeDefinition.getChildren().setAll(vBox);
    }

    @FXML
    private void btnToMenuClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/second_frame.fxml"));
        typeDefinition.getChildren().setAll(vBox);
    }

    @FXML
    private void theNext() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/fxml/type_definition_2.fxml"));
        typeDefinition.getChildren().setAll(vBox);
    }

    private void readData() {

        theLastTableList.clear();

        // Получаем объекты из предыдущего контроллера
        theLastTableList = ControllerTypeDefinition1.theLastTableList;
    }


    /**
     * Заполнение таблицы
     **/
    private void fillTables() {
        _table_view.getEngine().loadContent(theLastTableListHTML);
        _table_view_abons.getEngine().loadContent(abonsHTML);
    }

    private void formHtmlMainTable(){

        theLastTableListHTML = "<table width=\"100%\" border=\"1\" cellpadding=\"4\" cellspacing=\"0\">\n";
        theLastTableListHTML += "<tr><th>Должностное лицо</th><th>Оконечное устройсиво</th></tr>\n";

        ArrayList<String> officialPersons = TheLastTable.getAllOfficialPerson(theLastTableList);

        for (int i = 0; i < officialPersons.size(); i++) {

            ObservableList<TheLastTable> Abons = TheLastTable.filterByOfficialPerson(officialPersons.get(i), theLastTableList);

            theLastTableListHTML += "<tr><td rowspan=\"" + Abons.size() + "\">" + officialPersons.get(i) + "</td><td>" + Abons.get(0).getTypeAbon() + "</td></tr>";

            for (int j = 1; j < Abons.size(); j++){
                TheLastTable abon = Abons.get(j);
                theLastTableListHTML += "<tr><td>" + abon.getTypeAbon() + "</td></tr>";

            }

        }

        theLastTableListHTML += " </table>";

//        theLastTableListHTML = "<table border=\"1\" cellpadding=\"4\" cellspacing=\"0\">\n" +
//                "   <tr>\n" +
//                "    <td rowspan=\"2\">Браузер</td>\n" +
//                "    <td colspan=\"2\">Internet  Explorer</td>\n" +
//                "    <td colspan=\"3\">Opera</td>\n" +
//                "    <td colspan=\"2\">Firefox</td>\n" +
//                "   </tr>\n" +
//                "   <tr>\n" +
//                "    <td>6.0</td><td>7.0</td><td>7.0</td><td>8.0</td><td>9.0</td><td>1.0</td><td>2.0</td>\n" +
//                "   </tr>\n" +
//                "   <tr align=\"center\">\n" +
//                "    <td>Поддерживается</td>\n" +
//                "    <td>Нет</td><td>Да</td><td>Нет</td><td>Да</td><td>Да</td><td>Да</td><td>Да</td>\n" +
//                "   </tr>\n" +
//                "  </table>";
    }

    private void formHtmlAbonsTable(){

        abonsHTML = "<table width=\"100%\" border=\"1\" cellpadding=\"4\" cellspacing=\"0\">\n";
        abonsHTML += "<tr><th>Оконечное устройсиво</th><th>Общее количество</th></tr>\n";

        ArrayList<String> abons = TheLastTable.getAllTypeAbon(theLastTableList);

        for (int i = 0; i < abons.size(); i++) {

            ObservableList<TheLastTable> items = TheLastTable.filterByTypeAbon(abons.get(i), theLastTableList);

            abonsHTML += "<tr><td>" + abons.get(i) + "</td><td>" + items.size() + "</td></tr>";
        }

        abonsHTML += " </table>";

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        readData();

        formHtmlMainTable();
        formHtmlAbonsTable();

        fillTables();
    }
}
