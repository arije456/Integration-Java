
package GUI;
/*
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import com.lynden.gmapsfx.GoogleMapView;*/
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.media.Media;  
import javafx.scene.media.MediaPlayer;  
import javafx.scene.media.MediaView; 
/*import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.*;*/

/**
 *
 * @author zeine
 */
public class FXMLDocumentController implements Initializable {
    //, MapComponentInitializedListener, DirectionsServiceCallback
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
    /*
    @FXML
    private TextField fromTextField;
    @FXML
    private TextField toTextField;
     @FXML
   protected GoogleMapView mapView = new GoogleMapView("de-DE","AIzaSyCYL7GC8RuujvLyy0VV6rfTm-NRQufumWM");  
   protected StringProperty from = new SimpleStringProperty();
   protected StringProperty to = new SimpleStringProperty();
   protected DirectionsService directionsService;
   protected DirectionsPane directionsPane;
   //protected DirectionsRenderer directionsRenderer = null;
    @FXML
    private GoogleMapView mapView;
    @FXML
    private TextField fromTextField;
    @FXML
    private TextField toTextField;*/
    @FXML
    private Button Instagram;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      /*  
        mapView.addMapInializedListener(this);
       to.bindBidirectional(toTextField.textProperty());
       from.bindBidirectional(fromTextField.textProperty());*/
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
         Parent root = FXMLLoader.load(getClass().getResource("ajouter cours.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void gerer_coaching(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ajouterseancecoaching.fxml"));
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

    /*
    @FXML
    private void fromTextField(ActionEvent event) {
    }

    @FXML
    private void toTextField(ActionEvent event) {
        
       DirectionsRequest request = new DirectionsRequest(from.get(), to.get(), TravelModes.DRIVING);
       //directionsRenderer = new DirectionsRenderer(true, mapView.getMap(), directionsPane);
           // directionsService.getRoute(request, this, directionsRenderer);
       directionsService.getRoute(request, this, new DirectionsRenderer(true, mapView.getMap(), directionsPane));
    }*/
    /*

    @Override
    public void mapInitialized() {
        
         MapOptions options = new MapOptions();

         options.center(new LatLong(47.606189,122.335842))
                .zoomControl(true)
                .zoom(12)
                .overviewMapControl(false)
                .mapType(MapTypeIdEnum.ROADMAP);
         
      
  options.center(new LatLong(47.606189,122.335842)).zoomControl(true) .zoom(12).overviewMapControl(false).mapType(MapTypeIdEnum.ROADMAP);
        GoogleMap map = mapView.createMap(options);
        directionsService = new DirectionsService();
        directionsPane = mapView.getDirec();

    }

    @Override
    public void directionsReceived(DirectionsResult dr, DirectionStatus ds) {
        
    }*/

    @FXML
    private void Affiche_Instagram(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Instagram.fxml"));
    Scene newScene= new Scene(root);
    Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
       window.setScene(newScene);
       window.show();
    }
    
}
