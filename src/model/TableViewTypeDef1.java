package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableViewTypeDef1 {
    private SimpleStringProperty nameOfOfficial;
    private SimpleStringProperty equipment;
    private SimpleBooleanProperty checked;

    public TableViewTypeDef1(String nameOfOfficial, String equipment, Boolean boolen) {
        this.nameOfOfficial = new SimpleStringProperty(nameOfOfficial);
        this.equipment = new SimpleStringProperty(equipment);
        this.checked = new SimpleBooleanProperty(boolen);
    }

    public TableViewTypeDef1(String nameOfOfficial,String equipment) {
        this.nameOfOfficial = new SimpleStringProperty(nameOfOfficial);
        this.equipment = new SimpleStringProperty(equipment);
        this.checked = new SimpleBooleanProperty(false);
    }

    public String getNameOfOfficial() {
        return nameOfOfficial.get();
    }

    public SimpleStringProperty nameOfOfficialProperty() {
        return nameOfOfficial;
    }

    public void setNameOfOfficial(String nameOfOfficial) {
        this.nameOfOfficial.set(nameOfOfficial);
    }

    public String getEquipment() {
        return equipment.get();
    }

    public SimpleStringProperty equipmentProperty() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment.set(equipment);
    }

    public boolean isChecked() {
        return checked.get();
    }

    public SimpleBooleanProperty checkedProperty() {
        return checked;
    }

    public void setChecked(boolean boolen) {
        this.checked.set(boolen);
    }

    public static ObservableList<TableViewTypeDef1> filterByNameOfOfficial(String official, ObservableList<TableViewTypeDef1> data){
        ObservableList<TableViewTypeDef1> result = FXCollections.observableArrayList();

        for (TableViewTypeDef1 tableViewTypeDef1 : data)
            if(tableViewTypeDef1.getNameOfOfficial().equals(official))
                result.add(tableViewTypeDef1);

        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) return false;

        boolean condition_1 = false;
        boolean condition_2 = false;
        boolean condition_3 = false;

        if (this.getNameOfOfficial().equals(((TableViewTypeDef1)obj).getNameOfOfficial()))
            condition_1 = true;
        if (this.getEquipment().equals(((TableViewTypeDef1)obj).getEquipment()))
            condition_2 = true;
        if (this.isChecked() == ((TableViewTypeDef1)obj).isChecked())
            condition_3 = true;


        return condition_1 && condition_2 && condition_3;
    }
}
