package View;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ManageQuestions extends Application {
	
    private AnchorPane mainBorder;
	private static Stage primary;

	@Override
	public void start(Stage primaryStage) {
		try {
			mainBorder = FXMLLoader.load(getClass().getResource("/View/ManageQuestions.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(mainBorder);
//		scene.getStylesheets().add(getClass().getResource("/View/manage.css").toExternalForm());
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

}
