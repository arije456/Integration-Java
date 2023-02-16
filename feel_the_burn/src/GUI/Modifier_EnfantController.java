/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import Entities.Enfant;
import Services.EnfantServices;
import GUI.Afficher_EnfantController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author zeine
 */
public class Modifier_EnfantController implements Initializable {

    @FXML
    private TextField mod_ide1;
    @FXML
    private TextField mod_nome1;
    @FXML
    private TextField mod_prenom1;
    @FXML
    private TextField mod_age;
    @FXML
    private TextField mod_sexe1;
    @FXML
    private TextField mod_photo1;
    @FXML
    private TextField mod_idu1;
    @FXML
    private Button modif_enf;
    @FXML
    private Button aff_enf;
    @FXML
    private Button GOBACK;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        EnfantServices es = new EnfantServices();
        
        try{
            FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("Afficher_Enfant.fxml"));
          Stage prStage = new Stage();
            Parent root= loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            
            Afficher_EnfantController ec = loader.getController();
             int id = Integer.parseInt(Afficher_EnfantController.idxx);
            System.out.println(id);
            mod_nome1.setText(es.getNomEbyID(id));
            mod_prenom1.setText(es.getPrenomEbyID(id));
            mod_age.setText(es.getAgebyId(id).toString());
            mod_sexe1.setText(es.getSexebyID(id));
            mod_photo1.setText(es.getPhotobyID(id));
            mod_idu1.setText(es.getID_UserbyId(id).toString());
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(Modifier_EnfantController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void modifier_enfant(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Afficher_Enfant.fxml"));
            Stage prStage = new Stage();
            Parent root= loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            
            Afficher_EnfantController ec = loader.getController();
            EnfantServices es = new EnfantServices();
            Enfant e = new Enfant();
            
            int id= Integer.parseInt(Afficher_EnfantController.idxx);
            System.out.println(id);
            e.setNom(mod_nome1.getText());
            e.setPrenom(mod_prenom1.getText());
            e.setAge(Integer.parseInt(mod_age.getText()));
            e.setSexe(mod_sexe1.getText());
            e.setPhoto(mod_photo1.getText());
            e.setId_a(Integer.parseInt(mod_idu1.getText()));
            
            es.modifier(e, id);
            Alert alert = new Alert (Alert.AlertType.INFORMATION);
             alert.setTitle("succes");
             alert.setHeaderText("!!! Modification effectuer avec suucces !!!");
             alert.setContentText("succes");
             alert.showAndWait();
    }

    @FXML
    private void afficher_enfant(ActionEvent event) {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("Afficher_Enfant.fxml"));
        //Afficher_ActiviteController ac = loader.getController();
        //Afficher_ActiviteController aac = new Afficher_ActiviteController();
        //aac.tab_act.refresh();
         try{
            Parent root = loader.load();
           Afficher_EnfantController ac = loader.getController();
           mod_ide1.getScene().setRoot(root);
           ac.tab_enf.refresh();
        }
        catch(IOException ex){
           System.out.println(ex.getMessage());
       }
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
