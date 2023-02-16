/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Panier;
import Services.PanierService;
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
public class Update_panierController implements Initializable {

    @FXML
    private TextField up_id_pa;
    @FXML
    private TextField up_q;
    @FXML
    private TextField up_coupon;
    @FXML
    private TextField up_id_p;
    @FXML
    private Button up_valider;
   /*@FXML
    private Button up_valider;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
         // TODO
        PanierService ps = new PanierService();
        
        
        try
        {
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("Afficher_Panier.fxml"));
          Stage prStage = new Stage();
            Parent root= loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            Afficher_PanierController ac = loader.getController();
           int id = Integer.parseInt(Afficher_PanierController.idxx);
           
            System.out.println(id);
            
           up_id_pa.setText(String.valueOf(id));
          // up_q.setText(ps.getQuantitebyID(id).toString());
          up_q.setText(String.valueOf(ps.getQuantitebyID(id)));

           up_coupon.setText(ps.getCouponyID(id));
           up_id_p.setText(String.valueOf(ps.getId_PbyId(id)));
           

           
         
        } catch (IOException ex) {
            Logger.getLogger(Update_panierController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void up_valider(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Afficher_Panier.fxml"));
            Stage prStage = new Stage();
            Parent root= loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            
            Afficher_PanierController ac = loader.getController();
            PanierService as = new PanierService();
            Panier a = new Panier();
            
            int id= Integer.parseInt(Afficher_PanierController.idxx);
            System.out.println(id);
            a.setQuantite(Integer.parseInt(up_q.getText()));
            
            a.setCoupon(up_coupon.getText());
            a.setId_P(Integer.parseInt(up_id_p.getText()));
             //as.updatePanier(Integer.valueOf(up_id_pa.getText()),Integer.valueOf(up_q.getText()),up_coupon.getText(),Integer.valueOf(up_id_p.getText()));
            as.modifier(a, id);
             Alert alert = new Alert (Alert.AlertType.INFORMATION);
             alert.setTitle("succes");
             alert.setHeaderText("!!! Modification effectuer avec suucces !!!");
             alert.setContentText("succes");
             alert.showAndWait();
             FXMLLoader loader1= new FXMLLoader(getClass().getResource("Afficher_Panier.fxml"));
        
        try{
           
           Parent root2 = loader1.load();
           Afficher_PanierController ac1 = loader.getController();
           
           up_id_pa.getScene().setRoot(root2);
    
         
           
           
       }
       catch(IOException ex){
           System.out.println(ex.getMessage());
       }
    
}
}
