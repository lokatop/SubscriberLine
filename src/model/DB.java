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

                    Path database_create_file_path = Paths.get("src/resource/database_create.sql");
                    String sql_create_db = new String(Files.readAllBytes(database_create_file_path));
                    stmt.executeUpdate(sql_create_db);

                    //Наполнение БД
                    Path database_filling_file_path = Paths.get("src/resource/database_filling.sql");
                    String sql_fill_db = new String(Files.readAllBytes(database_filling_file_path));
                    stmt.executeUpdate(sql_fill_db);

                    stmt.close();
                }

                connection = DriverManager.getConnection(DB_URL);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

    static public ObservableList<Catalog> getCatalog() {
        ObservableList<Catalog> result = FXCollections.observableArrayList();

        try {
            Connection connection = getConnection();

            PreparedStatement pstat = null;

            pstat = connection.prepareStatement("SELECT * FROM catalog");

            ResultSet rs = pstat.executeQuery();

            while (rs.next()) {
                result.add(new Catalog(
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


    static public ObservableList<Catalog> getCatalogTitles() {
        ObservableList<Catalog> result = FXCollections.observableArrayList();

        try {
            Connection connection = getConnection();

            PreparedStatement pstat = null;

            pstat = connection.prepareStatement("SELECT id, title FROM catalog");

            ResultSet rs = pstat.executeQuery();

            while (rs.next()) {
                result.add(new Catalog(
                        rs.getInt("id"),
                        rs.getString("title")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    static public ObservableList<Catalog> getCatalogTitlesByType(String type) {
        ObservableList<Catalog> result = FXCollections.observableArrayList();

        try {
            Connection connection = getConnection();

            PreparedStatement pstat = null;

            pstat = connection.prepareStatement("SELECT id, title FROM catalog WHERE type = ?");
            pstat.setString(1, type);

            ResultSet rs = pstat.executeQuery();

            while (rs.next()) {
                result.add(new Catalog(
                        rs.getInt("id"),
                        rs.getString("title")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    static public Catalog getCatalogItemById(Integer id) throws SQLException {

        Catalog result = null;

        Connection connection = getConnection();

        PreparedStatement pstat = connection.prepareStatement("SELECT * FROM catalog WHERE id = ?");
        pstat.setInt(1, id);

        ResultSet rs = pstat.executeQuery();

        result = new Catalog(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("type"),
                rs.getString("description"),
                rs.getString("image")
        );

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
            pstat.setString(4, saveImage(image));

            pstat.setInt(5, id);

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
        File folder = new File(PATH_RESOURCE + PATH_IMAGES);
        String filename = System.currentTimeMillis() + ".png";
        File outputFile = new File(folder.toString() + "/" + filename);
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
}
