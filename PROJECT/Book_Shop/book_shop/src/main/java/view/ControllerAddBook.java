package view;

import database.BookTable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Author;
import model.Book;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class ControllerAddBook {

    @FXML
    private Button backButton,addAuthorButton,addAnotherAuthorButton,finishAuthorsButton;
    @FXML
    private Button addGenreButton,addAnotherGenreButton,finishGenresButton;
    @FXML
    private Button addBookButton;
    @FXML
    private TextField titleTextField, nrOfPagesTextField, availableQuantityTextField, priceTextField, publicationYearTextField, publisherTextField, languageTextField;
    @FXML
    private TextField firstNameTextField, lastNameTextField, genreTextField;
    @FXML
    private Label succes,genresLabel,titleLabel,nrOfPagesLabel,availableQuantityLabel,priceLabel,publicationYearLabel,languageLabel,publisherLabel;


    private ArrayList<Author> authors =new ArrayList<>();
    private ArrayList<String> genres=new ArrayList<>();


    @FXML
    protected void backScene() throws IOException {
        Stage stage =(Stage)backButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("librarian-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }
    @FXML
    protected void addAuthorClick(){
        addAuthorButton.setDisable(true);
        addAnotherAuthorButton.setDisable(false);
        finishAuthorsButton.setDisable(false);
        try{
            String firstName=firstNameTextField.getText();
            String lastName=lastNameTextField.getText();
            if(firstName.isEmpty() || lastName.isEmpty()){
                throw new RuntimeException();
            }
            Author author=new Author(firstName,lastName);
            authors.add(author);
            succes.setText("Author added successfully");
        }catch(RuntimeException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(exception.getMessage());
            succes.setText("Wrong characters\n Type again the author");
//            exception.printStackTrace();
            Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            confirm.setDefaultButton(false);
            confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            alert.showAndWait();
            return;
        }
    }

    @FXML
    protected void addAnotherAuthorClick(){
        addAuthorButton.setDisable(false);
        addAnotherAuthorButton.setDisable(true);
        finishAuthorsButton.setDisable(true);
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        succes.setText("Type another author in the fields");
    }
    @FXML
    protected void finishAuthorsClick(){

        //disable author
        addAnotherAuthorButton.setDisable(true);
        finishAuthorsButton.setDisable(true);
        firstNameTextField.setDisable(true);
        lastNameTextField.setDisable(true);

        //enable genre
        genresLabel.setVisible(true);
        genreTextField.setVisible(true);
        addGenreButton.setVisible(true);
        addAnotherGenreButton.setVisible(true);
        finishGenresButton.setVisible(true);

        succes.setText("");
    }
    @FXML
    protected void addGenreClick(){
        addGenreButton.setDisable(true);
        addAnotherGenreButton.setDisable(false);
        finishGenresButton.setDisable(false);
        try{
            String genre=genreTextField.getText();
            if(genre.isEmpty()){
                throw new RuntimeException();
            }
            genres.add(genre);
            succes.setText("Genre added successfully");
        }catch(RuntimeException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(exception.getMessage());
            succes.setText("Wrong characters\n Type again the genre");
//            exception.printStackTrace();
            Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            confirm.setDefaultButton(false);
            confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            alert.showAndWait();
            return;
        }
    }
    @FXML
    protected void addAnotherGenreClick(){
        addGenreButton.setDisable(false);
        addAnotherGenreButton.setDisable(true);
        finishGenresButton.setDisable(true);
        genreTextField.setText("");
        succes.setText("Type another genre in the field");
    }
    @FXML
    protected void finishGenresClick(){

        //disable genre
        addAnotherGenreButton.setDisable(true);
        finishGenresButton.setDisable(true);
        genreTextField.setDisable(true);

        //enable the other properties for the book
        titleLabel.setVisible(true);
        nrOfPagesLabel.setVisible(true);
        availableQuantityLabel.setVisible(true);
        priceLabel.setVisible(true);
        publicationYearLabel.setVisible(true);
        languageLabel.setVisible(true);
        publisherLabel.setVisible(true);

        titleTextField.setVisible(true);
        nrOfPagesTextField.setVisible(true);
        availableQuantityTextField.setVisible(true);
        priceTextField.setVisible(true);
        publicationYearTextField.setVisible(true);
        publisherTextField.setVisible(true);
        languageTextField.setVisible(true);

        addBookButton.setVisible(true);

        succes.setText("");

    }
    @FXML
    protected void addBookClick(){
        try{
            String title=titleTextField.getText();
            if(title.isEmpty()){
                throw new RuntimeException();
            }
            int nrOfPages = Integer.parseInt(nrOfPagesTextField.getText());
            int availableQuantity =Integer.parseInt(availableQuantityTextField.getText());
            double price =Double.parseDouble(priceTextField.getText());
            int publicationYear=Integer.parseInt(publicationYearTextField.getText());
            String language=languageTextField.getText();
            String publisher=publisherTextField.getText();
            if(language.isEmpty() || publisher.isEmpty()){
                throw new RuntimeException();
            }

            Book book=new Book(title,nrOfPages,availableQuantity,price,publicationYear,language,publisher,authors,genres);
            BookTable bookTable = new BookTable();
            if(!bookTable.searchBook(title)){
                bookTable.insertIntoTable(book,User.LIBRARIAN);
            }else{
                succes.setText("This book is already in the shop");
            }
            addBookButton.setDisable(true);
        }catch(RuntimeException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(exception.getMessage());
            succes.setText("Wrong characters\n Type again data in textfield");
//            exception.printStackTrace();
            Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            confirm.setDefaultButton(false);
            confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            alert.showAndWait();
            return;
        }
    }


}
