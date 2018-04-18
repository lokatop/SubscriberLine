package model;

import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.List;

@XmlSeeAlso({InfoModel.class, ChooseModel.class, TheLastTable.class, TableViewTypeDef1.class})
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
            String path = Paths.get(context.getClass().getResource("/").toURI()).toString();
            FileOutputStream file = new FileOutputStream(path + "/" + fileName);

            // Чистим папку для изображений
            File folder = new File(path + "/resource/images");
            if (folder.exists() && folder.listFiles().length != 0)
                for (File f : folder.listFiles())
                    if (f.isFile()) f.delete();

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
            String path = Paths.get(context.getClass().getResource("/").toURI()).toString();
            FileInputStream file = new FileInputStream(path + "/" + fileName);

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
                    img = new Image(data);
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

            String path = Paths.get(this.getClass().getResource("/resource").toURI()).toString();
            File folder = new File(path + "/images");
            File outputFile = new File(folder.toString() + "/" + System.currentTimeMillis() + ".png");
            BufferedImage bImage = SwingFXUtils.fromFXImage(v, null);
            try {
                if (!folder.exists()){
                    folder.mkdir();
                }
                outputFile.createNewFile();
                ImageIO.write(bImage, "png", outputFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
//            BufferedImage bImg = SwingFXUtils.fromFXImage(v, null);
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            ImageIO.write(bImg, "png", bos);

            return outputFile.toURI().toString();
        }
    }
}
