package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlType;

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
    public void increaseCount_used() {
        this.count_used.set(this.count_used.get() + 1);
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
     *
     * @param appName
     * @param infoData
     * @return FilteredList&lt;TableViewAbonent&gt;
     */
    public static ObservableList<TableViewAbonent> filterInfoModelByApparatusName(String appName, ObservableList<TableViewAbonent> infoData) {
        ObservableList<TableViewAbonent> result = FXCollections.observableArrayList();

        for (TableViewAbonent abonent : infoData) {
            if (abonent.getParentApparatus().equals(appName)) {
                    result.add(abonent);
                }
        }

        return result;

//        return infoData.filtered(new Predicate<TableViewAbonent>() {
//            @Override
//            public boolean test(TableViewAbonent abonent) {
//                if (abonent.getParentApparatus().equals(appName)) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });
    }

    @Override
    public boolean equals(Object obj) {

        boolean is_equal = false;

        boolean coudition_1 = false;
        boolean coudition_2 = false;
        boolean coudition_3 = false;
        boolean coudition_4 = false;
        boolean coudition_5 = false;

        if (obj.getClass().equals(this.getClass())){
            if (this.getFullName() != null && ((TableViewAbonent) obj).getFullName() != null)
                if (this.getFullName().equals(((TableViewAbonent) obj).getFullName()))
                    coudition_1 = true;
                else
                    coudition_1 = false;
            else if (this.getFullName() == null && ((TableViewAbonent) obj).getFullName() == null)
                coudition_1 = true;

            if (this.getParentApparatus() != null && ((TableViewAbonent) obj).getParentApparatus() != null)
                if (this.getParentApparatus().equals(((TableViewAbonent) obj).getParentApparatus()))
                    coudition_2 = true;
                else
                    coudition_2 = false;
            else if (this.getParentApparatus() == null && ((TableViewAbonent) obj).getParentApparatus() == null)
                coudition_2 = true;

            if (this.getCount() == ((TableViewAbonent) obj).getCount())
                coudition_3 = true;

            if (this.getCount_used() == ((TableViewAbonent) obj).getCount_used())
                coudition_4 = true;

            if (this.isChoose() == ((TableViewAbonent) obj).isChoose())
                coudition_5 = true;
        }

        is_equal = coudition_1 && coudition_2 && coudition_3 && coudition_4 && coudition_5;

        return is_equal;
    }
}
