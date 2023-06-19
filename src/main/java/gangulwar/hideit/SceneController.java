package gangulwar.hideit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class SceneController {
    private Scene scene;
    private Parent root;
    private Stage stage;

    @FXML
    private TextField inputImagePathTextField;

    @FXML
    private TextField outputImagePathTextField;

    @FXML
    private TextArea messageToHide;

    @FXML
    private Label successfulMessage;

    @FXML
    private TextArea messageRetrived;

    public void encodeScene(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("encode_screen.fxml"));
        root = loader.load();
        scene = new Scene(root);
        setSceneCSS(scene);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void decodeScene(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("decode_screen.fxml"));
        root = loader.load();
        scene = new Scene(root);
        setSceneCSS(scene);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void mainScene(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("starting_scene.fxml"));
        root = loader.load();
        scene = new Scene(root);
        setSceneCSS(scene);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            String selectedFilePath = selectedFile.getAbsolutePath();
            System.out.println("Selected File Path: " + selectedFilePath);

            inputImagePathTextField.setText(selectedFilePath);
        } else {
            inputImagePathTextField.setText("Error in selecting file path");
        }
    }

    public void openFolderChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");

        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            String selectedFolderpath = selectedDirectory.getAbsolutePath();
            System.out.println("Output Path = " + selectedFolderpath);
            outputImagePathTextField.setText(selectedFolderpath);
        } else {
            outputImagePathTextField.setText("Error in selecting Directory");
        }
    }

    public void startEncoding() {
        String imagepath = inputImagePathTextField.getText();
        String message = messageToHide.getText() + ".";
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("_yyyy-MM-dd_HH-mm-ss");

        String formattedDateTime = currentDateTime.format(formatter);

        String outputImagePath = outputImagePathTextField.getText() + "\\encoded_" + formattedDateTime + ".png";

        System.out.println(outputImagePath);
        ImageSteganography.hideMessage(imagepath, message, outputImagePath);
        successfulMessage.setVisible(true);
    }

    public void startDecoding() {
        String imagePath = inputImagePathTextField.getText();
        messageRetrived.setVisible(true);
        messageRetrived.setText("Retrieved Message: " + ImageSteganography.retrieveMessage(imagePath));
    }

    private void setSceneCSS(Scene scene) {
        String css = Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm();
        scene.getStylesheets().add(css);
    }
}