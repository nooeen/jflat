package jflat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class JFlatGUI extends Application {
    Database dictDB = new Database();
    GCloudTTS tts = new GCloudTTS();
    GCloudTranslator translator = new GCloudTranslator();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("jflat.fxml"));
        primaryStage.setTitle("JFlat");
        primaryStage.setScene(new Scene(root, 960, 540));
        ListView listBoxAll = new ListView();
        listBoxAll.getItems().add("Item 1");
        listBoxAll.getItems().add("Item 2");
        listBoxAll.getItems().add("Item 3");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
