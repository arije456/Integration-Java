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
import Entities.Enfant;
import Services.EnfantServices;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import static Entities.Enfant.filename;
import javafx.scene.Node;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author zeine
 */
public class Ajouter_EnfantController implements Initializable {

    @FXML
    private TextField add_ide;
    @FXML
    private TextField add_nome;
    @FXML
    private TextField add_prenom;
    @FXML
    private TextField add_age;
    @FXML
    private TextField add_sexe;
    @FXML
    private TextField add_idu;
    @FXML
    private Button add_enf;
    @FXML
    private Button aff_enf;
    @FXML
    private Button upload;
    @FXML
    private ImageView imagefield;
    public String imagecomp; 
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
        if(add_ide.getText().isEmpty() | add_nome.getText().isEmpty() | add_prenom.getText().isEmpty() |  add_age.getText().isEmpty() | add_sexe.getText().isEmpty() | add_idu.getText().isEmpty()) {
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
        EnfantServices bs= new EnfantServices();
        if(Integer.parseInt(bs.calculer_nbAct(add_nome.getText())) != 0) {
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
    private void ajouter_enfant(ActionEvent event) {
       String ide= add_ide.getText();
       int id= Integer.parseInt(ide);
       String ida = add_idu.getText();
       int idu= Integer.parseInt(ida);
       String ageS= add_age.getText();
       int age= Integer.parseInt(ageS);
        
        Enfant e1 = new Enfant(id,add_nome.getText(),add_prenom.getText(),age,add_sexe.getText(),Enfant.filename,idu);
        if(ValidateFields()){
            if(ValidateFields2()){
        
       // e1.setId_enfant(Integer.valueOf(add_ide.getText()));
       // e1.setNom(add_nome.getText());
       // e1.setPrenom(add_prenom.getText());
      //  e1.setAge(Integer.valueOf(add_age.getText()));
       // e1.setSexe(add_sexe.getText());
      //  e1.setPhoto(add_photo.getText());
      //  e1.setId_a(Integer.valueOf(add_idu.getText()));
        
        EnfantServices es = new EnfantServices();
        es.ajouterEnfant(e1);
        //Alert alert = new Alert (Alert.AlertType.INFORMATION);
        //alert.setTitle("succes");
        //alert.setHeaderText(null);
        //alert.setContentText("!!!succes !!!");
        //alert.showAndWait();
        
         FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Enfant.fxml"));
         
         try
         {
           Parent root = loader.load();
           Afficher_EnfantController ec = loader.getController();  
            add_ide.getScene().setRoot(root);
            Alert alert = new Alert (Alert.AlertType.INFORMATION);
             alert.setTitle("succes");
        alert.setHeaderText(null);
        alert.setContentText("!!!succes !!!");
        alert.showAndWait();              
         } catch (IOException ex) {
             System.out.println(ex.getMessage());
              Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.show();
         }
    }}}

    @FXML
    private void afficher_enfant(ActionEvent event) {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Enfant.fxml"));
        Enfant a2= new Enfant();
        a2.setId_a(Integer.valueOf(add_ide.getText()));
      
        try{
            Parent root = loader.load();
           Afficher_EnfantController ec = loader.getController();
           add_ide.getScene().setRoot(root);
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

           Enfant.filename = "C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\src\\Assets" + filename;
            //se.sendphp(fc.getAbsolutePath());
        }
        imagefield.setFitHeight(65);
        imagefield.setFitWidth(95);
        //..\img\google.png

        //C:\Users\splin\Documents\NetBeansProjects\FanArt\\com\esprit\img
        Enfant.pathfile = fc.getAbsolutePath();
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
