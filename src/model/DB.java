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

    static public Connection connection = null;

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.sqlite.JDBC";
    static final String DB_URL = "jdbc:sqlite:database.db";

    static public Connection getConnect() {
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

    static public Catalog getCatalogById(Integer id) throws SQLException {

        Catalog result = null;

        Connection connection = getConnect();

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

    static public void closeConnection() throws SQLException {
        if (connection != null)
            connection.close();
    }
}
