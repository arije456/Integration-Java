/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.media.Media;  
import javafx.scene.media.MediaPlayer;  
import javafx.scene.media.MediaView;  

/**
 *
 * @author zeine
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private Button gerer_act;
    @FXML
    private Button gerer_enf;
    @FXML
    private Button gerer_blog;
    @FXML
    private Button gerer_com;
    @FXML
    private Button gerer_cours;
    @FXML
    private Button gerer_coaching;
    @FXML
    private Button gerer_produit;
    @FXML
    private Button gerer_panier;
    @FXML
    private Button gerer_consult;
    @FXML
    private Button gerer_prog;
    @FXML
    private Button Map;
    @FXML
    private MediaView audio;
    @FXML
    private Button playAudio;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void gerer_act(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Ajouter_Activite.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void gerer_enf(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Ajouter_Enfant.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void gerer_blog(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Ajouter_Blog.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void gerer_com(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Ajouter_Comm.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void gerer_cours(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("ajouterseancecoaching.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void gerer_coaching(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ajouter cours.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void gerer_panier(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Ajouter_Panier.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void gerer_produit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Ajouter_Produit.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void gerer_consult(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ajouter_consultation.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void gerer_prog(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("ajouter_programme.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void Map(ActionEvent event) {
        Stage stage = new Stage ();
         
        final WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("/GUI/googleMaps.html").toString());
       
        // create scene
       // stage.getIcons().add(new Image("/Assets/logo.png"));
        stage.setTitle("localisation");
        Scene scene = new Scene(webView,1000,700, Color.web("#666970"));
        stage.setScene(scene);
        // show stage
        stage.show();
    }

    @FXML
    private void playAudio(ActionEvent event) {
        //Initialising path of the media file, replace this with your file path   
        String path = "/home/javatpoint/Downloads/test.mp3";  
          Stage stage = new Stage (); 
        //Instantiating Media class  
        Media media = new Media(new File(path).toURI().toString());  
          
        //Instantiating MediaPlayer class   
        MediaPlayer mediaPlayer = new MediaPlayer(media);  
          
        //by setting this property to true, the audio will be played   
        mediaPlayer.setAutoPlay(true); 
        stage.setTitle("Playing Audio");
    }
    
}
