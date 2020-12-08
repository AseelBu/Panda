package View;

import java.io.File;
import java.io.IOException;

import Controller.DisplayController;
import Controller.QuestionMgmtController;
import Controller.ScoreBoardController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainscreenGUI extends Application {

    private AnchorPane mainBorder;
	private static Stage primary;	

	@Override
	public void start(Stage primaryStage) {
		DisplayController.mainscreen = this;
		try {
			mainBorder = FXMLLoader.load(getClass().getResource("/View/mainscreen.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(mainBorder);
//		scene.getStylesheets().add(getClass().getResource("/View/mainscreen.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hamka");
		primaryStage.setResizable(false);
//		primaryStage.initStyle(StageStyle.UNDECORATED);  is Used to lock windows, wont be able to move the window
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/View/pictures/logo.png")));
		primaryStage.show();
		primary = primaryStage;

	}

	public Stage getPrimary() {
		if(primary == null) {
			primary = new Stage();
		}
		return primary;
	}
	
    @FXML
    void exit(ActionEvent event) {
    	
    }

    @FXML
    void loadGame(ActionEvent event) {
    	File file = null;
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text", "*.txt");
	    FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().clear();
    	fileChooser.getExtensionFilters().add(extFilter);
    	
    	file = fileChooser.showOpenDialog(primary);
    	
    	if (file != null) {        	
        	DisplayController.getInstance().closeMainscreen();
        	DisplayController.getInstance().showBoard(file);
        }
    }

    @FXML
    void manageQuestions(ActionEvent event) {
    	
    	DisplayController.getInstance().closeMainscreen();
    	DisplayController.getInstance().showManageQuestions();
    }

    @FXML
    void scoreboard(ActionEvent event) {

    }

    @FXML
    void startGame(ActionEvent event) {
		DisplayController.getInstance().closeMainscreen();
		DisplayController.getInstance().showBoard();
    }
	
}
