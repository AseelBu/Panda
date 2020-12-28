package View;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import Controller.BoardController;
import Controller.DisplayController;
import Utils.PrimaryColor;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Nicknames extends Application {

	private static AnchorPane mainAnchor;
	private Stage primary;
	private File file;
	private HashMap<String,String> pieces;
	private PrimaryColor turn;
	private boolean queens;
	
	
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
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/View/pictures/logo.png")));
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {

			}
		});
		primary = primaryStage;
		queens = false;
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
				
				String name1 = getPlayerName(PrimaryColor.WHITE);
				String name2 = getPlayerName(PrimaryColor.BLACK);
				boolean err = false;
				if(name1.matches("")) {
					((Label) mainAnchor.lookup("#WHITE_err")).setVisible(true);
					err = true;
				}else {
					((Label) mainAnchor.lookup("#WHITE_err")).setVisible(false);
				}
				if(name2.matches("")) {
					((Label) mainAnchor.lookup("#BLACK_err")).setVisible(true);
					err = true;
				}else {
					((Label) mainAnchor.lookup("#BLACK_err")).setVisible(false);
				}
				if(err) {
					event.consume();
					return;
				}
				
				BoardController.getInstance().setPlayersNicknames(name1, name2);
				
				DisplayController.getInstance().closeMainscreen();
				if(file != null)
					DisplayController.getInstance().showBoard(BoardController.getInstance().getPlayers(), file);
				else if(pieces != null)
					DisplayController.getInstance().showBoard(BoardController.getInstance().getPlayers(), BoardController.getInstance().createPieces(pieces), turn);
				else
					DisplayController.getInstance().showBoard(BoardController.getInstance().getPlayers());
				DisplayController.getInstance().closeNicknames();

			}
		});
		
		ImageView pic = new ImageView(new Image(getClass().getResource("pictures/logo.png").toString()));
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
		
		TextField txt1 = new TextField("Player 1");
		txt1.setId("WHITE");
		txt1.setLayoutX(33.0);
		txt1.setLayoutY(125.0);
		txt1.setPromptText("Nickname");
		txt1.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.length() > 10)
				txt1.setText(oldValue);
		});
		
		ImageView pic1 = new ImageView(new Image(getClass().getResource("pictures/Soldier_WHITE.png").toString()));
		pic1.setFitHeight(67.0);
		pic1.setFitWidth(76.0);
		pic1.setLayoutX(74.0);
		pic1.setLayoutY(30.0);
		pic1.setPickOnBounds(true);
		pic1.setPreserveRatio(true);
		
		Label err1 = new Label("Please enter a nickname");
		err1.setLayoutX(33.0);
		err1.setLayoutY(156.0);
		err1.setPrefHeight(17.0);
		err1.setPrefWidth(149.0);
		err1.setTextFill(Color.RED);
		err1.setVisible(false);
		err1.setId("WHITE_err");
		
		//Player 2
		Label lbl2 = new Label("Black Player:");
		lbl2.setLayoutX(413.0);
		lbl2.setLayoutY(102.0);
		lbl2.setFont(new Font("Berlin Sans FB Demi Bold", 14.0));
		
		TextField txt2 = new TextField("Player 2");
		txt2.setId("BLACK");
		txt2.setLayoutX(409.0);
		txt2.setLayoutY(125.0);
		txt2.setPromptText("Nickname");
		txt2.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.length() > 10)
				txt2.setText(oldValue);
		});
		
		ImageView pic2 = new ImageView(new Image(getClass().getResource("pictures/Soldier_BLACK.png").toString()));
		pic2.setFitHeight(67.0);
		pic2.setFitWidth(76.0);
		pic2.setLayoutX(450.0);
		pic2.setLayoutY(30.0);
		pic2.setPickOnBounds(true);
		pic2.setPreserveRatio(true);
		
		Label err2 = new Label("Please enter a nickname");
		err2.setLayoutX(409.0);
		err2.setLayoutY(156.0);
		err2.setPrefHeight(17.0);
		err2.setPrefWidth(149.0);
		err2.setTextFill(Color.RED);
		err2.setVisible(false);
		err2.setId("BLACK_err");
		
		Button cancel = new Button("Cancel");
		cancel.setLayoutX(14.0);
		cancel.setLayoutY(278.0);
		cancel.setMnemonicParsing(false);
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
		    	DisplayController.getInstance().closeNicknames();
			}
		});
		
		// add components to the screen
		mainAnchor.getChildren().add(btn);
		mainAnchor.getChildren().add(pic);
		mainAnchor.getChildren().add(lbl1);
		mainAnchor.getChildren().add(txt1);
		mainAnchor.getChildren().add(pic1);
		mainAnchor.getChildren().add(lbl2);
		mainAnchor.getChildren().add(txt2);
		mainAnchor.getChildren().add(pic2);
		mainAnchor.getChildren().add(err1);
		mainAnchor.getChildren().add(err2);
		mainAnchor.getChildren().add(cancel);

	}
	
	// Getters and setters
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public HashMap<String, String> getPieces() {
		return pieces;
	}

	public void setPieces(HashMap<String, String> pieces) {
		this.pieces = pieces;
	}

	public PrimaryColor getTurn() {
		return turn;
	}

	public void setTurn(PrimaryColor turn) {
		this.turn = turn;
	}

	public String getPlayerName(PrimaryColor color) {
		String str = String.valueOf("#" + color);
		return ((TextField) mainAnchor.lookup(str)).getText();
	}
	
	public boolean isQueens() {
		return queens;
	}

	public void setQueens(boolean queens) {
		this.queens = queens;
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
	
	/**
	 * Pop up alert with an error message
	 * @param err
	 */
	public void notifyError(String err) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText(err);
		alert.showAndWait();
	}
}
