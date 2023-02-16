/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Services.BlogService;
import Entities.Blog;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import GUI.Afficher_BlogController;

/**
 * FXML Controller class
 *
 * @author ARIJ
 */
public class Modifier_blogController implements Initializable {

    @FXML
    private TextField Id_bmod;
    @FXML
    private TextField titre_mod;
    @FXML
    private TextField contenu_mod;
    @FXML
    private TextField image_mod;
    @FXML
    private TextField auteur_mod;
    @FXML
    private TextField date_mod;
    @FXML
    private TextField categorie_mod;
    @FXML
    private Button modifier;
    @FXML
    private Button retour_blog;
    @FXML
    private Button aff_mod_blog;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BlogService bs = new BlogService();
        
        
        try
        {
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("Afficher_Blog.fxml"));
          Stage prStage = new Stage();
            Parent root= loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            Afficher_BlogController ac = loader.getController();
           int id = Integer.parseInt(Afficher_BlogController.idxx);
            System.out.println(id);
            
           titre_mod.setText(bs.getNombyID(id));
           auteur_mod.setText(bs.getAuteurbyId(id));
           image_mod.setText(bs.getImagebyId(id));
           contenu_mod.setText(bs.getContenubyId(id));
           date_mod.setText(bs.getDatebyID(id));
           categorie_mod.setText(bs.getcategoriebyId(id));
          
        } catch (IOException ex) {
            Logger.getLogger(Modifier_blogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO
    }    

    @FXML
    private void modifier_blog(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Afficher_Blog.fxml"));
            Stage prStage = new Stage();
            Parent root= loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            
            Afficher_BlogController ac = loader.getController();
            BlogService as = new BlogService();
            Blog b = new Blog();
            
            int id= Integer.parseInt(Afficher_BlogController.idxx);
            System.out.println(id);
            b.setTitre(titre_mod.getText());
            b.setAuteur(auteur_mod.getText());
            b.setImage(image_mod.getText());
            b.setContenu(contenu_mod.getText());
            b.setDate(date_mod.getText());
            b.setcategorie(categorie_mod.getText());
            
            as.modifier(b, id);
             Alert alert = new Alert (Alert.AlertType.INFORMATION);
             alert.setTitle("succes");
             alert.setHeaderText("!!! Modification effectuer avec suucces !!!");
             alert.setContentText("succes");
             alert.showAndWait();
    
}

    @FXML
    private void retour_blog(ActionEvent event) throws IOException {
          Parent root = FXMLLoader.load(getClass().getResource("Afficher_Blog.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void afficher_blog(ActionEvent event) {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("Afficher_Blog.fxml"));
        //Afficher_ActiviteController ac = loader.getController();
        //Afficher_ActiviteController aac = new Afficher_ActiviteController();
        //aac.tab_act.refresh();
         try{
            Parent root = loader.load();
           Afficher_BlogController ac = loader.getController();
           Id_bmod.getScene().setRoot(root);
           ac.table_blog.refresh();
        }
        catch(IOException ex){
           System.out.println(ex.getMessage());
       }
    }
}
