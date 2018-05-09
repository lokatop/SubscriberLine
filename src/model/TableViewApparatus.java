package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableViewApparatus {

    private SimpleStringProperty fullName;
    private SimpleIntegerProperty count;
    private SimpleBooleanProperty choose;
    private SimpleStringProperty data;

    public TableViewApparatus(String fullName, Integer count, boolean choose, String data) {
        this.fullName = new SimpleStringProperty(fullName);
        this.count = new SimpleIntegerProperty(count);
        this.choose = new SimpleBooleanProperty(choose);
        this.data = new SimpleStringProperty(data);
    }

    public TableViewApparatus(String fullName) {
        this.fullName = new SimpleStringProperty(fullName);
        this.count = new SimpleIntegerProperty(1);
        this.choose = new SimpleBooleanProperty(false);
        this.data = new SimpleStringProperty("");
    }
    public TableViewApparatus(String fullName, String data) {
        this.fullName = new SimpleStringProperty(fullName);
        this.count = new SimpleIntegerProperty(1);
        this.choose = new SimpleBooleanProperty(false);
        this.data = new SimpleStringProperty(data);
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

    public int getCount() {
        return count.get();
    }

    public SimpleIntegerProperty countProperty() {
        return count;
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    @Override
    public String toString() {
        return getFullName();
    }

    public String getData() {
        return data.get();
    }

    public SimpleStringProperty dataProperty() {
        return data;
    }

    public void setData(String data) {
        this.data.set(data);
    }
}
