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
import javafx.scene.chart.PieChart;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import Tools.connexion;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Side;

/**
 * FXML Controller class
 *
 * @author zeine
 */
public class Consultation_StatsController implements Initializable {

    @FXML
    private PieChart cons_stat;
    private Statement st;
    private ResultSet rs;
    private Connection cnx;
        ObservableList<PieChart.Data>data=FXCollections.observableArrayList();
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          cnx = connexion.getInstance().getCnx();
       stat();
    }    

    private void stat() {
        try{
            String query = "SELECT COUNT(*),Sexe FROM consultation GROUP BY Sexe" ;
       
             PreparedStatement PreparedStatement = cnx.prepareStatement(query);
             rs = PreparedStatement.executeQuery();
             while (rs.next()){               
               data.add(new PieChart.Data(rs.getString("Sexe"),rs.getInt("COUNT(*)")));
            }  
             
        } catch (SQLException ex) {
            Logger.getLogger(Afficher_consultationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         cons_stat.setTitle("**Statistiques des Sexes qui ont fait une Consultation**");
        cons_stat.setLegendSide(Side.LEFT);
        cons_stat.setData(data);
    }
    
}
