package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

// определяем корневой элемент
//@XmlRootElement(name = "ChooseModel")
// определяем последовательность тегов в XML
@XmlType(propOrder = {"title", "type", "description"})
public class ChooseModel {

    public static String FILENAME_CHOOSEMODELS = "ChooseModels.xml";

    private final StringProperty title;
    private final StringProperty type;
    private final StringProperty description;

    /**
     * Конструктор по умолчанию.
     */
    public ChooseModel() {
        this(null, null, null);
    }

    /**
     * Конструктор с некоторыми начальными данными.
     *
     */
    public ChooseModel(String title, String type, String description) {
        this.title = new SimpleStringProperty(title);
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
    }
/*
    public ChooseModel(String title, String type) {
        this.title = new SimpleStringProperty(title);
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty("");
    }
*/
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

    // Для ComboBox'ов (Добавляем список моделей, получаем список заголовков)
    @Override
    public String toString()  {
        return this.getTitle();
    }
}
