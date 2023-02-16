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
import javafx.scene.control.TextField;
import Entities.Produit;
import Services.ProduitService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import static Entities.Produit.filename;
import java.io.File;
/**
 * FXML Controller class
 *
 * @author zeine
 */
public class Ajouter_ProduitController implements Initializable {

    @FXML
    private TextField aj_id;
    @FXML
    private TextField aj_nom;
    @FXML
    private TextField aj_prix;
    @FXML
    private TextField aj_cat;
    @FXML
    private Button ajouter_prod;

    
    @FXML
    private Button afficher_prod;
    @FXML
    private ImageView imagefield;
    public String imagecomp; 
    @FXML
    private Button upload;
    @FXML
    private Button GOBACK;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private boolean ValidateFields() {
        if(aj_id.getText().isEmpty() | aj_nom.getText().isEmpty() | aj_prix.getText().isEmpty()  | aj_cat.getText().isEmpty()) {
             Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validate Fields");
        alert.setHeaderText(null);
        alert.setContentText("Please enter into the fields");
        alert.showAndWait();
        return false;
        }
        return true;
    }
    private boolean ValidateFields2() {
        ProduitService bs= new ProduitService();
        if(Integer.parseInt(bs.calculer_nbProd(aj_nom.getText())) != 0) {
             Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validate Fields");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a non existant course name");
       // bs.calculer_nbseance(Add_Nom_Co.getText())
       //alert.setContentText(bs.calculer_nbseance(Add_Nom_Co.getText()));
        alert.showAndWait();
        return false;
        }
        return true;
    }
    
    @FXML
    private void add_prod(ActionEvent event) {
       // Produit p1= new Produit();
        
        String ide= aj_id.getText();
       int id= Integer.parseInt(ide);
       String prix1= aj_prix.getText();
       Float prix= Float.parseFloat(prix1);
    
       Produit p1= new Produit(id, aj_nom.getText(), prix,Produit.filename,aj_cat.getText());
       
        if(ValidateFields()){
            if(ValidateFields2()){
       // p1.setId_P(Integer.valueOf(aj_id.getText()));
        //p1.setNom_P(aj_nom.getText());
       // p1.setPrix(Float.valueOf(aj_prix.getText()));
       // p1.setPhoto(aj_photo.getText());
        //p1.setCategories(aj_cat.getText());
        
        ProduitService ps = new ProduitService();
        ps.ajouterProduit(p1);
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("succes");
        alert.setHeaderText(null);
        alert.setContentText("!!!succes !!!");
        alert.showAndWait();
         try {
                        Parent root = FXMLLoader.load(getClass().getResource("QR_code_Writer.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(QR_code_WriterController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
    }}}

    @FXML
    private void afficher_prod(ActionEvent event) {
         FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Produit.fxml"));
        try{
           
           Parent root = loader.load();
           Afficher_ProduitController ac = loader.getController();
           
           aj_id.getScene().setRoot(root);
           
           
       }
       catch(IOException ex){
           System.out.println(ex.getMessage());
       }
    }

    @FXML
    private void uploadimg(ActionEvent event) {
        FileChooser f = new FileChooser();
        String img;

        f.getExtensionFilters().add(new FileChooser.ExtensionFilter("image", "*.png","*.jpg"));
        File fc = f.showOpenDialog(null);
         if (f != null) {
            //System.out.println(fc.getName());
            img = fc.getAbsoluteFile().toURI().toString();
            Image i = new Image(img);
            imagefield.setImage(i);
            imagecomp = fc.toString();
            System.out.println(imagecomp);
            int index = imagecomp.lastIndexOf('\\');
            if (index > 0) {
                filename = imagecomp.substring(index + 1);
            }

           Produit.filename = "C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\src\\Assets" + filename;
            //se.sendphp(fc.getAbsolutePath());
        }
        imagefield.setFitHeight(65);
        imagefield.setFitWidth(95);
        Produit.pathfile = fc.getAbsolutePath();
    }

    @FXML
    private void GOBACK_TOACCUEIL(ActionEvent event) throws IOException {
        
         Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }
    
}
