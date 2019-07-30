package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableCableModel {
    private final IntegerProperty cableId;
    private final StringProperty name;
    private final IntegerProperty length;
    private final IntegerProperty count;

    public TableCableModel(Integer cableId, String name, Integer count, Integer length) {
        this.cableId = new SimpleIntegerProperty(cableId);
        this.name = new SimpleStringProperty(name);
        this.count = new SimpleIntegerProperty(count);
        this.length = new SimpleIntegerProperty(length);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public int getCount() {
        return count.get();
    }

    public void setCount(Integer count) {
        this.count.set(count);
    }

    public IntegerProperty countProperty() {
        return count;
    }

    @Override
    public boolean equals(Object obj) {

        boolean condition_1 = false;
        boolean condition_2 = false;
        boolean condition_3 = false;
        boolean condition_4 = false;

        if (this.getName().equals(((TableCableModel) obj).getName()))
            condition_1 = true;
        if (this.getCount() == ((TableCableModel) obj).getCount())
            condition_2 = true;
        if (this.getCableId() == ((TableCableModel) obj).getCableId())
            condition_3 = true;
        if (this.getLength() == ((TableCableModel) obj).getLength())
            condition_4 = true;

        return condition_1 && condition_2 && condition_3 && condition_4;
    }

    public int getLength() {
        return length.get();
    }

    public IntegerProperty lengthProperty() {
        return length;
    }

    public int getCableId() {
        return cableId.get();
    }

    public IntegerProperty cableIdProperty() {
        return cableId;
    }
}
