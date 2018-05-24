package model;

import controllers.ControllerInformationFrameChangeDialogForApparatus;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.image.Image;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.*;
import java.util.function.Predicate;

// определяем корневой элемент
//@XmlRootElement(name = "InfoModel")
// определяем последовательность тегов в XML
@XmlType(propOrder = {"title", "type", "description", "image", "data", "cables"})
public class InfoModel {
    public static String FILENAME_INFOMODELS = "InfoModels.xml";

    private final StringProperty title;
    private final StringProperty type;
    private final StringProperty description;
    private final ObjectProperty<Image> image;
    private final StringProperty data;
    private final StringProperty cables;

    /**
     * Список сокращений категоий
     */
    public static String[] CATEGORIES = {"DS","ZAS","ARM","CableAndOther","AOZU","ATZU"};
    /**
     * Список описаний категоий
     */
    public static String[] CATEGORIES_DESC = {"Телефонные аппараты ДС","Телефонные аппараты ЗАС","Автоматизированные рабочие места","Кабель применяемый для развертывания абонентской сети","Аппаратные тактического звена управления","Аппаратные оперативного звена управления"};

    /**
     * Конструктор по умолчанию.
     */
    public InfoModel() {
        this(null, null, null, null, null, null);
    }
    public InfoModel(String title, String type, String description, Image image) {
        this(title, type, description, image, null, null);
    }

    /**
     * Конструктор с некоторыми начальными данными.
     *
     */
    public InfoModel(String title, String type, String description, Image image, String data, String cables) {
        this.title = new SimpleStringProperty(title);
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
        this.image = new SimpleObjectProperty<Image>(image);
        this.data = new SimpleStringProperty(data);
        this.cables = new SimpleStringProperty(cables);
    }

    @XmlElement
    public String getTitle() {
        return title.get().toString();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    @XmlElement
    public String getType() {
        return type.get().toString();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }

    @XmlElement
    public String getDescription() {
        return description.get().toString();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    @XmlJavaTypeAdapter(XMLsaver.ImageAdapter.class)
    public Image getImage() {
        return image.get();
    }

    public void setImage(Image image) {
        this.image.set(image);
    }

    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    public String getData() {
        return data.get();
    }

    public void setData(String data){
        this.data.set(data);
    }

    public StringProperty dataProperty() {
        return data;
    }


    // Для ComboBox'ов (Добавляем список моделей, получаем список заголовков)
    @Override
    public String toString()  {
        return this.getTitle();
    }

    /**
     * Фильтрует модели по типу
     * @param type
     * @param infoData
     * @return FilteredList&lt;InfoModel&gt;
     */
    public static ObservableList<InfoModel> filterInfoModelByType(String type, ObservableList<InfoModel> infoData){
        return infoData.filtered(new Predicate<InfoModel>() {
            @Override
            public boolean test(InfoModel infoModel) {
                // Если несколько разделены запятой, то хренацим массив да цикл
                if(infoModel.getType().contains(",")){
                    String[] types = infoModel.getType().split(",");
                    for (int i = 0; i < types.length; i++) {
                        if (type.equals(types[i])) {
                            return true;
                        }
                    }
                } else {
                    if (infoModel.getType().equals(type)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });
    }

    public String getCables() {
        return cables.get();
    }

    public StringProperty cablesProperty() {
        return cables;
    }

    public void setCables(String cables){
        this.cables.set(cables);
    }

    /**
     * Результат в виде<br>
     *     List&lt;Map&lt;String, String&gt;&gt; data = app.getDataApparatus(); <br>
     *     data.get(0).get("name");<br>
     *     data.get(0).get("count"); // Это строка! Для перевода в число: Integer.parseInt(data.get(0).get("count"));
     * @return
     */
    public List<Map< String, String >> parseCables(){
        List<Map< String, String >> result = new ArrayList<>();
        if (this.getData() != null) {
            String[] substrs;
            substrs = this.getCables().split(";");
            for (String substr : substrs) {

                String strTA[] = substr.split(":");
                Map<String, String> toAdd = new HashMap<String, String>();
                toAdd.put("name", strTA[0]);
                toAdd.put("count", strTA[1]);
                result.add(toAdd);
            }
        }
        return result;
    }
}
