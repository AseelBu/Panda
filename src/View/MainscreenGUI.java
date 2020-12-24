package View;

import java.io.File;
import java.io.IOException;

import Controller.DisplayController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
		
		loadesign(1);

	}

	private void loadesign(int design) {
		mainBorder.getChildren().clear();
		AnchorPane background = new AnchorPane();
		background.setId("mainPane");
		AnchorPane.setBottomAnchor(background, 0.0);
		AnchorPane.setLeftAnchor(background, 0.0);
		AnchorPane.setRightAnchor(background, 0.0);
		AnchorPane.setTopAnchor(background, 0.0);
		mainBorder.getChildren().add(background);
		
		ImageView title = new ImageView(new Image(getClass().getResourceAsStream("/View/pictures/Pieace_6.png")));
		title.setId("title");
		title.setFitHeight(61);
		title.setFitWidth(285);
		title.setLayoutX(150);
		title.setLayoutY(20);
		title.setPickOnBounds(true);
		title.setPreserveRatio(true);
		mainBorder.getChildren().add(title);
		
		addButton(new Image(getClass().getResourceAsStream("/View/pictures/settings.png"))
				, 405, 5, 45, 45).addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
					loadesign(3);
	    });
		
		switch(design) {
			case 1:{
				addButton(new Image(getClass().getResourceAsStream("/View/pictures/startgame_btn.png"))
						, 150, 110, 310, 70).addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//			        this.startGame();
					loadesign(2);
			        event.consume();
			    });
//			
//				addButton(new Image(getClass().getResourceAsStream("/View/pictures/load_btn.png"))
//						, 150, 195, 310, 70).addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//			       this.loadGame();
//			       event.consume();
//			    });
				
				addButton(new Image(getClass().getResourceAsStream("/View/pictures/instructions_btn.png"))
						, 150, 195, 310, 70).addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			       this.InstructionsGame();
			       event.consume();
			    });

				addButton(new Image(getClass().getResourceAsStream("/View/pictures/score_btn.png"))
						, 150, 280, 310, 70).addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
					this.scoreboard();
			        event.consume();
			    });
				
				addButton(new Image(getClass().getResourceAsStream("/View/pictures/mgmt_btn.png"))
						, 150, 365, 310, 70).addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			        this.manageQuestions();
			        event.consume();
			    });

				break;
			}
			case 2:{
				addButton(new Image(getClass().getResourceAsStream("/View/pictures/back.png"))
						, 10, 5, 45, 45).addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
							loadesign(1);
							event.consume();
			    });
				
				addButton(new Image(getClass().getResourceAsStream("/View/pictures/normalgame_btn.png"))
						, 150, 110, 310, 70).addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
							this.startGame();
							event.consume();
			    });
				
				addButton(new Image(getClass().getResourceAsStream("/View/pictures/load_btn.png"))
						, 150, 195, 310, 70).addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
							this.loadGame();
			       			event.consume();
			    });
				
				addButton(new Image(getClass().getResourceAsStream("/View/pictures/customGame_btn.png"))
						, 150, 280, 310, 70).addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
							
							event.consume();
			    });
				
				break;
			}
			case 3:{
				addButton(new Image(getClass().getResourceAsStream("/View/pictures/back.png"))
						, 10, 5, 45, 45).addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
							loadesign(1);
							event.consume();
			    });
				
				//TODO settings screen
				break;
			}
		}
		
	}
	
	public ImageView addButton(Image img, double layoutX, double layoutY, double width, double height) {
		ImageView button = new ImageView(img);
		button.setLayoutX(layoutX);
		button.setLayoutY(layoutY);
		button.setFitWidth(width);
		button.setFitHeight(height);
		button.setPickOnBounds(true);
		button.setPreserveRatio(true);
		button.setCursor(Cursor.HAND);
		mainBorder.getChildren().add(button);
		return button;
	}

	public Stage getPrimary() {
		if(primary == null) {
			primary = new Stage();
		}
		return primary;
	}
	

	
    @FXML
    void exit(ActionEvent event) {
    	Platform.exit();
    }

   
    void loadGame() {
    	File file = null;
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text", "*.txt");
	    FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().clear();
    	fileChooser.getExtensionFilters().add(extFilter);
    	
    	file = fileChooser.showOpenDialog(primary);
    	
    	if (file != null) {        	
//        	DisplayController.getInstance().closeMainscreen();
//        	DisplayController.getInstance().showBoard(file);
    		DisplayController.getInstance().showNicknames(file);
        }
    }

    
    void manageQuestions() {
    	DisplayController.getInstance().closeMainscreen();
    	DisplayController.getInstance().showManageQuestions();
    }

    
    void scoreboard() {
    	
    	DisplayController.getInstance().closeMainscreen();
    	DisplayController.getInstance().showScoreboard();;
    	
    }

    
    void startGame() {
//		DisplayController.getInstance().closeMainscreen();
//		DisplayController.getInstance().showBoard();
		DisplayController.getInstance().showNicknames();
    }
    
    
    void InstructionsGame() {
    DisplayController.getInstance().showInstructionsGame();
    }

	
}
