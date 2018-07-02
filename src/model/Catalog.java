package model;

import javafx.scene.image.Image;

public class Catalog {

    private final Integer id;
    private final String title;
    private final String type;
    private final String description;
    private final Image image;

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
    public Catalog() {
        this(null, null, null, null, null);
    }

    public Catalog(Integer id, String title) {
        this(id, title, null, null, null);
    }

    /**
     * Конструктор с некоторыми начальными данными.
     */
    public Catalog(Integer id, String title, String type, String description, String image_filename) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.description = description;

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
        this.image = img;

    }

    // Для ComboBox'ов (Добавляем список моделей, получаем список заголовков)
    @Override
    public String toString() {
        return this.getTitle();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Image getImage() {
        return image;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (((Catalog)obj).getId() == this.getId())
//            return true;
//        else
//            return false;
//    }
}
