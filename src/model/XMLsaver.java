package model;

import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
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
import java.util.List;

@XmlSeeAlso({InfoModel.class, ChooseModel.class})
public class XMLsaver {

    private static String PATH_RESOURCE = "resource";
    private static String PATH_IMAGES = "/images";

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

            if (createOrClearImagesFolder(context.getClass())) {

                // Открываем файл
                FileOutputStream file = new FileOutputStream(fileName);

                // Маршаллируем и сохраняем XML в файл.
                m.marshal(wrapper, file);

                // Закрываем файл
                file.close();

                return true;
            } else {
                return false;
            }
        } catch (Exception e) { // catches ANY exception
            return false;
        }
    }

    private static boolean createOrClearImagesFolder(Class context) {
        try{
//        String app_path = Paths.get(context.getClass().getResource("/").toURI()).toString();

        File resourceFolder = new File( PATH_RESOURCE);
        File imagesFolder = new File( PATH_RESOURCE + PATH_IMAGES);

        if (!resourceFolder.exists()) resourceFolder.mkdir();
        if (!imagesFolder.exists()) imagesFolder.mkdir();

        if (imagesFolder.listFiles().length != 0)
            for (File f : imagesFolder.listFiles())
                if (f.isFile()) f.delete();

        return true;
        } catch (ExceptionHasMessage e){
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
//            String path = Paths.get(context.getClass().getResource("/").toURI()).toString();
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
//                    String path = Paths.get(this.getClass().getResource(PATH_RESOURCE + PATH_IMAGES).toURI()).toString();
                    img = new Image("file:" + PATH_RESOURCE + PATH_IMAGES + "/" + data);
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
//            String path = Paths.get(this.getClass().getResource(PATH_RESOURCE+PATH_IMAGES).toURI()).toString();
            File folder = new File(PATH_RESOURCE + PATH_IMAGES);
            String filename = System.currentTimeMillis() + ".png";
            File outputFile = new File(folder.toString() + "/" + filename);
            BufferedImage bImage = SwingFXUtils.fromFXImage(v, null);
            try {
                outputFile.createNewFile();
                ImageIO.write(bImage, "png", outputFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return filename;
        }
    }
}
