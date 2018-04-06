package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlType;
import java.util.function.Predicate;

@XmlType(propOrder = {"fullName", "count", "parentApparatus"})
public class TableViewAbonent {
    public static String XML_FILENAME = "Abonents.xml";

    private SimpleStringProperty fullName;
    private SimpleIntegerProperty count;
    private SimpleIntegerProperty count_used;
    private SimpleBooleanProperty choose;
    private SimpleStringProperty parentApparatus;

    public TableViewAbonent(String fullName, Integer count, Integer count_used, String parentApparatus, boolean choose) {
        this.fullName = new SimpleStringProperty(fullName);
        this.count = new SimpleIntegerProperty(count);
        this.count_used = new SimpleIntegerProperty(count_used);
        this.choose = new SimpleBooleanProperty(choose);
        this.parentApparatus = new SimpleStringProperty(parentApparatus);
    }

    public TableViewAbonent(String fullName, Integer count, String parentApparatus) {
        this.fullName = new SimpleStringProperty(fullName);
        this.count = new SimpleIntegerProperty(count);
        this.count_used = new SimpleIntegerProperty(count);
        this.choose = new SimpleBooleanProperty(false);
        this.parentApparatus = new SimpleStringProperty(parentApparatus);
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

    public int getCount_used() {
        return count_used.get();
    }

    public SimpleIntegerProperty count_usedProperty() {
        return count_used;
    }

    public void setCount_used(int count_used) {
        this.count_used.set(count_used);
    }

    public String getParentApparatus() {
        return parentApparatus.get();
    }

    public SimpleStringProperty parentApparatusProperty() {
        return parentApparatus;
    }

    public void setParentApparatus(String parentApparatus) {
        this.parentApparatus.set(parentApparatus);
    }

    @Override
    public String toString() {
        return getFullName();
    }

    /**
     * Фильтрует по названию аппаратной
     * @param appName
     * @param infoData
     * @return FilteredList&lt;TableViewAbonent&gt;
     */
    public static ObservableList<TableViewAbonent> filterInfoModelByApparatusName(String appName, ObservableList<TableViewAbonent> infoData){
        return infoData.filtered(new Predicate<TableViewAbonent>() {
            @Override
            public boolean test(TableViewAbonent abonent) {
                if (abonent.getParentApparatus().equals(appName)) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}
