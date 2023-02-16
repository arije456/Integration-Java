/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Panier;

import Services.PanierService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author rekik
 */
public class Ajouter_PanierController implements Initializable {

    @FXML
    private TextField aj_id_pa;
    @FXML
    private TextField aj_quantite;
    @FXML
    private TextField aj_coupon;
    @FXML
    private TextField aj_id_p;
    
    @FXML
    private Button afficher_pan;
    @FXML
    private Button ajouter_pan;
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

     private boolean ValidateFields() {
        if(aj_id_pa.getText().isEmpty() | aj_quantite.getText().isEmpty() | aj_coupon.getText().isEmpty() |  aj_id_p.getText().isEmpty()) {
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
        PanierService bs= new PanierService();
        if(Integer.parseInt(bs.calculer_nbcoupon(aj_coupon.getText())) != 0) {
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
    private void ajouter_pan(ActionEvent event) {
        
          Panier pa= new Panier();
          if(ValidateFields()){
            if(ValidateFields2()){
        pa.setId_Pa(Integer.valueOf(aj_id_pa.getText()));
        pa.setQuantite(Integer.valueOf(aj_quantite.getText()));
        pa.setCoupon(aj_coupon.getText());
        pa.setId_P(Integer.valueOf(aj_id_p.getText()));        
        PanierService psa = new PanierService();
        psa.ajouterPanier(pa);
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("succes");
        alert.setHeaderText(null);
        alert.setContentText("!!!succes !!!");
        alert.showAndWait();
        
        ObservableList<Panier> listpa;
      FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Panier.fxml"));
      
       try{
           
           Parent root = loader.load();
           Afficher_PanierController ac = loader.getController();
           //ac.loadData();
           //ac.setAff_act(as.afficherActivite().toString());
           aj_id_pa.getScene().setRoot(root);
           
           
           
       }
       catch(IOException ex){
           System.out.println(ex.getMessage());
       }
    }
       
          }}   
       
        
        
      @FXML
    private void afficher_pan(ActionEvent event) {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Panier.fxml"));
        
      
        try{
            Parent root = loader.load();
           Afficher_PanierController ap= loader.getController();
           
           aj_id_pa.getScene().setRoot(root);
        }
        catch(IOException ex){
           System.out.println(ex.getMessage());
       }
    }

    }

      
        
    
 

    
    
 


