package model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.sqlite.JDBC";
    static final String DB_URL = "jdbc:sqlite:database.db";

    //  Database credentials
    static final String USER = "username1";
    static final String PASS = "password";

    static public void createDB(){
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating database...");
            stmt = conn.createStatement();

            Path database_create_file_path = Paths.get("src/resource/database_create.sql");
            String sql_create_db = new String(Files.readAllBytes(database_create_file_path));
            stmt.executeUpdate(sql_create_db);

            System.out.println("Database created successfully...");


            fillDB();

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

    static public Connection getConnect(){
        Connection conn = null;
        Statement stmt = null;

        try{
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    static private void fillDB(){
        try {
            Connection connection = getConnect();

            if (connection != null){
                
                Statement stat = connection.createStatement();

                Path database_filling_file_path = Paths.get("src/resource/database_filling.sql");
                String sql_fill_db = new String(Files.readAllBytes(database_filling_file_path));
                stat.executeUpdate(sql_fill_db);

            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
