package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;

public class TableViewChooseCategory {

    private SimpleStringProperty fullName;
    private SimpleBooleanProperty choose;

    public TableViewChooseCategory(String fullName, boolean choose) {
        this.fullName = new SimpleStringProperty(fullName);
        this.choose = new SimpleBooleanProperty(choose);
    }

    public TableViewChooseCategory(String fullName) {
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
}
