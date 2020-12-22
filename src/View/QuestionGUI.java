package View;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import Controller.BoardController;
import Controller.BoardQuestionsController;
import Controller.DisplayController;
import Controller.GameController;
import Utils.DifficultyLevel;
import Utils.PrimaryColor;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class QuestionGUI extends Application{

	private static AnchorPane mainAnchor;
	private Stage primary;
	private ToggleGroup group;
	private PrimaryColor turnColor;

	public QuestionGUI(PrimaryColor color) {
		turnColor = color;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			mainAnchor = FXMLLoader.load(getClass().getResource("/View/questions.fxml"));
			mainAnchor = new AnchorPane();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(mainAnchor, 681.0, 465.0);
		scene.getStylesheets().add(getClass().getResource("/View/question.css").toExternalForm());
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

	}

	/**
	 * Loads Screen Design
	 */
	public void loadDesign(int questionId, String question, HashMap<Integer, String> answers, DifficultyLevel diff) throws Exception{
		if(question.matches("")) throw new Exception("Invalid Question");
		if(answers.isEmpty()) throw new Exception("Question has no Answers");
		for(String s : answers.values())
			if(s.matches("")) throw new Exception("Invalid Answer");

		// loads background
		mainAnchor.getChildren().clear();
		AnchorPane background = new AnchorPane();
		background.setId("background");
		AnchorPane.setBottomAnchor(background, 0.0);
		AnchorPane.setLeftAnchor(background, 0.0);
		AnchorPane.setRightAnchor(background, 0.0);
		AnchorPane.setTopAnchor(background, 0.0);
		mainAnchor.getChildren().add(background);

		ImageView img = new ImageView(new Image(getClass().getResource("pictures/questionPanda.png").toString()));
		img.setFitHeight(65.0);
		img.setFitWidth(64.0);
		img.setLayoutX(28.0);
		img.setPickOnBounds(true);
		img.setPreserveRatio(true);
		mainAnchor.getChildren().add(img);

		Button btn = new Button("Submit");
		btn.setLayoutX(294.0);
		btn.setLayoutY(412.0);
		btn.setMnemonicParsing(false);
		btn.setPrefHeight(39.0);
		btn.setPrefWidth(93.0);
		btn.setFont(new Font(18.0));
		mainAnchor.getChildren().add(btn);

		loadDifficultyLabel(diff);

		Label ques = new Label(question);
		ques.setLayoutX(29);
		ques.setLayoutY(64);
		ques.setPrefHeight(77);
		ques.setPrefWidth(674);
		ques.setFont(new Font(18));
		mainAnchor.getChildren().add(ques);

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				if(((Button) event.getSource()).getText().equals("Submit")) {

					int s = questionId;
					if(BoardQuestionsController.checkQuestionAnswer(s,getSelectedAnswerIndex()))
					{
			
						DifficultyLevel diff=BoardQuestionsController.Diffeculty(s);
						if(diff==	DifficultyLevel .EASY) {
						notifyTrueAnswer("You earn 100 extra points :)\nWell done!");
						}
						else {   if(diff==	DifficultyLevel .HARD)
							{
								notifyTrueAnswer("You earn 500 extra points :)\nWell done!");
							}
							else
							{
								notifyTrueAnswer("You earn 200 extra points :)\nWell done!");
							}
					}}
					else {
						DifficultyLevel diff=BoardQuestionsController.Diffeculty(s);
						if(diff==	DifficultyLevel .EASY) {
							notifyFalseAnswer("You lost 250  points :(\nGood luck next time");
						}
						else {	if(diff==	DifficultyLevel .HARD)
							{
								notifyFalseAnswer("You lost 50 points :(\nGood luck next time");
							}
						else 
							{
								notifyFalseAnswer("You lost 100 points :(\nGood luck next time");
							}
						}
					}
					BoardController.getInstance().refreshScoreInBoardGUI();

					GameController.getInstance().switchTurn();
					BoardController.getInstance().setAnsweringQuestion(false);
					DisplayController.boardGUI.setPlayerScore(turnColor,BoardController.getInstance().getPlayerScore(turnColor));
					if(GameController.getInstance().isGameRunning()) {
						PrimaryColor newColor = BoardController.getInstance().getPlayerTurn();
						if(newColor != turnColor) {
							DisplayController.boardGUI.setNewTurn(BoardController.getInstance().getPlayerTurn());
							DisplayController.boardGUI.getTurnTimer().resetColors();
						}
					}
					primary.close();
				}

			}
		});

		GridPane grid = new GridPane();
		grid.setId("Answers");
		grid.setLayoutX(28.0);
		grid.setLayoutY(141.0);
		grid.setPrefWidth(681.0);
		mainAnchor.getChildren().add(grid);
		grid.getColumnConstraints().clear();
		grid.getRowConstraints().clear();
		grid.getColumnConstraints().add(new ColumnConstraints(100));
		loadAnswers(answers);
	}

	/**
	 * loads question difficulty label
	 * @param diff
	 */
	public void loadDifficultyLabel(DifficultyLevel diff) {
		Label lbl = new Label();
		lbl.setLayoutX(150.0);
		lbl.setLayoutY(26.0);
		lbl.setPrefHeight(25.0);
		lbl.setPrefWidth(131.0);
		lbl.setFont(new Font("System Bold", 16.0));

		ImageView img = new ImageView();
		img.setFitHeight(39.0);
		img.setFitWidth(36.0);
		img.setLayoutX(107.0);
		img.setLayoutY(21.0);
		img.setPickOnBounds(true);
		img.setPreserveRatio(true);

		switch(diff) {
		case EASY:{

			img.setImage(new Image(getClass().getResource("pictures/easy_question.png").toString()));
			lbl.setText("Easy Question");
			lbl.setTextFill(Color.GREEN);
			break;
		}
		case MEDIOCRE:{

			img.setImage(new Image(getClass().getResource("pictures/intermediate_question.png").toString()));
			lbl.setText("Medicore Question");
			lbl.setTextFill(Color.ORANGE);
			break;
		}
		case HARD:{

			img.setImage(new Image(getClass().getResource("pictures/hard_question.png").toString()));
			lbl.setText("Hard Question");
			lbl.setTextFill(Color.RED);
			break;
		}
		}
		
		mainAnchor.getChildren().add(img);
		mainAnchor.getChildren().add(lbl);
	}

	/**
	 * Pop up notification for true answer
	 * @param info
	 */
	public void notifyTrueAnswer(String info) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Correct Answer");
		alert.setHeaderText(info);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() != null){
			GameController.getInstance().unpauseGame();
		} 	
	}
	/**
	 * Pop up notification for false answer
	 * @param info
	 */
	public void notifyFalseAnswer(String info) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wrong Answer");
		alert.setHeaderText(info);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() != null){
			GameController.getInstance().unpauseGame();
		} 
	}
	/**
	 * load answers
	 * @param answers
	 */
	public void loadAnswers(HashMap<Integer, String> answers) {
		GridPane grid = (GridPane) mainAnchor.lookup("#Answers");
		group = new ToggleGroup();

		for(int i = 1 ; i <= answers.keySet().size() ; i++) {
			grid.getRowConstraints().add(new RowConstraints(65.0));
			Pane pane = new Pane();
			pane.setPrefHeight(200.0);
			pane.setPrefWidth(200.0);
			grid.add(pane, 0, i - 1);

			RadioButton rb = new RadioButton();
			rb.setId(String.valueOf(i));
			rb.setLayoutX(23.0);
			rb.setLayoutY(20.0);
			rb.setMnemonicParsing(false);
			rb.setFont(new Font(16.0));
			rb.setToggleGroup(group);
			pane.getChildren().add(rb);

			Label lbl = new Label(answers.get(i));
			lbl.setLayoutX(74.0);
			lbl.setLayoutY(2.0);
			lbl.setPrefHeight(56.0);
			lbl.setPrefWidth(589.0);
			pane.getChildren().add(lbl);

			Separator sp = new Separator();
			sp.setPrefHeight(0.0);
			sp.setPrefWidth(604.0);
			sp.setLayoutY(1);
			pane.getChildren().add(sp);

		}
	}


	/**
	 * 
	 * @return index of the selected answer, if not found returns -1
	 */
	public int getSelectedAnswerIndex() {
		RadioButton selected = (RadioButton) group.getSelectedToggle();
		if(selected != null)
			return Integer.valueOf(selected.getId());
		return -1;
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
