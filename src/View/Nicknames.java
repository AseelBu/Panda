package View;

import java.io.IOException;

import Controller.BoardController;
import Utils.PrimaryColor;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Nicknames extends Application {

	private static AnchorPane mainAnchor;
	private Stage primary;

	@Override
	public void start(Stage primaryStage) {
		try {
			mainAnchor = FXMLLoader.load(getClass().getResource("/View/Nicknames.fxml"));
			mainAnchor = new AnchorPane();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(mainAnchor, 578.0, 312.0);
//		scene.getStylesheets().add(getClass().getResource("/View/board.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hamka");
		primaryStage.setResizable(false);
		//		primaryStage.initStyle(StageStyle.UNDECORATED);  is Used to lock windows, wont be able to move the window
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/View/pictures/logo.png")));
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {

			}
		});
		primary = primaryStage;
		
		loadDesign();
	}
	
	/**
	 * Loads Screen Design
	 */
	public void loadDesign() {
		mainAnchor.getChildren().clear();
		AnchorPane pane = new AnchorPane(); // this pane can be as a background pic
		pane.setPrefHeight(312);
		pane.setPrefWidth(587);
		AnchorPane.setBottomAnchor(pane, 0.0);
		AnchorPane.setLeftAnchor(pane, 0.0);
		AnchorPane.setRightAnchor(pane, 0.0);
		AnchorPane.setTopAnchor(pane, 0.0);
		mainAnchor.getChildren().add(pane);
		
		Button btn = new Button("Start Game");
		btn.setMnemonicParsing(false);
		btn.setLayoutX(257);
		btn.setLayoutY(256);
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Player 1 Nickname: " + getPlayerName(PrimaryColor.WHITE));
				System.out.println("Player 2 Nickname: " + getPlayerName(PrimaryColor.BLACK));

			}
		});
		
		ImageView pic = new ImageView(new Image(getClass().getResource("pictures/logo.gif").toString()));
		pic.setFitHeight(158.0);
		pic.setFitWidth(162.0);
		pic.setLayoutX(215.0);
		pic.setLayoutY(31.0);
		pic.setPickOnBounds(true);
		pic.setPreserveRatio(true);
		
		//Player 1
		Label lbl1 = new Label("White Player:");
		lbl1.setLayoutX(31.0);
		lbl1.setLayoutY(102.0);
		lbl1.setFont(new Font("Berlin Sans FB Demi Bold", 14.0));
		
		TextField txt1 = new TextField();
		txt1.setId("WHITE");
		txt1.setLayoutX(33.0);
		txt1.setLayoutY(125.0);
		txt1.setPromptText("Nickname");
		
		ImageView pic1 = new ImageView(new Image(getClass().getResource("pictures/Soldier_WHITE.png").toString()));
		pic1.setFitHeight(67.0);
		pic1.setFitWidth(76.0);
		pic1.setLayoutX(74.0);
		pic1.setLayoutY(30.0);
		pic1.setPickOnBounds(true);
		pic1.setPreserveRatio(true);
		
		//Player 2
		Label lbl2 = new Label("Black Player:");
		lbl2.setLayoutX(413.0);
		lbl2.setLayoutY(102.0);
		lbl2.setFont(new Font("Berlin Sans FB Demi Bold", 14.0));
		
		TextField txt2 = new TextField();
		txt2.setId("BLACK");
		txt2.setLayoutX(409.0);
		txt2.setLayoutY(125.0);
		txt2.setPromptText("Nickname");
		
		ImageView pic2 = new ImageView(new Image(getClass().getResource("pictures/Soldier_BLACK.png").toString()));
		pic2.setFitHeight(67.0);
		pic2.setFitWidth(76.0);
		pic2.setLayoutX(450.0);
		pic2.setLayoutY(30.0);
		pic2.setPickOnBounds(true);
		pic2.setPreserveRatio(true);
		
		
		// add components to the screen
		mainAnchor.getChildren().add(btn);
		mainAnchor.getChildren().add(pic);
		mainAnchor.getChildren().add(lbl1);
		mainAnchor.getChildren().add(txt1);
		mainAnchor.getChildren().add(pic1);
		mainAnchor.getChildren().add(lbl2);
		mainAnchor.getChildren().add(txt2);
		mainAnchor.getChildren().add(pic2);

	}

	public String getPlayerName(PrimaryColor color) {
		String str = String.valueOf("#" + color);
		return ((TextField) mainAnchor.lookup(str)).getText();
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
	
}
