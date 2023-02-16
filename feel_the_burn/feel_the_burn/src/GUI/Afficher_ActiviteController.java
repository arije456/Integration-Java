
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import Entities.Activite;
import Services.ActiviteService;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Optional;
import java.util.Random;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import GUI.Mailing;


public class Afficher_ActiviteController implements Initializable {

    private TextField aff_act;
    @FXML
    private VBox vbox_act;
    @FXML
     TableView<Activite> tab_act;
    @FXML
    private TableColumn<Activite,Integer> aff_id_a;
    @FXML
    private TableColumn<Activite, String> aff_nom_a;
    @FXML
    private TableColumn<Activite,Integer> aff_cat;
    @FXML
    private TableColumn<Activite,String> aff_type;
    @FXML
    private TableColumn<Activite, Integer> aff_id_e;
    @FXML
    private Button del_act;
    @FXML
    private Button upd_act;

    ObservableList<Activite> act;
    String query = null;
    PreparedStatement ste = null;
    ResultSet rs = null;
    Activite a= null;
    Connection connection;
    public static Activite actDetail= new Activite();
    @FXML
    private Button fermer;
    private TextField upd_id;
    private TextField upd_nom;
    private TextField upd_cat;
    private TextField upd_type;
    private TextField upd_ide;
    
    public static String idxx;
    @FXML
    private TextField rech_act;
    @FXML
    private Button count_act;
    @FXML
    private TextField nom_occ;
    @FXML
    private TextField nb_occ;
    @FXML
    private Label time;
    @FXML
    private Button pdf_act;
    @FXML
    private Button ExportPDF;
    @FXML
    private Button stat;
    @FXML
    private Button mailing_Act;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TO DO
        //loadData();
        Timenow();
        ActiviteService as= new ActiviteService();
        
        tab_act.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
               aff_id_a.setText(String.valueOf(as.liste2().get(tab_act.getSelectionModel().getSelectedIndex()).getId_a()
               ));
               aff_nom_a.setText( as.liste2().get(tab_act.getSelectionModel().getSelectedIndex()).getNom_a());
               aff_cat.setText(String.valueOf(as.liste2().get(tab_act.getSelectionModel().getSelectedIndex()).getCat_age()));
               aff_type.setText(as.liste2().get(tab_act.getSelectionModel().getSelectedIndex()).getType());
               aff_id_e.setText(String.valueOf(as.liste2().get(tab_act.getSelectionModel().getSelectedIndex()).getId_enfant() ));
               
            };
        });
        
                 
                 ObservableList<Activite> list;
                 try
                 {
                     list = as.getActiviteList();
                     aff_id_a.setCellValueFactory(new PropertyValueFactory<>("id_a"));
                     aff_nom_a.setCellValueFactory(new PropertyValueFactory<>("nom_a"));
                     aff_cat.setCellValueFactory(new PropertyValueFactory<>("cat_age"));
                     aff_type.setCellValueFactory(new PropertyValueFactory<>("type"));
                     aff_id_e.setCellValueFactory(new PropertyValueFactory<>("id_enfant"));
                     tab_act.setItems(list);
                     // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Activite> filteredData = new FilteredList<>(list, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		rech_act.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Activite -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Activite.getNom_a().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} 
                                else if (Activite.getType().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
                              
				} 
                                else if (String.valueOf(Activite.getCat_age()).indexOf(lowerCaseFilter)!=-1)
				     return true;
                                else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Activite> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tab_act.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tab_act.setItems(sortedData);
                     
                 } catch (SQLException ex) {
            Logger.getLogger(Afficher_ActiviteController.class.getName()).log(Level.SEVERE, null, ex);
        }
                 
     
    }
    
    //------------------------ Afficher -----------------------------------------------//
    

    public void setAff_act(String aff_act) {
        this.aff_act.setText(aff_act);
    }

    Activite at = new Activite();
    ActiviteService as = new ActiviteService();
    
    
    //----------------------------- Bouton supprimer -----------------------------------//
    
    @FXML
    private void supprimer_act(ActionEvent event) {
        delete();
        tab_act.getItems().removeAll(tab_act.getSelectionModel().getSelectedItem());
        System.out.println(tab_act);
        tab_act.refresh();
    }
    
    public void delete()
    {
       as.supprimerActivite(tab_act.getSelectionModel().getSelectedItem().getId_a());
        System.out.println(tab_act.getSelectionModel().getSelectedItem().getId_a());
    }

    
   
    private void sort_act(ActionEvent event) {
        tab_act.sort();
    }

    void refresh_act(ActionEvent event) {
       
        tab_act.refresh();
        
    }

    private void search_act(ActionEvent event) {
        ActiviteService as = new ActiviteService();
        as.displayByID(a.getId_a());
        
        aff_id_a.setText(String.valueOf(as.liste2().get(tab_act.getSelectionModel().getSelectedIndex()).getId_a()
        ));
        aff_nom_a.setText( as.liste2().get(tab_act.getSelectionModel().getSelectedIndex()).getNom_a());
        aff_cat.setText(String.valueOf(as.liste2().get(tab_act.getSelectionModel().getSelectedIndex()).getCat_age()));
        aff_type.setText(as.liste2().get(tab_act.getSelectionModel().getSelectedIndex()).getType());
        aff_id_e.setText(String.valueOf(as.liste2().get(tab_act.getSelectionModel().getSelectedIndex()).getId_enfant() ));
        
        
    }
    
    void setlist(String aff_act){
        this.aff_act.setText(aff_act);
        
        
        
    }

    @FXML
    private void liste_act(MouseEvent event) {
    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void update_act(ActionEvent event) throws SQLException {
        
        Activite a = new Activite();
        ActiviteService as = new ActiviteService();
        if (!upd_id.getText().equals("") && !upd_nom.getText().equals("") && !upd_cat.getText().equals("") && !upd_type.getText().equals("") && !upd_ide.getText().equals("")){
          a.setId_a(Integer.parseInt(upd_id.getText()));
        a.setNom_a(upd_nom.getText());
        a.setCat_age(Integer.parseInt(upd_cat.getText()));
        a.setType(upd_type.getText());
        a.setId_enfant(Integer.parseInt(upd_ide.getText()));  
        
        as.update2(a);
            System.out.println(a.toString());
            
            upd_id.setText("");
            upd_nom.setText("");
            upd_cat.setText(null);
            upd_type.setText("");
            upd_ide.setText(null);
            
            tab_act.setItems(as.getActiviteslistnew());
            System.out.println(as.getActiviteslistnew());
            
        }
        
        
    }

    @FXML
    private void modifier_act(ActionEvent event) throws IOException {
     //idxx=tab_act.getSelectionModel().getSelectedItem().getId_a().toString();
    
     idxx= (Integer.toString(tab_act.getSelectionModel().getSelectedItem().getId_a()));
               
        FXMLLoader loader = new FXMLLoader();
       
                Stage prStage =new Stage(); 
                loader.setLocation(getClass().getResource("Modifier_Activite.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                prStage.setScene(scene);
                prStage.show();
                tab_act.refresh();
                
    }

    @FXML
    private void Count_act(ActionEvent event) throws SQLException {
        /*
        //ObservableList<ObservableList> rows = dbController.queryRows(tableName);
        ObservableList<ObservableList> rows = as.getCountAct(tab_act);
        rows.forEach(row -> System.out.println(row));
         System.out.println();
         TableView<ObservableList> tableView = new TableView<>();
          tableView.getItems().addAll(rows);
*/
        
    }

    @FXML
    private void nom_occ(KeyEvent event) {
       ActiviteService bs= new ActiviteService();
                      String k = null;
                      if (event.getCode().equals(KeyCode.ENTER)){
                      k=(nom_occ.getText());
                      nb_occ.setText(bs.calculer_nbAct(k));}

    }
    
 
//-------------------------------- Time -------------------------------------------------------//
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
    
    //-------------------------------------- SET FILE LEL PDF ---------------------------------------------------//
    // public void setFILES(String Body, String Body1, String Body2, String Body3, String path,int id) // PATH TO IMAGE
    public void setFILES(String Body, String Body1, String Body2, String Body3,int id) {
        try {
            Random rand = new Random();

// Obtain a number between [0 - 49].
            int n = rand.nextInt(50);
            OutputStream file = new FileOutputStream(new File("Activite-" + id + ".pdf"));

            Document document = new Document();

            PdfWriter.getInstance(document, file);

            document.open();
            document.addTitle("Activite");

            //com.itextpdf.text.Image img;
            //img = com.itextpdf.text.Image.getInstance(path);
            //com.itextpdf.text.Image.getInstance(img);
            //img.scaleToFit(450 , 300);
            document.add(new Paragraph(Body));
            document.add(new Paragraph("                    "));
            //document.add(new Paragraph(" Image of the Sport :                   "));
            //document.add(img);

            document.add(new Paragraph("                    "));
            document.add(new Paragraph("                    "));

            document.add(new Paragraph(Body1));
            document.add(new Paragraph(Body2));
            document.add(new Paragraph(Body3));
            document.close();
            file.close();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }
//-------------------------------------------------- Imprimer as PDF --------------------------------------------//
    @FXML
    private void pdf_act(ActionEvent event) throws SQLException {
        //PRINT M3AWJA
        /*
        PrinterJob job = PrinterJob.createPrinterJob();
       
        Node root= this.tab_act;
        
     if(job != null){
     job.showPrintDialog(root.getScene().getWindow()); // Window must be your main Stage
     Printer printer = job.getPrinter();
     PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
     boolean success = job.printPage(pageLayout, root);
     if(success){
        job.endJob();
     }*/
        Document doc = new Document();
       
        
        
    }

     public void btnPDF(String Body, String Body1, String Body2, String Body3,int id) throws IOException, SQLException {

         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Button button2 = new Button();
        button2.setStyle("-fx-background-color: #00ff00");
        alert.setTitle("PDF ");
        alert.setContentText("Hello there !  Would you like to export this sport as a PDF File ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //setFILES(Body, Body1, Body2, Body3, path,id); //pathe lel IMAGE
            setFILES(Body, Body1, Body2, Body3,id);

        } else {

        }
    }
    
    @FXML
    private void ExportPDF(MouseEvent event) throws SQLException {
        /*
        String Body = "Name de l'Activite : "+actDetail.getNom_a();
        String Body1 = "Category d'age: "+actDetail.getCat_age();
        String Body2 = "Type d'Activité : "+actDetail.getType();
        String Body3 = "Id Enfant : "+actDetail.getId_enfant();
        
         try {
            btnPDF(Body, Body1, Body2, Body3,actDetail.getId_a());
        } catch (IOException ex) {
            Logger.getLogger(Afficher_ActiviteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Afficher_ActiviteController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        ActiviteService as = new ActiviteService();
        ObservableList<Activite> list = as.getActiviteList();
        
        try{
            OutputStream file = new FileOutputStream(new File("C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\Activite.pdf"));
            Document document = new Document();
            PdfWriter.getInstance(document, file);
            document.open();
            
            Font font = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
            Paragraph pdfTitle = new Paragraph("Liste des Activités", font);
            pdfTitle.setAlignment(Element.ALIGN_CENTER);
            
            document.add(pdfTitle);
            document.add(new Chunk("\n"));
            PdfPTable table = new PdfPTable(3);
            table.setHeaderRows(1);
            
            table.addCell("Nom");
            table.addCell("Catégorie d'Age");
            table.addCell("Type");
            
            list.forEach((_item) -> {
                table.addCell(_item.getNom_a());
                table.addCell(String.valueOf(_item.getCat_age()));
                table.addCell(_item.getType());
            });
            
            document.add(table);
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Success");
            alert.setContentText("Success!");
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
    private void OnClickedStatistique(ActionEvent event) {
         try {
                   
            Parent parent = FXMLLoader.load(getClass().getResource("/GUI/Act_Stats.fxml"));
            Scene scene = new Scene(parent);
            
            Stage stage = new Stage();
            //stage.getIcons().add(new Image("/images/logo.png"));
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Afficher_ActiviteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void mailing_Act(ActionEvent event) throws Exception{
        /*
        try{
            Activite a = new Activite();
            Mailing me = new Mailing();
        me.mailing("zeineb.haraketi@esprit.tn");
        
        }
        }catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
       */ 
    }
           

    
    
    
}
