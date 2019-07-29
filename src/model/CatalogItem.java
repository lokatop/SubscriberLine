package model;

import javafx.beans.property.*;
import javafx.scene.image.Image;

public class CatalogItem {

    private IntegerProperty id;
    private StringProperty title;
    private StringProperty type;
    private StringProperty description;
    private ObjectProperty<Image> image;
    private IntegerProperty count = null;
    private FloatProperty mass = null;
    private FloatProperty cable_length = null;
    private StringProperty connect_type = null;
    private IntegerProperty work_pair = null;

    /**
     * Список сокращений категоий
     */
    public static String[] CATEGORIES = {"DS", "ZAS", "ARM", "CableAndOther", "FIT", "AOZU", "ATZU"};
    /**
     * Список описаний категоий
     */
    public static String[] CATEGORIES_DESC = {"Телефонные аппараты открытой ДС", "Телефонные аппараты ЗАС", "Оборудование терминальное и ЗВКС", "Кабель применяемый для развертывания абонентской сети", "Кабельная арматура, применяемая при развертывании абонентской линии", "Аппаратные тактического звена управления", "Аппаратные оперативного звена управления"};

    /**
     * Список позможных типов подключения
     */
    public static String[] CONNECT_TYPES = {"1ПР", "2ПР", "4ПР"};

    /**
     * Конструктор по умолчанию.
     */

    public CatalogItem(Integer id, String title) {
        this(id, title, null, null, null, null, null, null, null);
    }

    public CatalogItem(Integer id, String title, String type, String description, String image_filename) {
        this(id, title, type, description, image_filename, null, null, null, null);
    }
    public CatalogItem(Integer id, String title, String type, String description, String image_filename, String connect_type) {
        this(id, title, type, description, image_filename, null, null, connect_type, null);
    }
    public CatalogItem(Integer id, String title, String type, String description, String image_filename, Float mass, Float cable_length, Integer work_pair) {
        this(id, title, type, description, image_filename, mass, cable_length, null, work_pair);
    }

    /**
     * Конструктор с некоторыми начальными данными.
     */
    public CatalogItem(Integer id, String title, String type, String description, String image_filename, Float mass, Float cable_length, String connect_type, Integer work_pair) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
        this.count = new SimpleIntegerProperty(0);

        Image img;
        if (image_filename != null && !image_filename.isEmpty()) {
            try {
                img = new Image("file:" + "resource" + "/images" + "/" + image_filename);
            } catch (Exception e) {
                img = new Image("resource/noimage.png");
            }
        } else {
            img = new Image("resource/noimage.png");
        }
        this.image = new SimpleObjectProperty<Image>(img);

        if (mass != null)
            this.mass = new SimpleFloatProperty(mass);
        else
            this.mass = new SimpleFloatProperty(0);

        if (cable_length != null)
            this.cable_length = new SimpleFloatProperty(cable_length);
        else
            this.cable_length = new SimpleFloatProperty(0);

        if (work_pair != null)
            this.work_pair = new SimpleIntegerProperty(work_pair);
        else
            this.work_pair = new SimpleIntegerProperty(0);

        if (connect_type != null)
            this.connect_type = new SimpleStringProperty(connect_type);
        else
            this.connect_type = new SimpleStringProperty("");
    }

    public Integer getCount() {
        return count.get();
    }

    public void setCount(Integer value) {
        count.setValue(value);
    }


    // Для ComboBox'ов (Добавляем список моделей, получаем список заголовков)
    @Override
    public String toString() {
        return this.getTitle();
    }

    public Integer getId() {
        return id.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getType() {
        return type.get();
    }

    public String getDescription() {
        return description.get();
    }

    public Image getImage() {
        return image.get();
    }

    public float getMass() {
        return mass.get();
    }

    public FloatProperty massProperty() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass.set(mass);
    }

    public float getCable_length() {
        return cable_length.get();
    }

    public FloatProperty cable_lengthProperty() {
        return cable_length;
    }

    public void setCable_length(float cable_length) {
        this.cable_length.set(cable_length);
    }

    public String getConnect_type() {
        return connect_type.get();
    }

    public StringProperty connect_typeProperty() {
        return connect_type;
    }

    public void setConnect_type(String connect_type) {
        this.connect_type.set(connect_type);
    }

    public int getWork_pair() {
        return work_pair.get();
    }

    public IntegerProperty work_pairProperty() {
        return work_pair;
    }

    public void setWork_pair(int work_pair) {
        this.work_pair.set(work_pair);
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (((CatalogItem)obj).getId() == this.getId())
//            return true;
//        else
//            return false;
//    }

    public static class Wire {
        private StringProperty material;
        private IntegerProperty count;

        public Wire (String mareial, Integer count){
            this.material = new SimpleStringProperty(mareial);
            this.count = new SimpleIntegerProperty(count);
        }

        public String getMaterial() {
            return material.get();
        }

        public StringProperty materialProperty() {
            return material;
        }

        public void setMaterial(String material) {
            this.material.set(material);
        }

        public int getCount() {
            return count.get();
        }

        public IntegerProperty countProperty() {
            return count;
        }

        public void setCount(int count) {
            this.count.set(count);
        }
    }

    public static class FittingCable {

        private IntegerProperty cable_id;
        private StringProperty title;
        private IntegerProperty count;

        public FittingCable(Integer cable_id, String title, Integer count){
            this.cable_id = new SimpleIntegerProperty(cable_id);
            this.title = new SimpleStringProperty(title);
            this.count = new SimpleIntegerProperty(count);
        }

        public String getTitle() {
            return title.get();
        }

        public StringProperty titleProperty() {
            return title;
        }

        public void setTitle(String title) {
            this.title.set(title);
        }

        public int getCount() {
            return count.get();
        }

        public IntegerProperty countProperty() {
            return count;
        }

        public void setCount(int count) {
            this.count.set(count);
        }

        public int getCable_id() {
            return cable_id.get();
        }

        public IntegerProperty cable_idProperty() {
            return cable_id;
        }

        public void setCable_id(int cable_id) {
            this.cable_id.set(cable_id);
        }
    }
}
