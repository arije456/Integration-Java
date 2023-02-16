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
import Entities.Activite;
import Services.ActiviteService;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import GUI.Afficher_ActiviteController;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import static Entities.Activite.filename;
import java.io.File;

/**
 * FXML Controller class
 *
 * @author zeine
 */
public class Ajouter_ActiviteController implements Initializable {

    @FXML
    private TextField add_id_a;
    @FXML
    private TextField add_nom_a;
    @FXML
    private TextField add_cat;
    @FXML
    private TextField add_type;
    @FXML
    private TextField add_id_e;
    @FXML
    private Button add_act;
    @FXML
    private Button GOBACK;
    @FXML
    private Button afficher;
    @FXML
    private ImageView imageAct;
    public String imagecomp; 
    @FXML
    private Button upload;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
     private boolean ValidateFields() {
        if(add_id_a.getText().isEmpty() | add_nom_a.getText().isEmpty() | add_cat.getText().isEmpty() |  add_type.getText().isEmpty() | add_id_e.getText().isEmpty()) {
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
        ActiviteService bs= new ActiviteService();
        if(Integer.parseInt(bs.calculer_nbAct(add_nom_a.getText())) != 0) {
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
    private void add_act(ActionEvent event) {
       
        String ida= add_id_a.getText();
        int id = Integer.parseInt(ida);
        String catage= add_cat.getText();
        int cat = Integer.parseInt(catage);
        String ide1= add_id_e.getText();
        int ide= Integer.parseInt(ide1);
        
        //Activite a2= new Activite();
        Activite a = new Activite(id,add_nom_a.getText(), cat,add_type.getText(),Activite.filename, ide);
          if(ValidateFields()){
            if(ValidateFields2()){
        /*
        a2.setId_a(Integer.valueOf(add_id_a.getText()));
        a2.setNom_a(add_nom_a.getText());
        a2.setCat_age(Integer.valueOf(add_cat.getText()));
        a2.setType(add_type.getText());
        a2.setId_enfant(Integer.valueOf(add_id_e.getText()));
        ActiviteService acs = new ActiviteService();
        
        acs.ajouterActivite(a2);
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("succes");
        alert.setHeaderText(null);
        alert.setContentText("!!!succes !!!");
        alert.showAndWait();*/
        ActiviteService as = new ActiviteService();
        as.ajouterActivite(a);
   
        
    //  ObservableList<Activite> lista;
      FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Activite.fxml"));
      
       try{
           
           Parent root = loader.load();
           Afficher_ActiviteController ac = loader.getController();
           //ac.loadData();
           //ac.setAff_act(as.afficherActivite().toString());
           add_id_a.getScene().setRoot(root);
           Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("succes");
        alert.setHeaderText(null);
        alert.setContentText("!!!succes !!!");
        alert.showAndWait();
           
           
       }
       catch(IOException ex){
           System.out.println(ex.getMessage());
            System.out.println(ex.getMessage());
              Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.show();
       }
    }}}

    private void annuler_act(ActionEvent event) {
        /*
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Activite.fxml"));
        Activite a2= new Activite();
        a2.setId_a(Integer.valueOf(add_id_a.getText()));
      
        try{
            Parent root = loader.load();
           Afficher_ActiviteController ac = loader.getController();
           add_id_a.getScene().setRoot(root);
        }
        catch(IOException ex){
           System.out.println(ex.getMessage());
       }*/
    }

    @FXML
    private void GOBACK_TOACCUEIL(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void afficher_act(ActionEvent event) {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Activite.fxml"));
        Activite a2= new Activite();
        a2.setId_a(Integer.valueOf(add_id_a.getText()));
      
        try{
            Parent root = loader.load();
           Afficher_ActiviteController ac = loader.getController();
           add_id_a.getScene().setRoot(root);
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
            imageAct.setImage(i);
            imagecomp = fc.toString();
            System.out.println(imagecomp);
            int index = imagecomp.lastIndexOf('\\');
            if (index > 0) {
                filename = imagecomp.substring(index + 1);
            }

           Activite.filename = "C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\src\\Assets" + filename;
            //se.sendphp(fc.getAbsolutePath());
        }
        imageAct.setFitHeight(65);
        imageAct.setFitWidth(95);
        //..\img\google.png

        //C:\Users\splin\Documents\NetBeansProjects\FanArt\\com\esprit\img
        Activite.pathfile = fc.getAbsolutePath();
    }

    
}

