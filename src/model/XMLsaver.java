package model;

import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@XmlSeeAlso(InfoModel.class)
public class XMLsaver {

    /**
     *
     * @param data
     * @param fileName
     * @return
     */
    public static boolean saveToXML(Object data, String fileName){
        try {

            // Если передали класс, расширяющий List, обёртываем
            if (!(data instanceof List)){
                List temp = FXCollections.observableArrayList();
                temp.add(data);
                data = temp;
            }

            // Обёртываем наши данные об адресатах.
            ListWrapper wrapper = new ListWrapper();
            wrapper.setList((List)data);

            JAXBContext context = JAXBContext.newInstance(ListWrapper.class);
            Marshaller m = context.createMarshaller();
            // устанавливаем флаг для читабельного вывода XML в JAXB
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Открываем файл
            FileOutputStream file = new FileOutputStream(fileName);


            // Маршаллируем и сохраняем XML в файл.
            m.marshal(wrapper, file);

            // Закрываем файл
            file.close();

            return true;
        } catch (Exception e) { // catches ANY exception
            return false;
        }
    }

    public static List loadFromXML(String fileName){
        try {
            JAXBContext context = JAXBContext.newInstance(ListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Открываем файл
            FileInputStream file = new FileInputStream(fileName);

            // Чтение XML из файла и демаршализация.
            ListWrapper result = (ListWrapper) um.unmarshal(file);

            // Закрываем файл
            file.close();

            return result.getList();
        } catch (Exception e) { // catches ANY exception
            return null;
        }
    }

    /**
     * Сохраняет список объектов InfoModel из файла InfoModels.xml
     * @return
     */
    public boolean saveInfoModelsToXMLFile(ObservableList<InfoModel> infoData) {
        try {
//            JAXBContext context = JAXBContext.newInstance(InfoModelListWrapper.class);
//
//            // Обёртываем наши данные об адресатах.
//            InfoModelListWrapper wrapper = new InfoModelListWrapper();
//            wrapper.setInfoModels(infoData);
//
////            this.saveToXML(wrapper,"InfoModels.xml",context);

            return true;
        } catch (Exception e) { // catches ANY exception
            return false;
        }
    }
    /**
     * Загружает список объектов InfoModel из файла InfoModels.xml
     * @return
//     */
//    private ObservableList<InfoModel> loadInfoModelsFromXMLFile(){
//        try {
//            JAXBContext context = JAXBContext.newInstance(InfoModelListWrapper.class);
//            Unmarshaller um = context.createUnmarshaller();
//
//            // Открываем файл
//            FileInputStream file = new FileInputStream("InfoModels.xml");
//
//            // Чтение XML из файла и демаршализация.
//            InfoModelListWrapper wrapper = (InfoModelListWrapper) um.unmarshal(file);
//
//            ObservableList<InfoModel> infoDataReaded = FXCollections.observableArrayList();
//
//            infoDataReaded.addAll(wrapper.getInfoModels());
//
//
//            file.close();
//
//            return infoDataReaded;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

}
