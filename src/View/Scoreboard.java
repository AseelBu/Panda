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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
		
		File f = new File("D:\\Projects\\Panda\\src\\View\\pictures\\icons8-back-arrow-64.png");
		
		Image im1 = new Image(f.toURI().toString());
		ImageView back = new ImageView();
		back.setFitWidth(70);
		back.setFitHeight(70);
		back.setImage(im1);
		Hyperlink hl = new Hyperlink();
		hl.setGraphic(back);
		hl.setLayoutX(2);
		hl.setLayoutY(2);
		hl.setPrefHeight(10);
		hl.setPrefWidth(10);
		
		mainAnchor.getChildren().add(hl);
		
		f = new File("D:\\Projects\\Panda\\src\\View\\pictures\\stage.png");
		
		if(f.exists())
	    {
	        System.out.println("file exist!");//I would print file path here.
	    }
	    else
	    {
	        System.out.println("file does not exist!");
	    }
		 
		Image image = new Image(f.toURI().toString());
		ImageView iv = new ImageView();
		iv.setImage(image);
		
		iv.setX(10);
		iv.setLayoutY(75);
		iv.setFitWidth(450);
		iv.setFitHeight(200);
		
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
		
		
		
		
		hl.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ScoreBoardController.getInstance().getScoreBoardScreen().getPrimary().close();
				DisplayController.getInstance().showMainScreen();
			}
			
			
			
		});
		
		
		
		
	}
	
}
