/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author zeine
 */
public class InstagramController implements Initializable {

    @FXML
    private WebView webView;
    public static String instagram = "" ;
    @FXML
    private Button GOBACK;


   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       WebEngine engine = webView.getEngine();
    // String youtube="https://www.youtube.com/results?search_query=recette+";
     // String  youtube="https://www.facebook.com/Epicode1233";
 String youtube="https://www.instagram.com/feel_the_burn11/?hl=fr" ;
  
      String lien=youtube+instagram.replace(" ","+");
        engine.load(lien);
        System.out.println(lien);
    }

    @FXML
    private void GOBACK_TOACCUIEL(ActionEvent event) throws IOException {
        
         Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }
    
    }    
    

