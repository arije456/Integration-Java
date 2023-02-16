
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;   
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author zeine
 */
public class MapsController implements MapComponentInitializedListener {

    @FXML
    private TextField toTextFieldAction;
    @FXML
    private TextField fromTextField;

    //implements  MapComponentInitializedListener
    //Initializable
    //@Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    //AIzaSyCYL7GC8RuujvLyy0VV6rfTm-NRQufumWM

    private GoogleMap map = null;
    @Override
    public void mapInitialized() {
        
    }

    @FXML
    private void toTextFieldAction(ActionEvent event) {
    }

    @FXML
    private void fromTextField(ActionEvent event) {
    }
    
}
