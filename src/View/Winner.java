package View;

import java.io.IOException;

import Controller.BoardController;
import Controller.DisplayController;
import Controller.ScoreBoardController;
import Utils.PrimaryColor;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Winner extends Application{

	private static AnchorPane mainAnchor;
	private Stage primary;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			mainAnchor = FXMLLoader.load(getClass().getResource("/View/Winner.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(mainAnchor);
		scene.getStylesheets().add(getClass().getResource("/View/board.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hamka");
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/View/pictures/logo.png")));
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {

			}
		});
		primary = primaryStage;
		
	}

	/**
	 * loads score of the winner
	 */
	public void loadDisplay(String name, int score, PrimaryColor color) {
//		int score = BoardController.getInstance().getWinnerScore();
		if(score == Integer.MIN_VALUE) {
			Label lbl = (Label) mainAnchor.lookup("#Score");
			lbl.setVisible(false);
			Label txt = (Label) mainAnchor.lookup("#txt");
			txt.setText("It is a tie..");
			((Label) mainAnchor.lookup("#scorelbl")).setVisible(false);;
			((ImageView) mainAnchor.lookup("#img")).setVisible(false);;
		}else {
			Label lbl = (Label) mainAnchor.lookup("#Score");
			lbl.setText(String.valueOf(score));
			Label txt = (Label) mainAnchor.lookup("#txt");
			txt.setText(name + " Has Won!");
			ImageView img = (ImageView) mainAnchor.lookup("#img");
			img.setImage(new Image(getClass().getResource("pictures/Queen_" + color + ".png").toString()));
		}
	}
	
	public Stage getPrimary() {
		if(primary == null) {
			primary = new Stage();
		}
		return primary;
	}

	public void destruct() {
		mainAnchor = null;
		primary = null;
	}
	
    @FXML
    void finish(ActionEvent event) {
    	ScoreBoardController.getInstance().writeHistory();
    	DisplayController.getInstance().closeWinner();
    	DisplayController.getInstance().showMainScreen();
    	DisplayController.getInstance().closeBoard();
    }
}
