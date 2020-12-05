package View;

import java.io.IOException;
import java.util.Optional;

import javax.management.Notification;

import Controller.BoardController;
import Controller.DisplayController;
import Exceptions.IllegalMoveException;
import Exceptions.LocationException;
import Model.Tile;
import Utils.Directions;
import Utils.PrimaryColor;
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
			e.printStackTrace();
		}
		Scene scene = new Scene(mainAnchor,1168.0, 768.0);
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
		flow.setPrefHeight(640.0);
		flow.setPrefWidth(640.0);
		flow.setId("board");

		FlowPane nums = new FlowPane();
		nums.setLayoutX(427.0);
		nums.setLayoutY(80.0);
		nums.setPrefHeight(640.0);
		nums.setPrefWidth(50.0);
		nums.setId("nums_1");
		
		FlowPane nums2 = new FlowPane();
		nums2.setLayoutX(1117.0);
		nums2.setLayoutY(80.0);
		nums2.setPrefHeight(640.0);
		nums2.setPrefWidth(50.0);
		nums2.setId("nums_2");
		
		FlowPane cols = new FlowPane();
		cols.setLayoutX(477.0);
		cols.setLayoutY(30.0);
		cols.setPrefHeight(50.0);
		cols.setPrefWidth(640.0);
		cols.setId("col_1");
		
		FlowPane cols_2 = new FlowPane();
		cols_2.setLayoutX(477.0);
		cols_2.setLayoutY(720.0);
		cols_2.setPrefHeight(50.0);
		cols_2.setPrefWidth(640.0);
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
		
		Label vsLbl = new Label("VS");
		vsLbl.setLayoutX(183.0);
		vsLbl.setLayoutY(280.0);
		vsLbl.setFont(new Font(28));
		
		TextField timeField = new TextField("00:00");
		timeField.setId("Turn_Timer");
		timeField.setLayoutX(266.0);
		timeField.setLayoutY(270.0);	
		timeField.setPrefHeight(60.0);
		timeField.setPrefWidth(108.0);
		timeField.setEditable(false);
		timeField.setDisable(true);
		timeField.setStyle("-fx-opacity: 1;");
		timeField.setAlignment(Pos.CENTER);
		timeField.setFont(new Font(28));
		
		
		mainAnchor.getChildren().add(vsLbl);
		mainAnchor.getChildren().add(timeField);
		
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
				label.setPrefHeight(80.0);
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
				label.setPrefWidth(80.0);
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
				tilePane.setPrefHeight(80.0);
				tilePane.setPrefWidth(80.0);
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
	public boolean addPieceToBoard(int row, char col, PrimaryColor pieceColor, boolean isSoldier) {
		FlowPane board = (FlowPane) mainAnchor.lookup("#board");
		String temp = String.valueOf("#" + row + "_" + col);
		FlowPane tile = (FlowPane) board.lookup(temp);
		if(tile == null) {
			System.out.println("(" + row + "," + col + ")");
			System.out.println("Board has no tile in piece's location");
			return false;
		}
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
		pieceImage.setFitHeight(80.0);
		pieceImage.setFitWidth(80.0);
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
					tempImage.setFitHeight(80.0);
					tempImage.setFitWidth(80.0);
					tempImage.setPickOnBounds(true);
					tempImage.setPreserveRatio(true);
					
					tempImage.setCursor(Cursor.HAND);	
					tempImage.setVisible(false);
					
					mainAnchor.getChildren().add(2, tempImage);

					int col = (int) ((int) event.getSceneX() - board.getLayoutX());
					int row = 8 - (((int) ( event.getSceneY() - board.getLayoutY() )) / 80);
					selectedCol2 = (char)((char)(col / 80) + 'A');
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
						int row = 8 - (((int) ( tempImage.getLayoutY() - board.getLayoutY() )) / 80);
						
						if(((char)((char)(col / 80) + 'A') != selectedCol2 && row != selectedRow2) 
								&& dragCol == '_' && dragRow == -1) {
							dragCol = (char)((char)(col / 80) + 'A');
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
					int relativeX = ((int) ( tempImage.getLayoutX() - board.getLayoutX() )) / 80;
					int relativeY = ((int) ( tempImage.getLayoutY() - board.getLayoutY() )) / 80;
					
					boolean tempType;
					
					FlowPane tempboard = (FlowPane) mainAnchor.lookup("#board");
					String ttemp = String.valueOf("#" + selectedRow2 + "_" + selectedCol2);
					FlowPane temptile = (FlowPane) tempboard.lookup(ttemp);
					
					if(temptile.getChildren().get(0).getId().split("_")[0].matches("Soldier")) {
						tempType = true;
					}else {
						tempType = false;
					}

					if((char) ((char) relativeX + 'A') == selectedCol2 || 8 - relativeY == selectedRow2) return ;
					if(!BoardController.getInstance().validateLocation(8 - relativeY, (char) ((char) relativeX + 'A'))) return;
					
					if(!pieceColor.equals(turnColor)) return;
					
					if(!tempType) {
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
							, 8-relativeY, (char) ((char) relativeX + 'A'), getDirectionByDrag(selectedRow2, selectedCol2, tempType));
					
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
		System.out.println(turnColor + " " + id[1]);
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
		System.out.println(color);
		this.turnColor = color;
		//TODO Put shadow on player on his turn
	}
	
	public void movePiece(int fromRow, char fromCol, int toRow, char toCol, Directions direction) {
		try {
			if(boardController.movePiece(fromRow, fromCol, toRow, toCol, direction)) {
				FlowPane board = (FlowPane) mainAnchor.lookup("#board");
				String temp = String.valueOf("#" + fromRow + "_" + fromCol);
				FlowPane fromTile = (FlowPane) board.lookup(temp);
				temp = String.valueOf("#" + toRow + "_" + toCol);
				FlowPane toTile = (FlowPane) board.lookup(temp);
				
				removeSelectionFromPiece((ImageView) fromTile.getChildren().get(0));
				selectedRow = -1;
				selectedCol = '_';
				
				if(!boardController.checkBurnCurrent(toRow, toCol)) {
					toTile.getChildren().add(fromTile.getChildren().get(0));
				}
				fromTile.getChildren().clear();
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
	
	public void upgradeToQueen(int row, char col) {
		FlowPane board = (FlowPane) mainAnchor.lookup("#board");
		String temp = String.valueOf("#" + row + "_" + col);
		FlowPane tile = (FlowPane) board.lookup(temp);
		String[] id = tile.getChildren().get(0).getId().split("_");
		
		if(id.length == 2 && (id[0].matches("Soldier") || id[0].matches("Queen"))) {
			((ImageView) tile.getChildren().get(0)).setImage(new Image(getClass().getResource("pictures/Queen_" + id[1] + ".png").toString()));
			((ImageView) tile.getChildren().get(0)).setId("Queen_" + id[1]);
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
		addArrowImgToAnchor(anchor, upLeft, Directions.UP_LEFT, 88, 451, toRow, toCol);
		addArrowImgToAnchor(anchor, upRight, Directions.UP_RIGHT, 188,451, toRow, toCol);
		addArrowImgToAnchor(anchor, downLeft, Directions.DOWN_LEFT, 88,550, toRow, toCol);
		addArrowImgToAnchor(anchor, downRight, Directions.DOWN_RIGHT, 188,550, toRow, toCol);
		Button dirCancel = new Button("Cancel Move");
		dirCancel.setLayoutX(130);
		dirCancel.setLayoutY(666);
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
		
		((TextField) mainAnchor.lookup("#TotalTime")).setText(str);;
	}
	
	public void notifyByError(String err) {
		//TODO use this method to show exception message
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText(err);
		alert.showAndWait();
	}
	
	public void notifyWinner(String name, int score, PrimaryColor color) {
		Alert alert = new Alert(AlertType.NONE);
		alert.setTitle("Game Finished");
		alert.setHeaderText(name);
		ButtonType button = new ButtonType("Close");
		alert.getButtonTypes().clear();
		alert.getButtonTypes().setAll(button);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == button){
			alert.close();
		    DisplayController.getInstance().closeBoard();
		    DisplayController.getInstance().showMainScreen();
		}
	}
	
	public Stage getPrimary() {
		if(primary == null) {
			primary = new Stage();
		}
		return primary;
	}
	
}
