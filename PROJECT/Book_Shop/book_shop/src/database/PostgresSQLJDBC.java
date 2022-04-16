package database;

import shop.*;
import java.sql.Connection;
import java.sql.DriverManager;

public abstract class PostgresSQLJDBC {

    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        PostgresSQLJDBC.connection = connection;
    }

    public static void connectToDatabase(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/book_shop", "postgres", "anamaria");
            System.out.println("Opened database successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    public abstract void insertIntoTable(Object obj,User user);
    public abstract void selectFromTable();
    public abstract int generateNextIdAvailable();
    public abstract void deleteFromTable(int id);

    public static void closeDatabase(){
        try {
            connection.close();
            System.out.println("Closed database successfully");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

}
