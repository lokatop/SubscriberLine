package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MilitaryPart {
    private IntegerProperty id;
    private StringProperty title;

    public MilitaryPart(String title) {
        this.title = new SimpleStringProperty(title);
    }
    public MilitaryPart(Integer id, String title) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
    }

    @Override
    public String toString() {
        return title.get();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }
}
