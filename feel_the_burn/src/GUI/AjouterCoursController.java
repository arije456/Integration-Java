/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Services.CoursServices;
import Entities.Cours;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author WAEL
 */
public class AjouterCoursController implements Initializable {

    @FXML
    private Button Ajouter_Cours;
    @FXML
    private Button Afficher_Cours;
    @FXML
    private TextField Add_Nom_Co;
    @FXML
    private TextField Add_Id_C;
    @FXML
    private TextField Add_Date_Co;
    @FXML
    private TextField Add_Id_Co;

    /**
     * Initializes the controller class.
     */
    
     ////////////////////////////////////////////////tejrab auto complete
    private AutoCompletionBinding<String> autoComplitionBinding;
    private String[] _possibleSuggestions = {"Cardio","RPM","Musculation","Yoga","Bodycombat","Bodyattack","Spinning","Caf","Pilates","Trx","Hiit","Zumba","Bodypump","Bodysculpt","Circuittraining","Dance","Boxe"};
    private Set <String> possibleSuggestions = new HashSet<>(Arrays.asList(_possibleSuggestions));
    @FXML
    private Button GOBACK;
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         /////kilaadaa tebaa auto complete
     TextFields.bindAutoCompletion(Add_Nom_Co, possibleSuggestions);
         
       // Add_Nom_Co.setText("30");
        // TODO
    } 
    private boolean ValidateFields() {
        if(Add_Nom_Co.getText().isEmpty() | Add_Id_C.getText().isEmpty() | Add_Date_Co.getText().isEmpty() |  Add_Id_Co.getText().isEmpty()) {
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
        CoursServices bs= new CoursServices();
        if(Integer.parseInt(bs.calculer_nbseance(Add_Nom_Co.getText())) != 0) {
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
    private void Ajouter_Cours(ActionEvent event) throws IOException {
        
        Cours c= new Cours();
        if(ValidateFields()){
            if(ValidateFields2()){

        c.setId_C(Integer.valueOf(Add_Id_C.getText()));
        c.setNom_C(Add_Nom_Co.getText());
        c.setDate_C(Add_Date_Co.getText());
        c.setId_Co(Integer.valueOf(Add_Id_Co.getText()));
        
        CoursServices acs = new CoursServices();
        acs.ajouterCours(c);
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("succes");
        alert.setHeaderText(null);
        alert.setContentText("!!!succes !!!");
        alert.showAndWait();
        
   
        
      //ObservableList<Cours> lista;
      FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Cours.fxml"));
      
       try{
           
           Parent root = loader.load();
           Afficher_CoursController ac = loader.getController();
           //ac.loadData();
           //ac.setAff_act(as.afficherActivite().toString());
           Add_Id_C.getScene().setRoot(root);
           
           
       }
       catch(IOException ex){
           System.out.println(ex.getMessage());
       }
    }
        }
        Calendar(Add_Nom_Co.getText(),Add_Date_Co.getText(),Add_Date_Co.getText());
    }
    @FXML
    private void Afficher_Cours(ActionEvent event) {
        
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Cours.fxml"));
        Cours a2= new Cours();
        a2.setId_C(Integer.valueOf(Add_Id_C.getText()));
      
        try{
            Parent root = loader.load();
           Afficher_CoursController ac = loader.getController();
           Add_Id_C.getScene().setRoot(root);
        }
        catch(IOException ex){
           System.out.println(ex.getMessage());
       }
    }
     public void Calendar(String Titre , String Datedeb , String Datefin) throws MalformedURLException, IOException{
        // Using Calendar api
          URL url = new URL("https://www.googleapis.com/calendar/v3/calendars/c_u5lhds76rf6q93bg5gcpguq9f8@group.calendar.google.com/events");
HttpURLConnection http = (HttpURLConnection)url.openConnection();
http.setRequestMethod("POST");
http.setDoOutput(true);
http.setRequestProperty("Authorization", "Bearer ya29.A0ARrdaM8dVFnkJviJkSUZLkRIW9PrDEOvq_08KJUNIXp5fQJf1Nnp5d7liX366i0f1IHBjB7F7ps2BBUT_V8mFLp3-iu2jn2aEwAMY7WToGazQUB0RZJ0pu9SsYbls8kAlei3hN_WMRgGdR0zi_jehMXUY5w2");
http.setRequestProperty("Content-Type", "application/json");

//String data = "{\n\"summary\": \""+Titre+"\",\n  \"location\": \"feel the burn Application\",\n  \"start\": {\n    \"dateTime\": \""+Datedeb+"T10:00:00.000-06:00\"\n  },\n  \"end\": {\n    \"dateTime\": \""+Datefin+"T10:25:00.000-06:00\"\n    }\n\n}";
//T10:00:00.000-06:00 heya 16h yaani 4pm
String data = "{\n\"summary\": \""+Titre+"\",\n  \"location\": \"feel the burn Application\",\n  \"start\": {\n    \"dateTime\": \""+Datedeb+"\"\n  },\n  \"end\": {\n    \"dateTime\": \""+Datefin+"\"\n    }\n\n}";
//String data = "{\n\"summary\": \"tournament\",\n  \"location\": \"Arena Application\",\n  \"start\": {\n    \"dateTime\": \""+tfDateDebut.getValue().format(DateTimeFormatter.ISO_DATE)+"T10:00:00.000-07:00\"\n  },\n  \"end\": {\n    \"dateTime\": \""+tfDateFin.getValue().format(DateTimeFormatter.ISO_DATE)+"\n    },\n\"etag\": \"\", \n      \"backgroundColor\": \"#b80672\", \n      \"timeZone\": \"UTC\", \n      \"accessRole\": \"reader\",\n\"kind\": \"calendar#calendarListEntry\", \n      \"foregroundColor\": \"#ffffff\", \n      \"defaultReminders\": [], \n      \"colorId\": \"2\"\n\n}\n";
byte[] out = data.getBytes(StandardCharsets.UTF_8);

OutputStream stream = http.getOutputStream();
stream.write(out);

System.out.println(http.getResponseCode() + " " + http.getResponseMessage() + "Cours added to Calendar Successfully");
http.disconnect();
        
        // end Calendar 
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
    

