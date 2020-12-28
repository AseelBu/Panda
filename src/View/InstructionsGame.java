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

public class InstructionsGame extends Application {
	
    private AnchorPane mainAnchor;
	private static Stage primary;	

	@Override
	public void start(Stage primaryStage) {
		try {
			mainAnchor = FXMLLoader.load(getClass().getResource("/View/InstructionsGame.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(mainAnchor);
     	scene.getStylesheets().add(getClass().getResource("/View/InstructionsGame.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hamka");
		primaryStage.setResizable(false);
//		primaryStage.initStyle(StageStyle.UNDECORATED);  is Used to lock windows, wont be able to move the window
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("pictures/logo.png")));
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				DisplayController.getInstance().closeInstructionsGame();
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
	public Button addButton(Image img, double layoutX, double layoutY, double width, double height) {
		ImageView i= new ImageView(img);
		i.setLayoutX(layoutX);
		i.setLayoutY(layoutY);
		i.setFitHeight( height);
		i.setFitWidth(width);
		Button button = new Button("Points Table",i);
		button.setLayoutX(layoutX);
		button.setLayoutY(layoutY);
		button.setMaxWidth(width+100);
   	button.maxHeight(height);
		button.setPickOnBounds(true);
//		button.setPreserveRatio(true);
		button.setCursor(Cursor.HAND);
		mainAnchor.getChildren().add(button);
		return button;

	}

	public void loadDesign() {
		
		ImageView backbutton = new ImageView(new Image(getClass().getResourceAsStream("/View/pictures/back.png")));
		backbutton.setLayoutX(10.0);
		backbutton.setLayoutY(5.0);
		backbutton.setFitWidth(45.0);
		backbutton.setFitHeight(45.0);
		backbutton.setPickOnBounds(true);
		backbutton.setPreserveRatio(true);
		backbutton.setCursor(Cursor.HAND);
		backbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			DisplayController.getInstance().closeInstructionsGame();
			event.consume();
		});
		
		mainAnchor.getChildren().add(backbutton);
		addButton(new Image(getClass().getResourceAsStream("/View/pictures/Earn_Reward_Points-512.png"))
				,320,590,45,45).addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
	       this.pointsTable();
	       event.consume();
	    });


	}
	  void pointsTable() {
		    DisplayController.getInstance().showPointsTable();
		    }
		
}

    
