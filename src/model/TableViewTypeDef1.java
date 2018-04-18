package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.function.Predicate;

@XmlType(propOrder = {"nameOfOfficial", "equipment","boolen"})
public class TableViewTypeDef1 {
    private SimpleStringProperty nameOfOfficial;
    private SimpleStringProperty equipment;
    private SimpleBooleanProperty boolen;

    public TableViewTypeDef1(){this(null,null,false);}

    public TableViewTypeDef1(String nameOfOfficial, String equipment, Boolean boolen) {
        this.nameOfOfficial = new SimpleStringProperty(nameOfOfficial);
        this.equipment = new SimpleStringProperty(equipment);
        this.boolen = new SimpleBooleanProperty(boolen);
    }

    public TableViewTypeDef1(String nameOfOfficial,String equipment) {
        this.nameOfOfficial = new SimpleStringProperty(nameOfOfficial);
        this.equipment = new SimpleStringProperty(equipment);
        this.boolen = new SimpleBooleanProperty(false);
    }
    @XmlElement
    public String getNameOfOfficial() {
        return nameOfOfficial.get();
    }

    public SimpleStringProperty nameOfOfficialProperty() {
        return nameOfOfficial;
    }

    public void setNameOfOfficial(String nameOfOfficial) {
        this.nameOfOfficial.set(nameOfOfficial);
    }
    @XmlElement
    public String getEquipment() {
        return equipment.get();
    }

    public SimpleStringProperty equipmentProperty() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment.set(equipment);
    }
    @XmlElement
    public boolean isBoolen() {
        return boolen.get();
    }

    public SimpleBooleanProperty boolenProperty() {
        return boolen;
    }

    public void setBoolen(boolean boolen) {
        this.boolen.set(boolen);
    }

    /**
     * Фильтрует по названию аппаратной
     * @param appName
     * @param infoData
     * @return FilteredList&lt;TableViewAbonent&gt;
     */
    public static ObservableList<TableViewTypeDef1> filterByDefCategoryName(String appName, ObservableList<TableViewTypeDef1> infoData){
        return infoData.filtered(new Predicate<TableViewTypeDef1>() {
            @Override
            public boolean test(TableViewTypeDef1 chooseCategory) {
                if (chooseCategory.getNameOfOfficial().equals(appName)) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}
