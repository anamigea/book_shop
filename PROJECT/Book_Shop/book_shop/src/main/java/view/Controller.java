package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Button guestButton, librarianButton, exitButton;

    @FXML
    private VBox mainVBox;
    @FXML
    protected void guestButtonOnMouseEntered(){
        guestButton.setStyle("-fx-background-color: #ff0000;");
    }

    @FXML
    protected void guestButtonOnMouseExited(){
        guestButton.setStyle("-fx-background-color: #fff000;");
    }

    @FXML
    protected void librarianButtonOnMouseEntered(){
        librarianButton.setStyle("-fx-background-color: #5DADE2;");
    }

    @FXML
    protected void librarianButtonOnMouseExited(){
        librarianButton.setStyle("-fx-background-color:  #A569BD;");
    }

//    @FXML
//    protected void exitApp(){
//        Stage stage =(Stage)exitButton.getScene().getWindow();
//        stage.close();
//    }

    @FXML
    protected void guestClick() throws IOException {
        Stage stage =(Stage)guestButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("guest-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        //stage.show();
    }

    @FXML
    protected void librarianClick() throws IOException {
        Stage stage =(Stage)guestButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("librarian-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        //stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainVBox.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                Stage stage =(Stage)mainVBox.getScene().getWindow();
                xOffset = stage.getX()-mouseEvent.getScreenX();
                yOffset = stage.getY()-mouseEvent.getScreenY();
            }
        });

        mainVBox.setOnMouseDragged(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                Stage stage =(Stage)mainVBox.getScene().getWindow();
                stage.setX(mouseEvent.getScreenX()+xOffset);
                stage.setY(mouseEvent.getScreenY()+yOffset);
            }
        });

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