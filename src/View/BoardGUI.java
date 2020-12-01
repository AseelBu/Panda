package View;

import java.io.IOException;

import Controller.BoardController;
import Model.Location;
import Model.Piece;
import Model.Soldier;
import Model.Tile;
import Utils.Directions;
import Utils.PrimaryColor;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BoardGUI extends Application {
	
    private static AnchorPane mainAnchor;
	private Stage primary;
	private BoardController boardController;
	private char selectedCol = '_';
	private int selectedRow = -1;
	private char selectedCol2 = '_';
	private int selectedRow2 = -1;
	private char dragCol = '_';
	private int dragRow = -1;
	private PrimaryColor turnColor;
	
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
		
		Label totalTime = new Label("Total Time:");
		totalTime.setLayoutX(26.0);
		totalTime.setLayoutY(35.0);
		totalTime.setFont(new Font(28.0));
		mainAnchor.getChildren().add(totalTime);
		
		TextField totalTimeF = new TextField("00:00:00");
		totalTimeF.setId("TotalTime");
		totalTimeF.setAlignment(Pos.CENTER);
		totalTimeF.setDisable(true);
		totalTimeF.setEditable(false);
		totalTimeF.setLayoutX(166.0);
		totalTimeF.setLayoutY(25.0);
		totalTimeF.setPrefHeight(60.0);
		totalTimeF.setPrefWidth(144.0);
		totalTimeF.setStyle("-fx-opacity: 1;");
		totalTimeF.setFont(new Font(28.0));
		mainAnchor.getChildren().add(totalTimeF);
		
		ImageView whiteImg = new ImageView(new Image(getClass().getResource("pictures/Queen_WHITE.png").toString()));
		whiteImg.setFitHeight(100);
		whiteImg.setFitWidth(100);
		whiteImg.setLayoutX(16);
		whiteImg.setLayoutY(122);
		
		ImageView blackImg = new ImageView(new Image(getClass().getResource("pictures/Queen_BLACK.png").toString()));
		blackImg.setFitHeight(100);
		blackImg.setFitWidth(100);
		blackImg.setLayoutX(16);
		blackImg.setLayoutY(320);
		
		mainAnchor.getChildren().add(whiteImg);
		mainAnchor.getChildren().add(blackImg);
		
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
	
	/**
	 * used to add piece to the board
	 * @param piece
	 * @return
	 */
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
			pieceImage.setId("PieceImage");
		}
		else {
			pieceImage = new ImageView(new Image(getClass().getResource("pictures/Queen_" + piece.getColor() + ".png").toString()));
		}
		pieceImage.setId("PieceImage_" + piece.getColor());
		pieceImage.setFitHeight(99.0);
		pieceImage.setFitWidth(100.0);
		pieceImage.setPickOnBounds(true);
		pieceImage.setPreserveRatio(true);
		pieceImage.setCursor(Cursor.HAND);
		pieceImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ImageView image = (ImageView) event.getSource();
				FlowPane tile = (FlowPane) image.getParent();
				String[] id = tile.getId().split("_");
				
				if(id.length != 2) return;
				if(!validateSelection(image)) return;
				
				if(selectedRow == -1) {
					selectedRow = Integer.valueOf(id[0]);
					selectedCol = id[1].toCharArray()[0];
					putSelectionOnPiece(image);
				}else {
					FlowPane board = (FlowPane) mainAnchor.lookup("#board");
					String temp = String.valueOf("#" + selectedRow + "_" + selectedCol);
					FlowPane tileToUnselect = (FlowPane) board.lookup(temp);
					removeSelectionFromPiece((ImageView) tileToUnselect.getChildren().get(0));
					
					if(selectedRow == Integer.valueOf(id[0]) && selectedCol == id[1].toCharArray()[0]) {
						selectedRow = -1;
						selectedCol = '_';
					}else {
						selectedRow = Integer.valueOf(id[0]);
						selectedCol = id[1].toCharArray()[0];
						putSelectionOnPiece(image);
					}
				}
			}
		});
//		pieceImage.setOnMousePressed(value);
		
		pieceImage.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (primary != null) {
					ImageView image = (ImageView) event.getSource();
					ImageView tempImage = new ImageView(image.getImage());
					tempImage.setId("drag");
					tempImage.setFitHeight(99.0);
					tempImage.setFitWidth(100.0);
					tempImage.setPickOnBounds(true);
					tempImage.setPreserveRatio(true);
					
					tempImage.setCursor(Cursor.HAND);	
					tempImage.setVisible(false);
					
					mainAnchor.getChildren().add(2, tempImage);

					int col = (int) ((int) event.getSceneX() - board.getLayoutX());
					int row = 8 - (((int) ( event.getSceneY() - board.getLayoutY() )) / 100);
					selectedCol2 = (char)((char)(col / 100) + 'A');
					selectedRow2 = row;
					

					
		            event.consume();
		        }
			}
			});
		
		pieceImage.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (primary != null) {
					ImageView tempImage = (ImageView) mainAnchor.lookup("#drag");
					if(tempImage != null) {
						if(!tempImage.isVisible())
							tempImage.setVisible(true);
						tempImage.setLayoutX(event.getSceneX());
						tempImage.setLayoutY(event.getSceneY());
						
						int col = (int) ((int) tempImage.getLayoutX() - board.getLayoutX());
						int row = 8 - (((int) ( tempImage.getLayoutY() - board.getLayoutY() )) / 100);
						
						if(((char)((char)(col / 100) + 'A') != selectedCol2 && row != selectedRow2) 
								&& dragCol == '_' && dragRow == -1) {
							dragCol = (char)((char)(col / 100) + 'A');
							dragRow = row;
						}
							
					}
					
		            event.consume();
		        }
			}
			});
		pieceImage.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (primary != null) {
					ImageView tempImage = (ImageView) mainAnchor.lookup("#drag");
					mainAnchor.getChildren().remove(tempImage);
					
					int relativeX = ((int) ( tempImage.getLayoutX() - board.getLayoutX() )) / 100;
					int relativeY = ((int) ( tempImage.getLayoutY() - board.getLayoutY() )) / 100;
					
					System.out.println(selectedRow2 + " " + selectedCol2);
					
					movePiece(selectedRow2, selectedCol2
							, 8-relativeY, (char) ((char) relativeX + 'A'), getDirectionByDrag(selectedRow2, selectedCol2));
					mainAnchor.getChildren().remove(tempImage);
					dragCol = '_';
					dragRow = -1;

		            event.consume();
		        }
			}
			});
//		pieceImage.setOnDragDropped(drag);
//		pieceImage.setOnDragExited(drag);

		tile.getChildren().add(pieceImage);
		return true;
	}
	
	/**
	 * 
	 * @return direction by drag
	 */
	public Directions getDirectionByDrag(int currentRow, char currentCol){
		int diffCol = dragCol - currentCol;
		int diffRow = dragRow - currentRow;
				
		if(diffCol > 0 && diffRow > 0) return Directions.UP_RIGHT;
		if(diffCol > 0 && diffRow < 0) return Directions.DOWN_RIGHT;
		if(diffCol < 0 && diffRow > 0) return Directions.UP_LEFT;
		if(diffCol < 0 && diffRow < 0) return Directions.DOWN_LEFT;

		return null;
	}
	
	/**
	 * Validates piece selection by its color
	 * @param piece piece's image on board
	 * @return true if the piece has the same color as the player, otherwise false
	 */
	public boolean validateSelection(ImageView piece) {
		String[] id = piece.getId().split("_");
		if(id.length != 2) return false;
		return String.valueOf(turnColor).equals(id[1]);
	}
	
	/**
	 * put shadow on selected piece
	 * @param piece
	 */
	public void putSelectionOnPiece(ImageView piece) {
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(26.2175);
		dropShadow.setHeight(53.25);
		dropShadow.setWidth(53.62);
		dropShadow.setSpread(0.69);
		dropShadow.setColor(Color.WHITE);
		piece.setEffect(dropShadow);
	}
	
	/**
	 * remove shadow from a selected piece
	 * @param piece
	 */
	public void removeSelectionFromPiece(ImageView piece) {
		piece.setEffect(null);
	}
	
	/**
	 * Initiate Game players names & scores set to 0
	 * @param whiteName Player 1 name
	 * @param blackName Player 2 name
	 */
	public void initiateGamePlayers(String whiteName, String blackName) {
		Label lp1 = new Label(whiteName);
		lp1.setLayoutX(105.0);
		lp1.setLayoutY(151.0);
		lp1.setFont(new Font(28.0));
		
		ColorAdjust colorAdjust = new ColorAdjust();
		
		Label label1 = new Label("Points:");
		label1.setLayoutX(106.0);
		label1.setLayoutY(195.0);
		label1.setEffect(colorAdjust);
		label1.setFont(new Font(20.0));
		
		TextField pointsWhite = new TextField("0");
		pointsWhite.setId("Points_WHITE");
		pointsWhite.setAlignment(Pos.CENTER);
		pointsWhite.setDisable(true);
		pointsWhite.setEditable(false);
		pointsWhite.setLayoutX(175.0);
		pointsWhite.setLayoutY(191.0);
		pointsWhite.setPrefHeight(24.0);
		pointsWhite.setPrefWidth(81.0);
		pointsWhite.setStyle("-fx-opacity: 1;");
		pointsWhite.setFont(new Font(17.0));
		
		Label lp2 = new Label(blackName);
		lp2.setLayoutX(105.0);
		lp2.setLayoutY(349.0);
		lp2.setFont(new Font(28.0));
		
		Label label2 = new Label("Points:");
		label2.setLayoutX(106.0);
		label2.setLayoutY(389.0);
		label2.setEffect(colorAdjust);
		label2.setFont(new Font(20.0));
		
		TextField pointsBlack = new TextField("0");
		pointsBlack.setId("Points_BLACK");
		pointsBlack.setAlignment(Pos.CENTER);
		pointsBlack.setDisable(true);
		pointsBlack.setEditable(false);
		pointsBlack.setLayoutX(175.0);
		pointsBlack.setLayoutY(385.0);
		pointsBlack.setPrefHeight(24.0);
		pointsBlack.setPrefWidth(81.0);
		pointsBlack.setStyle("-fx-opacity: 1;");
		pointsBlack.setFont(new Font(17.0));
		
		mainAnchor.getChildren().add(lp1);
		mainAnchor.getChildren().add(lp2);
		mainAnchor.getChildren().add(label1);
		mainAnchor.getChildren().add(label2);
		mainAnchor.getChildren().add(pointsWhite);
		mainAnchor.getChildren().add(pointsBlack);

	}
	
	/**
	 * Used to change players score
	 * @param color player primary color
	 * @param newScore new player score
	 */
	public void setPlayerScore(PrimaryColor color, int newScore) {
		String id = "#Points_" + color;
		TextField points = (TextField) mainAnchor.lookup(id);
		points.setText(String.valueOf(newScore));
	}
	
	/**
	 * set new turn
	 * @param color
	 */
	public void setNewTurn(PrimaryColor color) {
		turnColor = color;
	}
	
	public void movePiece(int fromRow, char fromCol, int toRow, char toCol, Directions direction) {
		if(boardController.movePiece(fromRow, fromCol, toRow, toCol, direction)) {
			FlowPane board = (FlowPane) mainAnchor.lookup("#board");
			String temp = String.valueOf("#" + fromRow + "_" + fromCol);
			FlowPane fromTile = (FlowPane) board.lookup(temp);
			temp = String.valueOf("#" + toRow + "_" + toCol);
			FlowPane toTile = (FlowPane) board.lookup(temp);
			
			if(!boardController.checkBurnCurrent(toRow, toCol)) {
				toTile.getChildren().add(fromTile.getChildren().get(0));
			}
			fromTile.getChildren().clear();
		}
	}
	
	public void removePiece(int row, char col, boolean isBurnt) {
		FlowPane board = (FlowPane) mainAnchor.lookup("#board");
		String temp = String.valueOf("#" + row + "_" + col);
		FlowPane fromTile = (FlowPane) board.lookup(temp);
		if(fromTile.getChildren().size() > 0) {
			if(fromTile.getChildren().get(0).getId().split("_").length == 2) {
				if(fromTile.getChildren().get(0).getId().split("_")[0].matches("PieceImage")) {
					fromTile.getChildren().clear();
				}
			}
		}
	}
	
	public void upgradeToQueen(int row, char col) {
		FlowPane board = (FlowPane) mainAnchor.lookup("#board");
		String temp = String.valueOf("#" + row + "_" + col);
		FlowPane tile = (FlowPane) board.lookup(temp);
		String[] id = tile.getChildren().get(0).getId().split("_");
		
		if(id.length == 2 && id[0].matches("PieceImage")) {
			((ImageView) tile.getChildren().get(0)).setImage(new Image(getClass().getResource("pictures/Queen_" + id[1] + ".png").toString()));
		}
	}
	
	public Stage getPrimary() {
		if(primary == null) {
			primary = new Stage();
		}
		return primary;
	}
	
}
