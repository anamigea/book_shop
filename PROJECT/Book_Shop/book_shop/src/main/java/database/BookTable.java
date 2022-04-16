package database;

import model.*;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BookTable extends PostgresSQLJDBC{

    @Override
    public int generateNextIdAvailable(){
        int id;
        int maxId=0;
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT book_id FROM BOOK;" );
            while ( rs.next() ) {
                id = rs.getInt("book_id");
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

    public boolean searchBook(String title){
        int id=-1;
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT book_id FROM BOOK WHERE title = '" + title + "';" );
            while ( rs.next() ) {
                id = rs.getInt("book_id");
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

    public int getBookId(String title){
        int id=-1;
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT book_id FROM BOOK WHERE title = '" + title + "';" );
            while ( rs.next() ) {
                id = rs.getInt("book_id");
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
    public void insertIntoTable(Object obj, User user){
        try {
            if(!searchBook(((Book)obj).getTitle())){
                if(user==User.LIBRARIAN){
                    PublisherTable publisher=new PublisherTable();
                    int bookId=generateNextIdAvailable();
                    int publisherId= publisher.getPublisherId(((Book)obj).getPublisher());
                    if(publisherId==-1){
                        publisherId=publisher.generateNextIdAvailable();
                        publisher.insertIntoTable(((Book)obj).getPublisher(),user );
                    }

                    LanguageTable language=new LanguageTable();
                    int languageId= language.getLanguageId(((Book)obj).getLanguage());
                    if(languageId==-1){
                        languageId=language.generateNextIdAvailable();
                        language.insertIntoTable(((Book)obj).getLanguage(),user );
                    }
                    Statement stmt = getConnection().createStatement();
                    String sql = "INSERT INTO BOOK (book_id, title, pages, available_quantity, price, publisher_id, language_id, publication_year)  "
                            + "VALUES (" + bookId + ",'" +  ((Book)obj).getTitle() + "'," + ((Book) obj).getNumberOfPages() + "," + ((Book)obj).getAvailableQuantity() + "," + ((Book)obj).getPrice() + "," + publisherId + "," + languageId + "," + ((Book)obj).getPublicationYear() + ");";
                    stmt.executeUpdate(sql);

                    stmt.close();

                    //linking the book with its authors into a special table- BOOK_AUTHOR table (many-to-many relationship between book and author)
                    AuthorTable authorTable=new AuthorTable();
                    ArrayList<Author> authors=((Book)obj).getAuthors();
                    for (Author author : authors) {
                        int authorId=authorTable.getAuthorId(author.getFirstName(),author.getLastName());
                        if(authorId==-1){
                            authorId=authorTable.generateNextIdAvailable();
                            authorTable.insertIntoTable(author,user );
                        }
                        Statement stmt1 = getConnection().createStatement();
                        String sql1 = "INSERT INTO BOOK_AUTHOR (book_id, author_id)  "
                                + "VALUES (" + bookId + "," + authorId + ");";
                        stmt1.executeUpdate(sql1);

                        stmt1.close();
                    }

                    //linking the book with its genres into a special table - BOOK_GENRE table (many-to-many relationship between book and genre)
                    GenreTable genreTable=new GenreTable();
                    ArrayList<String> genres=((Book)obj).getGenres();
                    for (String genre : genres) {
                        int genreId=genreTable.getGenreId(genre);
                        if(genreId==-1){
                            genreId=genreTable.generateNextIdAvailable();
                            genreTable.insertIntoTable(genre,user );
                        }
                        Statement stmt1 = getConnection().createStatement();
                        String sql1 = "INSERT INTO BOOK_GENRE (book_id, genre_id)  "
                                + "VALUES (" + bookId + "," + genreId + ");";
                        stmt1.executeUpdate(sql1);

                        stmt1.close();
                    }


                    System.out.println("Records created successfully");
                }else{
                    System.out.println("Only LIBRARIAN can modify database");
                }

            }else{
                System.out.println("Records already exist");
            }

        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    @Override
    public ArrayList<Object> selectFromTable(){
        ArrayList<Object> books = new ArrayList<>();
        try {
            System.out.println("---BOOK TABLE---");
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM BOOK;" );
            while ( rs.next() ) {
                int id = rs.getInt("book_id");
                String  title = rs.getString("title");
                int nrOfPages  = rs.getInt("pages");
                int availableQuantity  = rs.getInt("available_quantity");
                double price = rs.getFloat("price");
                int publisherId  = rs.getInt("publisher_id");
                int languageId  = rs.getInt("language_id");
                int publicationYear  = rs.getInt("publication_year");
                //System.out.println( "ID = " + id );
                //System.out.println( "TITLE = " + title );
                //System.out.println( "NUMBER OF PAGES = " + nrOfPages );
                //System.out.println( "AVAILABLE QUANTITY = " + availableQuantity );
                //System.out.println( "PRICE = " + price );
                //System.out.println( "PUBLISHER ID = " + publisherId );
                LanguageTable languageTable=new LanguageTable();
                String language = languageTable.getLanguage(languageId);
                PublisherTable publisherTable=new PublisherTable();
                String publisherName = publisherTable.getPublisher(publisherId);

                books.add(new Book(title,nrOfPages,availableQuantity,price,publicationYear,language,publisherName,null,null));
                //System.out.println( "LANGUAGE ID = " + languageId );
                //System.out.println( "PUBLICATION YEAR = " + publicationYear );

                System.out.println();
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return books;
    }

    public void updateAvailableQuantityOfBook(User user, Book book,int quantityToAdd){
        //suppose that before calling this method, searchBook method is called first to see if the book is in the database
        try{
            Statement stmt = getConnection().createStatement();
            if(user==User.GUEST){
                String sql = "UPDATE BOOK set available_quantity = available_quantity-1 where book_id=" + getBookId(book.getTitle()) + ";";
                deleteFromTableIfBookIsNotLongerInStock(getBookId(book.getTitle()));
                stmt.executeUpdate(sql);
            }else if(user==User.LIBRARIAN){
                String sql = "UPDATE BOOK set available_quantity = available_quantity+" + quantityToAdd + " where book_id=" + getBookId(book.getTitle()) + ";";
                stmt.executeUpdate(sql);
            }
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

    }

    public void deleteFromTableIfBookIsNotLongerInStock(int id){
        int availableQuantity=0;
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT available_quantity FROM BOOK WHERE book_id = " + id + ";" );
            while ( rs.next() ) {
                availableQuantity = rs.getInt("available_quantity");
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        if(availableQuantity==0){
            try{
                Statement stmt = getConnection().createStatement();
                String sql = "DELETE from BOOK where book_id = " + id + " ;";
                stmt.executeUpdate(sql);
                sql = "DELETE from BOOK_AUTHOR where book_id = " + id + " ;";
                stmt.executeUpdate(sql);
                sql = "DELETE from BOOK_GENRE where book_id = " + id + " ;";
                stmt.executeUpdate(sql);
                stmt.close();
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);
            }
        }
    }

    @Override
    public void deleteFromTable(int id){

    }
}
