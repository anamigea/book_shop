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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerTableViewAuthors implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private TableView<Author> tableAuthors;
    @FXML
    private TableColumn<Author,String> colFirstName;
    @FXML
    private TableColumn<Author,String> colLastName;

    private ObservableList<Author> obListAuthors = FXCollections.observableArrayList();

    @FXML
    protected void backScene() throws IOException {
        Stage stage =(Stage)backButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AuthorTable authorTable = new AuthorTable();
        ArrayList<Object> authors=authorTable.selectFromTable();
        for(Object a: authors){
            Author author=(Author)a;
            obListAuthors.add(new Author(author.getFirstName(),author.getLastName()));
        }

        colFirstName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFirstName()));
        colLastName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLastName()));

        tableAuthors.setItems(obListAuthors);
    }
}
