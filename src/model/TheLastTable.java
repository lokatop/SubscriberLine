package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TheLastTable {
    private SimpleStringProperty  officialPerson;
    private SimpleStringProperty  typeAbon      ;
    private SimpleStringProperty  appFrom1      ;
    private SimpleStringProperty  typeCable     ;
    private SimpleStringProperty  appFrom2      ;
    private SimpleIntegerProperty lengthCable   ;

    public TheLastTable(String officialPerson, String typeAbon, String appFrom1, String typeCable,
                        String appFrom2, int lengthCable) {
        this.officialPerson = new SimpleStringProperty(officialPerson);
        this.typeAbon       = new SimpleStringProperty(typeAbon);
        this.appFrom1       = new SimpleStringProperty(appFrom1);
        this.typeCable      = new SimpleStringProperty(typeCable);
        this.appFrom2       = new SimpleStringProperty(appFrom2);
        this.lengthCable    = new SimpleIntegerProperty(lengthCable);
    }
    public TheLastTable(String officialPerson, String typeAbon) {
        this.officialPerson = new SimpleStringProperty(officialPerson);
        this.typeAbon       = new SimpleStringProperty(typeAbon);
        this.appFrom1       = new SimpleStringProperty();
        this.typeCable      = new SimpleStringProperty();
        this.appFrom2       = new SimpleStringProperty();
        this.lengthCable    = new SimpleIntegerProperty();
    }

    public String getOfficialPerson() {
        return officialPerson.get();
    }

    public SimpleStringProperty officialPersonProperty() {
        return officialPerson;
    }

    public void setOfficialPerson(String officialPerson) {
        this.officialPerson.set(officialPerson);
    }

    public String getTypeAbon() {
        return typeAbon.get();
    }

    public SimpleStringProperty typeAbonProperty() {
        return typeAbon;
    }

    public void setTypeAbon(String typeAbon) {
        this.typeAbon.set(typeAbon);
    }

    public String getAppFrom1() {
        return appFrom1.get();
    }

    public SimpleStringProperty appFrom1Property() {
        return appFrom1;
    }

    public void setAppFrom1(String appFrom1) {
        this.appFrom1.set(appFrom1);
    }

    public String getTypeCable() {
        return typeCable.get();
    }

    public SimpleStringProperty typeCableProperty() {
        return typeCable;
    }

    public void setTypeCable(String typeCable) {
        this.typeCable.set(typeCable);
    }

    public String getAppFrom2() {
        return appFrom2.get();
    }

    public SimpleStringProperty appFrom2Property() {
        return appFrom2;
    }

    public void setAppFrom2(String appFrom2) {
        this.appFrom2.set(appFrom2);
    }

    public int getLengthCable() {
        return lengthCable.get();
    }

    public SimpleIntegerProperty lengthCableProperty() {
        return lengthCable;
    }

    public void setLengthCable(int lengthCable) {
        this.lengthCable.set(lengthCable);
    }
}
