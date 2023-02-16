/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Services.CoachingServices;
import Entities.Coaching;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author WAEL
 */
public class Afficher_CoachingController implements Initializable {

    @FXML
     TableView<Coaching> table_Coaching;
    @FXML
    private TableColumn<Coaching, Integer> afficher_Id_S;
    @FXML
    private TableColumn<Coaching, String> afficher_Nom_User;
    @FXML
    private TableColumn<Coaching, String> afficher_Prenom_User;
    @FXML
    private TableColumn<Coaching, String> afficher_Date_S;
    @FXML
    private TableColumn<Coaching, Float> afficher_Prix;
    @FXML
    private TableColumn<Coaching, Integer> afficher_Id_Co;
    @FXML
    private Button Modifier_Coaching;
    public static String idxx;
    @FXML
    private TextField Total;
    @FXML
    private TextField Date;
    @FXML
    private TextField nom_occ;
    public void delete()
    {
        CoachingServices cs=new CoachingServices();
       cs.supprimerCoaching(table_Coaching.getSelectionModel().getSelectedItem().getId_S());
        System.out.println(table_Coaching.getSelectionModel().getSelectedItem().getId_S());
    }
    @FXML
    private Button Supprimer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        // TODO
        
        CoachingServices bs= new CoachingServices();
        
        table_Coaching.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
       
                afficher_Id_S.setText(String.valueOf(bs.liste2().get(table_Coaching.getSelectionModel().getSelectedIndex()).getId_S()
               ));
               afficher_Date_S.setText(bs.liste2().get(table_Coaching.getSelectionModel().getSelectedIndex()).getDate_S());
               afficher_Prix.setText(String.valueOf(bs.liste2().get(table_Coaching.getSelectionModel().getSelectedIndex()).getPrix() ));
               afficher_Id_Co.setText(String.valueOf(bs.liste2().get(table_Coaching.getSelectionModel().getSelectedIndex()).getId_Co() ));
               afficher_Nom_User.setText( bs.liste2().get(table_Coaching.getSelectionModel().getSelectedIndex()).getNom_User());
               afficher_Prenom_User.setText((bs.liste2().get(table_Coaching.getSelectionModel().getSelectedIndex()).getPrenom_User()));
              
               
               
              
               
            };
        });
        
        
                 ObservableList <Coaching> list;
                 try
                 {
                     list = bs.getCoachingList();
                     afficher_Id_S.setCellValueFactory(new PropertyValueFactory<>("Id_S"));
                     afficher_Date_S.setCellValueFactory(new PropertyValueFactory<>("Date_S"));
                     afficher_Prix.setCellValueFactory(new PropertyValueFactory<>("Prix"));
                     afficher_Id_Co.setCellValueFactory(new PropertyValueFactory<>("Id_Co"));
                     afficher_Nom_User.setCellValueFactory(new PropertyValueFactory<>("Nom_User"));
                     afficher_Prenom_User.setCellValueFactory(new PropertyValueFactory<>("Prenom_User"));
                     
                     table_Coaching.setItems(list);
                     
                 } catch (SQLException ex) {
            Logger.getLogger(Afficher_CoachingController.class.getName()).log(Level.SEVERE, null, ex);
        
       
                 }  
    }


    @FXML
    private void Supprimer_Seance(ActionEvent event) {
         delete();
        table_Coaching.getItems().removeAll(table_Coaching.getSelectionModel().getSelectedItem());
        System.out.println(table_Coaching);
        table_Coaching.refresh();
    }

    @FXML
    private void liste_coaching(MouseEvent event) {
        
        
}

    @FXML
    private void Modifier_Coaching(ActionEvent event) throws IOException {
         idxx= (Integer.toString(table_Coaching.getSelectionModel().getSelectedItem().getId_S()));
               
        FXMLLoader loader = new FXMLLoader();
       
                Stage prStage =new Stage(); 
                loader.setLocation(getClass().getResource("Update_Coaching.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                prStage.setScene(scene);
                prStage.show();
    }

    @FXML
    private void Date(KeyEvent event) {
         CoachingServices bs= new CoachingServices();
                      String k = null;
                      if (event.getCode().equals(KeyCode.ENTER)){
                      k=(Date.getText());
                      nom_occ.setText(bs.calculer_nbseance(k));}
    }

    @FXML
    private void nom_occ(KeyEvent event) throws SQLException {
         if (event.getCode().equals(KeyCode.ENTER)){
                        CoachingServices bs= new CoachingServices();
                       ObservableList <Coaching> list;
                       list = bs.getCoachingList();
                       Total.setText(String.valueOf(bs.prixtotal()));
                       //setText(as.getPrixbyID(id).toString());
                       //Coaching co= new Coaching();
                       
                       // nom_occ.setText(k);
    }
}
}