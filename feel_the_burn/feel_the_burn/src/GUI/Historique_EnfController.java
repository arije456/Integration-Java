/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import Entities.Enfant;
import Services.EnfantServices;
import GUI.Afficher_EnfantController;
import javafx.fxml.FXMLLoader;
/**
 * FXML Controller class
 *
 * @author zeine
 */
public class Historique_EnfController implements Initializable {

    @FXML
    private ImageView btn_envoyer;
    @FXML
    //private TableView<?> tabhistorique;
    private TableView tabhistorique;
    @FXML
    private Pane retour;
     Set<Enfant> s_enfant = new HashSet<Enfant>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       try{
           afficher();
       } catch (IOException ex) {
            Logger.getLogger(Historique_EnfController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    private void afficher() throws IOException {
          EnfantServices es= new EnfantServices();
          FXMLLoader loader = new FXMLLoader();
          Afficher_EnfantController ec = loader.load();
          int ide= es.getID_UserbyId(Integer.parseInt(Afficher_EnfantController.idxx));
          List<Enfant> list= es.displayByID(ide);
          
          final ObservableList<Enfant> obsr =FXCollections.observableArrayList(list);
          System.out.println(obsr.toString());
            TableColumn< Enfant, String> column1 = new TableColumn<>("Id commande");
          
    }
    
}
