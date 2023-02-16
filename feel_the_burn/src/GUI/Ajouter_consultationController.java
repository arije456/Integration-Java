/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Consultation;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import Services.consultationservices;
import GUI.Afficher_consultationController;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author GAMRA
 */
public class Ajouter_consultationController implements Initializable {

    @FXML
    private Button ajouter;
    @FXML
    private TextField add_id;
    @FXML
    private TextField add_nom;
    @FXML
    private TextField add_prenom;
    @FXML
    private TextField add_age;
    @FXML
    private TextField add_mail;
    @FXML
    private TextArea add_etat;
    @FXML
    private TextField add_date;
    @FXML
    private TextField add_sexe;
    @FXML
    private TextField add_categorie;
    @FXML
    private Button Afficher;
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
        if(add_id.getText().isEmpty() | add_nom.getText().isEmpty() | add_prenom.getText().isEmpty() |  add_age.getText().isEmpty() | add_mail.getText().isEmpty() | add_etat.getText().isEmpty() | add_date.getText().isEmpty() | add_sexe.getText().isEmpty() | add_categorie.getText().isEmpty()) {
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
        consultationservices bs= new consultationservices();
        if(Integer.parseInt(bs.calculer_nbseance(add_nom.getText())) != 0) {
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
    private void ajouter_consult(ActionEvent event) {
       Consultation c= new Consultation();
       if(ValidateFields()){
            if(ValidateFields2()){
                if(accepterEmail()){
                    if(setDate()){
                
       c.setId_c(Integer.valueOf(add_id.getText()));
       c.setNom(add_nom.getText());
       c.setPrenom(add_prenom.getText());
       c.setSexe(add_sexe.getText());
       c.setAge(Integer.valueOf(add_age.getText()));
       c.setEmail(add_mail.getText());
       c.setEtat_physique (add_etat.getText());
       c.setDate_rdv(add_date.getText());
       c.setcategorie_c(add_categorie.getText());
       
          consultationservices con = new consultationservices();
          con.ajouterconsultation(c);
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("succes");
        alert.setHeaderText(null);
        alert.setContentText("!!!succes !!!");
        alert.showAndWait();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("afficher_consultation.fxml"));
         
       try{
           
           Parent root = loader.load();
            Afficher_consultationController ac = loader.getController();
           add_id.getScene().setRoot(root);
           
           
       }
       catch(IOException ex){
           System.out.println(ex.getMessage());
       }}}

        
         
    }}}

    @FXML
    private void afficher_consult(ActionEvent event) {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("afficher_consultation.fxml"));
        Consultation c1= new Consultation();
        c1.setId_c(Integer.valueOf(add_id.getText()));
      
        try{
            Parent root = loader.load();
           Afficher_consultationController ac = loader.getController();
           add_id.getScene().setRoot(root);
        }
        catch(IOException ex){
           System.out.println(ex.getMessage());
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
    public boolean setDate(){
         boolean ok = false;
        String format="dd/MM/yyyy";
        SimpleDateFormat df = new SimpleDateFormat(format);
        df.setLenient(false);
        try {
            Date date = df.parse(add_date.getText());
            ok = true;
        } catch (ParseException ex) {
           ok= false;
         Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validate Fields");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid date");
       // bs.calculer_nbseance(Add_Nom_Co.getText())
       //alert.setContentText(bs.calculer_nbseance(Add_Nom_Co.getText()));
        alert.showAndWait();
        }
        return ok;
    }
     public boolean accepterEmail() {
         boolean ok = false;
         String message = "", tex = add_mail.getText().trim();
         int posiArrobase = 0, posiPoint = 0, posi2 = 0;
 
        if (tex.contains(" ")) {                          // signaler l'espace
            message = " il y a un blanc dans l''adresse email ";
        }
        if (message.length() == 0) {
            posiArrobase = tex.indexOf("@");
            if (posiArrobase < 0) {
                message = " arrobase (@) manque dans l'adresse email ";
            }
            if ((posiArrobase == 0) || (tex.endsWith("@"))) {
                message = " arrobase (@) mal placé dans l'adresse email ";
            }
            if ((posiArrobase > 0) && (posiArrobase < tex.length())) {
                posi2 = tex.indexOf("@",posiArrobase+1);
                if (posi2 > posiArrobase) {
                    message = " double arrobase (@) dans l'adresse email ";
                }
            }
            if (message.length() == 0) {
                posiPoint = tex.indexOf(".");
                if (posiPoint == -1) {
                    message = " on doit trouver au moins un point dans l'adresse email ";
                }
                if ((posiPoint == 0) || (tex.endsWith(".")))  {
                    message = " point mal placé dans l'adresse email ";
                }
            }
            if (message.length() == 0) {
                if (tex.length() == 0) {
                    message = " l'adresse email est vide ";
                }
            }
        }
        if (message.length() == 0) {
            ok = true;
        }
        else {
            JOptionPane.showMessageDialog(null,message);
            //tf_email.requestFocusInWindow();
        }
         return(ok);
     }
    
    
}
