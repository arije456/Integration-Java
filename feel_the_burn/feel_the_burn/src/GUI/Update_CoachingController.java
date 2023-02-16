/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Services.CoachingServices;
import Entities.Coaching;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author WAEL
 */
public class Update_CoachingController implements Initializable {

    @FXML
    private TextField Id_S;
    @FXML
    private TextField Date_S;
    @FXML
    private TextField Prix;
    @FXML
    private TextField Id_Co;
    @FXML
    private TextField Nom_User;
    @FXML
    private TextField Prenom_User;
    @FXML
    private Button Valider_update_coaching;
    @FXML
    private Button afficher_co;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CoachingServices as = new CoachingServices();
        
        
        try
        {
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("Afficher_Coaching.fxml"));
          Stage prStage = new Stage();
            Parent root= loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            Afficher_CoachingController ac = loader.getController();
           int id = Integer.parseInt(Afficher_CoachingController.idxx);
            System.out.println(id);
            
           Date_S.setText(as.getDatebyID(id));
            Prix.setText(as.getPrixbyID(id).toString());
            Id_Co.setText(as.getId_CobyID(id).toString());
            Nom_User.setText(as.getNombyID(id));
            Prenom_User.setText(as.getPrenombyID(id));
           
         
        } catch (IOException ex) {
            Logger.getLogger(Update_CoursController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO
        // TODO
    }    

    @FXML
    private void Valider_update_coaching(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Afficher_Coaching.fxml"));
            Stage prStage = new Stage();
            Parent root= loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            
            Afficher_CoachingController ac = loader.getController();
            CoachingServices as = new CoachingServices();
            Coaching a = new Coaching();
            
            int id= Integer.parseInt(Afficher_CoachingController.idxx);
            System.out.println(id);
            a.setDate_S(Date_S.getText());
            a.setPrix(Float.parseFloat(Prix.getText()));
            a.setId_Co(Integer.parseInt(Id_Co.getText()));
             a.setNom_User(Nom_User.getText());
              a.setPrenom_User(Prenom_User.getText());
            
            as.modifier(a, id);
             Alert alert = new Alert (Alert.AlertType.INFORMATION);
             alert.setTitle("succes");
             alert.setHeaderText("!!! Modification effectuer avec suucces !!!");
             alert.setContentText("succes");
             alert.showAndWait();
    }

    @FXML
    private void afficher_co(ActionEvent event) {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("Afficher_Coaching.fxml"));
        //Afficher_ActiviteController ac = loader.getController();
        //Afficher_ActiviteController aac = new Afficher_ActiviteController();
        //aac.tab_act.refresh();
         try{
            Parent root = loader.load();
           Afficher_CoachingController ac = loader.getController();
           Id_S.getScene().setRoot(root);
           ac.table_Coaching.refresh();
        }
        catch(IOException ex){
           System.out.println(ex.getMessage());
       }
    }
    
}
