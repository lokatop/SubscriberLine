package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class TheLastTable {
    private SimpleStringProperty officialPerson;
    private SimpleStringProperty typeAbon;
    private SimpleStringProperty appFrom1;
    private SimpleStringProperty typeCable;
    private SimpleIntegerProperty lengthCable;

    public TheLastTable(String officialPerson, String typeAbon, String appFrom1, String typeCable,
                        String appFrom2, int lengthCable) {
        this.officialPerson = new SimpleStringProperty(officialPerson);
        this.typeAbon = new SimpleStringProperty(typeAbon);
        this.appFrom1 = new SimpleStringProperty(appFrom1);
        this.typeCable = new SimpleStringProperty(typeCable);
        this.lengthCable = new SimpleIntegerProperty(lengthCable);
    }

    public TheLastTable(String officialPerson, String typeAbon) {
        this.officialPerson = new SimpleStringProperty(officialPerson);
        this.typeAbon = new SimpleStringProperty(typeAbon);
        this.appFrom1 = new SimpleStringProperty();
        this.typeCable = new SimpleStringProperty();
        this.lengthCable = new SimpleIntegerProperty();
    }

    public TheLastTable(String officialPerson, String typeAbon, String appFrom1) {
        this.officialPerson = new SimpleStringProperty(officialPerson);
        this.typeAbon = new SimpleStringProperty(typeAbon);
        this.appFrom1 = new SimpleStringProperty(appFrom1);
        this.typeCable = new SimpleStringProperty();
        this.lengthCable = new SimpleIntegerProperty();
    }

    public TheLastTable(TheLastTable item) {
        this.officialPerson = new SimpleStringProperty(item.getOfficialPerson());
        this.typeAbon = new SimpleStringProperty(item.getTypeAbon());
        this.appFrom1 = new SimpleStringProperty(item.getAppFrom1());
        this.typeCable = new SimpleStringProperty(item.getTypeCable());
        this.lengthCable = new SimpleIntegerProperty(item.getLengthCable());
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

    public int getLengthCable() {
        return lengthCable.get();
    }

    public SimpleIntegerProperty lengthCableProperty() {
        return lengthCable;
    }

    public void setLengthCable(int lengthCable) {
        this.lengthCable.set(lengthCable);
    }

    public static ObservableList<TheLastTable> filterByOfficialPerson(String officialPerson, ObservableList<TheLastTable> data) {
        ObservableList<TheLastTable> result = FXCollections.observableArrayList();

        for (TheLastTable item : data) {
            if (item.getOfficialPerson().equals(officialPerson)) {
                result.add(item);
            }
        }

        return result;

//        return data.filtered(new Predicate<TheLastTable>() {
//            @Override
//            public boolean test(TheLastTable item) {
//                if (item.getOfficialPerson().equals(officialPerson)) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });
    }
    public static ObservableList<TheLastTable> filterFromApp(String appFrom, ObservableList<TheLastTable> data) {
        ObservableList<TheLastTable> result = FXCollections.observableArrayList();

        for (TheLastTable item : data) {
            if (item.getAppFrom1().equals(appFrom)) {
                result.add(item);
            }
        }

        return result;

//        return data.filtered(new Predicate<TheLastTable>() {
//            @Override
//            public boolean test(TheLastTable item) {
//                if (item.getAppFrom1().equals(appFrom)) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });
    }

    public static ObservableList<TheLastTable> filterByTypeAbon(String typeAbon, ObservableList<TheLastTable> data) {
        ObservableList<TheLastTable> result = FXCollections.observableArrayList();

        for (TheLastTable item : data) {
            if (item.getTypeAbon().equals(typeAbon)) {
                result.add(item);
            }
        }

        return result;
//        return data.filtered(new Predicate<TheLastTable>() {
//            @Override
//            public boolean test(TheLastTable item) {
//                if (item.getTypeAbon().equals(typeAbon)) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });
    }

    public static ArrayList<String> getAllOfficialPerson(ObservableList<TheLastTable> data) {

        ArrayList<String> result = new ArrayList<String>();

        for (TheLastTable item : data) {
            if (!result.contains(item.getOfficialPerson()))
                result.add(item.getOfficialPerson());
        }

        return result;
    }

    public static ArrayList<String> getAllTypeAbon(ObservableList<TheLastTable> data) {

        ArrayList<String> result = new ArrayList<String>();

        for (TheLastTable item : data) {
            if (!result.contains(item.getTypeAbon()))
                result.add(item.getTypeAbon());
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {

        boolean is_equal = false;

        boolean condition_1 = false;
        boolean condition_2 = false;
        boolean condition_3 = false;
        boolean condition_4 = false;
        boolean condition_5 = false;

        if (obj.getClass().equals(this.getClass())) {
            if (this.getOfficialPerson() != null && ((TheLastTable) obj).getOfficialPerson() != null)
                if (this.getOfficialPerson().equals(((TheLastTable) obj).getOfficialPerson()))
                    condition_1 = true;
                else
                    condition_1 = false;
            else if (this.getOfficialPerson() == null && ((TheLastTable) obj).getOfficialPerson() == null)
                condition_1 = true;

            if (this.getAppFrom1() != null && ((TheLastTable) obj).getAppFrom1() != null)
                if (this.getAppFrom1().equals(((TheLastTable) obj).getAppFrom1()))
                    condition_2 = true;
                else
                    condition_2 = false;
            else if (this.getAppFrom1() == null && ((TheLastTable) obj).getAppFrom1() == null)
                condition_2 = true;

            if (this.getTypeAbon() != null && ((TheLastTable) obj).getTypeAbon() != null)
                if (this.getTypeAbon().equals(((TheLastTable) obj).getTypeAbon()))
                    condition_3 = true;
                else
                    condition_3 = false;
            else if (this.getTypeAbon() == null && ((TheLastTable) obj).getTypeAbon() == null)
                condition_3 = true;

            if (this.getTypeCable() != null && ((TheLastTable) obj).getTypeCable() != null)
                if (this.getTypeCable().equals(((TheLastTable) obj).getTypeCable()))
                    condition_4 = true;
                else
                    condition_4 = false;
            else if (this.getTypeCable() == null && ((TheLastTable) obj).getTypeCable() == null)
                condition_4 = true;

            if (this.getLengthCable() == ((TheLastTable) obj).getLengthCable())
                condition_5 = true;

        }

        is_equal = condition_1 && condition_2 && condition_3 && condition_4 && condition_5;

        return is_equal;
    }
}
