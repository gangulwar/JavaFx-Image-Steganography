package gangulwar.hideit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Starting extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        try {
            System.out.println("Starting...\ngangulwar.hideit");

            Parent parent = FXMLLoader.load(getClass().getResource("starting_scene.fxml"));
            Scene scene = new Scene(parent);
            Image image = new Image(getClass().getResource("/gangulwar/hideit/assests/Image.png").toExternalForm());
            stage.getIcons().add(image);
            stage.setTitle("Hide It!");

            String css = getClass().getResource("style.css").toExternalForm();
            scene.getStylesheets().add(css);

            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
