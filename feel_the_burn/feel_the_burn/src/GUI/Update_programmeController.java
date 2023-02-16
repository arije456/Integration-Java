/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Programme;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Services.Programmeservices;

/**
 * FXML Controller class
 *
 * @author GAMRA
 */
public class Update_programmeController implements Initializable {

    private TextField id;
    private TextField nom;
    private TextField date_col;
    private TextField id_kine;
    private TextArea des;
    private TextField id_c;
    @FXML
    private Button modifier;
    @FXML
    private TextField mod_id;
    @FXML
    private TextField mod_nom;
    @FXML
    private TextField mod_dater;
    @FXML
    private TextField mod_id_kine;
    @FXML
    private TextArea mod_des;
    @FXML
    private TextField mod_id_c;
   

    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Programmeservices as = new Programmeservices();
        
        
        try
        {
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("afficher_programme.fxml"));
          Stage prStage = new Stage();
            Parent root= loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);  
            Afficher_programmeController ac = loader.getController();
           int id_line = Integer.parseInt(Afficher_programmeController.idxx);
            System.out.println(id_line);
           //mod_id.setText(as.getId_cbyId(id_line).toString());
           mod_nom.setText(as.getNombyID(id_line));
           mod_dater.setText(as.getDate_rbyId(id_line));  
           mod_id_kine.setText(as.getId_kinebyId(id_line).toString());
            mod_des.setText(as.getdescriptionbyID(id_line));
           mod_id_c.setText(as.getId_cbyId(id_line).toString()); 
           
         
         
        } catch (IOException ex) {
            Logger.getLogger(Update_programmeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO
    }    

    @FXML
    private void modifier_programme(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("afficher_programme.fxml"));
            Stage prStage = new Stage();
            Parent root =loader.load();
            Scene scene = new Scene(root); 
            prStage.setScene(scene);
            
            Afficher_programmeController ac = loader.getController();
            Programmeservices as = new Programmeservices();
            Programme p = new Programme();
            
            int Id= Integer.parseInt(Afficher_programmeController.idxx);
            System.out.println(Id);
            
            as.modifier(p, Id);
            
            p.setNom_p(mod_nom.getText());
            p.setDate_r(mod_dater.getText());
            p.setId_kine(Integer.parseInt(mod_id_kine.getText()));
            p.setdescription(mod_des.getText());
            p.setId_c(Integer.parseInt(mod_id_c.getText()));
            
             Alert alert = new Alert (Alert.AlertType.INFORMATION);
             alert.setTitle("succes");
             
             alert.setHeaderText("!!! Modification effectuer avec suucces !!!");
             alert.setContentText("succes");
             alert.showAndWait();
             
            
    }
    
}
