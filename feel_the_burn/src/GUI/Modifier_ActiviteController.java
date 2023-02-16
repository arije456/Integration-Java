
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
import GUI.Afficher_ActiviteController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class Modifier_ActiviteController implements Initializable {

    @FXML
    private TextField mod_ida;
    @FXML
    private TextField mod_nom;
    @FXML
    private TextField mod_cat;
    @FXML
    private TextField mod_type;
    @FXML
    private TextField mod_ide;
    @FXML
    private Button mod_act;
    @FXML
    private Button afficher_act;
    @FXML
    private Button GOBACK;
    @FXML
    private TextField mod_img;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        ActiviteService as = new ActiviteService();
        
        
        try
        {
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("Afficher_Activite.fxml"));
          Stage prStage = new Stage();
            Parent root= loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            Afficher_ActiviteController ac = loader.getController();
           int id = Integer.parseInt(Afficher_ActiviteController.idxx);
            System.out.println(id);
            
           mod_nom.setText(as.getNombyID(id));
           mod_cat.setText(as.getCatAgebyId(id).toString());
           mod_type.setText(as.getTypebyID(id));
           mod_img.setText(as.getImagebyID(id));
           mod_ide.setText(as.getID_EnfantbyId(id).toString());
           
         
        } catch (IOException ex) {
            Logger.getLogger(Modifier_ActiviteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void modifier_act(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Afficher_Activite.fxml"));
           // FXMLLoader loader = new FXMLLoader(getClass().getResource("Afficher_Activite.fxml"));
            Stage prStage = new Stage();
            Parent root= loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            
            Afficher_ActiviteController ac = loader.getController();
            ActiviteService as = new ActiviteService();
            Activite a = new Activite();
            
            int id= Integer.parseInt(Afficher_ActiviteController.idxx);
            System.out.println(id);
            a.setNom_a(mod_nom.getText());
            a.setCat_age(Integer.parseInt(mod_cat.getText()));
            a.setType(mod_type.getText());
            a.setImage(mod_img.getText());
            a.setId_enfant(Integer.parseInt(mod_ide.getText()));
            
           as.modifier(a, id);
           //as.updateActivite(Integer.valueOf(mod_ida.getText()),mod_nom.getText(),Integer.valueOf(mod_cat.getText()),mod_type.getText(),Integer.valueOf(mod_ide.getText()));
             Alert alert = new Alert (Alert.AlertType.INFORMATION);
             alert.setTitle("succes");
             alert.setHeaderText("!!! Modification effectuer avec suucces !!!");
             alert.setContentText("succes");
             alert.showAndWait();
            
         ac.refresh_act(event);
       
    }

    @FXML
    private void afficher_act(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Afficher_Activite.fxml"));
        //Afficher_ActiviteController ac = loader.getController();
        //Afficher_ActiviteController aac = new Afficher_ActiviteController();
        //aac.tab_act.refresh();
         try{
            Parent root = loader.load();
           Afficher_ActiviteController ac = loader.getController();
           mod_ida.getScene().setRoot(root);
           ac.tab_act.refresh();
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
