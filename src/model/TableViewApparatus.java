package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableViewApparatus {

    private SimpleIntegerProperty id;
    private SimpleStringProperty fullName;
    private SimpleIntegerProperty count;
    private SimpleBooleanProperty choose;
//    private SimpleStringProperty data;

    public TableViewApparatus(Integer id, String fullName, Integer count, boolean choose) {
        this.id = new SimpleIntegerProperty(id);
        this.fullName = new SimpleStringProperty(fullName);
        this.count = new SimpleIntegerProperty(count);
        this.choose = new SimpleBooleanProperty(choose);
    }

    public TableViewApparatus(Integer id, String fullName) {
        this.id = new SimpleIntegerProperty(id);
        this.fullName = new SimpleStringProperty(fullName);
        this.count = new SimpleIntegerProperty(1);
        this.choose = new SimpleBooleanProperty(false);
    }

    public String getFullName() {
        return fullName.get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public boolean isChoose() {
        return choose.get();
    }

    public SimpleBooleanProperty chooseProperty() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose.set(choose);
    }

    public int getCount() {
        return count.get();
    }

    public SimpleIntegerProperty countProperty() {
        return count;
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    @Override
    public String toString() {
        return getFullName();
    }


    /**
     * Результат в виде<br>
     *     List&lt;Map&lt;String, String&gt;&gt; data = app.getDataApparatus(); <br>
     *     data.get(0).get("name");<br>
     *     data.get(0).get("count"); // Это строка! Для перевода в число: Integer.parseInt(data.get(0).get("count"));
     * @return
     */
//    public List<Map< String, String >> getDataApparatus(){
//        List<Map< String, String >> result = new ArrayList<>();
//            if (this.getData() != null) {
//                String[] substrs;
//                substrs = this.getData().split(";");
//                for (String substr : substrs) {
//
//                    String strTA[] = substr.split(":");
//                    Map<String, String> toAdd = new HashMap<String, String>();
//                    toAdd.put("name", strTA[0]);
//                    toAdd.put("count", strTA[1]);
//                    result.add(toAdd);
//                }
//            }
//        return result;
//    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }
}
