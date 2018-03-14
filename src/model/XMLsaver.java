package model;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.Marshaller;
//import javax.xml.bind.Unmarshaller;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.util.List;
//
public class XMLsaver {
//
//    /**
//     *
//     * @param data Обрнутые данные
//     * @param fileName
//     * @param context  Например: JAXBContext.newInstance(InfoModelListWrapper.class)
//     * @return
//     */
//    public static boolean saveToXML(Object data, String fileName, JAXBContext context){
//        try {
//            Marshaller m = context.createMarshaller();
//            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//            // Открываем файл
//            FileOutputStream file = new FileOutputStream(fileName);
//
//            // Маршаллируем и сохраняем XML в файл.
//            m.marshal(data, file);
//
//            // Закрываем файл
//            file.close();
//
//            return true;
//        } catch (Exception e) { // catches ANY exception
//            return false;
//        }
//    }
//
//    public Object loadFromXML(Object data, String fileName, JAXBContext context){
//        try {
//            Unmarshaller um = context.createUnmarshaller();
//
//            // Открываем файл
//            FileInputStream file = new FileInputStream(fileName);
//
//            // Чтение XML из файла и демаршализация.
//            Object result = um.unmarshal(file);
//
//            // Закрываем файл
//            file.close();
//
//            return result;
//        } catch (Exception e) { // catches ANY exception
//            return null;
//        }
//    }
//
//    /**
//     * Сохраняет список объектов InfoModel из файла InfoModels.xml
//     * @return
//     */
//    public boolean saveInfoModelsToXMLFile(ObservableList<InfoModel> infoData) {
//        try {
//            JAXBContext context = JAXBContext.newInstance(InfoModelListWrapper.class);
//
//            // Обёртываем наши данные об адресатах.
//            InfoModelListWrapper wrapper = new InfoModelListWrapper();
//            wrapper.setInfoModels(infoData);
//
//            this.saveToXML(wrapper,"InfoModels.xml",context);
//
//            return true;
//        } catch (Exception e) { // catches ANY exception
//            return false;
//        }
//    }
//    /**
//     * Загружает список объектов InfoModel из файла InfoModels.xml
//     * @return
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
//
}
