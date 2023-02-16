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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Entities.Consultation;
import static GUI.Afficher_programmeController.idxx;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Services.consultationservices;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import GUI.CameraController;
import java.sql.Connection;
import java.sql.Statement;
import Tools.connexion;
import static com.teamdev.jxmaps.cg.c;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author GAMRA
 */
public class Afficher_consultationController implements Initializable {
  

    @FXML
    private TableView<Consultation> table_cons;
    @FXML
    private TableColumn<Consultation, Integer> id_c;
    @FXML
    private TableColumn<Consultation, String> nom;
    @FXML
    private TableColumn<Consultation, Integer> age;
    @FXML
    private TableColumn<Consultation, String> sexe;
    @FXML
    private TableColumn<Consultation, String> date;
    @FXML
    private TableColumn<Consultation, String> etat;
    @FXML
    private TableColumn<Consultation, String> categorie;
    @FXML
    private TableColumn<Consultation, String> prenom;
    @FXML
    private TableColumn<Consultation, String> email;
    @FXML
    private Button del_cons;
    @FXML
    private Button modifier;
       public static String idxx;
    @FXML
    private TextField recherche;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private Label time;
    @FXML
    private Button exit;
    @FXML
    private Button stat_cons;
    @FXML
    private Button Excel;
    @FXML
    private HBox hbox;
    @FXML
    private Button GOBACK;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Timenow();
        consultationservices c= new consultationservices();
        
        table_cons.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
              id_c.setText(String.valueOf(c.liste2().get(table_cons.getSelectionModel().getSelectedIndex()).getId_c()
               ));
               nom.setText( c.liste2().get(table_cons.getSelectionModel().getSelectedIndex()).getNom());
               age.setText(String.valueOf(c.liste2().get(table_cons.getSelectionModel().getSelectedIndex()).getAge()));
               sexe.setText(c.liste2().get(table_cons.getSelectionModel().getSelectedIndex()).getSexe());
               date.setText((c.liste2().get(table_cons.getSelectionModel().getSelectedIndex()).getDate_rdv()));
               etat.setText((c.liste2().get(table_cons.getSelectionModel().getSelectedIndex()).getEtat_physique ()));
               categorie.setText((c.liste2().get(table_cons.getSelectionModel().getSelectedIndex()).getcategorie_c () ));
               prenom.setText((c.liste2().get(table_cons.getSelectionModel().getSelectedIndex()).getPrenom() ));
               email.setText((c.liste2().get(table_cons.getSelectionModel().getSelectedIndex()).getEmail() ));
              
               
            };
        });
        
        
                 ObservableList<Consultation> list;
                 try
                 {
                     list = c.getconsultationList();
                     id_c.setCellValueFactory(new PropertyValueFactory<>("Id_c"));
                     nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
                     age.setCellValueFactory(new PropertyValueFactory<>("Age"));
                     sexe.setCellValueFactory(new PropertyValueFactory<>("Sexe"));
                     date.setCellValueFactory(new PropertyValueFactory<>("Date_rdv"));
                     etat.setCellValueFactory(new PropertyValueFactory<>("Etat_physique"));
                     categorie.setCellValueFactory(new PropertyValueFactory<>("categorie_c"));
                     prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
                     email.setCellValueFactory(new PropertyValueFactory<>("Email"));
                    table_cons.setItems(list);
                /*    // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Consultation> filteredData = new FilteredList<>(list, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		recherche.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(consultation -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (consultation.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} 
                                else if (consultation.getDate_rdv().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
                              
				} 
                                else if (String.valueOf(consultation.getId_c()).indexOf(lowerCaseFilter)!=-1)
				     return true;
                                else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Consultation> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(table_cons.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		table_cons.setItems(sortedData);*/
                table_cons.setItems(list);
                 } catch (SQLException ex) {
            Logger.getLogger(Afficher_consultationController.class.getName()).log(Level.SEVERE, null, ex);
        
        
    
                 }catch(Exception e){
            Logger.getLogger(Afficher_consultationController.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }
        // TODO

    @FXML
    private void liste_consult(MouseEvent event) {
    }

    //------------------------------------------ Bouton Supprimer -----------------------------------------------//
     public void delete()
    {
        consultationservices cs = new consultationservices();
        cs.supprimerconsultation(table_cons.getSelectionModel().getSelectedItem().getId_c());
        System.out.println(table_cons.getSelectionModel().getSelectedItem().getId_c());
    }
     
     
    
    @FXML
    private void supp_cons(ActionEvent event) {
        delete();
        table_cons.getItems().removeAll(table_cons.getSelectionModel().getSelectedItem());
        System.out.println(table_cons);
    }

    @FXML
    private void modifier_consult(ActionEvent event) throws IOException {
        
                  
       idxx= (Integer.toString(table_cons.getSelectionModel().getSelectedItem().getId_c()));
               
        FXMLLoader loader = new FXMLLoader();
       
                Stage prStage =new Stage(); 
                loader.setLocation(getClass().getResource("update_consultation.fxml"));
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

    @FXML
    private void stat_consult(ActionEvent event) {
        try {
                   
            Parent parent = FXMLLoader.load(getClass().getResource("/GUI/Consultation_Stats.fxml"));
            Scene scene = new Scene(parent);
            
            Stage stage = new Stage();
            //stage.getIcons().add(new Image("/images/logo.png"));
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Afficher_consultationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void exportPDF(ActionEvent event) throws SQLException {
        consultationservices cs= new consultationservices();
         ObservableList<Consultation> list = cs.getconsultationList();
         
         try{
             OutputStream file = new FileOutputStream(new File("C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\Consultation.pdf"));
            Document document = new Document();
            PdfWriter.getInstance(document, file);
            document.open();
            
            Font font = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
            Paragraph pdfTitle = new Paragraph("Liste des Consultation", font);
            pdfTitle.setAlignment(Element.ALIGN_CENTER);
            
            document.add(pdfTitle);
            document.add(new Chunk("\n"));
            PdfPTable table = new PdfPTable(8);
            table.setHeaderRows(1);
            
            table.addCell("Nom");
            table.addCell("Age");
            table.addCell("Sexe");
            table.addCell("Date RDV");
            table.addCell("Etat");
            table.addCell("Categorie");
            table.addCell("Prenom");
            table.addCell("Email");
            
            
            list.forEach((_item) -> {
                table.addCell(_item.getNom());
                table.addCell(String.valueOf(_item.getAge()));
                table.addCell(_item.getSexe());
                table.addCell(_item.getDate_rdv());
                table.addCell(_item.getEtat_physique());
                table.addCell(_item.getcategorie_c());
                table.addCell(_item.getPrenom());
                table.addCell(_item.getEmail());
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

    private void camera(ActionEvent event) throws IOException {
          Parent root = FXMLLoader.load(getClass().getResource("Camera.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void Excel(ActionEvent event) throws SQLException, IOException {
         ObservableList<Consultation> list;
         consultationservices bs= new consultationservices();
      
         list = bs.getconsultationList();
       Writer writer = null;
        try {
            //badel path fichier excel
            File file = new File("C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\Consultation.csv");
            writer = new BufferedWriter(new FileWriter(file));
            String text1 = "Nom cours"+"," +"Date Cours"+ "," + "Id cours"+ "," + "Id coach"+ "\n";
            writer.write(text1);
            for (Consultation ev : list) {

                String text = ev.getId_c()+"," +ev.getNom()+ "," + ev.getAge()+ "," + ev.getSexe()+","+ev.getDate_rdv()+","+ev.getEtat_physique()+","+ev.getcategorie_c()+","+ev.getPrenom()+","+ev.getEmail()+ "\n";

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
        alert.showAndWait();
        }
    }

    @FXML
    private void GOBACK_TOACCUEIL(ActionEvent event) throws IOException {
        
         Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    private void recherche(ActionEvent event) {
        consultationservices c= new consultationservices();
        ObservableList<Consultation> list;
                 try
                 {
                     list = c.getconsultationList();
                     id_c.setCellValueFactory(new PropertyValueFactory<>("Id_c"));
                     nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
                     age.setCellValueFactory(new PropertyValueFactory<>("Age"));
                     sexe.setCellValueFactory(new PropertyValueFactory<>("Sexe"));
                     date.setCellValueFactory(new PropertyValueFactory<>("Date_rdv"));
                     etat.setCellValueFactory(new PropertyValueFactory<>("Etat_physique"));
                     categorie.setCellValueFactory(new PropertyValueFactory<>("categorie_c"));
                     prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
                     email.setCellValueFactory(new PropertyValueFactory<>("Email"));
                    table_cons.setItems(list);
                    // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Consultation> filteredData = new FilteredList<>(list, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		recherche.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(consultation -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (consultation.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} 
                                else if (consultation.getDate_rdv().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
                              
				} 
                                else if (String.valueOf(consultation.getId_c()).indexOf(lowerCaseFilter)!=-1)
				     return true;
                                else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Consultation> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(table_cons.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		table_cons.setItems(sortedData);
                 }catch(Exception e){
            Logger.getLogger(Afficher_consultationController.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }

    @FXML
    private void recherche(KeyEvent event) {
        consultationservices c= new consultationservices();
        ObservableList<Consultation> list;
                 try
                 {
                     list = c.getconsultationList();
                     id_c.setCellValueFactory(new PropertyValueFactory<>("Id_c"));
                     nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
                     age.setCellValueFactory(new PropertyValueFactory<>("Age"));
                     sexe.setCellValueFactory(new PropertyValueFactory<>("Sexe"));
                     date.setCellValueFactory(new PropertyValueFactory<>("Date_rdv"));
                     etat.setCellValueFactory(new PropertyValueFactory<>("Etat_physique"));
                     categorie.setCellValueFactory(new PropertyValueFactory<>("categorie_c"));
                     prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
                     email.setCellValueFactory(new PropertyValueFactory<>("Email"));
                    table_cons.setItems(list);
                    // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Consultation> filteredData = new FilteredList<>(list, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		recherche.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(consultation -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (consultation.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} 
                                else if (consultation.getDate_rdv().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
                              
				} 
                                else if (String.valueOf(consultation.getId_c()).indexOf(lowerCaseFilter)!=-1)
				     return true;
                                else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Consultation> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(table_cons.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		table_cons.setItems(sortedData);
                 }catch(Exception e){
            Logger.getLogger(Afficher_consultationController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    }
        
    

