/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Produit;
import Services.ProduitService;
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
 * @author rekik
 */
public class Update_produitController implements Initializable {

    @FXML
    private TextField up_id;
    @FXML
    private TextField up_nom;
    @FXML
    private TextField up_prix;
    @FXML
    private TextField up_photo;
    @FXML
    private TextField up_cat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ProduitService as = new ProduitService();
        
        
        try
        {
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("Afficher_Produit.fxml"));
          Stage prStage = new Stage();
            Parent root= loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            Afficher_ProduitController ac = loader.getController();
           int id = Integer.parseInt(Afficher_ProduitController.idxx);
            System.out.println(id);
            
           up_id.setText(String.valueOf(id));
           up_nom.setText(as.getNombyID(id).toString());
          
           up_prix.setText(as.getPrixbyId(id).toString());
           
           up_photo.setText(as.getPhotoyID(id));
           
           up_cat.setText(as.getCategoriesbyId(id));
           

           
         
        } catch (IOException ex) {
            Logger.getLogger(Update_produitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void update_prod(ActionEvent event) throws IOException {
        /* ProduitService ps = new ProduitService();
         ps.updateProduit(Integer.valueOf(up_id.getText()),up_nom.getText(),Float.valueOf(up_prix.getText()),up_photo.getText(),up_cat.getText());
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
           up_id.getScene().setRoot(root);
           
           
       }
       catch(IOException ex){
           System.out.println(ex.getMessage());
       }*/
        
        
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Afficher_Produit.fxml"));
            Stage prStage = new Stage();
            Parent root= loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            
            Afficher_ProduitController ac = loader.getController();
            ProduitService as = new ProduitService();
            Produit p = new Produit();
             
            as.updateProduit(Integer.valueOf(up_id.getText()),up_nom.getText(),Float.valueOf(up_prix.getText()),up_photo.getText(),up_cat.getText());
            int id= Integer.parseInt(Afficher_ProduitController.idxx);
            System.out.println(id);
            p.setNom_P(up_nom.getText());
            p.setPrix(Float.parseFloat(up_prix.getText()));
            p.setPhoto(up_photo.getText());
            p.setCategories(up_cat.getText());
            
            
            //as.modifier(p, id);
             Alert alert = new Alert (Alert.AlertType.INFORMATION);
             alert.setTitle("succes");
             alert.setHeaderText("!!! Modification effectuer avec suucces !!!");
             alert.setContentText("succes");
             alert.showAndWait();
             FXMLLoader loader1= new FXMLLoader(getClass().getResource("Afficher_Produit.fxml"));
        
        try{
           
           Parent root1 = loader1.load();
           Afficher_ProduitController ac1 = loader.getController();
           //ac.loadData();
           //ac.setAff_act(as.afficherActivite().toString());
           up_id.getScene().setRoot(root1);
           
           
       }
       catch(IOException ex){
           System.out.println(ex.getMessage());
       }

        
        
        
        
        
        
        
        
        
    }
    
}
