
package GUI;

import Services.CommentaireService;
import Entities.Commentaire;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import GUI.Afficher_CommentaireController;


public class Modifier_commController implements Initializable {

    private TextField contenu_reponse;
    @FXML
    private Button modifier;
    @FXML
    private Button afficher;
    @FXML
    private TextField Id_mod;
    @FXML
    private TextField Nom_mod;
    @FXML
    private TextField Email_mod;
    @FXML
    private TextField Message_mod;
    @FXML
    private TextField Date_mod;
    @FXML
    private TextField Article_mod;
    @FXML
    private TextField b_mod;
    @FXML
    private Button GOBACK;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CommentaireService as = new CommentaireService();
        
        
        try
        {
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("Afficher_Commentaire.fxml"));
          Stage prStage = new Stage();
            Parent root= loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            Afficher_CommentaireController ac = loader.getController();
           int id = Integer.parseInt(Afficher_CommentaireController.idxx);
            System.out.println(id);
            
           Nom_mod.setText(as.getNombyID(id));
           Email_mod.setText(as.getEmailbyId(id));
           Message_mod.setText(as.getMessagebyID(id));
           Date_mod.setText(as.getDatebyID(id));
           Article_mod.setText(as.getNom_articlebyID(id));
           b_mod.setText(as.getId_bbyId(id).toString());
           
           
         
        } catch (IOException ex) {
            Logger.getLogger(Modifier_commController.class.getName()).log(Level.SEVERE, null, ex);
        }


        // TODO
    }    

    @FXML
    private void modifier_com(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Afficher_Commentaire.fxml"));
            Stage prStage = new Stage();
            Parent root= loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            
            Afficher_CommentaireController ac = loader.getController();
            CommentaireService cs = new CommentaireService();
            Commentaire c = new Commentaire();
            
            int id= Integer.parseInt(Afficher_CommentaireController.idxx);
            System.out.println(id);
            c.setNom_c(Nom_mod.getText());
            c.setEmail(Email_mod.getText());
            c.setMessage(Message_mod.getText());
             c.setDate(Date_mod.getText());
              c.setNom_article(Article_mod.getText());
            c.setId_b(Integer.parseInt(b_mod.getText()));
            
            cs.modifier(c, id);
             Alert alert = new Alert (Alert.AlertType.INFORMATION);
             alert.setTitle("succes");
             alert.setHeaderText("!!! Modification effectuer avec suucces !!!");
             alert.setContentText("succes");
             alert.showAndWait();
        
    }

    @FXML
    private void afficher_com(ActionEvent event) {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("Afficher_Commentaire.fxml"));
        //Afficher_ActiviteController ac = loader.getController();
        //Afficher_ActiviteController aac = new Afficher_ActiviteController();
        //aac.tab_act.refresh();
         try{
            Parent root = loader.load();
           Afficher_CommentaireController ac = loader.getController();
           Id_mod.getScene().setRoot(root);
           ac. table_comm.refresh();
        }
        catch(IOException ex){
           System.out.println(ex.getMessage());
       }
    }

    private void retour_com(ActionEvent event) throws IOException {
          Parent root = FXMLLoader.load(getClass().getResource("Afficher_Comm.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
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
