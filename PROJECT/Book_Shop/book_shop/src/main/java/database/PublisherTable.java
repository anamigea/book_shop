package database;

import model.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PublisherTable extends PostgresSQLJDBC{

    @Override
    public int generateNextIdAvailable(){
        int id;
        int maxId=0;
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT publisher_id FROM PUBLISHER;" );
            while ( rs.next() ) {
                id = rs.getInt("publisher_id");
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

    public boolean searchPublisher(String publisherName){
        int id=-1;
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT publisher_id FROM PUBLISHER WHERE name = '" + publisherName + "';" );
            while ( rs.next() ) {
                id = rs.getInt("publisher_id");
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

    public String getPublisher(int id){
        String publisherName="";
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT name FROM PUBLISHER WHERE publisher_id = " + id + ";" );
            while ( rs.next() ) {
                publisherName = rs.getString("name");
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return publisherName;
    }

    public int getPublisherId(String publisherName){
        int id=-1;
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT publisher_id FROM PUBLISHER WHERE name = '" + publisherName + "';" );
            while ( rs.next() ) {
                id = rs.getInt("publisher_id");
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
    public void insertIntoTable(Object publisherName, User user){
        try {
            if(!searchPublisher((String)publisherName)){
                if(user==User.LIBRARIAN){
                    int id=generateNextIdAvailable();
                    Statement stmt = getConnection().createStatement();
                    String sql = "INSERT INTO PUBLISHER (publisher_id, name)  "
                            + "VALUES (" + id + ",'" +  publisherName + "');";
                    stmt.executeUpdate(sql);

                    stmt.close();

                    System.out.println("Records created successfully");
                }else{
                    System.out.println("Only LIBRARIAN can modify database");
                }

            }
            else{
                System.out.println("Records already exist");
            }

        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    @Override
    public ArrayList<Object> selectFromTable(){
        ArrayList<Object> publishers = new ArrayList<>();
        try {
            System.out.println("---PUBLISHER TABLE---");
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PUBLISHER;" );
            while ( rs.next() ) {
                int id = rs.getInt("publisher_id");
                String  publisherName = rs.getString("name");
                //System.out.println( "ID = " + id );
                //System.out.println( "PUBLISHER NAME = " + publisherName );

                publishers.add(publisherName);

                System.out.println();
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return publishers;
    }

    @Override
    public void deleteFromTable(int id){

    }
}
