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

    /**
     * Список сокращений категоий
     */
    public static String[] CATEGORIES = {"DS", "ZAS", "ARM", "CableAndOther", "AOZU", "ATZU"};
    /**
     * Список описаний категоий
     */
    public static String[] CATEGORIES_DESC = {"Телефонные аппараты ДС", "Телефонные аппараты ЗАС", "Автоматизированные рабочие места", "Кабель применяемый для развертывания абонентской сети", "Аппаратные тактического звена управления", "Аппаратные оперативного звена управления"};

    /**
     * Конструктор по умолчанию.
     */
    public CatalogItem() {
        this(null, null, null, null, null);
    }

    public CatalogItem(Integer id, String title) {
        this(id, title, null, null, null);
    }

    /**
     * Конструктор с некоторыми начальными данными.
     */
    public CatalogItem(Integer id, String title, String type, String description, String image_filename) {
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
}
