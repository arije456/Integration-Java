
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;


public class CameraController extends Application {
   // implements Initializable
   private final Group space = new Group();
    private Sphere moon;
    
    public static void main(final String[] args) {
        launch(args);/*w   w  w .   d  e m  o   2 s    .c    om  */
    }
    
   // @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    // @Override
    public void start(final Stage stage){
        final Scene scene = new Scene(this.space, 800, 600, true, SceneAntialiasing.BALANCED);
        scene.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("C:\\Users\\zeine\\Music\\feel_the_burn\\src\\Assets\\stars.jpg"))));

        final Camera camera = new PerspectiveCamera(true);
        camera.setFarClip(1000);
        camera.setTranslateX(-15);
        camera.setTranslateY(-15);
        camera.setTranslateZ(-150);
        scene.setCamera(camera);
        
        
        final Box xAxis = new Box(100, 1, 1);
        final Box yAxis = new Box(1, 100, 1);
        final Box zAxis = new Box(1, 1, 100);
        this.space.getChildren().addAll(xAxis, yAxis, zAxis);

        xAxis.setMaterial(new PhongMaterial(Color.RED));
        yAxis.setMaterial(new PhongMaterial(Color.GREEN));
        zAxis.setMaterial(new PhongMaterial(Color.BLUE));
        
        this.moon = new Sphere(25);
        this.space.getChildren().add(this.moon);
        final PhongMaterial surface = new PhongMaterial();
        surface.setDiffuseMap(new Image(getClass().getResourceAsStream("moon.jpg")));
        this.moon.setMaterial(surface);

        final PointLight sun = new PointLight();
        sun.setTranslateX(30);
        sun.setTranslateY(-30);
        sun.setTranslateZ(-50);
        this.space.getChildren().add(sun);

        stage.setTitle("In 80 lines to the moon (use left/right arrow keys and mouse wheel)");
        stage.setScene(scene);
        stage.show();
        
         this.moon.setRotationAxis(new Point3D(0, 1, 0));
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                this.moon.setRotate(this.moon.getRotate() - 1);
            } else if (event.getCode() == KeyCode.RIGHT) {
                this.moon.setRotate(this.moon.getRotate() + 1);
            }
        });
        scene.setOnScroll(event -> {
            this.space.setTranslateZ(this.space.getTranslateZ() - event.getDeltaY() / 5);
        });
        
    }
    
}
