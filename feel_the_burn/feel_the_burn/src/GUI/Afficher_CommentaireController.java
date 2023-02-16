/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Services.CommentaireService;
import Entities.Commentaire;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ARIJ
 */
public class Afficher_CommentaireController implements Initializable {

    @FXML
     TableView<Commentaire> table_comm;
    @FXML
    private TableColumn<Commentaire,Integer> Id_aff;
    @FXML
    private TableColumn<Commentaire, String> Nom_aff;
    @FXML
    private TableColumn<Commentaire, String> Email_aff;
    @FXML
    private TableColumn<Commentaire, String> Message_aff;
    @FXML
    private TableColumn<Commentaire, String> Date_aff;
    @FXML
    private TableColumn<Commentaire, String> article_aff;
    @FXML
    private TableColumn<Commentaire, Integer> b_aff;
    @FXML
    private Button supprimer;
    @FXML
    private Button modifier;
    public static String idxx;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private Label datee;
    @FXML
    private Button exit;
    @FXML
    private TextField Date_occ;
    @FXML
    private TextField nb_occ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Timenow();
        CommentaireService cs= new CommentaireService();
        
        table_comm.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
              Id_aff.setText(String.valueOf(cs.liste2().get(table_comm.getSelectionModel().getSelectedIndex()).getId_com()
               ));
               Nom_aff.setText( cs.liste2().get(table_comm.getSelectionModel().getSelectedIndex()).getNom_c());
               Email_aff.setText((cs.liste2().get(table_comm.getSelectionModel().getSelectedIndex()).getEmail()));
               Message_aff.setText(cs.liste2().get(table_comm.getSelectionModel().getSelectedIndex()).getMessage());
               Date_aff.setText((cs.liste2().get(table_comm.getSelectionModel().getSelectedIndex()).getDate() ));
               article_aff.setText((cs.liste2().get(table_comm.getSelectionModel().getSelectedIndex()).getNom_article() ));
               b_aff.setText(String.valueOf(cs.liste2().get(table_comm.getSelectionModel().getSelectedIndex()).getId_b() ));
               
            };
        });
        
        
                 ObservableList<Commentaire> list;
                 try
                 {
                     list = cs.getCommentaireList();
                     Id_aff.setCellValueFactory(new PropertyValueFactory<>("Id_com"));
                     Nom_aff.setCellValueFactory(new PropertyValueFactory<>("Nom_c"));
                     Email_aff.setCellValueFactory(new PropertyValueFactory<>("Email"));
                     Message_aff.setCellValueFactory(new PropertyValueFactory<>("Message"));
                     Date_aff.setCellValueFactory(new PropertyValueFactory<>("Date"));
                     article_aff.setCellValueFactory(new PropertyValueFactory<>("Nom_article"));
                     b_aff.setCellValueFactory(new PropertyValueFactory<>("Id_b"));
                    table_comm.setItems(list);
                 } catch (SQLException ex) {
            Logger.getLogger(Afficher_CommentaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }  
    public void delete()
    {
        CommentaireService cs=new CommentaireService();
       cs.supprimerCommentaire(table_comm.getSelectionModel().getSelectedItem().getId_com());
        System.out.println(table_comm.getSelectionModel().getSelectedItem().getId_com());
    }
    @FXML
    private void list_comm(MouseEvent event) {
    }

    @FXML
    private void supprimer_com(ActionEvent event) {
        delete();
        table_comm.getItems().removeAll(table_comm.getSelectionModel().getSelectedItem());
        System.out.println(table_comm);
        table_comm.refresh();
    }

    @FXML
    private void modifier_com(ActionEvent event) throws IOException {
        idxx= (Integer.toString(table_comm.getSelectionModel().getSelectedItem().getId_com()));
               
        FXMLLoader loader = new FXMLLoader();
       
                Stage prStage =new Stage(); 
                loader.setLocation(getClass().getResource("Modifier_comm.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                prStage.setScene(scene);
                prStage.show();
    }

    
    
    private volatile boolean stop = false;
    private void Timenow() {
        Thread thread = new Thread (() -> {
         SimpleDateFormat sdf = new SimpleDateFormat ("hh:mm:ss a");
         while (!stop) {
             try {
                 Thread.sleep(1000);
         } catch(Exception e) {
             System.out.println(e);
         }
             final String timenow = sdf.format(new Date());
             Platform.runLater(() -> {
             datee.setText(timenow); 
             } );
         }
        }
        );
        thread.start();
    
    }
    Stage stage;
    @FXML
    private void exit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("do you want to logout ?");
        if(alert.showAndWait().get() == ButtonType.OK) {
        stage = (Stage) scenePane.getScene().getWindow();
        System.out.println("you successfully logged out ");
        stop = true;
        stage.close();}
    }

    @FXML
    private void Date_occ(KeyEvent event) {
         CommentaireService bs= new CommentaireService();
                      String k = null;
                      if (event.getCode().equals(KeyCode.ENTER)){
                      k=(Date_occ.getText());
                      nb_occ.setText(bs.calculer_nbseance(k));}
    }
    
}
