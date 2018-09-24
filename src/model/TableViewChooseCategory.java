package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableViewChooseCategory {

    private IntegerProperty id;
    private SimpleStringProperty fullName;
    private SimpleBooleanProperty choose;

    public TableViewChooseCategory(Integer id, String fullName, boolean choose) {
        this.id = new SimpleIntegerProperty(id);
        this.fullName = new SimpleStringProperty(fullName);
        this.choose = new SimpleBooleanProperty(choose);
    }

    public TableViewChooseCategory(String fullName) {
        this.id = new SimpleIntegerProperty(0);
        this.fullName = new SimpleStringProperty(fullName);
        this.choose = new SimpleBooleanProperty(false);
    }

    public String getFullName() {
        return fullName.get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public boolean isChoose() {
        return choose.get();
    }

    public SimpleBooleanProperty chooseProperty() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose.set(choose);
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
}
