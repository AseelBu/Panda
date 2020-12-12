package View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import Controller.BoardController;
import Controller.DisplayController;
import Controller.GameController;
import Controller.TurnTimerController;
import Exceptions.GameUpgradeException;
import Exceptions.IllegalMoveException;
import Exceptions.LocationException;
import Model.Game;
import Model.Tile;
import Utils.Directions;
import Utils.PrimaryColor;
import Utils.SeconderyTileColor;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BoardGUI extends Application {

	private static AnchorPane mainAnchor;
	private TurnTimerController turnTimer;
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
			mainAnchor = new AnchorPane();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(mainAnchor,1048.0, 648.0);
		scene.getStylesheets().add(getClass().getResource("/View/board.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hamka");
		primaryStage.setResizable(false);
		//		primaryStage.initStyle(StageStyle.UNDECORATED);  is Used to lock windows, wont be able to move the window
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/View/pictures/logo.png")));
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				BoardController.getInstance().forceFinishGame();

			}
		});

		primary = primaryStage;
		boardController = BoardController.getInstance();

		loadDesign();
		loadStandardTiles();
		boardController.loadPiecesToBoard();
		boardController.loadTilesColors();
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
		flow.setPrefHeight(525.0);
		flow.setPrefWidth(525.0);
		flow.setId("board");

		FlowPane nums = new FlowPane();
		nums.setLayoutX(427.0);
		nums.setLayoutY(80.0);
		nums.setPrefHeight(520.0);
		nums.setPrefWidth(50.0);
		nums.setId("nums_1");

		FlowPane nums2 = new FlowPane();
		nums2.setLayoutX(1000.0);
		nums2.setLayoutY(80.0);
		nums2.setPrefHeight(520.0);
		nums2.setPrefWidth(50.0);
		nums2.setId("nums_2");

		FlowPane cols = new FlowPane();
		cols.setLayoutX(477.0);
		cols.setLayoutY(30.0);
		cols.setPrefHeight(50.0);
		cols.setPrefWidth(525.0);
		cols.setId("col_1");

		FlowPane cols_2 = new FlowPane();
		cols_2.setLayoutX(477.0);
		cols_2.setLayoutY(600.0);
		cols_2.setPrefHeight(50.0);
		cols_2.setPrefWidth(525.0);
		cols_2.setId("col_2");

		mainAnchor.getChildren().add(flow);
		mainAnchor.getChildren().add(nums);
		mainAnchor.getChildren().add(nums2);
		mainAnchor.getChildren().add(cols);
		mainAnchor.getChildren().add(cols_2);

		Label totalTime = new Label("Total Time:");
		totalTime.setLayoutX(26.0);
		totalTime.setLayoutY(35.0);
		totalTime.setFont(new Font(24.0));
		mainAnchor.getChildren().add(totalTime);

		TextField totalTimeF = new TextField("00:00:00");
		totalTimeF.setId("TotalTime");
		totalTimeF.setAlignment(Pos.CENTER);
		totalTimeF.setDisable(true);
		totalTimeF.setEditable(false);
		totalTimeF.setLayoutX(166.0);
		totalTimeF.setLayoutY(25.0);
		totalTimeF.setPrefHeight(40.0);
		totalTimeF.setPrefWidth(144.0);
		totalTimeF.setStyle("-fx-opacity: 1;");
		totalTimeF.setFont(new Font(24.0));
		mainAnchor.getChildren().add(totalTimeF);

		ImageView whiteImg = new ImageView(new Image(getClass().getResource("pictures/Queen_WHITE.png").toString()));
		whiteImg.setFitHeight(90);
		whiteImg.setFitWidth(90);
		whiteImg.setLayoutX(16);
		whiteImg.setLayoutY(102);

		ImageView blackImg = new ImageView(new Image(getClass().getResource("pictures/Queen_BLACK.png").toString()));
		blackImg.setFitHeight(90);
		blackImg.setFitWidth(90);
		blackImg.setLayoutX(16);
		blackImg.setLayoutY(300);

		mainAnchor.getChildren().add(whiteImg);
		mainAnchor.getChildren().add(blackImg);

		Label vsLbl = new Label("VS");
		vsLbl.setLayoutX(183.0);
		vsLbl.setLayoutY(260.0);
		vsLbl.setFont(new Font(24));

		TextField timeField = new TextField("00:00");
		timeField.setId("Turn_Timer");
		timeField.setLayoutX(266.0);
		timeField.setLayoutY(250.0);	
		timeField.setPrefHeight(40.0);
		timeField.setPrefWidth(108.0);
		timeField.setEditable(false);
		timeField.setDisable(true);
		timeField.setStyle("-fx-opacity: 1;");
		timeField.setAlignment(Pos.CENTER);
		timeField.setFont(new Font(24));




		mainAnchor.getChildren().add(vsLbl);
		mainAnchor.getChildren().add(timeField);

		setupStandardColsRows();


		//		mainAnchor.getChildren().add(totalTimeF);

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
				label.setPrefHeight(65.0);
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
				label.setPrefWidth(65.0);
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
				PrimaryColor color = boardController.getTileColor(i, j);
				FlowPane tilePane = new FlowPane(); 
				tilePane.setPrefHeight(65.0);
				tilePane.setPrefWidth(65.0);
				tilePane.setId(String.valueOf(i+"_"+j));
				tilePane.setStyle("-fx-background-color: " + color + ";");
				board.getChildren().add(tilePane);
			}
		}
	}

	/**
	 * addds the tile secondary color to board GUI
	 * @param row
	 * @param col
	 * @param tileColor
	 * @return true if added, false otherwise
	 */
	public boolean addColoredTileToBoard(int row, char col, SeconderyTileColor tileColor) {
		FlowPane board = (FlowPane) mainAnchor.lookup("#board");
		String tileId = String.valueOf("#" + row + "_" + col);
		FlowPane tile = (FlowPane) board.lookup(tileId);
		if(tile == null) {
			System.out.println("(" + row + "," + col + ")");
			System.out.println("Error: Board doesn't contain requested tile");
			return false;
		}
		
		if(tileColor==SeconderyTileColor.ORANGE || tileColor==SeconderyTileColor.YELLOW_ORANGE) {
			 InnerShadow innerShadow = new InnerShadow(); 
		     		      
		      innerShadow.setChoke(0.25); 
		      innerShadow.setWidth(110);
		      innerShadow.setWidth(110); 
		      innerShadow.setRadius(54.5);
		      innerShadow.setColor(Color.rgb(255, 122, 6));        
		      tile.setEffect(innerShadow);      
			if(tileColor==SeconderyTileColor.YELLOW_ORANGE) {
			tileColor= SeconderyTileColor.YELLOW;
			}else {
				return true;
			}
		}
		ImageView tileImage;
		tileImage = new ImageView(new Image(getClass().getResource("pictures/"+tileColor+"Tile.png").toString()));
		tileImage.setId("Tile_" + tileColor);
		tileImage.setFitHeight(65.0);
		tileImage.setFitWidth(65.0);
		tileImage.setPickOnBounds(true);
		tileImage.setPreserveRatio(true);
		//		tileImage.setOnMouseReleased(new EventHandler<MouseEvent>() {
		//			@Override
		//			public void handle(MouseEvent event) {
		//				BoardController.getInstance().stepOnColorTile(row,col,tileColor);
		//			}
		//			
		//		});
		tile.getChildren().add(tileImage);
		return true;
	}

	public boolean removeAllColoredTiles() {
		FlowPane board = (FlowPane) mainAnchor.lookup("#board");
		ArrayList<Tile> coloredTiles = boardController.getAllColoredTiles();
		System.out.println("colored tiles board gui:\n"+coloredTiles.toString());
		for(Tile t :coloredTiles) {

			int row = t.getLocation().getRow();
			char col = t.getLocation().getColumn();
			String tileId = String.valueOf("#" + row + "_" + col);
			FlowPane tile = (FlowPane) board.lookup(tileId);
			if(tile == null) {
				System.out.println("(" + row + "," + col + ")");
				System.out.println("Error: Board has no tile in location ("+row+","+col+")");
				return false;
			}
			System.out.println("im removing from board in "+row+","+col );
			if(t.getColor2()==SeconderyTileColor.ORANGE||t.getColor2()==SeconderyTileColor.YELLOW_ORANGE) {
				tile.setEffect(null);
			}
			if(!t.getColor2().equals(SeconderyTileColor.ORANGE)){
				System.out.println("removing "+t.getColor2()+ "from loc "+t.getLocation());
			tile.getChildren().remove(0);
			}

		}
		return true;
	}

	/**
	 * used to add piece to the board
	 * @param piece
	 * @return
	 */
	public boolean addPieceToBoard(int row, char col, PrimaryColor pieceColor, boolean isSoldier) {
		FlowPane board = (FlowPane) mainAnchor.lookup("#board");
		String tileId = String.valueOf("#" + row + "_" + col);
		FlowPane tile = (FlowPane) board.lookup(tileId);
		if(tile == null) {
			System.out.println("(" + row + "," + col + ")");
			System.out.println("Board has no tile in piece's location");
			return false;
		}

		//remove color from 

		//TODO check piece existence in tile
		ImageView pieceImage;
		if(isSoldier) {
			pieceImage = new ImageView(new Image(getClass().getResource("pictures/Soldier_" + pieceColor + ".png").toString()));
			pieceImage.setId("Soldier_" + pieceColor);
		}
		else {
			pieceImage = new ImageView(new Image(getClass().getResource("pictures/Queen_" + pieceColor + ".png").toString()));
			pieceImage.setId("Queen_" + pieceColor);
		}
		pieceImage.setFitHeight(65.0);
		pieceImage.setFitWidth(65.0);
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
					tempImage.setFitHeight(65.0);
					tempImage.setFitWidth(65.0);
					tempImage.setPickOnBounds(true);
					tempImage.setPreserveRatio(true);

					tempImage.setCursor(Cursor.HAND);	
					tempImage.setVisible(false);

					mainAnchor.getChildren().add(2, tempImage);

					int col = (int) ((int) event.getSceneX() - board.getLayoutX());
					int row = 8 - (((int) ( event.getSceneY() - board.getLayoutY() )) / 65);
					selectedCol2 = (char)((char)(col / 65) + 'A');
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
						int row = 8 - (((int) ( tempImage.getLayoutY() - board.getLayoutY() )) / 65);

						if(((char)((char)(col / 65) + 'A') != selectedCol2 && row != selectedRow2) 
								&& dragCol == '_' && dragRow == -1) {
							dragCol = (char)((char)(col / 65) + 'A');
							dragRow = row;
							if(dragCol < 'A' || dragCol > 'H' || dragRow < 1 || dragRow > 8) {
								dragCol = '_';
								dragRow = -1;
							}

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
					int relativeX = ((int) ( tempImage.getLayoutX() - board.getLayoutX() )) / 65;
					int relativeY = ((int) ( tempImage.getLayoutY() - board.getLayoutY() )) / 65;

					boolean isSoldier;

					FlowPane tempboard = (FlowPane) mainAnchor.lookup("#board");
					String tileId = String.valueOf("#" + selectedRow2 + "_" + selectedCol2);
					FlowPane temptile = (FlowPane) tempboard.lookup(tileId);

					if(temptile.getChildren().get(0).getId().split("_")[0].matches("Soldier")) {
						isSoldier = true;
					}else {
						isSoldier = false;
					}

					if((char) ((char) relativeX + 'A') == selectedCol2 || 8 - relativeY == selectedRow2) return ;
					if(!BoardController.getInstance().validateLocation(8 - relativeY, (char) ((char) relativeX + 'A'))) return;

					if(!pieceColor.equals(turnColor)) return;

					if(!isSoldier) {
						AnchorPane tempDirections = new AnchorPane();
						tempDirections.setId("tempDirections");
						AnchorPane.setBottomAnchor(tempDirections, 0.0);
						AnchorPane.setLeftAnchor(tempDirections, 0.0);
						AnchorPane.setRightAnchor(tempDirections, 0.0);
						AnchorPane.setTopAnchor(tempDirections, 0.0);
						tempDirections.setStyle("-fx-background-color: #dbdbdb66;");
						mainAnchor.getChildren().add(tempDirections);
						openDirectionsWind(tempDirections, 8-relativeY ,(char) ((char) relativeX + 'A'));

						mainAnchor.getChildren().remove(tempImage);

						return;
					}
					movePiece(selectedRow2, selectedCol2
							, 8-relativeY, (char) ((char) relativeX + 'A'), getDirectionByDrag(selectedRow2, selectedCol2, isSoldier));
					if(mainAnchor != null)
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
	public Directions getDirectionByDrag(int currentRow, char currentCol, boolean isSoldier){
		return boardController.getDirection(currentRow, currentCol, dragRow, dragCol, isSoldier);
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
		lp1.setId("Name_WHITE");
		lp1.setLayoutX(105.0);
		lp1.setLayoutY(121.0);
		lp1.setFont(new Font(24.0));

		ColorAdjust colorAdjust = new ColorAdjust();

		Label label1 = new Label("Points:");
		label1.setLayoutX(106.0);
		label1.setLayoutY(165.0);
		label1.setEffect(colorAdjust);
		label1.setFont(new Font(20.0));

		TextField pointsWhite = new TextField("0");
		pointsWhite.setId("Points_WHITE");
		pointsWhite.setAlignment(Pos.CENTER);
		pointsWhite.setDisable(true);
		pointsWhite.setEditable(false);
		pointsWhite.setLayoutX(175.0);
		pointsWhite.setLayoutY(161.0);
		pointsWhite.setPrefHeight(24.0);
		pointsWhite.setPrefWidth(81.0);
		pointsWhite.setStyle("-fx-opacity: 1;");
		pointsWhite.setFont(new Font(17.0));

		Label lp2 = new Label(blackName);
		lp2.setId("Name_BLACK");
		lp2.setLayoutX(105.0);
		lp2.setLayoutY(319.0);
		lp2.setFont(new Font(24.0));

		Label label2 = new Label("Points:");
		label2.setLayoutX(106.0);
		label2.setLayoutY(359.0);
		label2.setEffect(colorAdjust);
		label2.setFont(new Font(20.0));

		TextField pointsBlack = new TextField("0");
		pointsBlack.setId("Points_BLACK");
		pointsBlack.setAlignment(Pos.CENTER);
		pointsBlack.setDisable(true);
		pointsBlack.setEditable(false);
		pointsBlack.setLayoutX(175.0);
		pointsBlack.setLayoutY(355.0);
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
	 * implement Turn Changing
	 * set new turn
	 * @param color
	 */
	public void setNewTurn(PrimaryColor color) {
		boolean running = (turnTimer == null ? false : true);
		if(!running) {
			turnTimer = new TurnTimerController();
			turnTimer.start();
		}
		String id = "#Name_" + color;
		Label name = (Label) mainAnchor.lookup(id);
		DropShadow ds = new DropShadow();
		ds.setColor(Color.RED);
		ds.setHeight(134.41);
		ds.setRadius(66.52);
		ds.setWidth(133.67);
		ds.setSpread(0.85);
		if(color.equals(PrimaryColor.WHITE))
		{
			String id2 = "#Name_BLACK"; 
			Label name2 = (Label) mainAnchor.lookup(id2);
			name2.setEffect(null);
			name2.setFont(new Font(28));
			name.setEffect(ds);

		}
		else
		{
			String id2 = "#Name_WHITE"; 
			Label name2 = (Label) mainAnchor.lookup(id2);
			name2.setEffect(null);
			name2.setFont(new Font(28));
			name.setEffect(ds);
		}
		//System.out.println(color);
		this.turnColor = color;
		//TODO Put shadow on player on his turn
	}

	public void movePiece(int fromRow, char fromCol, int toRow, char toCol, Directions direction) {
		SeconderyTileColor toTileColor=boardController.getTile(toRow, toCol).getColor2();
		final boolean isToTileYellow= (toTileColor==SeconderyTileColor.YELLOW ||
				toTileColor==SeconderyTileColor.YELLOW_ORANGE)? true:false;
		try {
			if(boardController.movePiece(fromRow, fromCol, toRow, toCol, direction)) {
				if(mainAnchor == null) return;
				FlowPane board = (FlowPane) mainAnchor.lookup("#board");
				String tileId = String.valueOf("#" + fromRow + "_" + fromCol);
				FlowPane fromTile = (FlowPane) board.lookup(tileId);
				tileId = String.valueOf("#" + toRow + "_" + toCol);
				FlowPane toTile = (FlowPane) board.lookup(tileId);

				//removing piece selection
				removeSelectionFromPiece((ImageView) fromTile.getChildren().get(0));
				selectedRow = -1;
				selectedCol = '_';

				boolean burnt = boardController.checkBurnCurrent(toRow, toCol);
				//System.out.println("BURNTTTTTTTTTTTT" + burnt);
				if(!burnt) {
					//					int pieceIndexInToTile = isToTileYellow? 1:0;
					//TODO should burning piece have the powers of colored tile???
					//if tile is colored in any color that is not yellow
					if(toTileColor!= null && !isToTileYellow) {
						//step on color
						removeAllColoredTiles();
						toTile.getChildren().add(fromTile.getChildren().get(0));
						fromTile.getChildren().clear();
						String msg = boardController.stepOnColorTile(toRow, toCol, toTileColor);
						if (msg != null) {
							if(msg.matches("BLUE")) {
								return;
							}
							notifyUpgradeInGame(msg);
						}

						System.out.println("removing "+toTileColor+" "+toRow+","+toCol);
						//remove all tiles color
					}
					else if(toTileColor== null) {
						removeAllColoredTiles();
						toTile.getChildren().add(fromTile.getChildren().get(0));
						fromTile.getChildren().clear();
					}
					//add piece back to board in new location

				}//moved piece did burn
				else {
					fromTile.getChildren().clear();
				}
				

				//if piece still on board,check if it needs to become  queen
				if(!burnt) {
					if(toRow == 1 && String.valueOf(toTile.getChildren().get(0).getId().split("_")[1]).matches("BLACK")) {
						this.upgradeToQueen(toTile);
					}else if(toRow == 8 && String.valueOf(toTile.getChildren().get(0).getId().split("_")[1]).matches("WHITE")) {
						this.upgradeToQueen(toTile);
					}

					if(toTileColor!=null && isToTileYellow) {
						//TODO handle  yellow
						String msg = boardController.stepOnColorTile(toRow, toCol, toTileColor);
						if(msg != null) {
							notifyUpgradeInGame(msg);
						}

						//remove all tiles color
						removeAllColoredTiles();
						System.out.println("tiles are removed");
						toTile.getChildren().add(fromTile.getChildren().get(0));
						fromTile.getChildren().clear();

						checkToBurnPiece();
						return;
					}
				}
				//moved piece is burnt
				else {
					//remove all tiles color
					removeAllColoredTiles();
				}

				checkToBurnPiece();
				GameController.getInstance().switchTurn();
				this.setPlayerScore(turnColor,boardController.getPlayerScore(turnColor));
				if(GameController.getInstance().isGameRunning()) {
					PrimaryColor newColor = boardController.getPlayerTurn();
					if(newColor != turnColor) {
						setNewTurn(boardController.getPlayerTurn());
						turnTimer.resetColors();
					}
				}
			}else {
				notifyByError("Please try moving the piece again!\nSomething went wrong while trying to move the piece!");
			}
		} catch (IllegalMoveException | LocationException e) {
			notifyByError(e.getMessage());

		}
	}

	public void removePiece(int row, char col, boolean isBurnt) {
		FlowPane board = (FlowPane) mainAnchor.lookup("#board");
		String temp = String.valueOf("#" + row + "_" + col);
		FlowPane fromTile = (FlowPane) board.lookup(temp);
		if(fromTile.getChildren().size() > 0) {
			if(fromTile.getChildren().get(0).getId().split("_").length == 2) {
				if(fromTile.getChildren().get(0).getId().split("_")[0].matches("Soldier") 
						|| fromTile.getChildren().get(0).getId().split("_")[0].matches("Queen")) {
					fromTile.getChildren().clear();
				}
			}
		}
	}

	public void upgradeToQueen(FlowPane checkTile) {
		
		String[] id = checkTile.getChildren().get(0).getId().split("_");

		if(id.length == 2 && (id[0].matches("Soldier") || id[0].matches("Queen"))) {
			((ImageView) checkTile.getChildren().get(0)).setImage(new Image(getClass().getResource("pictures/Queen_" + id[1] + ".png").toString()));
			((ImageView) checkTile.getChildren().get(0)).setId("Queen_" + id[1]);
		}
	}

	/**
	 * Receives an anchor pane as parameter, then complete adding its functionality
	 * @param anchor
	 * @param toCol 
	 * @param toRow 
	 */
	private void openDirectionsWind(AnchorPane anchor, int toRow, char toCol) {
		ImageView upLeft = new ImageView(new Image(getClass().getResource("pictures/up_left.png").toString()));
		ImageView upRight = new ImageView(new Image(getClass().getResource("pictures/up_right.png").toString()));
		ImageView downLeft = new ImageView(new Image(getClass().getResource("pictures/down_left.png").toString()));
		ImageView downRight = new ImageView(new Image(getClass().getResource("pictures/down_right.png").toString()));
		addArrowImgToAnchor(anchor, upLeft, Directions.UP_LEFT, 88, 385, toRow, toCol);
		addArrowImgToAnchor(anchor, upRight, Directions.UP_RIGHT, 188,385, toRow, toCol);
		addArrowImgToAnchor(anchor, downLeft, Directions.DOWN_LEFT, 88,484, toRow, toCol);
		addArrowImgToAnchor(anchor, downRight, Directions.DOWN_RIGHT, 188,484, toRow, toCol);
		Button dirCancel = new Button("Cancel Move");
		dirCancel.setLayoutX(130);
		dirCancel.setLayoutY(600);
		dirCancel.setMnemonicParsing(false);
		dirCancel.setFont(new Font(17.0));
		dirCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mainAnchor.getChildren().remove(anchor);
			}
		});
		anchor.getChildren().add(dirCancel);

	}

	/**
	 * adds arrow image to anchor
	 */
	private void addArrowImgToAnchor(AnchorPane anchor, ImageView image, Directions direction,
			double layoutX, double layoutY, int toRow, char toCol) {
		image.setFitHeight(100);
		image.setFitWidth(100);
		image.setLayoutX(layoutX);
		image.setLayoutY(layoutY);
		image.setPickOnBounds(true);
		image.setPreserveRatio(true);
		image.setCursor(Cursor.HAND);
		image.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mainAnchor.getChildren().remove(anchor);
				movePiece(selectedRow2, selectedCol2, toRow, toCol, direction);
			}
		});
		anchor.getChildren().add(image);
	}

	/**
	 * Method to update full timer visualization
	 * @param seconds
	 */
	public void updateFullTimer(int seconds) {
		int hour = 0;
		int minute = 0;
		int second = 0;

		if(seconds >= 3600) {
			hour = seconds / 3600;
			if(seconds - (hour * 3600 ) < 60)
				minute = seconds - (hour * 3600 );
			else
				minute = (seconds - (hour * 3600 ))/60;
			second = seconds - (minute * 60 + hour * 3600);
		}else if(seconds >= 60) {
			minute = seconds / 60;
			second = seconds - minute * 60;
		}else {
			second = seconds;
		}

		String str = String.valueOf((hour > 9 ? hour : ("0" + hour)) + ":" + (minute > 9 ? minute : ("0" + minute)) +
				":" + (second > 9 ? second : ("0" + second)));
		if(mainAnchor == null) return;
		((TextField) mainAnchor.lookup("#TotalTime")).setText(str);;
	}

	public void updateTurnTimer(int seconds) {
		int minute = 0;
		int second = 0;

		if(seconds >= 60) {
			minute = seconds / 60;
			second = seconds - minute * 60;
		}else {
			second = seconds;
		}

		String str = String.valueOf((minute > 9 ? minute : ("0" + minute)) + ":" + (second > 9 ? second : ("0" + second)));
		if(mainAnchor == null) return;
		((TextField) mainAnchor.lookup("#Turn_Timer")).setText(str);;
	}

	public void notifyUpgradeInGame(String info) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Congrats");
		alert.setHeaderText(info);
		alert.showAndWait();
	}

	public void notifyByError(String err) {
		//TODO use this method to show exception message
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText(err);
		alert.showAndWait();
	}

	public void notifyWinner(String name, int score, PrimaryColor color) {
		//		Alert alert = new Alert(AlertType.NONE);
		//		alert.setTitle("Game Finished");
		//		alert.setHeaderText(name);
		//		alert.setContentText("Congratulations,you are the winner!");
		//		ButtonType button = new ButtonType("Close");
		//		alert.getButtonTypes().clear();
		//		alert.getButtonTypes().setAll(button);
		//
		//		Optional<ButtonType> result = alert.showAndWait();
		//		if (result.get() == button){
		//			alert.close();
		//			DisplayController.getInstance().closeBoard();
		//			DisplayController.getInstance().showMainScreen();
		//		}
		DisplayController.getInstance().showWinner(name, score, color);
	}

	/**
	 * 
	 */
	public void checkToBurnPiece() {
		for(int i = 1 ; i <= 8 ; i+=2) {
			for(char c = 'A' ; c <= 'H' ; c+=2) {
				FlowPane board = (FlowPane) mainAnchor.lookup("#board");
				String temp = String.valueOf("#" + i + "_" + c);
				FlowPane tile = (FlowPane) board.lookup(temp);
				if(tile.getChildren().size() > 0) {
					if(tile.getChildren().get(0).getId().split("_").length == 2) {
						if(tile.getChildren().get(0).getId().split("_")[0].matches("Soldier") 
								|| tile.getChildren().get(0).getId().split("_")[0].matches("Queen")) {
							if(!boardController.pieceExists(i, c, 
									(tile.getChildren().get(0).getId().split("_")[1].matches(PrimaryColor.WHITE.toString()) ) ? PrimaryColor.WHITE : PrimaryColor.BLACK))
								this.removePiece(i, c, true);
						}
					}
				}
			}
		}
		for(int i = 2 ; i <= 8 ; i+=2) {
			for(char c = 'B' ; c <= 'H' ; c+=2) {
				FlowPane board = (FlowPane) mainAnchor.lookup("#board");
				String temp = String.valueOf("#" + i + "_" + c);
				FlowPane tile = (FlowPane) board.lookup(temp);
				if(tile.getChildren().size() > 0) {
					if(tile.getChildren().get(0).getId().split("_").length == 2) {
						if(tile.getChildren().get(0).getId().split("_")[0].matches("Soldier") 
								|| tile.getChildren().get(0).getId().split("_")[0].matches("Queen")) {
							if(!boardController.pieceExists(i, c, 
									(tile.getChildren().get(0).getId().split("_")[1].matches(PrimaryColor.WHITE.toString()) ) ? PrimaryColor.WHITE : PrimaryColor.BLACK))
								this.removePiece(i, c, true);
						}
					}
				}
			}
		}
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

	public void showRetrievalSelection(HashMap<Integer, ArrayList<Character>> tiles) {
		for(Integer i : tiles.keySet()) {
			for(Character c : tiles.get(i)) {
				System.out.println("Available Location : (" + i + "," + c + ")");
			}
		}

		AnchorPane tempBoard = new AnchorPane();
		tempBoard.setId("tempBoard");
		AnchorPane.setBottomAnchor(tempBoard, 0.0);
		AnchorPane.setLeftAnchor(tempBoard, 0.0);
		AnchorPane.setRightAnchor(tempBoard, 0.0);
		AnchorPane.setTopAnchor(tempBoard, 0.0);
		tempBoard.setStyle("-fx-background-color: #dbdbdb66;");
		mainAnchor.getChildren().add(tempBoard);

		FlowPane flow = new FlowPane();
		flow.setLayoutX(477.0);
		flow.setLayoutY(80.0);
		flow.setPrefHeight(525.0);
		flow.setPrefWidth(525.0);
		flow.setId("tempBoardFlow");
		tempBoard.getChildren().add(flow);
		for(int i = 8 ; i >= 1 ; i--) {
			for(char j = 'A' ; j <= 'H' ; j++) {
				PrimaryColor color = boardController.getTileColor(i, j);
				FlowPane tilePane = new FlowPane(); 
				tilePane.setPrefHeight(65.0);
				tilePane.setPrefWidth(65.0);
				tilePane.setStyle("-fx-background-color: " + color + "; -fx-opacity: 0.3;");
				tilePane.setId(String.valueOf("TEMP_"+i+"_"+j));
				flow.getChildren().add(tilePane);

				if(tiles.containsKey(i)) {
					if(tiles.get(i).contains(j)) {
						tilePane.setStyle("-fx-background-color: " + color + "; -fx-opacity: 1;");
						tilePane.setCursor(Cursor.HAND);
						tilePane.setOnMouseClicked(new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent event) {
								String[] tileId = tilePane.getId().split("_");
								System.out.println("Trying to Retrieve Soldier to Tile (" + tileId[1] + "," + tileId[2] + ")");
								mainAnchor.getChildren().remove(tempBoard);
								BoardController.getInstance().retrieveSoldier(Integer.parseInt(tileId[1]), tileId[2].toCharArray()[0], color);
								BoardController.getInstance().refreshScoreInBoardGUI();

								GameController.getInstance().switchTurn();
								DisplayController.boardGUI.setPlayerScore(turnColor,BoardController.getInstance().getPlayerScore(turnColor));
								if(GameController.getInstance().isGameRunning()) {
									PrimaryColor newColor = BoardController.getInstance().getPlayerTurn();
									if(newColor != turnColor) {
										DisplayController.boardGUI.setNewTurn(BoardController.getInstance().getPlayerTurn());
										DisplayController.boardGUI.getTurnTimer().resetColors();
									}
								}
							}
						});
					}
				}
			}
		}
	}

	public TurnTimerController getTurnTimer() {
		return turnTimer;
	}




	//	public void removeTileColor(int row, char col, SeconderyTileColor tileColor){
	//		if(mainAnchor == null) return;
	//		FlowPane board = (FlowPane) mainAnchor.lookup("#board");
	//		String tileId = String.valueOf("#" + row + "_" + col);
	//		FlowPane toTile = (FlowPane) board.lookup(tileId);
	//		
	//		try {
	//			//step on color
	//			boardController.stepOnColorTile(row, col, tileColor);
	//			//remove to tile color
	//			toTile.getChildren().clear();
	//		} catch (GameUpgradeException e) {
	//			// TODO Auto-generated catch block
	//			notifyUpgradeInGame(e.getMessage());;
	//		}
	//		
	//		
	//	}


}
