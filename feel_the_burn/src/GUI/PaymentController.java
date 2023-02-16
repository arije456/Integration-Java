
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import Entities.Produit;
import Services.ProduitService;


public class PaymentController implements Initializable {

    @FXML
    private TextField id_pay;
    @FXML
    private TextField price_pay;
    @FXML
    private Button pay_now;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void payment(ActionEvent event) {
        
    }

    @FXML
    private void keyPressed(KeyEvent event) {
        Produit p = new Produit();
        ProduitService ps= new ProduitService();
        if (event.getCode().equals(KeyCode.ENTER)){
            String cardno = id_pay.getText();
            
            try{
                
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    
}
