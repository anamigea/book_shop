package view;

import database.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Author;
import model.Book;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerTableViewBooks implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private TableView<Book> tableBooks;
    @FXML
    private TableColumn<Book,String> colTitle;
    @FXML
    private TableColumn<Book,String> colNrOfPages;
    @FXML
    private TableColumn<Book,String> colAvailableQuantity;
    @FXML
    private TableColumn<Book,String> colPrice;
    @FXML
    private TableColumn<Book,String> colPublisher;
    @FXML
    private TableColumn<Book,String> colLanguage;
    @FXML
    private TableColumn<Book,String> colPublicationYear;


    private ObservableList<Book> obListBooks = FXCollections.observableArrayList();

    @FXML
    protected void backScene() throws IOException {
        Stage stage =(Stage)backButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        BookTable bookTable = new BookTable();
        ArrayList<Object> books=bookTable.selectFromTable();
        for(Object b: books){
            Book book=(Book)b;
            obListBooks.add(new Book(book.getTitle(),book.getNumberOfPages(),book.getAvailableQuantity(),book.getPrice(),book.getPublicationYear(),book.getLanguage(),book.getPublisher(),null,null));
        }

        colTitle.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
        colNrOfPages.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getNumberOfPages())));
        colAvailableQuantity.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getAvailableQuantity())));
        colPrice.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(df.format(cellData.getValue().getPrice()))));
        colPublisher.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPublisher()));
        colLanguage.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLanguage()));
        colPublicationYear.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getPublicationYear())));

        tableBooks.setItems(obListBooks);
    }
}
