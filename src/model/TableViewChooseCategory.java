package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.function.Predicate;


@XmlType(propOrder = {"fullName", "choose"})
public class TableViewChooseCategory {

    private SimpleStringProperty fullName;
    private SimpleBooleanProperty choose;

    public TableViewChooseCategory(){this(null,false);}

    public TableViewChooseCategory(String fullName, boolean choose) {
        this.fullName = new SimpleStringProperty(fullName);
        this.choose = new SimpleBooleanProperty(choose);
    }

    public TableViewChooseCategory(String fullName) {
        this.fullName = new SimpleStringProperty(fullName);
        this.choose = new SimpleBooleanProperty(false);
    }

    @XmlElement
    public String getFullName() {
        return fullName.get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    @XmlElement
    public boolean isChoose() {
        return choose.get();
    }

    public SimpleBooleanProperty chooseProperty() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose.set(choose);
    }

    /**
     * Фильтрует по названию аппаратной
     * @param appName
     * @param infoData
     * @return FilteredList&lt;TableViewAbonent&gt;
     */
    public static ObservableList<TableViewChooseCategory> filterByChooseCategoryName(String appName, ObservableList<TableViewChooseCategory> infoData){
        return infoData.filtered(new Predicate<TableViewChooseCategory>() {
            @Override
            public boolean test(TableViewChooseCategory chooseCategory) {
                if (chooseCategory.getFullName().equals(appName)) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}
