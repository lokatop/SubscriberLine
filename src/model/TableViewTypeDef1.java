package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.function.Predicate;

public class TableViewTypeDef1 {
    private SimpleStringProperty nameOfOfficial;
    private SimpleStringProperty equipment;
    private SimpleBooleanProperty boolen;

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

    public boolean isBoolen() {
        return boolen.get();
    }

    public SimpleBooleanProperty boolenProperty() {
        return boolen;
    }

    public void setBoolen(boolean boolen) {
        this.boolen.set(boolen);
    }

    public static ObservableList<TableViewTypeDef1> filterByNameOfOfficial(String official, ObservableList<TableViewTypeDef1> data){
        ObservableList<TableViewTypeDef1> result = FXCollections.observableArrayList();

        for (TableViewTypeDef1 tableViewTypeDef1 : data)
            if(tableViewTypeDef1.getNameOfOfficial().equals(official))
                result.add(tableViewTypeDef1);

        return result;
    }
}
