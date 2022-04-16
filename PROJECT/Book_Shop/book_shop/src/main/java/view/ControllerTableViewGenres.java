package view;

import database.AuthorTable;
import database.GenreTable;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerTableViewGenres implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private TableView<String> tableGenres;
    @FXML
    private TableColumn<String,String> colName;

    private ObservableList<String> obListGenres = FXCollections.observableArrayList();

    @FXML
    protected void backScene() throws IOException {
        Stage stage =(Stage)backButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GenreTable genreTable = new GenreTable();
        ArrayList<Object> genres=genreTable.selectFromTable();
        for(Object g: genres){
            String genre=(String)g;
            obListGenres.add(genre);
        }

        colName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue()));

        tableGenres.setItems(obListGenres);
    }
}
