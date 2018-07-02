package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class DB {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.sqlite.JDBC";
    static final String DB_URL = "jdbc:sqlite:database.db";

    static public Connection getConnect() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);

            // Проверка на существование файла БД
            File DBfile = new File("database.db");
            if (!DBfile.exists()) {
                // Создание БД

                conn = DriverManager.getConnection(DB_URL);
                stmt = conn.createStatement();

                Path database_create_file_path = Paths.get("src/resource/database_create.sql");
                String sql_create_db = new String(Files.readAllBytes(database_create_file_path));
                stmt.executeUpdate(sql_create_db);

                //Наполнение БД
                Path database_filling_file_path = Paths.get("src/resource/database_filling.sql");
                String sql_fill_db = new String(Files.readAllBytes(database_filling_file_path));
                stmt.executeUpdate(sql_fill_db);

                stmt.close();
            }

            conn = DriverManager.getConnection(DB_URL);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return conn;
    }

    static public ObservableList<InfoModel> getCatalog() throws SQLException {
        ObservableList<InfoModel> result = FXCollections.observableArrayList();

        Connection connection = getConnect();

        String query = "SELECT * FROM catalog";

        Statement stat = connection.createStatement();

        ResultSet rs = stat.executeQuery(query);

        while (rs.next()) {
//            result.add(new InfoModel(
//                    rs.getInt("id"),
//                    rs.getString("title"),
//                    rs.getString("type"),
//                    rs.getString("description"),
//                    readPhoto(rs.getString("image"))
//            ));
        }

        return result;
    }

    static public ResultSet getCatalogTitles() throws SQLException {

        Connection connection = getConnect();

        String query = "SELECT id, title FROM catalog";

        Statement stat = connection.createStatement();

        ResultSet result = stat.executeQuery(query);

        return result;
    }

    static public ObservableList<Catalog> getCatalogTitlesByType(String type) {
        ObservableList<Catalog> result = FXCollections.observableArrayList();

        try {
            Connection connection = getConnect();

            PreparedStatement pstat = null;

            pstat = connection.prepareStatement("SELECT id, title FROM catalog WHERE type = ?");
            pstat.setString(1, type);

            ResultSet rs = pstat.executeQuery();

            while (rs.next()){
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

    static public ResultSet getCatalogById(Integer id) throws SQLException {

        Connection connection = getConnect();

        PreparedStatement pstat = connection.prepareStatement("SELECT id, title FROM catalog WHERE id = ?");
        pstat.setInt(1, id);

        ResultSet result = pstat.executeQuery();

        return result;
    }


    static private Image readPhoto(String filename) {
        if (filename == null || filename.isEmpty()) {
            return null;
        } else {
            Image img;
            try {
                img = new Image("file:" + "resource" + "/images" + "/" + filename);
            } catch (Exception e) {
                img = new Image("resource/noimage.png");
            }
            return img;
        }
    }
}
