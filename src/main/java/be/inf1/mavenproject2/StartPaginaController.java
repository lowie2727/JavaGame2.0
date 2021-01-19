package be.inf1.mavenproject2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class StartPaginaController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button startButton;
    
    @FXML
    private Button exitButton;
    
    @FXML
    private Button spelregelsButton;

    @FXML
    void initialize() {
        startButton.setOnAction(this::gaNaarGame);
        exitButton.setOnAction(this::sluitGame);
        spelregelsButton.setOnAction(this::gaNaarSpelregels);
    }

    private void gaNaarGame(ActionEvent t) {
        try {
            Parent modelParent = FXMLLoader.load(getClass().getResource("model.fxml"));
            Scene modelScene = new Scene(modelParent, 1100, 600);
            Stage gameScherm = (Stage) ((Node) t.getSource()).getScene().getWindow();
            gameScherm.hide();
            gameScherm.setScene(modelScene);
            gameScherm.show();
        } catch (IOException io) {
        }
    }

    private void sluitGame(ActionEvent t) {
        System.exit(0);
    }

    private void gaNaarSpelregels(ActionEvent t) {
        try {
            Parent modelParent = FXMLLoader.load(getClass().getResource("spelregels.fxml"));
            Scene modelScene = new Scene(modelParent, 1100, 600);
            Stage gameScherm = (Stage) ((Node) t.getSource()).getScene().getWindow();
            gameScherm.hide();
            gameScherm.setTitle("Atari Breakout");
            gameScherm.setScene(modelScene);
            gameScherm.show();
        } catch (IOException io) {
        }
    }
}