package View;

import java.io.IOException;

import Controller.BoardController;
import Model.Piece;
import Model.Soldier;
import Model.Tile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BoardGUI extends Application {
	
    private AnchorPane mainAnchor;
	private Stage primary;
	private BoardController boardController;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			mainAnchor = FXMLLoader.load(getClass().getResource("/View/Board.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(mainAnchor);
		scene.getStylesheets().add(getClass().getResource("/View/board.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hamka");
		primaryStage.setResizable(false);
//		primaryStage.initStyle(StageStyle.UNDECORATED);  is Used to lock windows, wont be able to move the window
//		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("logo1.png"))); add logo
		primaryStage.show();
		primary = primaryStage;
		boardController = BoardController.getInstance();
		
		loadDesign();
		loadStandardTiles();
		boardController.loadPiecesToBoard();
	}
	
	/**
	 * used to load basic design
	 */
	private void loadDesign(){
		mainAnchor.getChildren().clear();
		AnchorPane background = new AnchorPane();
		background.setId("background");
		AnchorPane.setBottomAnchor(background, 0.0);
		AnchorPane.setLeftAnchor(background, 0.0);
		AnchorPane.setRightAnchor(background, 0.0);
		AnchorPane.setTopAnchor(background, 0.0);
		mainAnchor.getChildren().add(background);
		
		FlowPane flow = new FlowPane();
		flow.setLayoutX(477.0);
		flow.setLayoutY(80.0);
		flow.setPrefHeight(800.0);
		flow.setPrefWidth(800.0);
		flow.setId("board");

		FlowPane nums = new FlowPane();
		nums.setLayoutX(427.0);
		nums.setLayoutY(80.0);
		nums.setPrefHeight(800.0);
		nums.setPrefWidth(50.0);
		nums.setId("nums_1");
		
		FlowPane nums2 = new FlowPane();
		nums2.setLayoutX(1277.0);
		nums2.setLayoutY(80.0);
		nums2.setPrefHeight(800.0);
		nums2.setPrefWidth(50.0);
		nums2.setId("nums_2");
		
		FlowPane cols = new FlowPane();
		cols.setLayoutX(477.0);
		cols.setLayoutY(30.0);
		cols.setPrefHeight(50.0);
		cols.setPrefWidth(800.0);
		cols.setId("col_1");
		
		FlowPane cols_2 = new FlowPane();
		cols_2.setLayoutX(477.0);
		cols_2.setLayoutY(880.0);
		cols_2.setPrefHeight(50.0);
		cols_2.setPrefWidth(800.0);
		cols_2.setId("col_2");
		
		mainAnchor.getChildren().add(flow);
		mainAnchor.getChildren().add(nums);
		mainAnchor.getChildren().add(nums2);
		mainAnchor.getChildren().add(cols);
		mainAnchor.getChildren().add(cols_2);
		setupStandardColsRows();

	}	
	
	/**
	 * used to load standard columns & rows numbers and characters
	 */
	private void setupStandardColsRows() {
		for(int i = 0 ; i < 2 ; i++) {
			String id = "#nums_" + (i+1);
			FlowPane nums = (FlowPane) mainAnchor.lookup(String.valueOf(id));
			for(int j = 8; j > 0 ; j--) {
				Label label = new Label(String.valueOf(j));
				label.setAlignment(Pos.CENTER);
				label.setPrefHeight(100.0);
				label.setPrefWidth(50.0);
				label.setFont(new Font("System Bold",28.0));
				nums.getChildren().add(label);
			}
		}
		
		for(int i = 0 ; i < 2 ; i++) {
			String id = "#col_" + (i+1);
			FlowPane cols = (FlowPane) mainAnchor.lookup(String.valueOf(id));
			for(char j = 'A'; j <= 'H' ; j++) {
				Label label = new Label(String.valueOf(j));
				label.setAlignment(Pos.CENTER);
				label.setPrefHeight(50.0);
				label.setPrefWidth(100.0);
				label.setFont(new Font("System Bold",28.0));
				cols.getChildren().add(label);
			}
		}
		
	}

	/**
	 * loads standard game tile's design
	 */
	private void loadStandardTiles() {
		FlowPane board = (FlowPane) mainAnchor.lookup("#board");
		if(board.getChildren().size() > 0) return;
		for(int i = 8 ; i >= 1 ; i--) {
			for(char j = 'A' ; j <= 'H' ; j++) {
				Tile tile = boardController.getTile(i, j);
				FlowPane tilePane = new FlowPane(); 
				tilePane.setPrefHeight(100.0);
				tilePane.setPrefWidth(100.0);
				tilePane.setId(String.valueOf(i+"_"+j));
				tilePane.setStyle("-fx-background-color: " + tile.getColorName() + ";");
				board.getChildren().add(tilePane);
			}
		}
	}
	
	public boolean addPieceToBoard(Piece piece) {
		FlowPane board = (FlowPane) mainAnchor.lookup("#board");
		String temp = String.valueOf("#" + piece.getLocation().getRow() + "_" + piece.getLocation().getColumn());
		FlowPane tile = (FlowPane) board.lookup(temp);
		if(tile == null) {
			System.out.println(piece.getLocation());
			System.out.println("Board has no tile in piece's location");
			return false;
		}
		//TODO check piece existence in tile
		ImageView pieceImage;
		if(piece instanceof Soldier) {
			pieceImage = new ImageView(new Image(getClass().getResource("pictures/Soldier_" + piece.getColor() + ".png").toString()));
		}
		else {
			pieceImage = new ImageView(new Image(getClass().getResource("pictures/Queen_" + piece.getColor() + ".png").toString()));
		}
		pieceImage.setFitHeight(99.0);
		pieceImage.setFitWidth(100.0);
		pieceImage.setPickOnBounds(true);
		pieceImage.setPreserveRatio(true);

		tile.getChildren().add(pieceImage);
		return true;
	}
	
	
	public Stage getPrimary() {
		if(primary == null) {
			primary = new Stage();
		}
		return primary;
	}

}
