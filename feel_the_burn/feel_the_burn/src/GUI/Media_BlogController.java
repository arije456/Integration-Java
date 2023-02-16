
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.MediaView;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Media_BlogController implements Initializable {

    @FXML
    private MediaView mediaview;
    private MediaPlayer mediaplayer;
    private Media media;
    public static String videoName;
    @FXML
    private Slider volume;
    @FXML
    private Button play;
    @FXML
    private Button pause;
    @FXML
    private Button stop;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //String path = new File("src/video/" + videoName + ".mp4").getAbsolutePath();
       // media = new Media(new File(path).toURI().toString());
      try{
           String videoname = null;
           String path = new File("src/video/" + videoname + ".mp4").getAbsolutePath();
           File mediaFile = new File("C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\src\\video\\amine.mp4");
           Media media = new Media(mediaFile.toURI().toURL().toString());
            mediaplayer = new MediaPlayer(media);
            mediaview.setMediaPlayer(mediaplayer);
            volume.setValue(mediaplayer.getVolume() * 100);
            
             volume.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaplayer.setVolume(volume.getValue() / 100);

                // TODO    }    
            }

        });
      } catch (MalformedURLException ex) {
           System.out.println(ex.getMessage());
      }
        //mediaplayer = new MediaPlayer(media);
       // mediaview.setMediaPlayer(mediaplayer);
        //volume.setValue(mediaplayer.getVolume() * 100);
       
    }    

    @FXML
    public void click(MouseEvent event) {
    }

    @FXML
    private void play(ActionEvent event) {
        mediaplayer.play();
    }

    @FXML
    private void pause(ActionEvent event) {
         mediaplayer.pause();
    }

    @FXML
    private void stop(ActionEvent event) {
        mediaplayer.seek(mediaplayer.getStartTime());
        mediaplayer.stop();
    }
    
}
