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
import Entities.Produit;
import Services.ProduitService;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author zeine
 */
public class Ajouter_ProduitController implements Initializable {

    @FXML
    private TextField aj_id;
    @FXML
    private TextField aj_nom;
    @FXML
    private TextField aj_prix;
    @FXML
    private TextField aj_photo;
    @FXML
    private TextField aj_cat;
    @FXML
    private Button ajouter_prod;

    
    @FXML
    private Button afficher_prod;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private boolean ValidateFields() {
        if(aj_id.getText().isEmpty() | aj_nom.getText().isEmpty() | aj_prix.getText().isEmpty() |  aj_photo.getText().isEmpty() | aj_cat.getText().isEmpty()) {
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
        ProduitService bs= new ProduitService();
        if(Integer.parseInt(bs.calculer_nbProd(aj_nom.getText())) != 0) {
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
    private void add_prod(ActionEvent event) {
        Produit p1= new Produit();
        if(ValidateFields()){
            if(ValidateFields2()){
        p1.setId_P(Integer.valueOf(aj_id.getText()));
        p1.setNom_P(aj_nom.getText());
        p1.setPrix(Float.valueOf(aj_prix.getText()));
        p1.setPhoto(aj_photo.getText());
        p1.setCategories(aj_cat.getText());
        
        ProduitService ps = new ProduitService();
        ps.ajouterProduit(p1);
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("succes");
        alert.setHeaderText(null);
        alert.setContentText("!!!succes !!!");
        alert.showAndWait();
        
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Produit.fxml"));
        
        try{
           
           Parent root = loader.load();
           Afficher_ProduitController ac = loader.getController();
           //ac.loadData();
           //ac.setAff_act(as.afficherActivite().toString());
           aj_id.getScene().setRoot(root);
           
           
       }
       catch(IOException ex){
           System.out.println(ex.getMessage());
       }
    }}}

    @FXML
    private void afficher_prod(ActionEvent event) {
         FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Produit.fxml"));
        try{
           
           Parent root = loader.load();
           Afficher_ProduitController ac = loader.getController();
           
           aj_id.getScene().setRoot(root);
           
           
       }
       catch(IOException ex){
           System.out.println(ex.getMessage());
       }
    }
    
}
