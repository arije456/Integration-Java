/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Services.BlogService;
import Entities.Blog;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import static Entities.Blog.filename;
import com.twilio.Twilio;
import static com.twilio.example.Example.ACCOUNT_SID;
import static com.twilio.example.Example.AUTH_TOKEN;
import com.twilio.rest.api.v2010.account.Message;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ARIJ
 */
public class AjouterBlogController implements Initializable {

    @FXML
    private TextField Id_b;
    @FXML
    private TextField Titre;
    @FXML
    private TextField Auteur;
    @FXML
    private TextField date;
    @FXML
    private TextField contenu;
    @FXML
    private TextField categorie;
    @FXML
    private Button ajouter;
    @FXML
    private Button afficher;
    @FXML
    private ImageView imagefield;
    @FXML
    private Button upload;
    public String imagecomp; 
    public static final String ACCOUNT_SID = "ACf8c4371d8c45bf5dbc55d4a677acd8f4";
    public static final String AUTH_TOKEN = "52226b6188420c815a48535eabdeebcb";
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
        if(Id_b.getText().isEmpty() | Titre.getText().isEmpty() | Auteur.getText().isEmpty() |  date.getText().isEmpty() | contenu.getText().isEmpty() | categorie.getText().isEmpty()) {
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
        BlogService bs= new BlogService();
        if((bs.calculer_nbblog(Auteur.getText())) != 0) {
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
    private void ajouter_Blog(ActionEvent event) {
        String idb= Id_b.getText();
        int id= Integer.parseInt(idb);
        Blog b1=new Blog(id,Titre.getText(), contenu.getText(),date.getText(),Auteur.getText(),Blog.filename,categorie.getText());
        
         if(ValidateFields()){
            if(ValidateFields2()){
       // b1.setId_b(Integer.valueOf(Id_b.getText()));
        //b1.setTitre(Titre.getText());
        //b1.setContenu(contenu.getText());
        //b1.setDate(date.getText());
        // b1.setAuteur(Auteur.getText());
        // b1.setImage(image.getText());
       // b1.setcategorie(categorie.getText());
        BlogService bs = new BlogService();
        bs.ajouterBlog(b1);
        
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("succes");
        alert.setHeaderText(null);
        alert.setContentText("!!!succes !!!");
        alert.showAndWait();
        //SEND SMS START  win affichage !!
        
          Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+216 20072897"),
                new com.twilio.type.PhoneNumber("+17152882584"),
                "Blog ajouter a ete  ajouté avec success")
            .create();

        System.out.println(message.getSid());
        
        //SEND SMS END
        
        
        
        
        
        
      ObservableList<Blog> listb;
      FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Blog.fxml"));
      
       try{
           
           Parent root = loader.load();
           Afficher_BlogController ac = loader.getController();
           //ac.loadData();
           //ac.setAff_act(as.afficherActivite().toString());
           Id_b.getScene().setRoot(root);
           
           
       }
       catch(IOException ex){
           System.out.println(ex.getMessage());
       }
    }
         }}
    @FXML
    private void afficher_Blog(ActionEvent event) {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Afficher_Blog.fxml"));
        Blog b1=new Blog();
        b1.setId_b(Integer.valueOf(Id_b.getText()));
      
        try{
            Parent root = loader.load();
           Afficher_BlogController bs = loader.getController();
           Id_b.getScene().setRoot(root);
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

          Blog.filename = "C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\src\\Assets" + filename;
            //se.sendphp(fc.getAbsolutePath());
        }
        imagefield.setFitHeight(65);
        imagefield.setFitWidth(95);
        //..\img\google.png

        //C:\Users\splin\Documents\NetBeansProjects\FanArt\\com\esprit\img
        Blog.pathfile = fc.getAbsolutePath();
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
