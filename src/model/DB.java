package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.UIManager.getInt;

public class DB {

    private static String PATH_RESOURCE = "resource";
    private static String PATH_IMAGES = "/images";

    static public Connection connection = null;

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.sqlite.JDBC";
    static final String DB_URL = "jdbc:sqlite:database.db";

    static public Connection getConnection() {
        if (connection == null) {

            Statement stmt = null;

            try {
                Class.forName(JDBC_DRIVER);

                // Проверка на существование файла БД
                File DBfile = new File("database.db");
                if (!DBfile.exists()) {
                    // Создание БД

                    connection = DriverManager.getConnection(DB_URL);
                    stmt = connection.createStatement();

//                    Path database_create_file_path = Paths.get("src/resource/database_create.sql");
//                    String sql_create_db = new String(Files.readAllBytes(database_create_file_path));
                    String sql_create_db = "PRAGMA foreign_keys=on;\n" +
                            "\n" +
                            "CREATE TABLE if not exists 'catalog' (\n" +
                            "  'id' INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "  'title' TEXT NOT NULL,\n" +
                            "  'type' TEXT NOT NULL,\n" +
                            "  'description' TEXT,\n" +
                            "  'image' TEXT,\n" +
                            "  'mass' FLOAT DEFAULT NULL,\n" +
                            "  'cable_length' FLOAT DEFAULT NULL\n" +
                            ");\n" +
                            "\n" +
                            "CREATE TABLE if not exists 'apparatus_to_cable' (\n" +
                            "  'apparatus_id' INTEGER NOT NULL,\n" +
                            "  'cable_id' INTEGER NOT NULL,\n" +
                            "  FOREIGN KEY (apparatus_id) REFERENCES catalog(id),\n" +
                            "  FOREIGN KEY (cable_id) REFERENCES catalog(id),\n" +
                            "  PRIMARY KEY (apparatus_id,cable_id)\n" +
                            ");\n" +
                            "\n" +
                            "CREATE TABLE if not exists 'apparatus_to_ta' (\n" +
                            "  'apparatus_id' INTEGER NOT NULL,\n" +
                            "  'ta_id' INTEGER NOT NULL,\n" +
                            "  'ta_count' INTEGER NOT NULL,\n" +
                            "  FOREIGN KEY (apparatus_id) REFERENCES catalog(id),\n" +
                            "  FOREIGN KEY (ta_id) REFERENCES catalog(id),\n" +
                            "  PRIMARY KEY (apparatus_id,ta_id)\n" +
                            ");\n" +
                            "\n" +
                            "CREATE TABLE if not exists 'cable_wires' (\n" +
                            "  'cable_id' INTEGER NOT NULL,\n" +
                            "  'wire_material' TEXT NOT NULL,\n" +
                            "  'wire_count' INTEGER NOT NULL,\n" +
                            "  FOREIGN KEY (cable_id) REFERENCES catalog(id),\n" +
                            "  PRIMARY KEY (cable_id,wire_material)\n" +
                            ");\n" +
                            "\n" +
                            "-- CREATE TABLE if not exists 'ParentContent' (\n" +
                            "--   'parent_id' INTEGER NOT NULL,\n" +
                            "--   'child_id' INTEGER NOT NULL,\n" +
                            "--   'child_count' INTEGER NOT NULL,\n" +
                            "--   FOREIGN KEY (apparatus_id) REFERENCES catalog(id),\n" +
                            "--   FOREIGN KEY (ta_id) REFERENCES catalog(id),\n" +
                            "--   PRIMARY KEY (apparatus_id,ta_id)\n" +
                            "-- );\n" +
                            "-- create view apparatus_to_cable\n" +
                            "--   as\n" +
                            "--   select parent_id as apparatus_id,\n" +
                            "--     child_id as cable_id\n" +
                            "--   from ParentContent pc join catalog c on pc.child_id=c.id\n" +
                            "--   where c.type='CableAndOther'";
                    stmt.executeUpdate(sql_create_db);

                    //Наполнение БД
//                    Path database_filling_file_path = Paths.get("src/resource/database_filling.sql");
//                    String sql_fill_db = new String(Files.readAllBytes(database_filling_file_path));
                    String sql_fill_db = "INSERT INTO catalog\n" +
                            "(title, type)\n" +
                            "VALUES\n" +
                            "(\"ТА-57\", \"DS\"),(\"ТА-88\", \"DS\"),(\"П-380 ТА\", \"DS\"),(\"Селенит\", \"DS\"),(\"П-170\", \"ZAS\"),(\"П-171Д\", \"ZAS\"),(\"АТ-3031\", \"ZAS\"),(\"Рамек-2\", \"ARM\"),(\"П-274М\", \"CableAndOther\"),(\"П-269 4х2+2х4\", \"CableAndOther\"),(\"ПРК 5х2\", \"CableAndOther\"),(\"ПТРК 5х2\", \"CableAndOther\"),(\"Витая пара\", \"CableAndOther\"),(\"ВП\", \"CableAndOther\"),(\"РМ2\", \"CableAndOther\"),(\"П-240И\", \"AOZU\"),(\"МП-1И\", \"AOZU\"),(\"МП-2И\", \"AOZU\"),(\"П-260-О\", \"ATZU\"),(\"П-260-У\", \"ATZU\"),(\"П-260-Т\", \"ATZU\"),(\"П-244И\", \"ATZU\"),(\"П-244И-4\", \"ATZU\")";
                    stmt.executeUpdate(sql_fill_db);

                    stmt.close();
                }

                connection = DriverManager.getConnection(DB_URL);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

    static public ObservableList<CatalogItem> getCatalogItems() {
        ObservableList<CatalogItem> result = FXCollections.observableArrayList();

        try {
            Connection connection = getConnection();

            PreparedStatement pstat = null;

            pstat = connection.prepareStatement("SELECT * FROM catalog");

            ResultSet rs = pstat.executeQuery();

            while (rs.next()) {
                result.add(new CatalogItem(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getString("image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    static public ObservableList<CatalogItem> getCatalogTitles() {
        ObservableList<CatalogItem> result = FXCollections.observableArrayList();

        try {
            Connection connection = getConnection();

            PreparedStatement pstat = null;

            pstat = connection.prepareStatement("SELECT id, title FROM catalog");

            ResultSet rs = pstat.executeQuery();

            while (rs.next()) {
                result.add(new CatalogItem(
                        rs.getInt("id"),
                        rs.getString("title")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    static public ObservableList<CatalogItem> getCatalogTitlesByType(String type) {
        ObservableList<CatalogItem> result = FXCollections.observableArrayList();

        try {
            Connection connection = getConnection();

            PreparedStatement pstat = null;

            pstat = connection.prepareStatement("SELECT id, title FROM catalog WHERE type = ?");
            pstat.setString(1, type);

            ResultSet rs = pstat.executeQuery();

            while (rs.next()) {
                result.add(new CatalogItem(
                        rs.getInt("id"),
                        rs.getString("title")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    static public CatalogItem getCatalogItemById(Integer id) throws SQLException {

        CatalogItem result = null;

        Connection connection = getConnection();

        PreparedStatement pstat = connection.prepareStatement("SELECT * FROM catalog WHERE id = ?");
        pstat.setInt(1, id);

        ResultSet rs = pstat.executeQuery();

        result = new CatalogItem(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("type"),
                rs.getString("description"),
                rs.getString("image")
        );

        return result;
    }

    static public CatalogItem getCableById(Integer id) throws SQLException {

        CatalogItem result = null;

        Connection connection = getConnection();

        PreparedStatement pstat = connection.prepareStatement("SELECT * FROM catalog WHERE id = ?");
        pstat.setInt(1, id);

        ResultSet rs = pstat.executeQuery();

        result = new CatalogItem(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("type"),
                rs.getString("description"),
                rs.getString("image"),
                rs.getFloat("mass"),
                rs.getFloat("cable_length")
        );

        return result;
    }

    static public boolean saveCableById(Integer id, String title, String type, String description, Image image, Float mass, Float cable_length) {

        boolean result = false;
        Connection connection = getConnection();

        try {
            PreparedStatement pstat = null;
            pstat = connection.prepareStatement("UPDATE catalog SET title=?, type=?, description=?, mass=?, cable_length=?, image=? WHERE id=?");

            pstat.setString(1, title);
            pstat.setString(2, type);
            pstat.setString(3, description);
            pstat.setFloat(4, mass);
            pstat.setFloat(5, cable_length);

            String imageFilename = saveImage(image);
            if (imageFilename == null)
                pstat.setNull(6, Types.NULL);
            else
                pstat.setString(6, imageFilename);

            pstat.setInt(7, id);

            pstat.execute();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static public boolean saveCatalogItemById(Integer id, String title, String type, String description, Image image) {

        boolean result = false;
        Connection connection = getConnection();

        try {
            PreparedStatement pstat = null;
            pstat = connection.prepareStatement("UPDATE catalog SET title=?, type=?, description=?, image=? WHERE id=?");

            pstat.setString(1, title);
            pstat.setString(2, type);
            pstat.setString(3, description);

            String imageFilename = saveImage(image);
            if (imageFilename == null)
                pstat.setNull(4, Types.NULL);
            else
                pstat.setString(4, imageFilename);

            pstat.setInt(5, id);

            pstat.execute();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static public boolean saveNewCatalogItem(String title, String type, String description, Image image) {

        boolean result = false;
        Connection connection = getConnection();

        try {
            PreparedStatement pstat = null;
            pstat = connection.prepareStatement("INSERT INTO catalog (title,type,description,image) VALUES (?,?,?,?);");

            pstat.setString(1, title);
            pstat.setString(2, type);
            pstat.setString(3, description);

            String imageFilename = saveImage(image);
            if (imageFilename == null)
                pstat.setNull(4, Types.NULL);
            else
                pstat.setString(4, imageFilename);

            pstat.execute();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static public boolean saveNewCableItem(String title, String type, String description, Image image, Float mass, Float cableLength) {

        boolean result = false;
        Connection connection = getConnection();

        try {
            PreparedStatement pstat = null;
            pstat = connection.prepareStatement("INSERT INTO catalog (title,type,description,image,mass,cable_length) VALUES (?,?,?,?,?,?)");

            pstat.setString(1, title);
            pstat.setString(2, type);
            pstat.setString(3, description);
            pstat.setFloat(4, mass);
            pstat.setFloat(5, cableLength);

            String imageFilename = saveImage(image);
            if (imageFilename == null)
                pstat.setNull(6, Types.NULL);
            else
                pstat.setString(6, imageFilename);

            pstat.execute();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static public boolean deleteCatalogItemById(Integer id) {

        boolean result = false;
        Connection connection = getConnection();

        try {
            PreparedStatement pstat = null;
            pstat = connection.prepareStatement("DELETE FROM catalog WHERE id=?");

            pstat.setInt(1, id);

            pstat.execute();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static public boolean updateTypeById(String type, Integer id) {

        boolean result = false;
        Connection connection = getConnection();

        try {
            PreparedStatement pstat = null;
            pstat = connection.prepareStatement("UPDATE catalog SET type=? WHERE id=?");

            pstat.setString(1, type);
            pstat.setInt(2, id);

            pstat.execute();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static public ObservableList<CatalogItem> getCablesInApparatousById(Integer id) {
        ObservableList<CatalogItem> result = FXCollections.observableArrayList();

        try {
            Connection connection = getConnection();

            PreparedStatement pstat = null;

            pstat = connection.prepareStatement("SELECT * FROM catalog LEFT JOIN apparatus_to_cable a on catalog.id = a.cable_id WHERE a.apparatus_id = ?");
            pstat.setInt(1, id);

            ResultSet rs = pstat.executeQuery();

            while (rs.next()) {
                result.add(new CatalogItem(
                        rs.getInt("id"),
                        rs.getString("title")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    static public ObservableList<CatalogItem> getTaInApparatousById(Integer id) {
        ObservableList<CatalogItem> result = FXCollections.observableArrayList();

        try {
            Connection connection = getConnection();

            PreparedStatement pstat = null;

            pstat = connection.prepareStatement("SELECT *, a.ta_count FROM catalog LEFT JOIN apparatus_to_ta a on catalog.id = a.ta_id WHERE a.apparatus_id = ?");
            pstat.setInt(1, id);

            ResultSet rs = pstat.executeQuery();

            while (rs.next()) {
                CatalogItem item = new CatalogItem(
                        rs.getInt("id"),
                        rs.getString("title")
                );
                item.setCount(rs.getInt("ta_count"));
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    static public ObservableList<CatalogItem> getCablesNotInApparatousById(Integer id) {
        ObservableList<CatalogItem> result = FXCollections.observableArrayList();

        try {
            Connection connection = getConnection();

            PreparedStatement pstat = null;

            pstat = connection.prepareStatement("SELECT * FROM catalog WHERE id NOT IN (SELECT cable_id FROM apparatus_to_cable WHERE apparatus_id=?) AND catalog.type=\"CableAndOther\"");
            pstat.setInt(1, id);

            ResultSet rs = pstat.executeQuery();

            while (rs.next()) {
                result.add(new CatalogItem(
                        rs.getInt("id"),
                        rs.getString("title")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    static public ObservableList<CatalogItem> getTaNotInApparatousById(Integer id) {
        ObservableList<CatalogItem> result = FXCollections.observableArrayList();

        try {
            Connection connection = getConnection();

            PreparedStatement pstat = null;

            pstat = connection.prepareStatement("SELECT * FROM catalog WHERE id NOT IN (SELECT ta_id FROM apparatus_to_ta WHERE apparatus_id=?) AND (catalog.type=\"DS\" OR catalog.type=\"ZAS\" OR catalog.type=\"ARM\")");
            pstat.setInt(1, id);

            ResultSet rs = pstat.executeQuery();

            while (rs.next()) {
                CatalogItem item = new CatalogItem(
                        rs.getInt("id"),
                        rs.getString("title")
                );
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    static public boolean updateCountTaInApparatousById(Integer apparatousId, Integer taId, Integer count) {

        boolean result = false;
        Connection connection = getConnection();

        try {
            PreparedStatement pstat = null;
            pstat = connection.prepareStatement("UPDATE apparatus_to_ta SET ta_count=? WHERE apparatus_id=? AND ta_id=?");

            pstat.setInt(1, count);
            pstat.setInt(2, apparatousId);
            pstat.setInt(3, taId);

            pstat.execute();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static public boolean addTaInApparatous(Integer apparatousId, Integer taId, Integer count) {

        boolean result = false;
        Connection connection = getConnection();

        try {
            PreparedStatement pstat = null;
            pstat = connection.prepareStatement("INSERT OR IGNORE INTO apparatus_to_ta (apparatus_id, ta_id, ta_count) VALUES (?,?,?)");

            pstat.setInt(1, apparatousId);
            pstat.setInt(2, taId);
            pstat.setInt(3, count);

            pstat.execute();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static public boolean deleteTaInApparatous(Integer apparatousId, Integer taId) {

        boolean result = false;
        Connection connection = getConnection();

        try {
            PreparedStatement pstat = null;
            pstat = connection.prepareStatement("DELETE FROM apparatus_to_ta WHERE apparatus_id=? AND ta_id=?");

            pstat.setInt(1, apparatousId);
            pstat.setInt(2, taId);

            pstat.execute();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static public boolean addCableInApparatous(Integer apparatousId, Integer cableId) {

        boolean result = false;
        Connection connection = getConnection();

        try {
            PreparedStatement pstat = null;
            pstat = connection.prepareStatement("INSERT OR IGNORE INTO apparatus_to_cable (apparatus_id, cable_id) VALUES (?,?)");

            pstat.setInt(1, apparatousId);
            pstat.setInt(2, cableId);

            pstat.execute();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static public boolean deleteCableInApparatous(Integer apparatousId, Integer cableId) {

        boolean result = false;
        Connection connection = getConnection();

        try {
            PreparedStatement pstat = null;
            pstat = connection.prepareStatement("DELETE FROM apparatus_to_cable WHERE apparatus_id=? AND cable_id=?");

            pstat.setInt(1, apparatousId);
            pstat.setInt(2, cableId);

            pstat.execute();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static public ObservableList<CatalogItem.Wire> getWiresFromCableById(Integer id) {
        ObservableList<CatalogItem.Wire> result = FXCollections.observableArrayList();

        try {
            Connection connection = getConnection();

            PreparedStatement pstat = null;

            pstat = connection.prepareStatement("SELECT * FROM cable_wires WHERE cable_id = ?");
            pstat.setInt(1, id);

            ResultSet rs = pstat.executeQuery();

            while (rs.next()) {
                result.add(new CatalogItem.Wire(
                        rs.getString("wire_material"),
                        rs.getInt("wire_count")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    static public boolean updateCountWiresInCableById(Integer cableId, String material, Integer count) {

        boolean result = false;
        Connection connection = getConnection();

        try {
            PreparedStatement pstat = null;
            pstat = connection.prepareStatement("UPDATE cable_wires SET wire_count=? WHERE cable_id=? AND wire_material=?");

            pstat.setInt(1, count);
            pstat.setInt(2, cableId);
            pstat.setString(3, material);

            pstat.execute();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static public boolean deleteWireInCable(Integer cableId, String material) {

        boolean result = false;
        Connection connection = getConnection();

        try {
            PreparedStatement pstat = null;
            pstat = connection.prepareStatement("DELETE FROM cable_wires WHERE cable_id=? AND wire_material=?");

            pstat.setInt(1, cableId);
            pstat.setString(2, material);

            pstat.execute();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static public boolean addWireToCable(Integer cableId, String material, Integer count) {

        boolean result = false;
        Connection connection = getConnection();

        try {
            PreparedStatement pstat = null;
            pstat = connection.prepareStatement("INSERT OR IGNORE INTO cable_wires (cable_id, wire_material, wire_count) VALUES (?,?, ?)");

            pstat.setInt(1, cableId);
            pstat.setString(2, material);
            pstat.setInt(3, count);

            pstat.execute();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static public void closeConnection() throws SQLException {
        if (connection != null)
            connection.close();
    }

    static private String saveImage(Image image) {
        if (image == null || image.isError()) return null;

        File resourceFolder = new File( PATH_RESOURCE);
        File imagesFolder = new File( PATH_RESOURCE + PATH_IMAGES);

        if (!resourceFolder.exists()) resourceFolder.mkdir();
        if (!imagesFolder.exists()) imagesFolder.mkdir();

        String filename = System.currentTimeMillis() + ".png";
        File outputFile = new File(imagesFolder.toString() + "/" + filename);
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            outputFile.createNewFile();
            ImageIO.write(bImage, "png", outputFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return filename;
    }

    static private Image loadImage(String filename) {
        Image img;
        try {
            img = new Image("file:" + PATH_RESOURCE + PATH_IMAGES + "/" + filename);
        } catch (Exception e) {
            img = new Image(PATH_RESOURCE + "/noimage.png");
        }
        return img;
    }

    private static List<String> getImagesList() {

        List<String> result = new ArrayList<String>();

        try {
            Connection connection = getConnection();

            PreparedStatement pstat = null;
            pstat = connection.prepareStatement("SELECT image FROM catalog WHERE image NOT NULL");
            ResultSet rs = pstat.executeQuery();

            while (rs.next()) {
                result.add(rs.getString("image"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean clearImagesFolder() {

        File resourceFolder = new File(PATH_RESOURCE);
        File imagesFolder = new File(PATH_RESOURCE + PATH_IMAGES);

        List<String> imageFilenameList = DB.getImagesList();

//            if (!resourceFolder.exists()) resourceFolder.mkdir();
//            if (!imagesFolder.exists()) imagesFolder.mkdir();

        if (imagesFolder.exists())
            if (imagesFolder.listFiles().length != 0)
                for (File f : imagesFolder.listFiles())
                    if (f.isFile() && !imageFilenameList.contains(f.getName())) f.delete();

        return true;
    }
}
