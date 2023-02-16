/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Tools.connexion;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author zeine
 */
public class Prod_StatsController implements Initializable {

    @FXML
    private PieChart Prod_Stats;
     private Statement st;
    private ResultSet rs;
    private Connection cnx;
   ObservableList<PieChart.Data>data=FXCollections.observableArrayList();
    @FXML
    private Button GOBACK;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         cnx = connexion.getInstance().getCnx();
       stat();
    }    

    private void stat() {
        try{
            String query = "SELECT COUNT(*),Prix FROM produit GROUP BY Prix" ;
       
             PreparedStatement PreparedStatement = cnx.prepareStatement(query);
             rs = PreparedStatement.executeQuery();
             while (rs.next()){               
               data.add(new PieChart.Data((String.valueOf(rs.getFloat("Prix"))),rs.getInt("COUNT(*)")));
            }  
             
        } catch (SQLException ex) {
            Logger.getLogger(Afficher_ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         Prod_Stats.setTitle("**Statistiques des Prix des Produit**");
        Prod_Stats.setLegendSide(Side.LEFT);
        Prod_Stats.setData(data);
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
