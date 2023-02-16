/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeltheburn;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import Entities.Coaching;
import GUI.Afficher_CoachingController;
import Services.CoachingServices;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author zeine
 */
public class AjouterseancecoachingController implements Initializable {

    @FXML
    private TextField add_Id_S;
    @FXML
    private Button ajouter_seance;
    @FXML
    private Button afficher_seance;
    @FXML
    private TextField add_Nom_C;
    @FXML
    private TextField add_Prenom_C;
    @FXML
    private TextField add_Prix;
    @FXML
    private TextField add_Id_C;
    @FXML
    private TextField add_Date_S;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ajouter_seance(ActionEvent event) {
              Coaching co= new Coaching();
        co.setId_S(Integer.valueOf(add_Id_S.getText()));
        co.setNom_User(add_Nom_C.getText());
        co.setPrenom_User(add_Prenom_C.getText());
        co.setDate_S(add_Date_S.getText());
        co.setPrix(Float.valueOf(add_Prix.getText()));
        co.setId_Co(Integer.valueOf(add_Id_C.getText()));
        
        CoachingServices acs = new CoachingServices();
        acs.ajouterCoaching(co);
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("succes");
        alert.setHeaderText(null);
        alert.setContentText("!!!succes !!!");
        alert.showAndWait();
        
         FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Coaching.fxml"));
      
       try{
           Parent root = loader.load();
           Afficher_CoachingController ac = loader.getController();
           //ac.loadData();
           //ac.setAff_act(as.afficherActivite().toString());
           add_Id_S.getScene().setRoot(root);
           
           
       }
       catch(IOException ex){
           System.out.println(ex.getMessage());
       }
    }

    @FXML
    private void afficher_seance(ActionEvent event) {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Coaching.fxml"));
        Coaching a2= new Coaching();
        a2.setId_S(Integer.valueOf(add_Id_S.getText()));
      
        try{
            Parent root = loader.load();
           Afficher_CoachingController ac = loader.getController();
           add_Id_S.getScene().setRoot(root);
        }
        catch(IOException ex){
           System.out.println(ex.getMessage());
       }
    }
    
}
