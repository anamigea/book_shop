package database;

import shop.*;
import java.sql.ResultSet;
import java.sql.Statement;

public class GenreTable extends PostgresSQLJDBC{
    @Override
    public int generateNextIdAvailable(){
        int id;
        int maxId=0;
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT genre_id FROM GENRE;" );
            while ( rs.next() ) {
                id = rs.getInt("genre_id");
                if(id>maxId)
                    maxId=id;
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return maxId+1;
    }

    public boolean searchGenre(String genreName){
        int id=-1;
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT genre_id FROM GENRE WHERE name = '" + genreName + "';" );
            while ( rs.next() ) {
                id = rs.getInt("genre_id");
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        if(id==-1)
            return false;
        return true;
    }

    public int getGenreId(String genreName){
        int id=-1;
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT genre_id FROM GENRE WHERE name = '" + genreName + "';" );
            while ( rs.next() ) {
                id = rs.getInt("genre_id");
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return id;
    }

    @Override
    public void insertIntoTable(Object genreName, User user){
        try {
            if(!searchGenre((String) genreName)) {
                if(user==User.LIBRARIAN){
                    int id=generateNextIdAvailable();
                    Statement stmt = getConnection().createStatement();
                    String sql = "INSERT INTO GENRE (genre_id, name)  "
                            + "VALUES (" + id + ",'" +  genreName + "');";
                    stmt.executeUpdate(sql);

                    stmt.close();

                    System.out.println("Records created successfully");
                }else{
                    System.out.println("Only LIBRARIAN can modify database");
                }

            } else{
                System.out.println("Records already exist");
            }
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    @Override
    public void selectFromTable(){
        try {
            System.out.println("---GENRE TABLE---");
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM GENRE;" );
            while ( rs.next() ) {
                int id = rs.getInt("genre_id");
                String  genreName = rs.getString("name");
                System.out.println( "ID = " + id );
                System.out.println( "GENRE NAME = " + genreName );

                System.out.println();
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    @Override
    public void deleteFromTable(int id){

    }
}
