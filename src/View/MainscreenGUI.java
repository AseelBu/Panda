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
	
	@FXML
    private AnchorPane mainPane;

    @FXML
    private ImageView startgame;

    @FXML
    private ImageView loadgame;

    @FXML
    private ImageView instruct;

    @FXML
    private ImageView mgmtques;

    @FXML
    private ImageView scoreboard;
    
    @FXML
    private ImageView title;
    
    @FXML
    private ImageView settings;
    
    @FXML
    private ImageView exit;

	
	

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
		
		this.startgame = (ImageView) mainBorder.lookup("#startgame");
		this.loadgame = (ImageView) mainBorder.lookup("#loadgame");
		this.instruct = (ImageView) mainBorder.lookup("#instruct");
		this.mgmtques = (ImageView) mainBorder.lookup("#mgmtques");
		this.scoreboard = (ImageView) mainBorder.lookup("#scoreboard");
		this.title = (ImageView) mainBorder.lookup("#title");
		this.settings = (ImageView) mainBorder.lookup("#settings");
		this.exit = (ImageView) mainBorder.lookup("#exit");
	
		
		loadesign();

	}

	private void loadesign() {
		
		this.title.setLayoutX(150);
		this.title.setLayoutY(20);
		
		Image seting = new Image(getClass().getResourceAsStream("/View/pictures/settings.png"));
		this.settings.setImage(seting);
		this.settings.setLayoutX(5);
		this.settings.setLayoutY(5);
		this.settings.setFitWidth(45);
		this.settings.setFitHeight(45);
		this.settings.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
	        
	     });
		
		this.settings.setCursor(Cursor.HAND);
		
		Image exitIMG = new Image(getClass().getResourceAsStream("/View/pictures/exit.png"));
		this.exit.setImage(exitIMG);
		this.exit.setLayoutX(405);
		this.exit.setLayoutY(5);
		this.exit.setFitWidth(45);
		this.exit.setFitHeight(45);
		this.exit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
	        this.exit();
	     });
		
		this.exit.setCursor(Cursor.HAND);
		
		
		Image start = new Image(getClass().getResourceAsStream("/View/pictures/startgame_btn.png"));
		this.startgame.setImage(start);
		this.startgame.setLayoutX(150);
		this.startgame.setLayoutY(110);
		this.startgame.setFitWidth(310);
		this.startgame.setFitHeight(70);
		this.startgame.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
	         this.startGame();
	         event.consume();
	     });
		
		this.startgame.setCursor(Cursor.HAND);
		
		Image load = new Image(getClass().getResourceAsStream("/View/pictures/load_btn.png"));
		this.loadgame.setImage(load);
		this.loadgame.setLayoutX(150);
		this.loadgame.setLayoutY(195);
		this.loadgame.setFitWidth(310);
		this.loadgame.setFitHeight(70);
		this.loadgame.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
	         this.loadGame();
	         event.consume();
	     });
		
		this.loadgame.setCursor(Cursor.HAND);
		
		Image instr = new Image(getClass().getResourceAsStream("/View/pictures/instructions_btn.png"));
		this.instruct.setImage(instr);
		this.instruct.setLayoutX(150);
		this.instruct.setLayoutY(280);
		this.instruct.setFitWidth(310);
		this.instruct.setFitHeight(70);
		this.instruct.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
	         this.InstructionsGame();
	         event.consume();
	     });
		
		this.instruct.setCursor(Cursor.HAND);
		
		Image score = new Image(getClass().getResourceAsStream("/View/pictures/score_btn.png"));
		this.scoreboard.setImage(score);
		this.scoreboard.setLayoutX(150);
		this.scoreboard.setLayoutY(365);
		this.scoreboard.setFitWidth(310);
		this.scoreboard.setFitHeight(70);
		this.scoreboard.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
	         this.scoreboard();
	         event.consume();
	     });
		
		this.scoreboard.setCursor(Cursor.HAND);
		
		Image manag = new Image(getClass().getResourceAsStream("/View/pictures/mgmt_btn.png"));
		this.mgmtques.setImage(manag);
		this.mgmtques.setLayoutX(150);
		this.mgmtques.setLayoutY(450);
		this.mgmtques.setFitWidth(310);
		this.mgmtques.setFitHeight(70);
		this.mgmtques.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
	         this.manageQuestions();
	         event.consume();
	     });
		
		this.mgmtques.setCursor(Cursor.HAND);
		
		
		
		
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

	
}
