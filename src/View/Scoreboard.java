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
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
				first_name.setLayoutX(215);
				first_name.setLayoutY(70);
				
				
				Label first_score = new Label("" + p.getCurrentScore());
				first_score.setFont(new Font("verdana",16.0));
				mainAnchor.getChildren().add(first_score);
				first_score.setTextFill(Color.web("#ffffff"));
				first_score.setLayoutX(214);
				first_score.setLayoutY(105);
				
				
			}
			else if(counter == 1) {
				
				Label first_name = new Label(p.getNickname()); 
				first_name.setFont(new Font("verdana",20));
				first_name.setTextFill(Color.web("#ffffff"));
				mainAnchor.getChildren().add(first_name);
				first_name.setLayoutX(110);
				first_name.setLayoutY(105);
				
				
				Label first_score = new Label("" + p.getCurrentScore());
				first_score.setFont(new Font("verdana",16.0));
				mainAnchor.getChildren().add(first_score);
				first_score.setTextFill(Color.web("#ffffff"));
				first_score.setLayoutX(93);
				first_score.setLayoutY(140);
				
			}
			else if(counter == 2) {
				
				Label first_name = new Label(p.getNickname()); 
				first_name.setFont(new Font("verdana",20));
				first_name.setTextFill(Color.web("#ffffff"));
				mainAnchor.getChildren().add(first_name);
				first_name.setLayoutX(310);
				first_name.setLayoutY(125);
				
				
				Label first_score = new Label("" + p.getCurrentScore());
				first_score.setFont(new Font("verdana",16.0));
				mainAnchor.getChildren().add(first_score);
				first_score.setTextFill(Color.web("#ffffff"));
				first_score.setLayoutX(305);
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
		

		ImageView backbutton = new ImageView(new Image(getClass().getResourceAsStream("/View/pictures/back.png")));
		backbutton.setLayoutX(10.0);
		backbutton.setLayoutY(5.0);
		backbutton.setFitWidth(45.0);
		backbutton.setFitHeight(45.0);
		backbutton.setPickOnBounds(true);
		backbutton.setPreserveRatio(true);
		backbutton.setCursor(Cursor.HAND);
		backbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			DisplayController.getInstance().closeScoreboard();
			event.consume();
		});
		
		mainAnchor.getChildren().add(backbutton);
	   
			
		
		




		
		
		
	}
	
}
