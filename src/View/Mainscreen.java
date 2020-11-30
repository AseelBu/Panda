package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Mainscreen extends Application {

    private AnchorPane mainBorder;
	private static Stage primary;	

	@Override
	public void start(Stage primaryStage) throws Exception {
		mainBorder = FXMLLoader.load(getClass().getResource("/View/mainscreen.fxml"));
		Scene scene = new Scene(mainBorder);
//		scene.getStylesheets().add(getClass().getResource("/View/mainscreen.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hamka");
		primaryStage.setResizable(false);
//		primaryStage.initStyle(StageStyle.UNDECORATED);  is Used to lock windows, wont be able to move the window
//		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("logo1.png"))); add logo
		primaryStage.show();
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

    }

    @FXML
    void manageQuestions(ActionEvent event) {

    }

    @FXML
    void scoreboard(ActionEvent event) {

    }

    @FXML
    void startGame(ActionEvent event) {

    }
	
}
