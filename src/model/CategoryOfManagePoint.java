package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CategoryOfManagePoint {
    private IntegerProperty id;
    private StringProperty title;
    private IntegerProperty militaryPartId;

    public CategoryOfManagePoint(String title, Integer militaryPartId) {
        this.title = new SimpleStringProperty(title);
        this.militaryPartId = new SimpleIntegerProperty(militaryPartId);
    }

    public CategoryOfManagePoint(Integer id, String title, Integer militaryPartId) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.militaryPartId = new SimpleIntegerProperty(militaryPartId);
    }
}
