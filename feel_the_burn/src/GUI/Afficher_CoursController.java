/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Services.CoursServices;
import Entities.Cours;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author WAEL
 */
public class Afficher_CoursController implements Initializable {

    @FXML
     TableView<Cours> tablecours;
    @FXML
    private TableColumn<Cours, Integer> Afficher_Id_C;
    @FXML
    private TableColumn<Cours, String> Afficher_Nom_C;
    @FXML
    private TableColumn<Cours, String> Afficher_Date_C;
    @FXML
    private TableColumn<Cours, Integer> Afficher_Id_Co;
    @FXML
    private Button Modifier_Cours;
    @FXML
    private TextField filterfield;
    public static String idxx;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private Button exit;
    @FXML
    private Label time;
    @FXML
    private Button Stat;
    @FXML
    private Button GOBACK;
    public void delete()
    {
        CoursServices cs=new CoursServices();
       cs.supprimerCours(tablecours.getSelectionModel().getSelectedItem().getId_C());
        System.out.println(tablecours.getSelectionModel().getSelectedItem().getId_C());
    }
    @FXML
    private Button Supprimer_Cours;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            // TODO
            Timenow();
        
        CoursServices bs= new CoursServices();
        
        tablecours.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
       
                Afficher_Id_C.setText(String.valueOf(bs.liste2().get(tablecours.getSelectionModel().getSelectedIndex()).getId_C()
               ));
               Afficher_Nom_C.setText(bs.liste2().get(tablecours.getSelectionModel().getSelectedIndex()).getNom_C());
               Afficher_Date_C.setText(bs.liste2().get(tablecours.getSelectionModel().getSelectedIndex()).getDate_C() );
               Afficher_Id_Co.setText(String.valueOf(bs.liste2().get(tablecours.getSelectionModel().getSelectedIndex()).getId_Co() ));
               
              
               
               
              
               
            };
        });
        
        
                 ObservableList <Cours> list;
                 try
                 {
                     list = bs.getCoursList();
                     Afficher_Id_C.setCellValueFactory(new PropertyValueFactory<>("Id_C"));
                     Afficher_Nom_C.setCellValueFactory(new PropertyValueFactory<>("Nom_C"));
                     Afficher_Date_C.setCellValueFactory(new PropertyValueFactory<>("Date_C"));
                     Afficher_Id_Co.setCellValueFactory(new PropertyValueFactory<>("Id_Co"));
                                    /*   // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Cours> filteredData = new FilteredList<>(list, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		filterfield.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Cours -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Cours.getNom_C().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} 
                                else if (Cours.getDate_C().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
                              
				} 
                                else if (String.valueOf(Cours.getId_C()).indexOf(lowerCaseFilter)!=-1)
				     return true;
                                else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Cours> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tablecours.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tablecours.setItems(sortedData);*/
                                    tablecours.setItems(list);
               
                     
                     
                 } catch (SQLException ex) {
            Logger.getLogger(Afficher_CoachingController.class.getName()).log(Level.SEVERE, null, ex);
        
       
                 }  

        
    }    
        

    @FXML
    private void Supprimer_Cours(ActionEvent event) {
          if (tablecours.getSelectionModel().getSelectedItem()!=null){
        delete();
        tablecours.getItems().removeAll(tablecours.getSelectionModel().getSelectedItem());
        System.out.println(tablecours);
        Notifications notificationBuilder = Notifications.create().title("Alert").text("cours supprimé avec succes").graphic(null).hideAfter(javafx.util.Duration.seconds(10)) .position(Pos.CENTER_LEFT) .onAction(new EventHandler<ActionEvent>(){ public void handle(ActionEvent event) 
{ 
System.out.println("clicked ON "); 
}}); 
notificationBuilder.darkStyle(); 
notificationBuilder.show(); 
        }
        else {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("error");
        alert.setHeaderText(null);
        alert.setContentText("!!!please select the course you want to delete !!!");
        alert.showAndWait();}
      
    }

    @FXML
    private void Modifier_Cours(ActionEvent event) throws IOException {
        if (tablecours.getSelectionModel().getSelectedItem()!=null){
        idxx= (Integer.toString(tablecours.getSelectionModel().getSelectedItem().getId_C()));
               
        FXMLLoader loader = new FXMLLoader();
       
                Stage prStage =new Stage(); 
                loader.setLocation(getClass().getResource("Update_Cours.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                prStage.setScene(scene);
                prStage.show();
                Notifications notificationBuilder = Notifications.create().title("Alert").text("cours modifié avec succes").graphic(null).hideAfter(javafx.util.Duration.seconds(10)) .position(Pos.CENTER_LEFT) .onAction(new EventHandler<ActionEvent>(){ public void handle(ActionEvent event) 
{ 
System.out.println("clicked ON "); 
}}); 
notificationBuilder.darkStyle(); 
notificationBuilder.show(); 
    }
        else {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("error");
        alert.setHeaderText(null);
        alert.setContentText("!!!please select the course you want to update !!!");
        alert.showAndWait();}
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
             time.setText(timenow); 
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

    private void filterfield(ActionEvent event) {
        ObservableList <Cours> list;
                 try
                 {
                     CoursServices bs= new CoursServices();
                     list = bs.getCoursList();
                     Afficher_Id_C.setCellValueFactory(new PropertyValueFactory<>("Id_C"));
                     Afficher_Nom_C.setCellValueFactory(new PropertyValueFactory<>("Nom_C"));
                     Afficher_Date_C.setCellValueFactory(new PropertyValueFactory<>("Date_C"));
                     Afficher_Id_Co.setCellValueFactory(new PropertyValueFactory<>("Id_Co"));
                    
                     tablecours.setItems(list);
       // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Cours> filteredData = new FilteredList<>(list, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		filterfield.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Cours -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Cours.getNom_C().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} 
                                else if (Cours.getDate_C().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
                              
				} 
                                else if (String.valueOf(Cours.getId_C()).indexOf(lowerCaseFilter)!=-1)
				     return true;
                                else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Cours> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tablecours.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tablecours.setItems(sortedData);}
    catch (SQLException ex) {
            Logger.getLogger(Afficher_CoachingController.class.getName()).log(Level.SEVERE, null, ex);
        
       
                 }  
        
    }

    @FXML
    private void filterfield(KeyEvent event) {
        ObservableList <Cours> list;
                 try
                 {
                     CoursServices bs= new CoursServices();
                     list = bs.getCoursList();
                     Afficher_Id_C.setCellValueFactory(new PropertyValueFactory<>("Id_C"));
                     Afficher_Nom_C.setCellValueFactory(new PropertyValueFactory<>("Nom_C"));
                     Afficher_Date_C.setCellValueFactory(new PropertyValueFactory<>("Date_C"));
                     Afficher_Id_Co.setCellValueFactory(new PropertyValueFactory<>("Id_Co"));
                    
                     tablecours.setItems(list);
       // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Cours> filteredData = new FilteredList<>(list, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		filterfield.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Cours -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Cours.getNom_C().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} 
                                else if (Cours.getDate_C().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
                              
				} 
                                else if (String.valueOf(Cours.getId_C()).indexOf(lowerCaseFilter)!=-1)
				     return true;
                                else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Cours> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tablecours.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tablecours.setItems(sortedData);}
    catch (SQLException ex) {
            Logger.getLogger(Afficher_CoachingController.class.getName()).log(Level.SEVERE, null, ex);
        
       
                 }  
        
    }

    private void exportPDF(ActionEvent event) throws SQLException {
        CoursServices cs = new CoursServices();
        ObservableList<Cours> list = cs.getCoursList();
        
        try{
            OutputStream file = new FileOutputStream(new File("C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\Cours.pdf"));
            Document document = new Document();
            PdfWriter.getInstance(document, file);
            document.open();
            
            Font font = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
            Paragraph pdfTitle = new Paragraph("Liste des Cours", font);
            pdfTitle.setAlignment(Element.ALIGN_CENTER);
            
            document.add(pdfTitle);
            document.add(new Chunk("\n"));
            PdfPTable table = new PdfPTable(2);
            table.setHeaderRows(1);

            table.addCell("Nom");
            table.addCell("Date");
            
            list.forEach((_item) -> {
                table.addCell(_item.getNom_C());
                table.addCell(_item.getDate_C());
              
            });

            document.add(table);
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Success");
            alert.setContentText("Success!");
            alert.show();
            document.close();

            file.close();
        } catch (Exception ex) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Cannot export data!");
            alert.show();
        }
    }

    @FXML
    private void Stat(ActionEvent event) {
          try {
                   
            Parent parent = FXMLLoader.load(getClass().getResource("/GUI/Stat_Cours.fxml"));
            Scene scene = new Scene(parent);
            
            Stage stage = new Stage();
            //stage.getIcons().add(new Image("/images/logo.png"));
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Afficher_CoursController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Excel(ActionEvent event) throws SQLException, IOException {
        CoursServices bs= new CoursServices();
         ObservableList <Cours> list;
         list = bs.getCoursList();
       Writer writer = null;
        try {
            //badel path fichier excel
            File file = new File("C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\Cours.csv");
            writer = new BufferedWriter(new FileWriter(file));
            String text1 = "Nom cours"+"," +"Date Cours"+ "," + "Id cours"+ "," + "Id coach"+ "\n";
            writer.write(text1);
            for (Cours ev : list) {

                String text = ev.getNom_C()+"," +ev.getDate_C()+ "," + ev.getId_C()+ "," + ev.getId_Co()+ "\n";

                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {

            writer.flush();
             writer.close();
             Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("excel");
        alert.setHeaderText(null);
        alert.setContentText("!!!excel exported!!!");
        alert.showAndWait();}
        
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
    


    

