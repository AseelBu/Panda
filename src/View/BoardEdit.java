package View;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import Controller.BoardController;
import Controller.DisplayController;
import Controller.MiscController;
import Model.Piece;
import Model.Tile;
import Utils.PrimaryColor;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BoardEdit extends Application {

	private static AnchorPane mainAnchor;
	private Stage primary;
//	private char selectedCol = '_';
//	private int selectedRow = -1;
	private PrimaryColor turn;
	private HashSet<String> whitePieces;
	private HashSet<String> blackPieces;
	private boolean tempAdded;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			mainAnchor = FXMLLoader.load(getClass().getResource("/View/BoardEdit.fxml"));
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
				if(!exitAlert()) {
					event.consume();
					return;
				}
				DisplayController.getInstance().showMainScreen();
			}
		});
		turn = null;
		tempAdded = false;
		whitePieces = new HashSet<>();
		blackPieces = new HashSet<>();

		primary = primaryStage;
		loadDesign();
		loadStandardTiles();
		loadStaticPieces();
		setSelectionAbility(true,PrimaryColor.WHITE);
		setSelectionAbility(true,PrimaryColor.BLACK);
		loadButtons();

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
	 * loads static pieces to the display
	 */
	public void loadStaticPieces() {
		addLabel("Build Your Board", -1, -1, 91.0, 40.0, new Font("System Bold", 28.0), null, null);
		
		//Draggable pieces
		addStaticPiece(PieceType.Soldier_BLACK, 65.0, 65.0, 63.0, 109.0);
		addStaticPiece(PieceType.Soldier_WHITE, 65.0, 65.0, 63.0, 209.0);
		addStaticPiece(PieceType.Queen_BLACK, 65.0, 65.0, 200.0, 109.0);
		addStaticPiece(PieceType.Queen_WHITE, 65.0, 65.0, 200.0, 209.0);
		
		loadPiecesCount();
		
		Separator s1 = new Separator();
		s1.setLayoutY(510.0);
		s1.setPrefSize(428.0, 1.0);
		s1.setStyle("-fx-background-color: black;");
		
		Separator s2 = new Separator();
		s2.setLayoutX(1.0);
		s2.setLayoutY(304.0);
		s2.setPrefSize(428.0, 1.0);
		s2.setStyle("-fx-background-color: black;");

		mainAnchor.getChildren().add(s1);
		mainAnchor.getChildren().add(s2);

		//Turn Selection
		
		addLabel("Player Turn", -1, -1, 145.0, 316.0, new Font("System Bold", 22.0), null, null);
		addLabel("Select Player Turn", 25.0, 138.0, 135.0, 471.0, new Font(16.0), "turn_lbl", "-fx-text-fill: RED;");
		
		Circle circleBlack = new Circle();
		circleBlack.setLayoutX(114.0);
		circleBlack.setLayoutY(421.0);
		circleBlack.setRadius(50.0);
		circleBlack.setStroke(Color.TRANSPARENT);
		circleBlack.setStrokeType(StrokeType.INSIDE);
		circleBlack.setFill(new ImagePattern(new Image(
				getClass().getResource("pictures/" + PieceType.Queen_BLACK + ".png").toString())));
		circleBlack.setCursor(Cursor.HAND);
		mainAnchor.getChildren().add(circleBlack);
		
		Circle circleWhite = new Circle();
		circleWhite.setLayoutX(294.0);
		circleWhite.setLayoutY(421.0);
		circleWhite.setRadius(50.0);
		circleWhite.setStroke(Color.TRANSPARENT);
		circleWhite.setStrokeType(StrokeType.INSIDE);
		circleWhite.setFill(new ImagePattern(new Image(
				getClass().getResource("pictures/" + PieceType.Queen_WHITE + ".png").toString())));
		circleWhite.setCursor(Cursor.HAND);
		mainAnchor.getChildren().add(circleWhite);

		circleWhite.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(turn == null)
					mainAnchor.getChildren().remove(((Label) mainAnchor.lookup("#turn_lbl")));
				turn = PrimaryColor.WHITE;
				circleBlack.setStroke(Color.TRANSPARENT);
				circleWhite.setStroke(Color.BLACK);
				refreshPiecesCount();
			}
		});
		circleBlack.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(turn == null)
					mainAnchor.getChildren().remove(((Label) mainAnchor.lookup("#turn_lbl")));
				turn = PrimaryColor.BLACK;
				circleBlack.setStroke(Color.BLACK);
				circleWhite.setStroke(Color.TRANSPARENT);
			}
		});
		
		
	}
	
	/**
	 * used to add static piece to the display
	 * @param type
	 * @param height
	 * @param width
	 * @param layoutX
	 * @param layoutY
	 */
	public void addStaticPiece(PieceType type, double height, double width, double layoutX, double layoutY) {
		ImageView piece = new ImageView(new Image(
				getClass().getResource("pictures/" + type + ".png").toString()));
		String id = "add_" + type;
		piece.setId(id);
		piece.setFitHeight(height);
		piece.setFitWidth(width);
		piece.setLayoutX(layoutX);
		piece.setLayoutY(layoutY);
		piece.setPickOnBounds(true);
		piece.setPreserveRatio(true);
		mainAnchor.getChildren().add(piece);
	}
	
	public void loadPiecesCount() {
		//BLACK
		
		addLabel("0", 32.0, 32.0, 296.0, 126.0, new Font(22.0), "COUNT_BLACK", null);
		addLabel("/", -1, -1, 326.0, 126.0, new Font(22.0), null, null);
		addLabel("12", -1, -1, 339.0, 126.0, new Font("System Bold",22.0), "MAX_BLACK", "-fx-text-fill: RED;");

		
		//WHITE
		
		addLabel("0", 32.0, 32.0, 296.0, 226.0, new Font(22.0), "COUNT_WHITE", null);
		addLabel("/", -1, -1, 326.0, 226.0, new Font(22.0), null, null);
		addLabel("12", -1, -1, 339.0, 226.0, new Font("System Bold",22.0), "MAX_WHITE", "-fx-text-fill: RED;");

		
	}
	
	/**
	 * loads standard game tile's design
	 */
	private void loadStandardTiles() {
		FlowPane board = (FlowPane) mainAnchor.lookup("#board");
		if(board.getChildren().size() > 0) return;
		for(int i = 8 ; i >= 1 ; i--) {
			for(char j = 'A' ; j <= 'H' ; j++) {
				PrimaryColor color = getTileColor(i, j);
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
	 * adds piece to a selected board tile
	 * 
	 * @param row
	 * @param col
	 * @param color
	 * @param isSoldier
	 */
	public void addPieceToTile(int row, char col, PieceType type) {
		FlowPane board = (FlowPane) mainAnchor.lookup("#board");
		String tileId = String.valueOf("#" + row + "_" + col);
		FlowPane tile = (FlowPane) board.lookup(tileId);
		if(tile.getChildren().size() > 0) {
			System.out.println("Tile is not empty");
			return;
		}
		ImageView pieceImage = new ImageView(new Image(
				getClass().getResource("pictures/" + type + ".png").toString()));
		pieceImage.setFitHeight(65);
		pieceImage.setFitWidth(65);
		
		ContextMenu contextMenu = new ContextMenu();
		MenuItem item1 = new MenuItem("Remove");
		item1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				removePieceFromTile(row, col);
			}
		});
		contextMenu.getItems().clear();
		contextMenu.getItems().add(item1);
		
		pieceImage.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent event) {
				contextMenu.show(pieceImage, event.getScreenX(), event.getScreenY());	
			}
		});
		
		tile.getChildren().add(pieceImage);
		if(type.equals(PieceType.Queen_WHITE) || type.equals(PieceType.Soldier_WHITE)) {
			whitePieces.add(String.valueOf(row + "_" + col));
		}else {
			blackPieces.add(String.valueOf(row + "_" + col));
		}
		
		refreshPiecesCount();
	}
	
	/**
	 * sets selection ability to static display pieces
	 * @param isAble
	 */
	public void setSelectionAbility(boolean isAble, PrimaryColor color) {
		String id = "";
		ImageView piece = null;
		
		if(isAble) {
			if(color.equals(PrimaryColor.BLACK)) {
				id = String.valueOf("#add_" + PieceType.Queen_BLACK);
				piece = (ImageView) mainAnchor.lookup(id);
				piece.setCursor(Cursor.HAND);
				piece.setOnMousePressed(new DragPiece(PieceType.Queen_BLACK, 1));
				piece.setOnMouseDragged(new DragPiece(PieceType.Queen_BLACK, 2));
				piece.setOnMouseReleased(new DragPiece(PieceType.Queen_BLACK, 3));

				id = String.valueOf("#add_" + PieceType.Soldier_BLACK);
				piece = (ImageView) mainAnchor.lookup(id);
				piece.setCursor(Cursor.HAND);
				piece.setOnMousePressed(new DragPiece(PieceType.Soldier_BLACK, 1));
				piece.setOnMouseDragged(new DragPiece(PieceType.Soldier_BLACK, 2));
				piece.setOnMouseReleased(new DragPiece(PieceType.Soldier_BLACK, 3));
			}else {
				id = String.valueOf("#add_" + PieceType.Queen_WHITE);
				piece = (ImageView) mainAnchor.lookup(id);
				piece.setCursor(Cursor.HAND);
				piece.setOnMousePressed(new DragPiece(PieceType.Queen_WHITE, 1));
				piece.setOnMouseDragged(new DragPiece(PieceType.Queen_WHITE, 2));
				piece.setOnMouseReleased(new DragPiece(PieceType.Queen_WHITE, 3));
				
				id = String.valueOf("#add_" + PieceType.Soldier_WHITE);
				piece = (ImageView) mainAnchor.lookup(id);
				piece.setCursor(Cursor.HAND);
				piece.setOnMousePressed(new DragPiece(PieceType.Soldier_WHITE, 1));
				piece.setOnMouseDragged(new DragPiece(PieceType.Soldier_WHITE, 2));
				piece.setOnMouseReleased(new DragPiece(PieceType.Soldier_WHITE, 3));
			}
		}else {
			if(color.equals(PrimaryColor.BLACK)) {
				id = String.valueOf("#add_" + PieceType.Queen_BLACK);
				piece = (ImageView) mainAnchor.lookup(id);
				piece.setCursor(Cursor.DEFAULT);
				piece.setOnMousePressed(null);
				piece.setOnMouseDragged(null);
				piece.setOnMouseReleased(null);

				id = String.valueOf("#add_" + PieceType.Soldier_BLACK);
				piece = (ImageView) mainAnchor.lookup(id);
				piece.setCursor(Cursor.DEFAULT);
				piece.setOnMousePressed(null);
				piece.setOnMouseDragged(null);
				piece.setOnMouseReleased(null);				
			}else {
				id = String.valueOf("#add_" + PieceType.Queen_WHITE);
				piece = (ImageView) mainAnchor.lookup(id);
				piece.setCursor(Cursor.DEFAULT);
				piece.setOnMousePressed(null);
				piece.setOnMouseDragged(null);
				piece.setOnMouseReleased(null);	
				
				id = String.valueOf("#add_" + PieceType.Soldier_WHITE);
				piece = (ImageView) mainAnchor.lookup(id);
				piece.setCursor(Cursor.DEFAULT);
				piece.setOnMousePressed(null);
				piece.setOnMouseDragged(null);
				piece.setOnMouseReleased(null);
			}
		}
	}
	
	/**
	 * loads buttons
	 */
	public void loadButtons() {
		Hyperlink saveHL = new Hyperlink("Save Game");
		saveHL.setId("SaveHL");
		saveHL.setLayoutX(101.0);
		saveHL.setLayoutY(565.0);
		saveHL.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color:rgba(255,0,0,0);");
		saveHL.setFont(new Font(15.0));
		saveHL.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				saveDialog();
			}
		});
		ImageView saveImg = new ImageView(new Image(
				getClass().getResource("pictures/save.png").toString()));
		saveImg.setId("SaveImg");
		saveImg.setFitHeight(40.0);
		saveImg.setFitWidth(40.0);
		saveImg.setLayoutX(65.0);
		saveImg.setLayoutY(560.0);
		saveImg.setCursor(Cursor.HAND);
		saveImg.setPickOnBounds(true);
		saveImg.setPreserveRatio(true);
		saveImg.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				saveDialog();
			}
		});
		
		
		Hyperlink startHL = new Hyperlink("Start Game");
		startHL.setId("StartHL");
		startHL.setLayoutX(278.0);
		startHL.setLayoutY(565.0);
		startHL.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color:rgba(255,0,0,0);");
		startHL.setFont(new Font(15.0));
		startHL.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DisplayController.getInstance().showNicknames(getPiecesHash(), turn);
			}
		});
		
		ImageView startImg = new ImageView(new Image(
				getClass().getResource("pictures/play.png").toString()));
		startImg.setId("StartImg");
		startImg.setFitHeight(40.0);
		startImg.setFitWidth(40.0);
		startImg.setLayoutX(243.0);
		startImg.setLayoutY(560.0);
		startImg.setCursor(Cursor.HAND);
		startImg.setPickOnBounds(true);
		startImg.setPreserveRatio(true);
		startImg.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				DisplayController.getInstance().showNicknames(getPiecesHash(), turn);
			}
		});
		mainAnchor.getChildren().add(saveHL);
		mainAnchor.getChildren().add(saveImg);
		mainAnchor.getChildren().add(startHL);
		mainAnchor.getChildren().add(startImg);

		btnsToDisable(true);
		
		
		ImageView backbutton = new ImageView(new Image(getClass().getResourceAsStream("/View/pictures/back.png")));
		backbutton.setLayoutX(10.0);
		backbutton.setLayoutY(5.0);
		backbutton.setFitWidth(45.0);
		backbutton.setFitHeight(45.0);
		backbutton.setPickOnBounds(true);
		backbutton.setPreserveRatio(true);
		backbutton.setCursor(Cursor.HAND);
		backbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			primary.fireEvent(
	                new WindowEvent(
	                		primary,
	                        WindowEvent.WINDOW_CLOSE_REQUEST
	                )
	        );
			event.consume();
		});
		
		mainAnchor.getChildren().add(backbutton);
	}
	
	/**
	 * add a label to the screen
	 * @param text
	 * @param height
	 * @param width
	 * @param layoutX
	 * @param layoutY
	 * @param font
	 * @param id
	 * @param style
	 */
	public void addLabel(String text, double height, double width, double layoutX, double layoutY
			, Font font, String id, String style) {
		Label label = new Label(text);
		if(id != null && !id.matches(""))
			label.setId(id);
		if(font != null)
			label.setFont(font);
		if(height > 0)
			label.setPrefHeight(height);
		if(width > 0)
			label.setPrefWidth(width);
		if(style != null && !style.matches(""))
			label.setStyle(style);
		label.setLayoutX(layoutX);
		label.setLayoutY(layoutY);
		mainAnchor.getChildren().add(label);
	}
	
	
	/**
	 * Used to empty a specific tile
	 * @param row
	 * @param col
	 */
	public void emptyTile(int row, char col) {
		FlowPane board = (FlowPane) mainAnchor.lookup("#board");
		String tileId = String.valueOf("#" + row + "_" + col);
		FlowPane tile = (FlowPane) board.lookup(tileId);
		if(tile.getChildren().size() == 0) {
			System.out.println("Tile is empty");
			return;
		}
		tile.getChildren().clear();
		
		refreshPiecesCount();
	}
	
	/**
	 * refreshes piece's count
	 */
	public void refreshPiecesCount() {
		Label white = ((Label) mainAnchor.lookup("#COUNT_WHITE"));
		Label black = ((Label) mainAnchor.lookup("#COUNT_BLACK"));
		
		int whiteSize = whitePieces.size();
		int blackSize = blackPieces.size();

		white.setText(String.valueOf(whiteSize));
		black.setText(String.valueOf(blackSize));
		
		if(whiteSize > 0 && blackSize > 0 && turn != null)
			btnsToDisable(false);
		else
			btnsToDisable(true);
		
		if(whiteSize >= 12)
			setSelectionAbility(false, PrimaryColor.WHITE);
		else
			setSelectionAbility(true, PrimaryColor.WHITE);

		if(blackSize >= 12)
			setSelectionAbility(false, PrimaryColor.BLACK);
		else
			setSelectionAbility(true, PrimaryColor.BLACK);

	}
	
	/**
	 * 
	 * @param piece
	 * @return ArrayList of available locations
	 */
	public ArrayList<String> getAvailableTiles(PieceType piece) {
		ArrayList<String> available = new ArrayList<>();
		
		for(int i = 8 ; i >= 1 ; i--) {
			for(char j = 'A' ; j <= 'H' ; j+=2) {
				if(i%2 == 0 && j == 'A') j = 'B';
				if(!isTileAvailable(i,j)) continue;
				
				if(piece.equals(PieceType.Soldier_WHITE)) {
					if(i == 8) continue;
				}else if(piece.equals(PieceType.Soldier_BLACK)) {
					if(i == 1) continue;
				}
				String location = String.valueOf(i + "_" + j);
				available.add(location);
			}
		}
		return available;
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return true if tile is available, false otherwise
	 */
	public boolean isTileAvailable(int row, char col) {
		String location = String.valueOf(row + "_" + col);
		return !whitePieces.contains(location) && !blackPieces.contains(location);
	}
	
	/**
	 * removes pieces from tile
	 * @param row
	 * @param col
	 */
	public void removePieceFromTile(int row, char col) {
		String location = String.valueOf(row + "_" + col);
		FlowPane board = (FlowPane) mainAnchor.lookup("#board");
		String tileId = String.valueOf("#" + row + "_" + col);
		FlowPane tile = (FlowPane) board.lookup(tileId);
		if(tile.getChildren().isEmpty()) return;
		tile.getChildren().clear();
		if(whitePieces.contains(location)) whitePieces.remove(location);
		else if(blackPieces.contains(location)) blackPieces.remove(location);
		refreshPiecesCount();
	}
	
	/**
	 * Alert on exit
	 * @return true if user selected to exit, false otherwise
	 */
	public boolean exitAlert() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exit Confirmation");
		alert.setHeaderText("Do you want to save game edit position ?");
		alert.setContentText("");
		alert.getButtonTypes().clear();
		ButtonType bt1 = ButtonType.YES;
		ButtonType bt2 = ButtonType.NO;
		ButtonType bt3 = ButtonType.CANCEL;
		alert.getButtonTypes().addAll(bt1,bt2,bt3);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES){
			if(saveDialog()){
				alert.close();
				return true;
			}else return false;
		} else if(result.get() == ButtonType.NO){
		    return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return tile color
	 */
	public PrimaryColor getTileColor(int row, char col) {
		if(row % 2 == 1) {
			if(((int) col - 'A' + 1) % 2 == 1) {
				return PrimaryColor.BLACK;
			}else {
				return PrimaryColor.WHITE;
			}
		}else {
			if(((int) col - 'A' + 1) % 2 == 1) {
				return PrimaryColor.WHITE;
			}else {
				return PrimaryColor.BLACK;
			}
		}
	}
	
	/**
	 * shows save position dialog
	 * @return true if succeeded, false otherwise
	 */
	public boolean saveDialog() {
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Game as text");
        fileChooser.setInitialFileName("Game_Backup.txt");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text", "*.txt"));
        File file = fileChooser.showSaveDialog(primary);
        ArrayList<Piece> pieces = BoardController.getInstance().createPieces(getPiecesHash());
        ArrayList<Tile> tiles = new ArrayList<>();
        if (file != null) {
    		for(int i = 8 ; i >= 1 ; i--) {
    			for(char j = 'A' ; j <= 'H' ; j+=2) {
    				if(i % 2 == 0 && j == 'A') j = 'B';
    				tiles.add(BoardController.getInstance().createTile(pieces, i, j));
    			}
    		}
        	MiscController.getInstance().saveGame(tiles, file, turn);
        	return true;
        }
        return false;
	}
	
	/**
	 * shows temp selection while dragging
	 * @param tiles
	 */
	public void showTempSelection(ArrayList<String> tiles) {
		AnchorPane tempBoard = new AnchorPane();
		tempBoard.setId("tempBoard");
		AnchorPane.setBottomAnchor(tempBoard, 0.0);
		AnchorPane.setLeftAnchor(tempBoard, 0.0);
		AnchorPane.setRightAnchor(tempBoard, 0.0);
		AnchorPane.setTopAnchor(tempBoard, 0.0);
		tempBoard.setStyle("-fx-background-color: #dbdbdb44;");
		mainAnchor.getChildren().add(3,tempBoard);
		tempAdded = true;

		FlowPane flow = new FlowPane();
		flow.setLayoutX(477.0);
		flow.setLayoutY(80.0);
		flow.setPrefHeight(525.0);
		flow.setPrefWidth(525.0);
		flow.setId("tempBoardFlow");
		tempBoard.getChildren().add(flow);
		for(int i = 8 ; i >= 1 ; i--) {
			for(char j = 'A' ; j <= 'H' ; j++) {
				PrimaryColor color = getTileColor(i, j);
				FlowPane tilePane = new FlowPane(); 
				tilePane.setPrefHeight(65.0);
				tilePane.setPrefWidth(65.0);
				tilePane.setStyle("-fx-background-color: " + color + "; -fx-opacity: 0.2;");
				tilePane.setId(String.valueOf("TEMP_"+i+"_"+j));
				flow.getChildren().add(tilePane);
				String location = String.valueOf(i + "_" + j);
				if(tiles.contains(location)) {
					tilePane.setStyle("-fx-background-color: " + color + "; -fx-opacity: 0.8;");
				}
			}
		}
	}
	
	/**
	 * gets piece hash to be used to get available tiles
	 * @return
	 */
	public HashMap<String,String> getPiecesHash(){
		HashMap<String, String> pieces = new HashMap<>();
		ArrayList<String> strPieces = new ArrayList<>();
		strPieces.addAll(whitePieces);
		strPieces.addAll(blackPieces);
		
		for(String str : strPieces) {
			FlowPane board = (FlowPane) mainAnchor.lookup("#board");
			String tileId = String.valueOf("#" + str);
			String url = ((ImageView)((FlowPane) board.lookup(tileId)).getChildren().get(0)).getImage().getUrl();
			String filename = url.substring(url.lastIndexOf('/') + 1);

			pieces.put(str, filename.substring(0, filename.lastIndexOf(".")));
		}
		return pieces;
	}
	
	/**
	 * sets whether the buttons are disabled or not
	 * @param disable
	 */
	public void btnsToDisable(boolean disable) {
		Hyperlink saveHL = (Hyperlink) mainAnchor.lookup("#SaveHL");
		Hyperlink startHL = (Hyperlink) mainAnchor.lookup("#StartHL");
		ImageView saveImg = (ImageView) mainAnchor.lookup("#SaveImg");
		ImageView startImg = (ImageView) mainAnchor.lookup("#StartImg");
		if(disable) {
			saveHL.setDisable(true);
			startHL.setDisable(true);
			saveImg.setDisable(true);
			startImg.setDisable(true);
		}else {
			saveHL.setDisable(false);
			startHL.setDisable(false);
			saveImg.setDisable(false);
			startImg.setDisable(false);
		}
	}
	
	/**
	 * used to ease the use of specifying pieces types
	 * @author firas
	 *
	 */
	private enum PieceType {
		Queen_BLACK, Queen_WHITE, Soldier_BLACK, Soldier_WHITE
	}
	
	/**
	 * Class is made to drag a piece specifying its type
	 * @author firas
	 *
	 */
	private class DragPiece implements EventHandler<MouseEvent> {
		
		private PieceType pieceType;
		private int eventType;
		
		/**
		 * 
		 * @param type
		 * @param eventType  1 is mouse pressed, 2 is mouse dragged, 3 is mouse released
		 */
		public DragPiece(PieceType type, int eventType) {
			this.pieceType = type;
			this.eventType = eventType;
		}
		
		@Override
		public void handle(MouseEvent event) {
			switch(eventType) {
				case 1 :{
					//On Mouse Pressed
					ImageView image = (ImageView) event.getSource();
					ImageView tempImage = new ImageView(image.getImage());
					tempImage.setId("drag");
					tempImage.setFitHeight(65.0);
					tempImage.setFitWidth(65.0);
					tempImage.setPickOnBounds(true);
					tempImage.setPreserveRatio(true);

					tempImage.setCursor(Cursor.HAND);	
					tempImage.setVisible(false);

					mainAnchor.getChildren().add(4, tempImage);
					event.consume();
					break;
				}
				case 2 :{
					//On Mouse Dragged
					ImageView tempImage = (ImageView) mainAnchor.lookup("#drag");
					if(tempImage != null) {
						if(!tempImage.isVisible())
							tempImage.setVisible(true);
						tempImage.setLayoutX(event.getSceneX());
						tempImage.setLayoutY(event.getSceneY());
					}
					if(!tempAdded)
						showTempSelection(getAvailableTiles(pieceType));

					event.consume();
					break;
				}
				case 3 :{
					//On Mouse Released
					ImageView tempImage = (ImageView) mainAnchor.lookup("#drag");
					FlowPane board = (FlowPane) mainAnchor.lookup("#board");
					mainAnchor.getChildren().remove((AnchorPane) mainAnchor.lookup("#tempBoard"));
					tempAdded = false;
					
					int relativeX = ((int) ( tempImage.getLayoutX() - board.getLayoutX() )) / 65;
					int relativeY = ((int) ( tempImage.getLayoutY() - board.getLayoutY() )) / 65;
					mainAnchor.getChildren().remove(tempImage);

					if(!BoardController.getInstance().validateLocation(8 - relativeY, (char) ((char) relativeX + 'A'))) return;
					
					if(mainAnchor != null)
						mainAnchor.getChildren().remove(tempImage);
					String location = String.valueOf((8 - relativeY) + "_" + (char)((char) relativeX + 'A'));
					if(getAvailableTiles(pieceType).contains(location)) {
						addPieceToTile(8-relativeY, (char) ((char) relativeX + 'A'),pieceType);
					}
					
					event.consume();
					
					break;
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
}
