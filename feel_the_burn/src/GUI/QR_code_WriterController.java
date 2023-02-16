/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author zeine
 */
public class QR_code_WriterController extends Application implements Initializable {

    @FXML
    private StackPane sp;
    @FXML
    private Button btn_produit;

    /**
     * Initializes the controller class. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
           // TODO
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String myPath ="C:\\Users\\zeine\\OneDrive\\Documents";
        int width = 300;
        int height = 300;
        String fileType ="png";
        
        
        BufferedImage bufferedImage = null;
        
        try {
            BitMatrix byteMatrix = qrCodeWriter.encode(myPath,BarcodeFormat.QR_CODE,width,height);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();
            
            Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
            graphics2D.setColor(Color.WHITE);
            graphics2D.fillRect(0, 0, width, height);
            graphics2D.setColor(Color.BLACK);
            
            for(int i = 0; i< height; i++) {
                for(int j = 0 ; j < width; j++) {
                    if(byteMatrix.get(i, j)) {
                        graphics2D.fillRect(i, j, 1,1);
                    }
                }
            }
            System.out.println("success");

        }catch(WriterException wr) {
            wr.printStackTrace();
        }
        ImageView qrView = new ImageView();
        Button buton = new Button("Afficher Produit");
        qrView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        sp.getChildren().add(buton);
        sp.getChildren().add(qrView);
    
            buton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("click");
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("Afficher_Produit.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(QR_code_WriterController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            });
                    
       
        
    }    

    @Override
    public void start(Stage primaryStage) throws Exception {
      
        
    }

    @FXML
    private void GoToAffiche(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Afficher_Produit.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

                        stage.setScene(scene);
        stage.show();
    }
    
}
