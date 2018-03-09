package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Properties;

public class InfoModel {
    private final StringProperty title;
    private final StringProperty type;
    private final StringProperty description;

    /**
     * Конструктор по умолчанию.
     */
    public InfoModel() {
        this(null, null, null);
    }

    /**
     * Конструктор с некоторыми начальными данными.
     *
     */
    public InfoModel(String title, String type, String description) {
        this.title = new SimpleStringProperty(title);
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
    }

    public String getTitle() {
        return title.get().toString();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getType() {
        return type.get().toString();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }
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
