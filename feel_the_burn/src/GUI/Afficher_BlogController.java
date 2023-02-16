/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Services.BlogService;
import Entities.Blog;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import GUI.Media_BlogController;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Optional;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 * FXML Controller class
 *
 * @author ARIJ
 */
public class Afficher_BlogController implements Initializable {

    @FXML
     TableView<Blog> table_blog;
     @FXML
    private TableColumn<Blog, Integer> Id_aff;
    @FXML
    private TableColumn<Blog, String> titre_affich;
    @FXML
    private TableColumn<Blog, String> auteur_aff;
    @FXML
    private TableColumn<Blog, String> date_aff;
    @FXML
    //private TableColumn<Blog, String> image_aff;
    private TableColumn<Blog, ImageView> image_aff;
    @FXML
    private TableColumn<Blog, String> contenu_aff;
    @FXML
    private TableColumn<Blog, String> categorie_aff;
    @FXML
    private Button del_blg;
    public static String idxx;
    @FXML
    private Button modifier;
    @FXML
    private TextField recherche;
    @FXML
    private Button Media;
    //private Media_BlogController Media_BlogController;
    File filesJpg[];
    @FXML
    private Button click;
    ObservableList<Blog> list;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BlogService bs= new BlogService();
        
        table_blog.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
              Id_aff.setText(String.valueOf(bs.liste2().get(table_blog.getSelectionModel().getSelectedIndex()).getId_b()));
               titre_affich.setText( bs.liste2().get(table_blog.getSelectionModel().getSelectedIndex()).getTitre());
               auteur_aff.setText((bs.liste2().get(table_blog.getSelectionModel().getSelectedIndex()).getAuteur()));
               image_aff.setText((bs.liste2().get(table_blog.getSelectionModel().getSelectedIndex()).getImage() ));
               contenu_aff.setText((bs.liste2().get(table_blog.getSelectionModel().getSelectedIndex()).getContenu() ));
               //categorie_aff.setText((bs.liste2().get(table_blog.getSelectionModel().getSelectedIndex()).getcategorie() ));
               categorie_aff.setText(bs.liste2().get(table_blog.getSelectionModel().getSelectedIndex()).getcategorie());
               date_aff.setText(bs.liste2().get(table_blog.getSelectionModel().getSelectedIndex()).getDate());
               //image_aff.setText((bs.liste2().get(table_blog.getSelectionModel().getSelectedIndex()).getImage() ));
              // contenu_aff.setText((bs.liste2().get(table_blog.getSelectionModel().getSelectedIndex()).getContenu() ));
               //categorie_aff.setText((bs.liste2().get(table_blog.getSelectionModel().getSelectedIndex()).getcategorie() ));
               
            };
        });
        
        
                 ObservableList<Blog> list;
                 try
                 {
                     list = bs.getBlogList();
                     Id_aff.setCellValueFactory(new PropertyValueFactory<>("Id_b"));
                     titre_affich.setCellValueFactory(new PropertyValueFactory<>("Titre"));
                     auteur_aff.setCellValueFactory(new PropertyValueFactory<>("Auteur"));
                     //image_aff.setCellValueFactory(new PropertyValueFactory<>("Image"));
                    image_aff.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Blog, ImageView>, ObservableValue<ImageView>>() {
                         @Override
                         public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Blog, ImageView> param) {
                             return new ReadOnlyObjectWrapper(param.getValue().getImage());
                         }
                     });

                     //date_aff.setCellValueFactory(new PropertyValueFactory<>("Date"));
                     //image_aff.setCellValueFactory(new PropertyValueFactory<>("Image"));
                     contenu_aff.setCellValueFactory(new PropertyValueFactory<>("Contenu"));                
                     categorie_aff.setCellValueFactory(new PropertyValueFactory<>("categorie"));
                     date_aff.setCellValueFactory(new PropertyValueFactory<>("Date"));
                     table_blog.setItems(list);
                   
                     
                   // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Blog> filteredData = new FilteredList<>(list, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		recherche.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Blog -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Blog.getTitre().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} 
                                else if (Blog.getDate().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
                              
				} 
                                else if (String.valueOf(Blog.getId_b()).indexOf(lowerCaseFilter)!=-1)
				     return true;
                                else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Blog> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(table_blog.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		table_blog.setItems(sortedData);
                
                
                 } catch (SQLException ex) {
            Logger.getLogger(Afficher_BlogController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }    
    //----------------------------- Bouton supprimer -----------------------------------//
    
    BlogService bs=new BlogService();
    
    public void delete()
    {
       bs.supprimerBlog( table_blog.getSelectionModel().getSelectedItem().getId_b());
        System.out.println(table_blog.getSelectionModel().getSelectedItem().getId_b());
       
    }

    @FXML
    private void liste_blog(MouseEvent event) {
        
    }

    @FXML
    private void supprimer_blog(ActionEvent event) {
        delete();
        table_blog.getItems().removeAll(table_blog.getSelectionModel().getSelectedItem());
        System.out.println(table_blog);
        table_blog.refresh();
        
    }

   private void update_blog(ActionEvent event) throws IOException {
                
    }

    @FXML
    private void modifier_blg(ActionEvent event) throws IOException {
        idxx= (Integer.toString(table_blog.getSelectionModel().getSelectedItem().getId_b()));
               
        FXMLLoader loader = new FXMLLoader();
       
                Stage prStage =new Stage(); 
                loader.setLocation(getClass().getResource("Modifier_blog.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                prStage.setScene(scene);
                prStage.show();
    }

    
    @FXML
    private void Media_Video(ActionEvent event) throws IOException {
         if(table_blog.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Warnning");
            alert.setHeaderText("SELECT YOUR Blog");
            Optional<ButtonType> result1 = alert.showAndWait();}
       else{
            try {
                Parent root;

                root = FXMLLoader.load(getClass().getResource("Media_Blog.fxml"));

                Media.getScene().setRoot(root);
            } catch (IOException ex) {
                Logger.getLogger(Afficher_programmeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    
       @FXML
    private void click(MouseEvent event) {
    Blog b= table_blog.getSelectionModel().getSelectedItem();
    /*        MediaController. videoname = null;
        MediaController. videoname = p.getNom_p();*/
    Media_BlogController.videoName= null;
    Media_BlogController.videoName= b.getTitre();
    
    }
     /*
  Media_BlogController mb=  Media_BlogController;
  public void mc.click(MouseEvent event) {
    }*/

    @FXML
    private void Excel(ActionEvent event) throws IOException {
          Writer writer = null;
        try {
            //badel path fichier excel
            File file = new File("C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\Blog.csv");
            writer = new BufferedWriter(new FileWriter(file));
            for (Blog ev : list) {

                String text = ev.getId_b()+"," +ev.getTitre()+ "," + ev.getContenu()+ ","+ev.getImage()+ "\n";

                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {

            writer.flush();
             writer.close();
              Alert alert= new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("excel");
        alert.setHeaderText(null);
        alert.setContentText("!!!excel exported!!!");
        alert.showAndWait();
        }
        
    }

    @FXML
    private void Recherche(ActionEvent event) {
         ObservableList<Blog> list;
                 try
                 {
                     list = bs.getBlogList();
                     Id_aff.setCellValueFactory(new PropertyValueFactory<>("Id_b"));
                     titre_affich.setCellValueFactory(new PropertyValueFactory<>("Titre"));
                     auteur_aff.setCellValueFactory(new PropertyValueFactory<>("Auteur"));
                     //image_aff.setCellValueFactory(new PropertyValueFactory<>("Image"));
                    image_aff.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Blog, ImageView>, ObservableValue<ImageView>>() {
                         @Override
                         public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Blog, ImageView> param) {
                             return new ReadOnlyObjectWrapper(param.getValue().getImage());
                         }
                     });

                     //date_aff.setCellValueFactory(new PropertyValueFactory<>("Date"));
                     //image_aff.setCellValueFactory(new PropertyValueFactory<>("Image"));
                     contenu_aff.setCellValueFactory(new PropertyValueFactory<>("Contenu"));                
                     categorie_aff.setCellValueFactory(new PropertyValueFactory<>("categorie"));
                     date_aff.setCellValueFactory(new PropertyValueFactory<>("Date"));
                     table_blog.setItems(list);
                   
                     
                   // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Blog> filteredData = new FilteredList<>(list, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		recherche.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Blog -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Blog.getTitre().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} 
                                else if (Blog.getDate().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
                              
				} 
                                else if (String.valueOf(Blog.getId_b()).indexOf(lowerCaseFilter)!=-1)
				     return true;
                                else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Blog> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(table_blog.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		table_blog.setItems(sortedData);
                
                
                 } catch (SQLException ex) {
            Logger.getLogger(Afficher_BlogController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    }
    

