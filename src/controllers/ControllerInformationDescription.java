package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import model.CatalogItem;
import model.DB;
import model.TableCableModel;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerInformationDescription {
    @FXML
    public VBox VboxDesc;

    public Label lblLabel;
    public ImageView imageDescr;
    public WebView lblDesc;
    public TreeView<String> _additional_data;

    private CatalogItem catalogItem;

    public void setModel(Integer id) {
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

        fillAdditionalData();
    }

    private void fillAdditionalData() {

        TreeItem<String> treeItemRoot = new TreeItem<String>("Дополнительная информация");
        treeItemRoot.setExpanded(true);

        switch (catalogItem.getType()) {
            case "AOZU":
            case "ATZU":

                // Получаем кабели в аппаратной
                ObservableList<TableCableModel> appCalbes = DB.getCablesInApparatousById(catalogItem.getId());
                // Получаем та в аппаратной
                ObservableList<CatalogItem> appTa = DB.getTaInApparatousById(catalogItem.getId());

                // Заполняем древо кабелями
                if (appCalbes.size() != 0) {
                    TreeItem<String> treeItemCables = new TreeItem<String>("Кабели");
                    treeItemCables.setExpanded(true);

                    for (int i = 0; i < appCalbes.size(); i++) {
                        treeItemCables.getChildren().add(new TreeItem<String>(
                                appCalbes.get(i).getName() + " " + appCalbes.get(i).getCount() + " шт. " + appCalbes.get(i).getLength() + " м."
                        ));
                    }

                    treeItemRoot.getChildren().add(treeItemCables);
                }

                // Заполняем древо та
                if (appTa.size() != 0) {
                    TreeItem<String> treeItemTa = new TreeItem<String>("Аппаратура");
                    treeItemTa.setExpanded(true);

                    for (int i = 0; i < appTa.size(); i++) {
                        treeItemTa.getChildren().add(new TreeItem<String>(
                                appTa.get(i).getTitle() + " " + appTa.get(i).getCount() + " шт."
                        ));
                    }

                    treeItemRoot.getChildren().add(treeItemTa);
                }

                break;
            case "CableAndOther":
                CatalogItem cableItem = null;
                try {
                    cableItem = DB.getCableById(catalogItem.getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                TreeItem<String> treeItemMass = new TreeItem<String>("Масса в катушке");
                treeItemMass.setExpanded(true);
                treeItemMass.getChildren().add(new TreeItem<String>(Float.toString(cableItem.getMass()) + " кг"));
                TreeItem<String> treeItemLength = new TreeItem<String>("Длина");
                treeItemLength.setExpanded(true);
                treeItemLength.getChildren().add(new TreeItem<String>(Float.toString(cableItem.getCable_length()) + " м"));
                TreeItem<String> treeItemWorkPair = new TreeItem<String>("Количество рабочих пар");
                treeItemWorkPair.setExpanded(true);
                treeItemWorkPair.getChildren().add(new TreeItem<String>(Integer.toString(cableItem.getWork_pair()) + " шт"));

                treeItemRoot.getChildren().add(treeItemMass);
                treeItemRoot.getChildren().add(treeItemLength);
                treeItemRoot.getChildren().add(treeItemWorkPair);
                break;

            case "DS":
            case "ZAS":
            case "ARM":
                // Заполняем древо
                TreeItem<String> treeItem = new TreeItem<String>("Режим подключения");
                treeItem.setExpanded(true);

                treeItem.getChildren().add(new TreeItem<String>(
                    catalogItem.getConnect_type()
                ));

                treeItemRoot.getChildren().add(treeItem);
                break;

            case "FIT":
                // Получаем кабели на вход
                ObservableList<CatalogItem.FittingCable> enterCables = DB.getFittingCableIn(catalogItem.getId());
                // Получаем кабели на выход
                ObservableList<CatalogItem.FittingCable> exitCables = DB.getFittingCableOut(catalogItem.getId());

                // Заполняем древо кабелями на вход
                if (enterCables.size() != 0) {
                    TreeItem<String> treeItemCables = new TreeItem<String>("Вход");
                    treeItemCables.setExpanded(true);

                    for (int i = 0; i < enterCables.size(); i++) {
                        treeItemCables.getChildren().add(new TreeItem<String>(
                                enterCables.get(i).getTitle() + " - " + enterCables.get(i).getCount() + " шт"
                        ));
                    }

                    treeItemRoot.getChildren().add(treeItemCables);
                }
                // Заполняем древо кабелями на выход
                if (exitCables.size() != 0) {
                    TreeItem<String> treeItemCables = new TreeItem<String>("Выход");
                    treeItemCables.setExpanded(true);

                    for (int i = 0; i < exitCables.size(); i++) {
                        treeItemCables.getChildren().add(new TreeItem<String>(
                                exitCables.get(i).getTitle() + " - " + enterCables.get(i).getCount() + " шт"
                        ));
                    }

                    treeItemRoot.getChildren().add(treeItemCables);
                }

                break;
        }

        // Отображаем древо, если оно не пустое
        if (treeItemRoot.getChildren().size() != 0)
            _additional_data.setVisible(true);

        _additional_data.setRoot(treeItemRoot);
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
