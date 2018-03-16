package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

// определяем корневой элемент
//@XmlRootElement(name = "InfoModel")
// определяем последовательность тегов в XML
@XmlType(propOrder = {"title", "type", "description", "imageURL"})
public class InfoModel {
    private final StringProperty title;
    private final StringProperty type;
    private final StringProperty description;
    private final StringProperty imageURL; // TODO:Возможно, заменить на изображение

    /**
     * Конструктор по умолчанию.
     */
    public InfoModel() {
        this(null, null, null, null);
    }

    /**
     * Конструктор с некоторыми начальными данными.
     *
     */
    public InfoModel(String title, String type, String description, String imageURL) {
        this.title = new SimpleStringProperty(title);
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
        this.imageURL = new SimpleStringProperty(imageURL);
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

    @XmlElement
    public String getImageURL() {
        return imageURL.get().toString();
    }

    public void setImageURL(String imageURL) {
        this.imageURL.set(imageURL);
    }

    public StringProperty imageURLProperty() {
        return imageURL;
    }

// Для ComboBox'ов (Добавляем список моделей, получаем список заголовков)
    @Override
    public String toString()  {
        return this.getTitle();
    }
}
