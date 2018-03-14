package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.InfoModel;
import model.InfoModelListWrapper;
import model.XMLsaver;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

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

    /**
     * Данные, в виде наблюдаемого списка адресатов.
     */
    private ObservableList<InfoModel> infoData = FXCollections.observableArrayList();

    public void toShow(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/information_frame_decription.fxml"));

        //Получаем выбранную модель
        Button b =(Button)actionEvent.getTarget();
        HBox vb = (HBox) b.getParent();
        ComboBox cb = (ComboBox) vb.getChildren().get(0);
        InfoModel model = (InfoModel) cb.getSelectionModel().getSelectedItem();

        try{
            VBox vBox = (VBox)loader.load();

            // Передаём выбранную модель в контроллер фрейма Описание
            ControllerInformationDescription controller = loader.getController();
            controller.setModel(model);

            // Оотображаем
            Vbox.getChildren().setAll(vBox);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void change(ActionEvent actionEvent) throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/information_frame_change.fxml"));
        Vbox.getChildren().setAll(vBox);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addTele();
    }

    private void addTele(){
        // В качестве образца добавляем некоторые данные
//        infoData.add(new InfoModel("ТА-57","DS","Описание", "image.png"));
//        infoData.add(new InfoModel("ТА-88","DS","Описание", "image.png"));
//        infoData.add(new InfoModel("П-380 ТА","DS","Описание", "image.png"));
//        infoData.add(new InfoModel("Селенит","DS","Описание", "image.png"));
//
//        infoData.add(new InfoModel("П-170","ZAS","Описание", "image.png"));
//        infoData.add(new InfoModel("П-171Д","ZAS","Описание", "image.png"));
//        infoData.add(new InfoModel("АТ-3031","ZAS","Описание", "image.png"));
//        infoData.add(new InfoModel("Селенит","ZAS","Описание", "image.png"));
//
//        infoData.add(new InfoModel("Рамек-2","ARM","Описание", "image.png"));
//
//        infoData.add(new InfoModel("П-274М","CableAndOther","Описание", "image.png"));
//        infoData.add(new InfoModel("П-269 4х2+2х4","CableAndOther","Описание", "image.png"));
//        infoData.add(new InfoModel("ПРК 5х2","CableAndOther","Описание", "image.png"));
//        infoData.add(new InfoModel("ПТРК 5х2","CableAndOther","Описание", "image.png"));
//        infoData.add(new InfoModel("Витая пара","CableAndOther","Описание", "image.png"));
//        infoData.add(new InfoModel("ВП","CableAndOther","Описание", "image.png"));
//        infoData.add(new InfoModel("РМ2","CableAndOther","Описание", "image.png"));
//
//        infoData.add(new InfoModel("П-240И","AOZU","Описание", "image.png"));
//        infoData.add(new InfoModel("МП-1И","AOZU","Описание", "image.png"));
//        infoData.add(new InfoModel("МП-2И","AOZU","Описание", "image.png"));
//
//        infoData.add(new InfoModel("П-260-О","ATZU","Описание", "image.png"));
//        infoData.add(new InfoModel("П-260-У","ATZU","Описание", "image.png"));
//        infoData.add(new InfoModel("П-260-Т","ATZU","Описание", "image.png"));
//        infoData.add(new InfoModel("П-244И","ATZU","Описание", "image.png"));
//        infoData.add(new InfoModel("П-244И-4","ATZU","Описание", "image.png"));
//        infoData.add(new InfoModel("П-242И","ATZU","Описание", "image.png"));

        //Пробуем сохранить  в xml
        infoData.addAll(loadInfoModelsFromXMLFile());

        if (!infoData.isEmpty()) {
            // Добавляем фильтрованный по "типу" список в ComboBox
            DC.getItems().addAll(filterInfoModelByType("DS"));
            // Выбираем первый эл-т для отображения
            DC.getSelectionModel().select(0);
            ZAS.getItems().addAll(filterInfoModelByType("ZAS"));
            ZAS.getSelectionModel().select(0);
            ARM.getItems().addAll(filterInfoModelByType("ARM"));
            ARM.getSelectionModel().select(0);
            telCable.getItems().addAll(filterInfoModelByType("CableAndOther"));
            telCable.getSelectionModel().select(0);
            AOZU.getItems().addAll(filterInfoModelByType("AOZU"));
            AOZU.getSelectionModel().select(0);
            ATZU.getItems().addAll(filterInfoModelByType("ATZU"));
            ATZU.getSelectionModel().select(0);
        }
    }

    @FXML
    private void btnBackClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("../fxml/second_frame.fxml"));
        Vbox.getChildren().setAll(vBox);
    }


    /**
     * Фильтрует модели по типу
     * @param type
     * @return FilteredList<InfoModel>
     */
    private FilteredList<InfoModel> filterInfoModelByType(String type){
        return infoData.filtered(new Predicate<InfoModel>() {
            @Override
            public boolean test(InfoModel infoModel) {
                if (infoModel.getType().equals(type)) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    // Сохранение в XML
    public void saveInfoModelsToXMLFile(ObservableList<InfoModel> infoData) {
        try {
            JAXBContext context = JAXBContext.newInstance(InfoModelListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Обёртываем наши данные об адресатах.
            InfoModelListWrapper wrapper = new InfoModelListWrapper();
            wrapper.setInfoModels(infoData);

            // Открываем файл
            FileOutputStream file = new FileOutputStream("InfoModels.xml");

            // Маршаллируем и сохраняем XML в файл.
            m.marshal(wrapper, file);

            // Закрываем файл
            file.close();

        } catch (Exception e) { // catches ANY exception
            String a = "";
        }
    }
    /**
     * Загружает список объектов InfoModel из файла file.xml
     * @return
     */
    private ObservableList<InfoModel> loadInfoModelsFromXMLFile(){
        try {
            JAXBContext context = JAXBContext.newInstance(InfoModelListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Открываем файл
            FileInputStream file = new FileInputStream("InfoModels.xml");

            // Чтение XML из файла и демаршализация.
            InfoModelListWrapper wrapper = (InfoModelListWrapper) um.unmarshal(file);

            ObservableList<InfoModel> infoDataReaded = FXCollections.observableArrayList();

            infoDataReaded.addAll(wrapper.getInfoModels());


            file.close();

            return infoDataReaded;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Сохраняет список объектов InfoModel в файл file
     * @param list
     */
    private void saveInfoModelsToFile(FilteredList<InfoModel> list){
        try {
            FileOutputStream file = new FileOutputStream("file");
            ObjectOutputStream in = new ObjectOutputStream(file);

            for (InfoModel model : list){
                in.writeObject(model);
            }

            in.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Загружает список объектов InfoModel из файла file
     * @return
     */
    private ObservableList<InfoModel> loadInfoModelsFromFile(){
        try {
            FileInputStream file = new FileInputStream("file");
            ObjectInputStream in = new ObjectInputStream(file);

            ObservableList<InfoModel> infoDataReaded = FXCollections.observableArrayList();

            Integer i = 0; // TODO: КОСТЫЛИИИ!!!!!!!!!!!!!!!!!!!1111
            do{
                i++;
                try {
                    InfoModel im = (InfoModel) in.readObject();

                    infoDataReaded.add(im);
                } catch (Exception e){}
            }while (i < 999);

            in.close();
            file.close();

            return infoDataReaded;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
