package model;

import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.transform.Result;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@XmlSeeAlso(InfoModel.class)
public class XMLsaver {

    /**
     * <p>Сохраняет список элементов в файла</p>
     * <p>Например:</p>
     * <p>ObservableList<InfoModel> infoData = FXCollections.observableArrayList();</p>
     * <p>infoData.add(new InfoModel("ТА-57","DS","Описание", "image.png"));</p>
     * <p>infoData.add(new InfoModel("ТА-88","DS","Описание", "image.png"));</p>
     * <p>XMLsaver.saveToXML(infoData, "InfoModels.xml");</p>
     * @param data одиночный элемент изи список (расширяющий List) элементов для сохранения
     * @param fileName путь к файлу (включая имя файла)
     * @return true/false - в зависимости от результата сохранения
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
            m.setAdapter(new ImageAdapter());
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

    /**
     * <p>Загружает список элементов из файла</p>
     * <p>Например:</p>
     * <p>ObservableList<InfoModel> infoData = FXCollections.observableArrayList();</p>
     * <p>infoData.addAll(XMLsaver.loadFromXML("InfoModels.xml"));</p>
     * @param fileName путь к файлу (включая имя файла)
     * @return List - содержит список загруженных элементов
     *
     */
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
            return FXCollections.emptyObservableList();
        }
    }


    public static class ImageAdapter extends XmlAdapter<String, Image> {

        @Override
        public Image unmarshal(String data) throws Exception {

            if (data == null || data.isEmpty()){
                return null;
            } else {
                Image img;
                try {
                    img = new Image(new ByteArrayInputStream(Base64.getDecoder().decode(data)));
                } catch (Exception e){
                    img = new Image("resource/noimage.png");
                }
                return img;
            }
        }

        @Override
        public String marshal(Image v) throws Exception {
            if (v == null) {
                return "";
            }
            BufferedImage bImg = SwingFXUtils.fromFXImage(v, null);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImg, "png", bos);

            return Base64.getEncoder().encodeToString(bos.toByteArray());
        }
    }
}
