package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.function.Predicate;


@XmlType(propOrder = {"officialPerson", "typeAbon", "appFrom1","typeCable","appFrom2","lengthCable"})
public class TheLastTable {

    public static String FILENAME_THELASTTABLE = "TheLastTable.xml";
    private static ObservableList<TheLastTable> theLastTableObservableList = FXCollections.observableArrayList();


    private SimpleStringProperty  officialPerson;
    private SimpleStringProperty  typeAbon      ;
    private SimpleStringProperty  appFrom1      ;
    private SimpleStringProperty  typeCable     ;
    private SimpleStringProperty  appFrom2      ;
    private SimpleIntegerProperty lengthCable   ;

    public TheLastTable() {this(null,null,null,null,null,0);}

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

    @XmlElement
    public String getOfficialPerson() {
        return officialPerson.get();
    }

    public SimpleStringProperty officialPersonProperty() {
        return officialPerson;
    }

    public void setOfficialPerson(String officialPerson) {
        this.officialPerson.set(officialPerson);
    }

    @XmlElement
    public String getTypeAbon() {
        return typeAbon.get();
    }

    public SimpleStringProperty typeAbonProperty() {
        return typeAbon;
    }

    public void setTypeAbon(String typeAbon) {
        this.typeAbon.set(typeAbon);
    }

    @XmlElement
    public String getAppFrom1() {
        return appFrom1.get();
    }

    public SimpleStringProperty appFrom1Property() {
        return appFrom1;
    }

    public void setAppFrom1(String appFrom1) {
        this.appFrom1.set(appFrom1);
    }

    @XmlElement
    public String getTypeCable() {
        return typeCable.get();
    }

    public SimpleStringProperty typeCableProperty() {
        return typeCable;
    }

    public void setTypeCable(String typeCable) {
        this.typeCable.set(typeCable);
    }

    @XmlElement
    public String getAppFrom2() {
        return appFrom2.get();
    }

    public SimpleStringProperty appFrom2Property() {
        return appFrom2;
    }

    public void setAppFrom2(String appFrom2) {
        this.appFrom2.set(appFrom2);
    }

    @XmlElement
    public int getLengthCable() {
        return lengthCable.get();
    }

    public SimpleIntegerProperty lengthCableProperty() {
        return lengthCable;
    }

    public void setLengthCable(int lengthCable) {
        this.lengthCable.set(lengthCable);
    }

    /**
     * Фильтрует модели по типу
     * @param type
     * @param theLast
     * @return FilteredList&lt;InfoModel&gt;
     */
    public static ObservableList<TheLastTable> filterTheLastByOfficialPerson(String type, ObservableList<TheLastTable> theLast){
        return theLast.filtered(new Predicate<TheLastTable>() {
            @Override
            public boolean test(TheLastTable theLastTable) {
                // Если несколько разделены запятой, то хренацим массив да цикл
                if(theLastTable.getOfficialPerson().contains(",")){
                    String[] types = theLastTable.getOfficialPerson().split(",");
                    for (int i = 0; i < types.length; i++) {
                        if (type.equals(types[i])) {
                            return true;
                        }
                    }
                } else {
                    if (theLastTable.getOfficialPerson().equals(type)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });
    }
}
