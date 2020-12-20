package View;

import java.io.IOException;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
			e.printStackTrace();
		}
		Scene scene = new Scene(mainAnchor);
     	scene.getStylesheets().add(getClass().getResource("/View/scoreboard.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hamka");
		primaryStage.setResizable(false);
//		primaryStage.initStyle(StageStyle.UNDECORATED);  is Used to lock windows, wont be able to move the window
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("pictures/logo.png")));
		
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
	
	/**
	 * Loads GUI of Scoreboard
	 */
	
	public void loadDesign() {
		
		mainAnchor.getChildren().clear();
		
		ScrollPane mainsc = new ScrollPane();
		mainsc.setPrefHeight(350);
		mainsc.setPrefWidth(458);
		mainsc.setLayoutY(260);
		
		AnchorPane scroll_content = new AnchorPane();
		mainsc.setContent(scroll_content);
		
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
		
		
	
		ImageView back = new ImageView(new Image(getClass().getResource("pictures/icons8-back-arrow-64.png").toString()));
		back.setFitWidth(45);
		back.setFitHeight(45);
	
		
		
		
		ImageView iv = new ImageView(new Image(getClass().getResource("pictures/stage1.png").toString()));
		
		iv.setLayoutX(20);
		iv.setLayoutY(105);
		iv.setFitWidth(420);
		iv.setFitHeight(160);
		
		mainAnchor.getChildren().add(iv);
		
		
		
		ArrayList<Player> players = ScoreBoardController.getInstance().getSysData().getScoreboard();

		
		Collections.reverse(players);
		
		int counter = 0;
		
		for(Player p : players) {
			
			if(counter == 3) {
				break;
			}
			else if(counter == 0) {
				Label first_name = new Label(p.getNickname()); 
				first_name.setFont(new Font("verdana",20));
				first_name.setTextFill(Color.web("#ffffff"));
				mainAnchor.getChildren().add(first_name);
				first_name.setLayoutX(217);
				first_name.setLayoutY(80);
				
				
				Label first_score = new Label("" + p.getCurrentScore());
				first_score.setFont(new Font("verdana",16.0));
				mainAnchor.getChildren().add(first_score);
				first_score.setTextFill(Color.web("#ffffff"));
				first_score.setLayoutX(223);
				first_score.setLayoutY(105);
				
				
			}
			else if(counter == 1) {
				
				Label first_name = new Label(p.getNickname()); 
				first_name.setFont(new Font("verdana",20));
				first_name.setTextFill(Color.web("#ffffff"));
				mainAnchor.getChildren().add(first_name);
				first_name.setLayoutX(120);
				first_name.setLayoutY(115);
				
				
				Label first_score = new Label("" + p.getCurrentScore());
				first_score.setFont(new Font("verdana",16.0));
				mainAnchor.getChildren().add(first_score);
				first_score.setTextFill(Color.web("#ffffff"));
				first_score.setLayoutX(126);
				first_score.setLayoutY(140);
				
			}
			else if(counter == 2) {
				
				Label first_name = new Label(p.getNickname()); 
				first_name.setFont(new Font("verdana",20));
				first_name.setTextFill(Color.web("#ffffff"));
				mainAnchor.getChildren().add(first_name);
				first_name.setLayoutX(320);
				first_name.setLayoutY(135);
				
				
				Label first_score = new Label("" + p.getCurrentScore());
				first_score.setFont(new Font("verdana",16.0));
				mainAnchor.getChildren().add(first_score);
				first_score.setTextFill(Color.web("#ffffff"));
				first_score.setLayoutX(326);
				first_score.setLayoutY(160);
				
			}
			counter ++;
			
		}
		
		if(counter == 3) {
			
			int layoutx = 20,layouty = 10;
			
			for(int i = 3 ; i < players.size(); i++) {
				
				Label id = new Label("" + (i+1) + "."); 
				id.setFont(new Font("verdana",25));
				id.setTextFill(Color.web("#ffffff"));
				scroll_content.getChildren().add(id);
				id.setLayoutX(layoutx);
				id.setLayoutY(layouty);
				
				
				Label name = new Label(players.get(i).getNickname() + " - "); 
				name.setFont(new Font("verdana",25));
				name.setTextFill(Color.web("#ffffff"));
				scroll_content.getChildren().add(name);
				name.setLayoutX(layoutx+50);
				name.setLayoutY(layouty);
				
				
				Label score = new Label("" + players.get(i).getCurrentScore());
				score.setFont(new Font("verdana",25));
				scroll_content.getChildren().add(score);
				score.setTextFill(Color.web("#ffffff"));
				score.setLayoutX(layoutx+180);
				score.setLayoutY(layouty);
				
				layouty=layouty+40;
				
			}
			
		}
		
		
		
		
		
		
		

		
		 Image image = new Image(getClass().getResourceAsStream("pictures/icons8-back-arrow-64.png"));
		//back button to main screen
		Button back2 = new Button("");
		back2.setLayoutX(370.0);
		back2.setLayoutY(6.0);
		back2.setMnemonicParsing(false);
		back2.setGraphic(new ImageView(image));
		//cancel.setGraphic(new ImageView("pictures/icons8-back-arrow-64.png"));
	//	cancel.setGraphic(new Button("Back"));
		back2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
		    	DisplayController.getInstance().closeScoreboard();
			}
		});
		
		// add components to the screen
		mainAnchor.getChildren().add(back2);
		
		
		
	   
			
		
		




		
		
		
	}
	
}
