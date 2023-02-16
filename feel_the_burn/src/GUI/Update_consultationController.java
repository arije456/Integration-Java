/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Consultation;
//import Entities.Programme;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
//import Services.Programmeservices;
import Services.consultationservices;
import javafx.scene.Node;

/**
 * FXML Controller class
 *
 * @author GAMRA
 */
public class Update_consultationController implements Initializable {

    private TextField add_nom;
    private TextField add_prenom;
    private TextField add_sexe;
    private TextField add_age;
    private TextField add_mail;
    private TextArea add_etat;
    private TextField add_date;
    private TextField add_categorie;
    @FXML
    private Button modifier;
    @FXML
    private TextField mod_id;
    @FXML
    private TextField mod_nom;
    @FXML
    private TextField mod_prenom;
    @FXML
    private TextField mod_sexe;
    @FXML
    private TextField mod_age;
    @FXML
    private TextField mod_mail;
    @FXML
    private TextArea mod_etat;
    @FXML
    private TextField mod_date;
    @FXML
    private TextField mod_categorie;
    @FXML
    private Button GOBACK;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         consultationservices as = new consultationservices();
        
        
        try
        {
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("afficher_consultation.fxml"));
          Stage prStage = new Stage();
            Parent root= loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            Afficher_consultationController ac = loader.getController();
           int id = Integer.parseInt(Afficher_consultationController.idxx);
            System.out.println(id);
            
           mod_nom.setText(as.getNombyID(id));
           mod_age.setText(String.valueOf(as.getAgebyId(id)));
           mod_sexe.setText(as.getSexebyId(id));
           mod_date.setText(as.getadd_datebyID(id));
           mod_etat.setText(as.getadd_etatbyID(id));
           mod_categorie.setText(as.getadd_categoriebyID(id));
           mod_prenom.setText(as.getadd_prenombyID(id));
           mod_mail.setText(as.getadd_mailbyID(id));
           
         
        } catch (IOException ex) {
            Logger.getLogger(Update_consultationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void modifier_consult(ActionEvent event) throws IOException {
          FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("afficher_consultation.fxml"));
            Stage prStage = new Stage();
            Parent root =loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            
            Afficher_consultationController ac = loader.getController();
            consultationservices as = new consultationservices();
            Consultation p = new Consultation();
            
            int Id= Integer.parseInt(Afficher_consultationController.idxx);
            System.out.println(Id);
            p.setNom(mod_nom.getText());
            p.setAge(Integer.parseInt(mod_age.getText()));
            p.setSexe(mod_sexe.getText());
            p.setDate_rdv(mod_date.getText());
            p.setEtat_physique(mod_etat.getText());
            p.setcategorie_c(mod_categorie.getText());
            p.setPrenom(mod_prenom.getText());
            p.setEmail(mod_mail.getText());
            
            as.modifier(p, Id);
             Alert alert = new Alert (Alert.AlertType.INFORMATION);
             alert.setTitle("succes");
             alert.setHeaderText("!!! Modification effectuer avec suucces !!!");
             alert.setContentText("succes");
             alert.showAndWait();
    }

    @FXML
    private void GOBACK_TOACCUEIL(ActionEvent event) throws IOException {
        
         Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }
   
           
       
    }


    

