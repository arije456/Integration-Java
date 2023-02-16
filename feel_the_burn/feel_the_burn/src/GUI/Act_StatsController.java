/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

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
public class Act_StatsController implements Initializable {

    @FXML
    private PieChart Act_piechart;

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
            String query = "SELECT COUNT(*),type FROM activite GROUP BY type" ;
       
             PreparedStatement PreparedStatement = cnx.prepareStatement(query);
             rs = PreparedStatement.executeQuery();
             while (rs.next()){               
               data.add(new PieChart.Data(rs.getString("type"),rs.getInt("COUNT(*)")));
            }  
             
        } catch (SQLException ex) {
            Logger.getLogger(Afficher_ActiviteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         Act_piechart.setTitle("**Statistiques nombres des types d'Activités**");
        Act_piechart.setLegendSide(Side.LEFT);
        Act_piechart.setData(data);
    }
    
    
}
