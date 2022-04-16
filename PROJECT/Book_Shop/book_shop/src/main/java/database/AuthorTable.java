package database;

import model.*;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AuthorTable extends PostgresSQLJDBC{

    @Override
    public int generateNextIdAvailable(){
        int id;
        int maxId=0;
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT author_id FROM AUTHOR;" );
            while ( rs.next() ) {
                id = rs.getInt("author_id");
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

    public boolean searchAuthor(String firstName,String lastName){
        int id=-1;
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT author_id FROM AUTHOR WHERE first_name = '" + firstName + "' AND last_name = '" + lastName + "';" );
            while ( rs.next() ) {
                id = rs.getInt("author_id");
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

    public int getAuthorId(String firstName, String lastName){
        int id=-1;
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT author_id FROM AUTHOR WHERE first_name = '" + firstName + "' AND last_name = '" + lastName + "';" );
            while ( rs.next() ) {
                id = rs.getInt("author_id");
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
    public void insertIntoTable(Object author, User user){
        try {
            if(!searchAuthor(((Author) author).getFirstName(),((Author) author).getLastName())) {
                if(user==User.LIBRARIAN){
                    int id=generateNextIdAvailable();
                    Statement stmt = getConnection().createStatement();
                    String sql = "INSERT INTO AUTHOR (author_id, first_name, last_name)  "
                            + "VALUES (" + id + ",'" +  ((Author) author).getFirstName() + "', '" + ((Author) author).getLastName() + "');";
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
    public ArrayList<Object> selectFromTable(){
        ArrayList<Object> authors=new ArrayList<>();
        try {
            //System.out.println("---AUTHOR TABLE---");
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM AUTHOR;" );
            while ( rs.next() ) {
                int id = rs.getInt("author_id");
                String  firstName = rs.getString("first_name");
                String  lastName = rs.getString("last_name");
                //System.out.println( "ID = " + id );
                //System.out.println( "FIRST NAME = " + firstName );
                //System.out.println( "LAST NAME = " + lastName );
                authors.add(new Author(firstName,lastName));

                //System.out.println();
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        //System.out.println("Operation done successfully");
        return authors;
    }

    @Override
    public void deleteFromTable(int id){

    }
}
