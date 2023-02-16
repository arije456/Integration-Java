/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import Entities.Produit;
import Services.ProduitService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import  GUI.Ajouter_ProduitController;
import com.google.zxing.BarcodeFormat;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import static java.util.Collections.list;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author zeine
 */
public class Afficher_ProduitController implements Initializable {
    
    @FXML
    private TableView<Produit> tab_prod;
    @FXML
    private TableColumn<Produit, Integer> id_p;
    @FXML
    private TableColumn<Produit, String> nom_p;
    @FXML
    private TableColumn<Produit,Float> prix;
    @FXML
    //private TableColumn<Produit, String> photo;
    private TableColumn<Produit, ImageView> photo;
    @FXML
    private TableColumn<Produit, String> cat;
    @FXML
    private Button supp_prod;
    @FXML
    private Button update_prod;
    public static String idxx;
    File filesJpg[];
   // Ajouter_ProduitController apc= new Ajouter_ProduitController();
    @FXML
    private TextField recherche;
    @FXML
    private TextField total;
    @FXML
    private Button exportPDF;
    @FXML
    private Button Stat;
    @FXML
    private Button Qr;
    @FXML
    private Button exit;
    @FXML
    private Button GOBACK;
    @FXML
    private AnchorPane scenePane;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ProduitService ps = new ProduitService();
        
        tab_prod.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               id_p.setText(String.valueOf(ps.liste2().get(tab_prod.getSelectionModel().getSelectedIndex()).getId_P()));
               nom_p.setText(ps.liste2().get(tab_prod.getSelectionModel().getSelectedIndex()).getNom_P());
               prix.setText(String.valueOf(ps.liste2().get(tab_prod.getSelectionModel().getFocusedIndex()).getPrix()));
               photo.setText(ps.liste2().get(tab_prod.getSelectionModel().getSelectedIndex()).getPhoto());
               cat.setText(ps.liste2().get(tab_prod.getSelectionModel().getSelectedIndex()).getCategories());
               
            };
        });
        
        ObservableList<Produit> listp;
        try{
            listp = ps.getProduitList();
            id_p.setCellValueFactory(new PropertyValueFactory<>("Id_P"));
            nom_p.setCellValueFactory(new PropertyValueFactory<>("Nom_P"));
            prix.setCellValueFactory(new PropertyValueFactory<>("Prix"));
            //photo.setCellValueFactory(new PropertyValueFactory<>("Photo"));
            
            cat.setCellValueFactory(new PropertyValueFactory<>("Categories"));
            
            tab_prod.setItems(listp);
            photo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Produit, ImageView>, ObservableValue<ImageView>>() {
                @Override
                public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Produit, ImageView> param) {
                  return new ReadOnlyObjectWrapper(param.getValue().getPhoto());
                }
            });
            // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Produit> filteredData = new FilteredList<>(listp, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		recherche.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Produit -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Produit.getNom_P().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} 
                                else if (Produit.getCategories().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
                              
				} 
                                else if (String.valueOf(Produit.getId_P()).indexOf(lowerCaseFilter)!=-1)
				     return true;
                                else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Produit> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tab_prod.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tab_prod.setItems(sortedData);
            
        } catch (SQLException ex) {
            Logger.getLogger(Afficher_ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    

    @FXML
    private void liste_prod(MouseEvent event) {
    }

    
    //------------------------------------- Bouton Supprimer -----------------------------------------------------------------------//
    
    public void delete()
    {
      ProduitService ps = new ProduitService();
      ps.supprimerProduit(tab_prod.getSelectionModel().getSelectedItem().getId_P());
      System.out.println(tab_prod.getSelectionModel().getSelectedItem().getId_P());
    }
    
    @FXML
    private void supp_prod(ActionEvent event) {
        delete();
        tab_prod.getItems().removeAll(tab_prod.getSelectionModel().getSelectedItem());
        System.out.println(tab_prod);
    }

    @FXML
    private void update_prod(ActionEvent event) throws IOException {
         /*Parent root = FXMLLoader.load(getClass().getResource("update_produit.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();   */         
    idxx= (Integer.toString(tab_prod.getSelectionModel().getSelectedItem().getId_P()));
               
        FXMLLoader loader = new FXMLLoader();
       
                Stage prStage =new Stage(); 
                loader.setLocation(getClass().getResource("update_produit.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                prStage.setScene(scene);
                prStage.show();

    
    
    
}

    @FXML
    private void recherche_p(ActionEvent event) {
    }

    @FXML
    private void total(KeyEvent event) throws SQLException {
         if (event.getCode().equals(KeyCode.ENTER)){
                        ProduitService bs= new ProduitService();
                       ObservableList <Produit> list;
                       list = bs.getProduitList();
                       total.setText(bs.prixtotal().toString());
                       //setText(as.getPrixbyID(id).toString());
                       //Coaching co= new Coaching();
                       
                       // nom_occ.setText(k);
    }
}

    @FXML
    private void exportPDF(ActionEvent event) throws SQLException {
        ProduitService ps = new ProduitService();
        ObservableList<Produit> list = ps.getProduitList();
        
        try
        {
            //"C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\Produit.pdf"
            OutputStream file = new FileOutputStream(new File("C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\Produit.pdf"));
            Document document = new Document();
            PdfWriter.getInstance(document, file);
            document.open();
            
            Font font = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
            Paragraph pdfTitle = new Paragraph("Liste des  Produits", font);
            pdfTitle.setAlignment(Element.ALIGN_CENTER);

            document.add(pdfTitle);
            document.add(new Chunk("\n"));
            PdfPTable table = new PdfPTable(4);
            table.setHeaderRows(1);

             table.addCell("Nom");
            table.addCell("Prix");
            table.addCell("Photo");
            table.addCell("Categorie");
            
             list.forEach((_item) -> {
                table.addCell(_item.getNom_P());
                table.addCell(String.valueOf(_item.getPrix()));
                table.addCell(_item.getPhoto());
                table.addCell(_item.getCategories());
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
    private void Statistique(ActionEvent event) {
        try {
                   
            Parent parent = FXMLLoader.load(getClass().getResource("/GUI/Prod_Stats.fxml"));
            Scene scene = new Scene(parent);
            
            Stage stage = new Stage();
            //stage.getIcons().add(new Image("/images/logo.png"));
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Afficher_ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void generateqr(ActionEvent event) {
        ProduitService ps= new ProduitService();
        
        if(tab_prod.getSelectionModel().getSelectedItem() != null)
        {
           Produit p = new Produit();
           //e.setDescription(rs.liste2().get(affichagerepas.getSelectionModel().getSelectedIndex()).getDescription());
           p.setNom_P(ps.liste2().get(tab_prod.getSelectionModel().getSelectedIndex()).getNom_P());
           p.setPhoto(ps.liste2().get(tab_prod.getSelectionModel().getSelectedIndex()).getPhoto());
           p.setPrix(ps.liste2().get(tab_prod.getSelectionModel().getSelectedIndex()).getPrix());
           p.setCategories(ps.liste2().get(tab_prod.getSelectionModel().getSelectedIndex()).getCategories());
           
           Map hints = new HashMap();
            hints.put(com.google.zxing.EncodeHintType.ERROR_CORRECTION, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.H);
            com.google.zxing.qrcode.QRCodeWriter writer = new com.google.zxing.qrcode.QRCodeWriter();
            com.google.zxing.common.BitMatrix bitMatrix = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
            try{
                // Create a qr code with the url as content and a size of 250x250 px
                bitMatrix = writer.encode("The product's name= "+p.getNom_P()+"  "+"The product's photo= "+p.getPhoto()+"  "+"The product's price=  "+p.getPrix(), BarcodeFormat.QR_CODE, 250, 250, hints);
                MatrixToImageConfig config = new MatrixToImageConfig(MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE);
                // Load QR image
               //BufferedImage qrImage1 = MatrixToImageWriter.toBufferedImage(bitMatrix,config);
               BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
               
                // Load logo image
                File file = new File("C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\product.png");
                //File file = new File("C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\product.png");
                BufferedImage logoImage = ImageIO.read(file);
                // Calculate the delta height and width between QR code and logo
                int deltaHeight = qrImage.getHeight() - logoImage.getHeight();
                int deltaWidth = qrImage.getWidth() - logoImage.getWidth();
                
                // Initialize combined image
                BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = (Graphics2D) combined.getGraphics();
                // Write QR code to new image at position 0/0
                g.drawImage(qrImage, 0, 0, null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                // Write logo into combine image at position (deltaWidth / 2) and
                // (deltaHeight / 2). Background: Left/Right and Top/Bottom must be
                // the same space for the logo to be centered
                g.drawImage(logoImage, (int) Math.round(deltaWidth / 2), (int) Math.round(deltaHeight / 2), null);
                // Write combined image as PNG to OutputStream
                ImageIO.write(combined, "png", new File("C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn"));
                //ImageIO.write(combined, "png", new File("C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn"));
                //System.out.println("done");
            
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }
           
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Choose a row !");
            alert.show();
        }
    }

    private volatile boolean stop = false;
    @FXML
    private void exit(ActionEvent event) {
        Stage stage;
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
    private void GOBACK_TOACCUEIL(ActionEvent event) {
    }
}


