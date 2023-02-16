/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Services.CoursServices;
import Entities.Cours;
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
import GUI.Afficher_CoursController;
import javafx.scene.Node;

/**
 * FXML Controller class
 *
 * @author WAEL
 */
public class Update_CoursController implements Initializable {

    @FXML
    private TextField update_Id_C;
    @FXML
    private TextField update_Nom_C;
    @FXML
    private TextField update_Date__C;
    @FXML
    private TextField update_Id_Co;
    @FXML
    private Button Valider_update_cours;
    @FXML
    private Button afficher_cours;
    @FXML
    private Button GOBACK;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        CoursServices as = new CoursServices();
        
        
        try
        {
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("Afficher_Cours.fxml"));
          Stage prStage = new Stage();
            Parent root= loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            Afficher_CoursController ac = loader.getController();
           int id = Integer.parseInt(Afficher_CoursController.idxx);
            System.out.println(id);
            
           update_Nom_C.setText(as.getNombyID(id));
           update_Date__C.setText(as.getDatebyID(id));
           update_Id_Co.setText(as.getId_CobyID(id).toString());
           
         
        } catch (IOException ex) {
            Logger.getLogger(Update_CoursController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO
    }    

    @FXML
    private void Valider_update_cours(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Afficher_Cours.fxml"));
            Stage prStage = new Stage();
            Parent root= loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            
            Afficher_CoursController ac = loader.getController();
            CoursServices as = new CoursServices();
            Cours a = new Cours();
            
            int id= Integer.parseInt(Afficher_CoursController.idxx);
            System.out.println(id);
            a.setNom_C(update_Nom_C.getText());
            a.setDate_C(update_Date__C.getText());
            a.setId_Co(Integer.parseInt(update_Id_Co.getText()));
            
            as.modifier(a, id);
             Alert alert = new Alert (Alert.AlertType.INFORMATION);
             alert.setTitle("succes");
             alert.setHeaderText("!!! Modification effectuer avec suucces !!!");
             alert.setContentText("succes");
             alert.showAndWait();
    }

    @FXML
    private void afficher_cours(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Afficher_Cours.fxml"));
        //Afficher_ActiviteController ac = loader.getController();
        //Afficher_ActiviteController aac = new Afficher_ActiviteController();
        //aac.tab_act.refresh();
         try{
            Parent root = loader.load();
           Afficher_CoursController ac = loader.getController();
           update_Id_C.getScene().setRoot(root);
           ac.tablecours.refresh();
        }
        catch(IOException ex){
           System.out.println(ex.getMessage());
       }
    }

    @FXML
    private void GOBACK_TOACCUEIL(ActionEvent event) throws IOException {
        
         Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }
    
}
