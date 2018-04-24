package model;

import javafx.collections.ObservableList;

public class TableViewTypeDef1_many {

    private String nameOfOfficialPerson;
    private ObservableList<TableViewTypeDef1> tableViewTypeDef1;

    public TableViewTypeDef1_many(String nameOfOfficialPerson, ObservableList<TableViewTypeDef1> tableViewTypeDef1) {
        this.nameOfOfficialPerson = nameOfOfficialPerson;
        this.tableViewTypeDef1 = tableViewTypeDef1;
    }

    public String getNameOfOfficialPerson() {
        return nameOfOfficialPerson;
    }

    public void setNameOfOfficialPerson(String nameOfOfficialPerson) {
        this.nameOfOfficialPerson = nameOfOfficialPerson;
    }

    public ObservableList<TableViewTypeDef1> getTableViewTypeDef1() {
        return tableViewTypeDef1;
    }

    public void setTableViewTypeDef1(ObservableList<TableViewTypeDef1> tableViewTypeDef1) {
        this.tableViewTypeDef1 = tableViewTypeDef1;
    }
}
