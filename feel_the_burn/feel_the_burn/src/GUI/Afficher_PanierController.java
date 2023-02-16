/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Panier;
import Entities.Produit;
import Services.PanierService;
import Services.ProduitService;
import static GUI.Afficher_ProduitController.idxx;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import javafx.application.Platform;
import java.util.Date;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
 import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author rekik
 */
public class Afficher_PanierController implements Initializable {
 @FXML
    private TableView<Panier> tab_pan;
    @FXML
    private TableColumn<Panier,Integer > aj_id_pa;
    @FXML
    private TableColumn<Panier,Integer > aj_q;
    @FXML
    private TableColumn<Panier, String> aj_coupon;
    @FXML
    private TableColumn<Panier, Integer> aj_id_p;
   
    @FXML
    private Button update_pan;
    @FXML
    private Button supp_pan;
     public static String idxx;
    @FXML
    private Button exit;
    @FXML
    private TextField nb_occ;
    @FXML
    private TextField nb_occ2;
    @FXML
    private Label date;
    @FXML
    private AnchorPane scenePane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Timenow();
        PanierService ps= new PanierService();
        
        tab_pan.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                aj_id_pa.setText(String.valueOf(ps.liste2().get(tab_pan.getSelectionModel().getSelectedIndex()).getId_Pa()
            ));
            aj_q.setText(String.valueOf(ps.liste2().get(tab_pan.getSelectionModel().getSelectedIndex()).getQuantite() ));
            aj_coupon.setText( ps.liste2().get(tab_pan.getSelectionModel().getSelectedIndex()).getCoupon());
            aj_id_p.setText(String.valueOf(ps.liste2().get(tab_pan.getSelectionModel().getSelectedIndex()).getId_P() ));
               
            };
        });
        
        
                 ObservableList<Panier> list;
                 try
                 {
                     list = ps.getPanierList();
                     System.out.println(list);
                     aj_id_pa.setCellValueFactory(new PropertyValueFactory<>("Id_Pa"));
                     aj_q.setCellValueFactory(new PropertyValueFactory<>("Quantite"));
                     aj_coupon.setCellValueFactory(new PropertyValueFactory<>("Coupon"));
                     aj_id_p.setCellValueFactory(new PropertyValueFactory<>("Id_P"));
                     
                    tab_pan.setItems(list);
                 } catch (SQLException ex) {
            Logger.getLogger(Afficher_PanierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

   


       
  

    @FXML
    private void update_pan(ActionEvent event) throws IOException {
        idxx= (Integer.toString(tab_pan.getSelectionModel().getSelectedItem().getId_Pa()));
               
        FXMLLoader loader = new FXMLLoader();
       
                Stage prStage =new Stage(); 
                loader.setLocation(getClass().getResource("update_panier.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                prStage.setScene(scene);
                prStage.show();
    }
public void delete()
    {
      PanierService pa = new PanierService();
      pa.supprimerPanier(tab_pan.getSelectionModel().getSelectedItem().getId_Pa());
      System.out.println(tab_pan.getSelectionModel().getSelectedItem().getId_Pa());
    }
    @FXML
    private void supp_pan(ActionEvent event) {
        delete();
        tab_pan.getItems().removeAll(tab_pan.getSelectionModel().getSelectedItem());
        System.out.println(tab_pan);
        
        
    }

    @FXML
    private void list_pan(MouseEvent event) {
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

    //Date hedhi lwakt
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
             date.setText(timenow); 
             } );
         }
        }
        );
        thread.start();
    
    }
    
    @FXML
    private void nb_occ(KeyEvent event) {
        PanierService bs= new PanierService();
        
                      String k = null;
                      if (event.getCode().equals(KeyCode.ENTER)){
                      k=(nb_occ.getText());
                      nb_occ2.setText(bs.calculer_nbcoupon(k));}
      
    }

    @FXML
    private void nb_occ2(KeyEvent event) {
    }
    
}
