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
import Entities.Activite;
import Services.ActiviteService;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import GUI.Afficher_ActiviteController;

/**
 * FXML Controller class
 *
 * @author zeine
 */
public class Ajouter_ActiviteController implements Initializable {

    @FXML
    private TextField add_id_a;
    @FXML
    private TextField add_nom_a;
    @FXML
    private TextField add_cat;
    @FXML
    private TextField add_type;
    @FXML
    private TextField add_id_e;
    @FXML
    private Button add_act;
    @FXML
    private Button add_annuler;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
     private boolean ValidateFields() {
        if(add_id_a.getText().isEmpty() | add_nom_a.getText().isEmpty() | add_act.getText().isEmpty() |  add_type.getText().isEmpty() | add_id_e.getText().isEmpty()) {
             Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validate Fields");
        alert.setHeaderText(null);
        alert.setContentText("Please enter into the fields");
        alert.showAndWait();
        return false;
        }
        return true;
    }
    private boolean ValidateFields2() {
        ActiviteService bs= new ActiviteService();
        if(Integer.parseInt(bs.calculer_nbAct(add_nom_a.getText())) != 0) {
             Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validate Fields");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a non existant course name");
       // bs.calculer_nbseance(Add_Nom_Co.getText())
       //alert.setContentText(bs.calculer_nbseance(Add_Nom_Co.getText()));
        alert.showAndWait();
        return false;
        }
        return true;
    }

    @FXML
    private void add_act(ActionEvent event) {
       
        Activite a2= new Activite();
          if(ValidateFields()){
            if(ValidateFields2()){
        
        a2.setId_a(Integer.valueOf(add_id_a.getText()));
        a2.setNom_a(add_nom_a.getText());
        a2.setCat_age(Integer.valueOf(add_cat.getText()));
        a2.setType(add_type.getText());
        a2.setId_enfant(Integer.valueOf(add_id_e.getText()));
        ActiviteService acs = new ActiviteService();
        
        acs.ajouterActivite(a2);
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("succes");
        alert.setHeaderText(null);
        alert.setContentText("!!!succes !!!");
        alert.showAndWait();
        
   
        
    //  ObservableList<Activite> lista;
      FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Activite.fxml"));
      
       try{
           
           Parent root = loader.load();
           Afficher_ActiviteController ac = loader.getController();
           //ac.loadData();
           //ac.setAff_act(as.afficherActivite().toString());
           add_id_a.getScene().setRoot(root);
           
           
       }
       catch(IOException ex){
           System.out.println(ex.getMessage());
       }
    }}}

    @FXML
    private void annuler_act(ActionEvent event) {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Activite.fxml"));
        Activite a2= new Activite();
        a2.setId_a(Integer.valueOf(add_id_a.getText()));
      
        try{
            Parent root = loader.load();
           Afficher_ActiviteController ac = loader.getController();
           add_id_a.getScene().setRoot(root);
        }
        catch(IOException ex){
           System.out.println(ex.getMessage());
       }
    }

    
}

