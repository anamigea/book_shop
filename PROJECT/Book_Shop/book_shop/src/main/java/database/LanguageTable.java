package database;

import model.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LanguageTable extends PostgresSQLJDBC{

    @Override
    public int generateNextIdAvailable(){
        int id;
        int maxId=0;
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT language_id FROM LANGUAGE;" );
            while ( rs.next() ) {
                id = rs.getInt("language_id");
                if(maxId>id)
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

    public boolean searchLanguage(String language){
        int id=-1;
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT language_id FROM LANGUAGE WHERE name = '" + language + "';" );
            while ( rs.next() ) {
                id = rs.getInt("language_id");
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

    public String getLanguage(int id){
        String language="";
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT language FROM LANGUAGE WHERE language_id = " + id + ";" );
            while ( rs.next() ) {
                language = rs.getString("language");
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return language;
    }

    public int getLanguageId(String language){
        int id=-1;
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT language_id FROM LANGUAGE WHERE language = '" + language + "';" );
            while ( rs.next() ) {
                id = rs.getInt("language_id");
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
    public void insertIntoTable(Object language, User user){
        try {
            if(!searchLanguage((String)language)){
                if(user==User.LIBRARIAN){
                    int id=generateNextIdAvailable();
                    Statement stmt = getConnection().createStatement();
                    String sql = "INSERT INTO LANGUAGE (language_id, language)  "
                            + "VALUES (" + id + ",'" +  language + "');";
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
        ArrayList<Object> languages = new ArrayList<>();
        try {
            System.out.println("---LANGUAGE TABLE---");
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM LANGUAGE;" );
            while ( rs.next() ) {
                int id = rs.getInt("language_id");
                String  languageName = rs.getString("language");
                //System.out.println( "ID = " + id );
                //System.out.println( "LANGUAGE = " + languageName );

                languages.add(languageName);

                //System.out.println();
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return languages;
    }

    @Override
    public void deleteFromTable(int id){

    }
}
