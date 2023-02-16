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
import Entities.Enfant;
import Services.EnfantServices;
import com.itextpdf.text.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.image.ImageView;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.ReadOnlyObjectWrapper;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import javafx.beans.binding.Bindings;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javax.imageio.ImageIO;
/**
 * FXML Controller class
 *
 * @author zeine
 */
public class Afficher_EnfantController implements Initializable {

    @FXML
     TableView<Enfant> tab_enf;
    @FXML
    private TableColumn<Enfant, Integer> aff_ide;
    @FXML
    private TableColumn<Enfant, String> aff_nome;
    @FXML
    private TableColumn<Enfant, String> aff_prenom;
    @FXML
    private TableColumn<Enfant, Integer> aff_age;
    @FXML
    private TableColumn<Enfant, String> aff_sexe;
    @FXML
    // private TableColumn<Enfant, String> aff_photo;
    private TableColumn<Enfant, ImageView> aff_photo;
    @FXML
    private TableColumn<Enfant, Integer> aff_idu;
    @FXML
    private Button del_enf;
    public static String idxx;
    @FXML
    private Button upd_enf;
    @FXML
    private Pagination pagination_E;
    @FXML
    private Button Excel;
    File filesJpg[];
    @FXML
    private Button button;
    @FXML
    private AnchorPane anchor;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        EnfantServices es = new EnfantServices();
        
        tab_enf.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
              //aff_ide.setText(String.valueOf(es.liste2().get(tab_enf.getSelectionModel().getSelectedIndex()).getId_enfant()));
              aff_ide.setText(String.valueOf(es.liste2().get(tab_enf.getSelectionModel().getSelectedIndex()).getId_enfant()));
              aff_nome.setText(es.liste2().get(tab_enf.getSelectionModel().getSelectedIndex()).getNom());
              aff_prenom.setText(es.liste2().get(tab_enf.getSelectionModel().getSelectedIndex()).getPrenom());
              aff_age.setText(String.valueOf(es.liste2().get(tab_enf.getSelectionModel().getSelectedIndex()).getAge()));
              aff_sexe.setText(es.liste2().get(tab_enf.getSelectionModel().getSelectedIndex()).getSexe());
              aff_photo.setText(es.liste2().get(tab_enf.getSelectionModel().getSelectedIndex()).getPhoto());
              aff_idu.setText(String.valueOf(es.liste2().get(tab_enf.getSelectionModel().getSelectedIndex()).getId_a()));
            };
        });
        // final ImageView imageview = new ImageView();
        //imageview.fitWidthProperty().bind(Bindings.subtract(aff_photo.widthProperty(), 7));
    //imageview.setPreserveRatio(true);
        ObservableList<Enfant> list;
        try{
            list = es.getEnfantList();
            aff_ide.setCellValueFactory(new PropertyValueFactory<>("id_enfant"));
            aff_nome.setCellValueFactory(new PropertyValueFactory<>("nom"));
            aff_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            aff_age.setCellValueFactory(new PropertyValueFactory<>("age"));
            aff_sexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
            //aff_photo.setCellValueFactory(new PropertyValueFactory<>("photo"));
            aff_idu.setCellValueFactory(new PropertyValueFactory<>("id_a"));
            tab_enf.setItems(list);
            aff_photo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Enfant, ImageView>, ObservableValue<ImageView>>() {
            
            
            

                @Override
                public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Enfant, ImageView> param) {
                  return new ReadOnlyObjectWrapper(param.getValue().getPhoto());
                }});
        } catch (SQLException ex) {
            Logger.getLogger(Afficher_EnfantController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void liste_enf(MouseEvent event) {
    }

    //------------------------------------------------ Bouton Supprimer ----------------------------------------------------------------//
     public void delete()
    {
        EnfantServices es = new EnfantServices();
       es.supprimerEnfant(tab_enf.getSelectionModel().getSelectedItem().getId_enfant());
        System.out.println(tab_enf.getSelectionModel().getSelectedItem().getId_enfant());
    }

    @FXML
    private void supprimer_enfant(ActionEvent event) {
         delete();
        tab_enf.getItems().removeAll(tab_enf.getSelectionModel().getSelectedItem());
        System.out.println(tab_enf);
        tab_enf.refresh();
    }

    @FXML
    private void modifier_enfant(ActionEvent event) throws IOException {
        idxx= (Integer.toString(tab_enf.getSelectionModel().getSelectedItem().getId_enfant()));
               
        FXMLLoader loader = new FXMLLoader();
       
                Stage prStage =new Stage(); 
                loader.setLocation(getClass().getResource("Modifier_Enfant.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                prStage.setScene(scene);
                prStage.show();
    }

    @FXML
    private void Excel(ActionEvent event) throws Exception {
        /*
         try {
            
              EnfantServices es=new EnfantServices();
           //  ServiceReclamation ser = new ServiceReclamation();
            es.getDefendants("feel_the_burn");
                 
              
        }catch (SQLException e) {
                   System.out.println("Failed to get data from database");
                 }*/
    }

    @FXML
    private void handleButtonAction(ActionEvent event) 
    {
        /*
        Stage stage = (Stage) anchor.getScene().getWindow();        
        Pagination pagination= new Pagination();
        pagination.setPageCount(11);
        pagination.setCurrentPageIndex(2);
        pagination.setMaxPageIndicatorCount(1);
        
        pagination.setPageFactory((Integer pageIndex)->{
            
            Label label1 = new Label("Content for page with index: " + pageIndex);  

            Label label2 = new Label("Main content of the page ...");

            return new VBox(label1, label2);
        });
        
        
         openDirectoryChooser(stage); // calling method to open dicrectory chooser       
         
         
         //this code will create a page and load inside pagination control
         pagination_E.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {  // every time when you click pagination button this method will be called
              //  return creat;
                return createPage(pageIndex);
            }

            
        });
    }
     private void openDirectoryChooser(Stage parent) {
        
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(parent);
 
        if (selectedDirectory != null) {
            FilenameFilter filterJpg = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".jpg");
                }
            };
 
            filesJpg = selectedDirectory.listFiles(filterJpg);
            
        }
    }
     
    public VBox createPage(int index) {
 
        ImageView imageView = new ImageView();
 
        File file = filesJpg[index];
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageView.setImage(image);
            imageView.setFitWidth(400);
            imageView.setFitHeight(360);
           // imageView.setPreserveRatio(true);
            
            imageView.setSmooth(true);
            imageView.setCache(true);
        } catch (IOException ex) {
            
        }
         
        VBox pageBox = new VBox();
        pageBox.getChildren().add(imageView);        
        
        return pageBox;
    }  
}*/
}}
    
