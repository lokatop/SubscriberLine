package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TheLastTableCableBox {
    private SimpleStringProperty cable;

    public TheLastTableCableBox(String cable) {
        this.cable = new SimpleStringProperty(cable);
    }

    public String getCable() {
        return cable.get();
    }

    public SimpleStringProperty cableProperty() {
        return cable;
    }

    public void setCable(String cable) {
        this.cable.set(cable);
    }
}
