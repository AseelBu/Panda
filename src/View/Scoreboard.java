package View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import Controller.DisplayController;
import Controller.ScoreBoardController;
import Model.Player;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Scoreboard extends Application {
	
    private AnchorPane mainAnchor;
	private static Stage primary;	

	@Override
	public void start(Stage primaryStage) {
		try {
			mainAnchor = FXMLLoader.load(getClass().getResource("/View/scoreboard.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(mainAnchor);
     	scene.getStylesheets().add(getClass().getResource("/View/scoreboard.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hamka");
		primaryStage.setResizable(false);
//		primaryStage.initStyle(StageStyle.UNDECORATED);  is Used to lock windows, wont be able to move the window
//		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("logo1.png"))); add logo
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				
				DisplayController.getInstance().closeScoreboard();
				DisplayController.getInstance().showMainScreen();
				
			}
			
			
			
			
			
			
		});
		primaryStage.show();
		
		loadDesign();
	}

	public Stage getPrimary() {
		if(primary == null) {
			primary = new Stage();
		}
		return primary;
	}
	
	public void loadDesign() {
		
		mainAnchor.getChildren().clear();
		
		ScrollPane mainsc = new ScrollPane();
		mainsc.setPrefHeight(350);
		mainsc.setPrefWidth(458);
		mainsc.setLayoutY(260);
		
		
		
		
		AnchorPane background = new AnchorPane();
		background.setId("background");
		AnchorPane.setBottomAnchor(background, 0.0);
		AnchorPane.setLeftAnchor(background, 0.0);
		AnchorPane.setRightAnchor(background, 0.0);
		AnchorPane.setTopAnchor(background, 0.0);
		
		mainAnchor.getChildren().add(background);
		mainAnchor.getChildren().add(mainsc);
		
		ColorAdjust colorAdjust = new ColorAdjust(); 
		colorAdjust.setBrightness(-0.5);
		background.setEffect(colorAdjust);
		mainsc.setEffect(colorAdjust);
		
	
		ImageView back = new ImageView(new Image(getClass().getResource("pictures/icons8-back-arrow-64.png").toString()));
		back.setFitWidth(45);
		back.setFitHeight(45);
		Hyperlink hl = new Hyperlink();
		hl.setGraphic(back);
		hl.setLayoutX(2);
		hl.setLayoutY(2);
		hl.setPrefHeight(10);
		hl.setPrefWidth(10);
		
		mainAnchor.getChildren().add(hl);
		
		
		
		ImageView iv = new ImageView(new Image(getClass().getResource("pictures/stage1.png").toString()));
		
		iv.setLayoutX(20);
		iv.setLayoutY(105);
		iv.setFitWidth(420);
		iv.setFitHeight(160);
		
		mainAnchor.getChildren().add(iv);
		
		
		mainsc.setStyle("-fx-background-image: url(\"pictures/background.png\");");
		
		ArrayList<Player> players = ScoreBoardController.getInstance().getSysData().getScoreboard();

		
		Collections.reverse(players);
		
		int counter = 0;
		
		for(Player p : players) {
			
			if(counter == 3) {
				break;
			}
			else if(counter == 0) {
				Label first_name = new Label("Harry"); 
				first_name.setFont(new Font("verdana",16.0));
				
				Label first_score = new Label("" + p.getCurrentScore());
				first_score.setFont(new Font("verdana",16.0));
				
				
			}
			else if(counter == 1) {
				
			}
			else if(counter == 2) {
				
			}
			counter ++;
			
		}
		
		if(counter == 3) {
			
			for(int i = 3 ; i < players.size(); i++) {
				
				
				
			}
			
		}
		
		
		
		
		
		
		
		
		
		
		
	}
	
}
