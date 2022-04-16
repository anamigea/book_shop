package view;

import database.BookTable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Book;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerGuest implements Initializable {

    @FXML
    private Button exitButton,viewBooksButton,viewAuthorsButton,viewGenresButton,buyBookButton,buyButton;
    @FXML
    private TextField enterBook;
    @FXML
    private Label succes;
//    @FXML
//    protected void exitApp(){
//        Stage stage =(Stage)exitButton.getScene().getWindow();
//        stage.close();
//    }
    @FXML
    protected void viewBooks() throws IOException {
        Stage stage =(Stage)viewBooksButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("table-view-books.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    @FXML
    protected void viewAuthors() throws IOException {
        Stage stage =(Stage)viewAuthorsButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("table-view-authors.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }
    @FXML
    protected void viewGenres() throws IOException{
        Stage stage =(Stage)viewGenresButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("table-view-genres.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    @FXML
    protected void buyBook(){
        buyBookButton.setDisable(true);
        enterBook.setVisible(true);
        enterBook.setPromptText("Enter book's title.");
        buyButton.setVisible(true);
    }

    @FXML
    protected void buy(){
        try{
            boolean sem=false;
            String title=enterBook.getText();
            if(title.isEmpty()){
                throw new RuntimeException();
            }
            BookTable bookTable = new BookTable();
            ArrayList<Object> books = bookTable.selectFromTable();
            for(Object obj: books){
                Book book=(Book)obj;
                if(book.getTitle().equals(title)){
                    bookTable.updateAvailableQuantityOfBook(User.GUEST,book,0);
                    int bookId=bookTable.getBookId(title);
                    bookTable.deleteFromTableIfBookIsNotLongerInStock(bookId);
                    succes.setText("Successfully bought the book.");
                    sem=true;
                }
            }
            if(!sem){
                succes.setText("This book does not exist in this shop.");
            }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Exit");
                alert.setContentText("Are you sure you want to exit?");
                Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                confirm.setDefaultButton(false);
                confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Stage stage = (Stage) exitButton.getScene().getWindow();
                    stage.close();
                }
            }
        });
    }
}
