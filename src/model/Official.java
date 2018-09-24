package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Official {
    private IntegerProperty id;
    private StringProperty title;
    private IntegerProperty militaryPartId;

    public Official(String title, Integer militaryPartId) {
        this.title = new SimpleStringProperty(title);
        this.militaryPartId = new SimpleIntegerProperty(militaryPartId);
    }

    public Official(Integer id, String title, Integer militaryPartId) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.militaryPartId = new SimpleIntegerProperty(militaryPartId);
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

    public int getMilitaryPartId() {
        return militaryPartId.get();
    }

    public IntegerProperty militaryPartIdProperty() {
        return militaryPartId;
    }

    public void setMilitaryPartId(int militaryPartId) {
        this.militaryPartId.set(militaryPartId);
    }

    @Override
    public String toString() {
        return title.get();
    }
}
