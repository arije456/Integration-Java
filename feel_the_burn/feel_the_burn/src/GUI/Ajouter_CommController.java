/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Services.CommentaireService;
//import Entities.Blog;
import Entities.Commentaire;
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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ARIJ
 */
public class Ajouter_CommController implements Initializable {

    @FXML
    private TextField Id_com;
    @FXML
    private TextField Nom_c;
    @FXML
    private TextField Email;
    @FXML
    private TextField Message;
    @FXML
    private TextField Date;
    @FXML
    private TextField Nom_article;
    @FXML
    private TextField Id_b;
    @FXML
    private Button ajouter;
    @FXML
    private Button afficher;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
    }    

  private boolean ValidateFields() {
        if(Nom_c.getText().isEmpty() | Id_com.getText().isEmpty() | Email.getText().isEmpty() |  Message.getText().isEmpty() | Date.getText().isEmpty() | Nom_article.getText().isEmpty() | Id_b.getText().isEmpty()) {
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
        CommentaireService bs= new CommentaireService();
        if(Integer.parseInt(bs.calculer_nbseance(Nom_c.getText())) != 0) {
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
    private void ajouter_com(ActionEvent event) {
         Commentaire c1=new Commentaire();
          if(ValidateFields()){
            if(ValidateFields2()){
        c1.setId_com(Integer.valueOf(Id_com.getText()));
        c1.setNom_c(Nom_c.getText());
        c1.setEmail(Email.getText());
        c1.setMessage(Message.getText());
        c1.setDate(Date.getText());
        c1.setNom_article(Nom_article.getText());
        c1.setId_b(Integer.valueOf(Id_b.getText()));
        CommentaireService cs = new CommentaireService();
        cs.ajouterCommentaire(c1);
        
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("succes");
        alert.setHeaderText(null);
        alert.setContentText("!!!succes !!!");
        alert.showAndWait();
        
        
        
        
        
        
      ObservableList<Commentaire> listc;
      FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Commentaire.fxml"));
      
       try{
           
           Parent root = loader.load();
           Afficher_CommentaireController ac = loader.getController();
           //ac.loadData();
           //ac.setAff_act(as.afficherActivite().toString());
           Id_b.getScene().setRoot(root);
           
           
       }
       catch(IOException ex){
           System.out.println(ex.getMessage());
       }
    }
          }}
    @FXML
    private void afficher_com(ActionEvent event) {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Commentaire.fxml"));
        Commentaire c1=new Commentaire();
        c1.setId_com(Integer.valueOf(Id_com.getText()));
      
        try{
            Parent root = loader.load();
           Afficher_CommentaireController cs = loader.getController();
           Id_com.getScene().setRoot(root);
        }
        catch(IOException ex){
           System.out.println(ex.getMessage());
       }

    }
}
    


    

    
    
