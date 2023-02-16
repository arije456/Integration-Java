/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Consultation;
import Entities.Programme;
import com.sun.javafx.scene.control.skin.TableViewSkinBase;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Services.Programmeservices;
import Services.consultationservices;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author GAMRA
 */
public class Afficher_programmeController implements Initializable {

    @FXML
    private TableView<Programme> table_prog;
    @FXML
    private TableColumn<Programme, Integer> id;
    @FXML
    private TableColumn<Programme, String>nom_p ;
    @FXML
    private TableColumn<Programme, String>date_r ;
    @FXML
    private TableColumn<Programme, Integer> id_kine;
    @FXML
    private TableColumn<Programme, String>descript ;
    @FXML
    private TableColumn<Programme, Integer> id_c;
    @FXML
    private Button del_prog;
    @FXML
    private Button modifier;
    public static String idxx;
    @FXML
    private TextField recherche;
    @FXML
    private Button rechercher;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private TextField nb_occ;
    @FXML
    private TextField nb_occ2;
    @FXML
    private Label time;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Timenow();
          Programmeservices pro = new Programmeservices();
        
        table_prog.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                
                // pour rafraichir le tab
              table_prog.getProperties().put(TableViewSkinBase.RECREATE, Boolean.TRUE);
                
              id.setText(String.valueOf(pro.liste2().get(table_prog.getSelectionModel().getSelectedIndex()).getId()));
              nom_p.setText( pro.liste2().get(table_prog.getSelectionModel().getSelectedIndex()).getNom_p());
               date_r.setText((pro.liste2().get(table_prog.getSelectionModel().getSelectedIndex()).getDate_r()));
               id_kine.setText(String.valueOf(pro.liste2().get(table_prog.getSelectionModel().getSelectedIndex()).getId_kine()));
              descript.setText( pro.liste2().get(table_prog.getSelectionModel().getSelectedIndex()).getdescription());
                id_c.setText(String.valueOf(pro.liste2().get(table_prog.getSelectionModel().getSelectedIndex()).getId_c()));
             
                
               
            };
        });
        
        
                 ObservableList<Programme> list;
                 try
                 {
                     list = pro.getprogrammeList();
                     id.setCellValueFactory(new PropertyValueFactory<>("Id"));
                     nom_p.setCellValueFactory(new PropertyValueFactory<>("Nom_p"));
                     date_r.setCellValueFactory(new PropertyValueFactory<>("Date_r"));
                     id_kine.setCellValueFactory(new PropertyValueFactory<>("Id_kine"));
                     //des.setCellValueFactory(new PropertyValueFactory<>("description") );
                     descript.setCellValueFactory(new PropertyValueFactory<>("description"));
                     
                      id_c.setCellValueFactory(new PropertyValueFactory<>("Id_c"));
                    // table_prog.setItems(list);
                    // Wrap the ObservableList in a FilteredList (initially display all data).
                     FilteredList<Programme> filteredData = new FilteredList<>(list, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		recherche.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(programme -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (programme.getNom_p().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} 
                                else if (programme.getDate_r().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
                              
				} 
                                else if (String.valueOf(programme.getId()).indexOf(lowerCaseFilter)!=-1)
				     return true;
                                else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Programme> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(table_prog.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		table_prog.setItems(sortedData);
                 } catch (SQLException ex) {
            Logger.getLogger(Afficher_programmeController.class.getName()).log(Level.SEVERE, null, ex);
       
                 }catch(Exception e){
            Logger.getLogger(Afficher_programmeController.class.getName()).log(Level.SEVERE, null, e);
        }
    }    
//------------------------------------------ Bouton Supprimer -----------------------------------------------//
     public void delete()
    {
        Programmeservices cs = new Programmeservices();
        cs.supprimerprogramme(table_prog.getSelectionModel().getSelectedItem().getId());
        System.out.println(table_prog.getSelectionModel().getSelectedItem().getId());
    }
    @FXML
    private void supp_prog(ActionEvent event) {
        delete();
        table_prog.getItems().removeAll(table_prog.getSelectionModel().getSelectedItem());
        System.out.println(table_prog);
    }

    @FXML
    private void modifier_programme(ActionEvent event) throws IOException {
        idxx= (Integer.toString(table_prog.getSelectionModel().getSelectedItem().getId()));
               
        FXMLLoader loader = new FXMLLoader();
       
                Stage prStage =new Stage(); 
                loader.setLocation(getClass().getResource("update_programme.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                prStage.setScene(scene);
                prStage.show();
    }

    @FXML
    private void rechercher_programme(ActionEvent event) {
        
        
    }

    @FXML
    private void nb_occ(KeyEvent event) {
          Programmeservices bs= new Programmeservices();
                      String k = null;
                      if (event.getCode().equals(KeyCode.ENTER)){
                      k=(nb_occ.getText());
                      nb_occ2.setText(bs.calculer_nbseance(k));}
    }

    @FXML
    private void nb_occ2(KeyEvent event) {
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
             time.setText(timenow); 
             } );
         }
        }
        );
        thread.start();
    
    }
     //exiiiiiiiiiiiiiiiit
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
    
}

