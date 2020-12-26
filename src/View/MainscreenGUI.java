package View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import Controller.DisplayController;
import Controller.SoundController;
import Utils.Config;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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
		SoundController.getInstance().playIntro();

	}

	private void loadesign(int design) {
		mainBorder.getChildren().clear();
		AnchorPane background = new AnchorPane();
		background.setId("mainPane");
		AnchorPane.setBottomAnchor(background, 0.0);
		AnchorPane.setLeftAnchor(background, 0.0);
		AnchorPane.setRightAnchor(background, 0.0);
		AnchorPane.setTopAnchor(background, 0.0);
		ColorAdjust effect = new ColorAdjust();
		effect.setBrightness(-0.5);
		background.setEffect(effect);
				
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
					
					Properties prop=new Properties();
					FileInputStream ip = null;
					
					
					try {
						ip= new FileInputStream("config.properties");
						prop.load(ip);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
					
					
					ObservableList<String> colors = 
					        FXCollections.observableArrayList(
					          "#779556",
					          "#48769A",
					          "#B3B5B4",
					          "#7C3937",
					          "#000000"
					          
					        );
					ComboBox<String> theme = new ComboBox<String>(colors);
					theme.setLayoutX(250);
					theme.setLayoutY(150);
					
					Rectangle square = new Rectangle(250,180,90,60);
					
					
					Label l = new Label("Change Theme :");
					l.setFont(new Font("verdana", 20));
					l.setLayoutX(80);
					l.setLayoutY(149);
					l.setStyle("-fx-font-weight: bold");
					l.setStyle("-fx-text-fill: WHITE;");
					mainBorder.getChildren().add(l);
					
					
					mainBorder.getChildren().add(square);
					
					
					if(prop.getProperty("THEME_COLOR1").equals("#779556")) {
						theme.getSelectionModel().select(0);
						square.setFill(Color.web("#779556"));
						Config.THEME_COLOR1 = "#779556";
						Config.THEME_COLOR2 = "#EBECD0";
					}
					else if (prop.getProperty("THEME_COLOR1").equals("#48769A")) {
						theme.getSelectionModel().select(1);
						square.setFill(Color.web("#48769A"));
						Config.THEME_COLOR1 = "#48769A";
						Config.THEME_COLOR2 = "#B3B5B4";
					}
					else if (prop.getProperty("THEME_COLOR1").equals("#B3B5B4")) {
						theme.getSelectionModel().select(2);
						square.setFill(Color.web("#B3B5B4"));
						Config.THEME_COLOR1 = "#B3B5B4";
						Config.THEME_COLOR2 = "#DADADA";
					}
					else if (prop.getProperty("THEME_COLOR1").equals("#7C3937")) {
						theme.getSelectionModel().select(3);
						square.setFill(Color.web("#7C3937"));
						Config.THEME_COLOR1 = "#7C3937";
						Config.THEME_COLOR2 = "#D68E5F";
					}
					else {
						theme.getSelectionModel().select(4);
						square.setFill(Color.web("#000000"));
						Config.THEME_COLOR1 = "#000000";
						Config.THEME_COLOR2 = "#ffffff";
					}
					
					
					mainBorder.getChildren().add(theme);
					
					theme.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
									
						
							if(theme.getSelectionModel().getSelectedItem().equals("#779556")) {
								theme.getSelectionModel().select(0);
								square.setFill(Color.web("#779556"));
								Config.THEME_COLOR1 = "#779556";
								Config.THEME_COLOR2 = "#EBECD0";
							}
							else if (theme.getSelectionModel().getSelectedItem().equals("#48769A")) {
								theme.getSelectionModel().select(1);
								square.setFill(Color.web("#48769A"));
								Config.THEME_COLOR1 = "#48769A";
								Config.THEME_COLOR2 = "#B3B5B4";
							}
							else if (theme.getSelectionModel().getSelectedItem().equals("#B3B5B4")) {
								theme.getSelectionModel().select(2);
								square.setFill(Color.web("#B3B5B4"));
								Config.THEME_COLOR1 = "#B3B5B4";
								Config.THEME_COLOR2 = "#DADADA";
							}
							else if (theme.getSelectionModel().getSelectedItem().equals("#7C3937")) {
								theme.getSelectionModel().select(3);
								square.setFill(Color.web("#7C3937"));
								Config.THEME_COLOR1 = "#7C3937";
								Config.THEME_COLOR2 = "#D68E5F";
							}
							else {
								square.setFill(Color.web("#000000"));
								Config.THEME_COLOR1 = "#000000";
								Config.THEME_COLOR2 = "#ffffff";
							}
							
							
							square.setFill(Color.web((theme.getSelectionModel().getSelectedItem())));
							prop.setProperty("THEME_COLOR1", theme.getSelectionModel().getSelectedItem());
							try {
								prop.store(new FileOutputStream("config.properties"),null);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				
							
						}
										
					});
	    });
		
		switch(design) {
			case 1:{
				addButton(new Image(getClass().getResourceAsStream("/View/pictures/startgame_btn.png"))
						, 150, 110, 310, 70).addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
					loadesign(2);
			        event.consume();
			    });
				
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
					    	DisplayController.getInstance().closeMainscreen();
							DisplayController.getInstance().showBoardEdit();
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
	

	
    void exit() {
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

    void pointstable() {
        DisplayController.getInstance().showPointsTable();
        }
	
}
