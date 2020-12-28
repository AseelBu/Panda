package View;


import java.util.ArrayList;
import java.util.Arrays;

import Controller.DisplayController;
import Controller.QuestionMgmtController;
import Model.Answer;
import Model.Question;
import Model.SysData;
import Utils.DifficultyLevel;
import Utils.QuestionListCell;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;

public class ManageQuestions extends Application {
	
    private AnchorPane mainAnchor;
	private static Stage primary;
	private ListView<Question> questionList;
	private ScrollPane listScroll;
	private ChangeListener<Question> listener = null;
	
	/**
	 * Starts the page
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		mainAnchor = FXMLLoader.load(getClass().getResource("/View/ManageQuestions.fxml"));
		Scene scene = new Scene(mainAnchor);
		scene.getStylesheets().add(getClass().getResource("/View/manage.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hamka");
		primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				
				DisplayController.getInstance().closeManageQuestions();
				QuestionMgmtController.getInstance().WriteQuestions();
				QuestionMgmtController.getInstance().LoadQuestions();
				DisplayController.getInstance().showMainScreen();
				
			}
			
		});
		primary = primaryStage;
		primary.show();
		
		
		loadDesign();
		
	}

	public Stage getPrimary() {
		if(primary == null) {
			primary = new Stage();
		}
		return primary;
	}
	
	/**
	 * load design of page
	 */
	private void loadDesign(){
		
		mainAnchor.getChildren().clear();
		
		Label l = new Label("Question Management");
		l.setFont(new Font("verdana",16.0));
		l.setStyle("-fx-font-weight: bold;");
		l.setLayoutX(420);
		l.setLayoutY(10);
		
		Label error = new Label("This is error");
		error.setStyle("-fx-text-color: white;");
		error.setFont(new Font("verdana",16.0));
		error.setStyle("-fx-font-weight: bold;");
		error.setLayoutX(125);
		error.setLayoutY(490);
		error.setTextFill(Color.web("#FF0000"));
		error.setVisible(false);
		mainAnchor.getChildren().add(error);
		
		
		mainAnchor.getChildren().add(l);
		
		AnchorPane background = new AnchorPane();
		background.setId("background");
		AnchorPane.setBottomAnchor(background, 0.0);
		AnchorPane.setLeftAnchor(background, 0.0);
		AnchorPane.setRightAnchor(background, 0.0);
		AnchorPane.setTopAnchor(background, 0.0);
		mainAnchor.getChildren().add(background);
		
		listScroll = new ScrollPane();
		listScroll.setPrefWidth(780);
		listScroll.setPrefHeight(230);
		listScroll.setLayoutX(125);
		listScroll.setLayoutY(45);
		
		Rectangle green = new Rectangle(10,150,25,5);
		green.setFill(Color.GREEN);
		Label greenl = new Label("EASY");
		greenl.setFont(new Font("verdana",12.0));
		greenl.setStyle("-fx-font-weight: bold;");
		greenl.setLayoutX(5);
		greenl.setLayoutY(160);
		mainAnchor.getChildren().add(greenl);
		mainAnchor.getChildren().add(green);
		
		
		Rectangle red = new Rectangle(10,180,25,5);
		red.setFill(Color.RED);
		Label red1 = new Label("HARD");
		red1.setFont(new Font("verdana",12.0));
		red1.setStyle("-fx-font-weight: bold;");
		red1.setLayoutX(5);
		red1.setLayoutY(190);
		mainAnchor.getChildren().add(red);
		mainAnchor.getChildren().add(red1);
		
		Rectangle orange = new Rectangle(10,210,25,5);
		orange.setFill(Color.ORANGE);
		Label orange1 = new Label("INTERMEDIATE");
		orange1.setFont(new Font("verdana",12.0));
		orange1.setStyle("-fx-font-weight: bold;");
		orange1.setLayoutX(5);
		orange1.setLayoutY(220);
		mainAnchor.getChildren().add(orange1);

		mainAnchor.getChildren().add(orange);
		
		
		questionList = new ListView<Question>();
		ObservableList<Question> data = FXCollections.observableArrayList();
		
		for(Question q : QuestionMgmtController.getInstance().getQuestions()) {
			
			data.add(q);
			
		}
		
		questionList.setCellFactory(new Callback<ListView<Question>, ListCell<Question>>() {
		    @Override 
		    public ListCell<Question> call(ListView<Question> list) {
		        return new QuestionListCell();
		    }
		});
		
		questionList.setPrefHeight(230);
		questionList.setPrefWidth(780);
		
		questionList.getItems().addAll(data);
		listScroll.setContent(questionList);
		mainAnchor.getChildren().add(listScroll);
		
		Label question = new Label("Question Content :");
		question.setLayoutX(150);
		question.setLayoutY(280);
		question.setFont(new Font("verdana", 18));
		mainAnchor.getChildren().add(question);
		
		TextField questionContent = new TextField();
		questionContent.setLayoutX(335);
		questionContent.setLayoutY(280);
		questionContent.setPrefHeight(25);
		questionContent.setPrefWidth(555);
		mainAnchor.getChildren().add(questionContent);
		
		Label answer1 = new Label("Answer 1 :");
		answer1.setLayoutX(150);
		answer1.setLayoutY(320);
		answer1.setFont(new Font("verdana", 14));
		mainAnchor.getChildren().add(answer1);
		
		Label answer2 = new Label("Answer 2 :");
		answer2.setLayoutX(565);
		answer2.setLayoutY(320);
		answer2.setFont(new Font("verdana", 14));
		mainAnchor.getChildren().add(answer2);
		
		Label answer3 = new Label("Answer 3 :");
		answer3.setLayoutX(150);
		answer3.setLayoutY(380);
		answer3.setFont(new Font("verdana", 14));
		mainAnchor.getChildren().add(answer3);
		
		Label answer4 = new Label("Answer 4 :");
		answer4.setLayoutX(565);
		answer4.setLayoutY(380);
		answer4.setFont(new Font("verdana", 14));
		mainAnchor.getChildren().add(answer4);
		
		Label difficulty = new Label("Difficulty :");
		difficulty.setLayoutX(390);
		difficulty.setLayoutY(450);
		difficulty.setFont(new Font("verdana", 14));
		mainAnchor.getChildren().add(difficulty);
		
		ToggleGroup toggleGroup = new ToggleGroup();
		
		RadioButton ans1 = new RadioButton();
		ans1.setLayoutX(150);
		ans1.setLayoutY(345);
		mainAnchor.getChildren().add(ans1);
		ans1.setToggleGroup(toggleGroup);
		
		RadioButton ans2 = new RadioButton();
		ans2.setLayoutX(565);
		ans2.setLayoutY(345);
		mainAnchor.getChildren().add(ans2);
		ans2.setToggleGroup(toggleGroup);
		
		RadioButton ans3 = new RadioButton();
		ans3.setLayoutX(150);
		ans3.setLayoutY(405);
		mainAnchor.getChildren().add(ans3);
		ans3.setToggleGroup(toggleGroup);
		
		RadioButton ans4 = new RadioButton();
		ans4.setLayoutX(565);
		ans4.setLayoutY(405);
		mainAnchor.getChildren().add(ans4);
		ans4.setToggleGroup(toggleGroup);
		
		String diffs[] = 
            { "EASY", "INTERMEDIATE", "HARD"
                             }; 
		
		ComboBox<String> diff_selector = new ComboBox<String>();
		diff_selector.getItems().addAll(Arrays.asList(diffs));
		diff_selector.setLayoutX(480);
		diff_selector.setLayoutY(447);
		mainAnchor.getChildren().add(diff_selector);
		
		TextField ta1 = new TextField();
		ta1.setLayoutX(175);
		ta1.setLayoutY(342);
		ta1.setPrefHeight(3);
		ta1.setPrefWidth(300);
			
		mainAnchor.getChildren().add(ta1);
		
		TextField ta2 = new TextField();
		ta2.setLayoutX(590);
		ta2.setLayoutY(342);
		ta2.setPrefHeight(3);
		ta2.setPrefWidth(300);
			
		mainAnchor.getChildren().add(ta2);
		
		TextField ta3 = new TextField();
		ta3.setLayoutX(175);
		ta3.setLayoutY(403);
		ta3.setPrefHeight(3);
		ta3.setPrefWidth(300);
			
		mainAnchor.getChildren().add(ta3);
		
		TextField ta4 = new TextField();
		ta4.setLayoutX(590);
		ta4.setLayoutY(403);
		ta4.setPrefHeight(3);
		ta4.setPrefWidth(300);
			
		mainAnchor.getChildren().add(ta4);
		
		Button btnCancel = new Button();
		ImageView cancel = new ImageView(new Image(getClass().getResourceAsStream("/View/pictures/cancel.png")));
		
		btnCancel.setGraphic(cancel);
		btnCancel.setFont(new Font("verdana", 16));
		Button btnSaveOrAdd = new Button();
		btnSaveOrAdd.setId("Add");
		btnSaveOrAdd.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/View/pictures/add.png"))));
		btnSaveOrAdd.setFont(new Font("verdana", 16));
		Button btnDelete = new Button();
		btnDelete.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/View/pictures/delete.png"))));
		btnDelete.setFont(new Font("verdana", 16));
		Button btnNew = new Button("New");
		btnNew.setFont(new Font("verdana", 16));
		
		btnDelete.setVisible(false);
		btnNew.setVisible(false);
		
		btnNew.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				error.setTextFill(Color.web("#FF0000"));
				
				btnSaveOrAdd.setId("Add");
				btnSaveOrAdd.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/View/pictures/add.png"))));
				btnSaveOrAdd.setFont(new Font("verdana", 16));
	    		btnSaveOrAdd.setDisable(false);
	    		btnDelete.setVisible(false);
	    		
	    		ans1.setSelected(false);
	    		ans2.setSelected(false);
	    		ans3.setSelected(false);
	    		ans4.setSelected(false);
	    		
	    		error.setVisible(false);
	    		
	    		ta1.setText("");
	    		ta2.setText("");
	    		ta3.setText("");
	    		ta4.setText("");
	    		
	    		questionContent.setText("");
	    		diff_selector.getSelectionModel().clearSelection();
	    		
	    		btnNew.setVisible(false);
	    		questionList.getSelectionModel().clearSelection();
				
				
			}		
		});
		
		btnNew.setLayoutX(290);
		btnNew.setLayoutY(550);
		
		btnCancel.setLayoutX(360);
		btnCancel.setLayoutY(550);
		
		btnSaveOrAdd.setLayoutX(440);
		btnSaveOrAdd.setLayoutY(550);
		
		btnDelete.setLayoutX(520);
		btnDelete.setLayoutY(550);
		
		mainAnchor.getChildren().add(btnDelete);
		mainAnchor.getChildren().add(btnSaveOrAdd);
		mainAnchor.getChildren().add(btnCancel);
		mainAnchor.getChildren().add(btnNew);
		
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				error.setTextFill(Color.web("#FF0000"));
				
				
	    		btnSaveOrAdd.setDisable(false);
	    		btnDelete.setVisible(false);
	    		
	    		ans1.setSelected(false);
	    		ans2.setSelected(false);
	    		ans3.setSelected(false);
	    		ans4.setSelected(false);
	    		
	    		error.setVisible(false);
	    		
	    		ta1.setText("");
	    		ta2.setText("");
	    		ta3.setText("");
	    		ta4.setText("");
	    		
	    		questionContent.setText("");
	    		diff_selector.getSelectionModel().clearSelection();
	    		
	    		
	    		questionList.getSelectionModel().clearSelection();
				
				
			}
			
			
			
			
		});
		

		
		this.listener = (ObservableValue<? extends Question> observable, Question oldValue, Question newValue) -> {
		    	
		    	if(newValue == null) {
		    		btnSaveOrAdd.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/View/pictures/add.png"))));
		    		btnSaveOrAdd.setId("Add");
		    		btnDelete.setVisible(false);
		    		btnNew.setVisible(false);
		    		ta1.setText("");
		    		ta2.setText("");
		    		ta3.setText("");
		    		ta4.setText("");
		    		error.setVisible(false);
		    		questionContent.setText("");
		    		diff_selector.getSelectionModel().clearSelection();
		    		questionList.getSelectionModel().clearSelection();
		    		
		    	}else if(!newValue.equals(oldValue)){
		    		btnSaveOrAdd.setId("Save");
		    		btnSaveOrAdd.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/View/pictures/save.png"))));
		    		btnNew.setVisible(true);
		    		
		    		error.setVisible(false);
		    		
		    		btnSaveOrAdd.setDisable(false);
		    		btnDelete.setVisible(true);
		    		
		    		ans1.setSelected(false);
		    		ans2.setSelected(false);
		    		ans3.setSelected(false);
		    		ans4.setSelected(false);
		    		
		    		ta1.clear();
		    		ta2.clear();
		    		ta3.clear();
		    		ta4.clear();
		    		
		    		questionContent.setText("");
		    		diff_selector.getSelectionModel().clearSelection();
		    		
		    		System.out.println(newValue);
		    		//ArrayList<String> data = new ArrayList<String>(Arrays.asList(newValue.split("\\.")));
		    		
		    		//int questionID = Integer.valueOf(data.get(0)) - 1;
		    		Question q = QuestionMgmtController.getInstance().getSysData().getQuesById(newValue.getId());
		    		
		    		if(q== null) {
		    			return;
		    		}
		    		
		    		questionContent.setText(q.getContent());
		    		
		    		int count = 0;		    		
		    		
		    		
		    		for(Answer a : q.getAnswers()) {
		    			
		    			if(count == 0) {
		    				if(a.isCorrect()) {
		    					ans1.setSelected(true);
		    				}
		    				ta1.setText(a.getContent());
		    			}
		    			else if(count == 1) {
		    				if(a.isCorrect()) {
		    					ans2.setSelected(true);
		    				}
		    				ta2.setText(a.getContent());
		    			}
		    			else if(count == 2) {
		    				if(a.isCorrect()) {
		    					ans3.setSelected(true);
		    				}
		    				ta3.setText(a.getContent());
		    			}
		    			else if(count == 3) {
		    				if(a.isCorrect()) {
		    					ans4.setSelected(true);
		    				}
		    				ta4.setText(a.getContent());
		    			}
		    			
		    			count++;
		    		}
		    		
		    		if(q.getDifficulty().equals(DifficultyLevel.EASY))
		    			diff_selector.getSelectionModel().select(0);
		    		if(q.getDifficulty().equals(DifficultyLevel.MEDIOCRE))
		    			diff_selector.getSelectionModel().select(1);
		    		if(q.getDifficulty().equals(DifficultyLevel.HARD))
		    			diff_selector.getSelectionModel().select(2);
		    		
		    		
		    		btnDelete.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							
							btnSaveOrAdd.setId("Add");
							btnSaveOrAdd.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/View/pictures/add.png"))));
							btnSaveOrAdd.setFont(new Font("verdana", 16));
				    		btnSaveOrAdd.setDisable(false);
				    		btnDelete.setVisible(false);
				    		btnNew.setVisible(false);
				    		
				    		ans1.setSelected(false);
				    		ans2.setSelected(false);
				    		ans3.setSelected(false);
				    		ans4.setSelected(false);
				    		
				    		ta1.setText("");
				    		ta2.setText("");
				    		ta3.setText("");
				    		ta4.setText("");
				    		
				    		questionContent.setText("");
				    		diff_selector.getSelectionModel().clearSelection();
				    		

				    		
				    		QuestionMgmtController.getInstance().removeQuestions(questionList.getSelectionModel().getSelectedItem().getId());
				    		QuestionMgmtController.getInstance().WriteQuestions();
				    		
				    		questionList = new ListView<Question>();
							questionList.setCellFactory(new Callback<ListView<Question>, ListCell<Question>>() {
							    @Override 
							    public ListCell<Question> call(ListView<Question> list) {
							        return new QuestionListCell();
							    }
							});
							ObservableList<Question> data = FXCollections.observableArrayList();
							
							for(Question q : QuestionMgmtController.getInstance().getQuestions()) {
								
								data.add(q);
								
							}
							questionList.getSelectionModel().selectedItemProperty().addListener(listener);
							questionList.getItems().addAll(data);
							questionList.setPrefHeight(230);
							questionList.setPrefWidth(780);
							listScroll.setContent(questionList);
				    		
				    		error.setVisible(true);
							error.setText("Successfuly Deleted!");
							error.setTextFill(Color.web("#FFFFFF"));
							
							PauseTransition visiblePause = new PauseTransition(
							        Duration.seconds(3)
							);
							visiblePause.setOnFinished(t -> error.setVisible(false)
							         
							);
							visiblePause.play();
				    		
							
				    		
							
						}
		    								    			
		    		});
		    		
		    		btnSaveOrAdd.setOnAction(new EventHandler<ActionEvent>() {
		    			
		    			
		    			@Override
						public void handle(ActionEvent event) {
		    				
		    			if(((Button) event.getSource()).getId().equals("Save")) {
		    				
		    				error.setTextFill(Color.web("#FF0000"));
		    				
		    				ArrayList<Integer> answers = new ArrayList<Integer>();
		    				int count_answers = 0;
		    				
		    				if(ta1.getText().equals("") || ta1.getText() == null) {
		    					
		    				}
		    				else if(ta1.getText().length() != 0) {
		    					count_answers++;
		    					answers.add(1);
		    				}
		    				if(ta2.getText().equals("") || ta2.getText() == null) {
		    					
		    				}
		    				else if(ta2.getText().length() != 0) {
		    					count_answers++;
		    					answers.add(2);
		    				}
		    				if(ta3.getText().equals("") || ta3.getText() == null) {
		    					
		    				}
		    				else if(ta3.getText().length() != 0) {
		    					count_answers++;
		    					answers.add(3);
		    				}
		    				if(ta4.getText().equals("") || ta4.getText() == null) {
		    					
		    				}
		    				else if(ta4.getText().length() != 0) {
		    					count_answers++;
		    					answers.add(4);
		    				}
		    				
		    				// at least two answers
		    				
		    				if(count_answers < 2) {
		    					error.setVisible(true);
		    					error.setText("Fill In At Least Two Answers");
		    					return;
		    				}
		    				
		    				// correct answer 
		    				
		    				int correctAnswer = 0;
		    				if(toggleGroup.getSelectedToggle() == null) {
		    					error.setVisible(true);
		    					error.setText("Select A Correct Answer");
		    					return;
		    				}
		    				if(toggleGroup.getSelectedToggle().equals(ans1)) {
		    					correctAnswer = 1;
		    				}
		    				else if(toggleGroup.getSelectedToggle().equals(ans2)) {
		    					correctAnswer = 2;
		    				}
		    				else if(toggleGroup.getSelectedToggle().equals(ans3)) {
		    					correctAnswer = 3;
		    				}
		    				else if(toggleGroup.getSelectedToggle().equals(ans4)) {
		    					correctAnswer = 4;
		    				}
		    				
		    				// fill in question content
		    				
		    				if(questionContent.getText().length() == 0 ) {
		    					error.setVisible(true);
		    					error.setText("Fill In Question Content");
		    					return;
		    					
		    				}
		    				
		    		
		    				DifficultyLevel dl = null;
		    				
		    				// fill in difficulty 
		    				if(diff_selector.getSelectionModel().getSelectedItem() == null) {
		    					error.setVisible(true);
		    					error.setText("Choose Question Difficulty");
		    					return;
		    				}
		    				
		    				else if(diff_selector.getSelectionModel().getSelectedIndex() == 0) {
		    					dl = DifficultyLevel.EASY;
		    				}
		    				else if(diff_selector.getSelectionModel().getSelectedIndex() == 1) {
		    					dl = DifficultyLevel.MEDIOCRE;
		    				}
		    				else if(diff_selector.getSelectionModel().getSelectedIndex() == 2) {
		    					dl = DifficultyLevel.HARD;
		    				}
		    				
		    				if(dl == null) {
		    					error.setVisible(true);
		    					error.setText("Choose Question Difficulty");
		    					return;
		    				}
		    				
		    				ArrayList<Answer> new_Answers = new ArrayList<Answer>();
		    				
		    				int counter = 1;
		    				for(Integer i : answers) {
		    					
		    					if(i == 1) {
		    						if(correctAnswer == 1)
		    							new_Answers.add(new Answer(counter,ta1.getText(),true));
		    						else
		    							new_Answers.add(new Answer(counter,ta1.getText(),false));
		    					}
		    					else if(i == 2) {
		    						if(correctAnswer == 2)
		    							new_Answers.add(new Answer(counter,ta2.getText(),true));
		    						else
		    							new_Answers.add(new Answer(counter,ta2.getText(),false));
		    					}
		    					else if(i == 3) {
		    						if(correctAnswer == 3)
		    							new_Answers.add(new Answer(counter,ta3.getText(),true));
		    						else
		    							new_Answers.add(new Answer(counter,ta3.getText(),false));
		    					}
		    					else if(i == 4) {
		    						if(correctAnswer == 4)
		    							new_Answers.add(new Answer(counter,ta4.getText(),true));
		    						else
		    							new_Answers.add(new Answer(counter,ta4.getText(),false));
		    					}
		    					
		    					counter++;
		    				}
		    				
		    				Question updated_question = new Question(q.getId(),questionContent.getText(),dl,new_Answers,"panda");
		    				
		    				boolean foundCorrect = false;
		    				
		    				for(Answer a : updated_question.getAnswers()) {
		    					
		    					if(a.isCorrect()) {
		    						foundCorrect = true;
		    					}
		    				}
		    				
		    				// correct Answer Cannot Be Empty
		    				if(foundCorrect == false) {
		    					error.setVisible(true);
		    					error.setText("Choose A Correct Answer That Is Not Blank");
		    					return;
		    					
		    				}
		    				
		    				// Same As Old Question 
		    				
		    				if(updated_question.equals(q)) {
		    					error.setVisible(true);
		    					error.setText("Question Not Changed");
		    					return;
		    				}
		    				else {
		    					QuestionMgmtController.getInstance().getSysData().editQuestion(q.getId(), updated_question);
		    					QuestionMgmtController.getInstance().WriteQuestions();
		    					
		    					questionList = new ListView<Question>();
		    					questionList.setCellFactory(new Callback<ListView<Question>, ListCell<Question>>() {
		    					    @Override 
		    					    public ListCell<Question> call(ListView<Question> list) {
		    					        return new QuestionListCell();
		    					    }
		    					});
		    					ObservableList<Question> data = FXCollections.observableArrayList();
		    					
		    					for(Question q : QuestionMgmtController.getInstance().getQuestions()) {
		    						
		    						data.add(q);
		    						
		    					}
		    					questionList.getSelectionModel().selectedItemProperty().addListener(listener);
		    					questionList.getItems().addAll(data);
		    					questionList.setPrefHeight(230);
		    					questionList.setPrefWidth(780);
		    					listScroll.setContent(questionList);
		    					error.setVisible(true);
		    					error.setText("Successfuly Saved!");
		    					error.setTextFill(Color.web("#4A95FF"));
		    					
		    					PauseTransition visiblePause = new PauseTransition(
		    					        Duration.seconds(3)
		    					);
		    					visiblePause.setOnFinished(t -> error.setVisible(false)
		    					         
		    					);
		    					visiblePause.play();
		    					
		    					
		    				}
		    				
		    				
		    			}
		    			
		    			else if(((Button) event.getSource()).getId().equals("Add")) {
		    				
		    				error.setTextFill(Color.web("#FF0000"));
		    				
		    				ArrayList<Integer> answers = new ArrayList<Integer>();
		    				int count_answers = 0;
		    				
		    				if(ta1.getText().equals("") || ta1.getText() == null) {
		    					
		    				}
		    				else if(ta1.getText().length() != 0) {
		    					count_answers++;
		    					answers.add(1);
		    				}
		    				if(ta2.getText().equals("") || ta2.getText() == null) {
		    					
		    				}
		    				else if(ta2.getText().length() != 0) {
		    					count_answers++;
		    					answers.add(2);
		    				}
		    				if(ta3.getText().equals("") || ta3.getText() == null) {
		    					
		    				}
		    				else if(ta3.getText().length() != 0) {
		    					count_answers++;
		    					answers.add(3);
		    				}
		    				if(ta4.getText().equals("") || ta4.getText() == null) {
		    					
		    				}
		    				else if(ta4.getText().length() != 0) {
		    					count_answers++;
		    					answers.add(4);
		    				}
		    				
		    				// at least two answers
		    				
		    				if(count_answers < 2) {
		    					error.setVisible(true);
		    					error.setText("Fill In At Least Two Answers");
		    					return;
		    				}
		    				
		    				// correct answer 
		    				
		    				int correctAnswer = 0;
		    				// no correct answer marked
		    				if(toggleGroup.getSelectedToggle() == null) {
		    					error.setVisible(true);
		    					error.setText("Select A Correct Answer");
		    					return;
		    				}
		    				else if(toggleGroup.getSelectedToggle().equals(ans1)) {
		    					correctAnswer = 1;
		    				}
		    				else if(toggleGroup.getSelectedToggle().equals(ans2)) {
		    					correctAnswer = 2;
		    				}
		    				else if(toggleGroup.getSelectedToggle().equals(ans3)) {
		    					correctAnswer = 3;
		    				}
		    				else if(toggleGroup.getSelectedToggle().equals(ans4)) {
		    					correctAnswer = 4;
		    				}
		    				
		    				// fill in question content
		    				
		    				if(questionContent.getText().length() == 0 ) {
		    					error.setVisible(true);
		    					error.setText("Fill In Question Content");
		    					return;
		    					
		    				}
		    				
		    				DifficultyLevel dl = null;
		    				
		    				// fill in difficulty 
		    				if(diff_selector.getSelectionModel().getSelectedItem() == null) {
		    					error.setVisible(true);
		    					error.setText("Choose Question Difficulty");
		    					return;
		    				}
		    				
		    				else if(diff_selector.getSelectionModel().getSelectedIndex() == 0) {
		    					dl = DifficultyLevel.EASY;
		    				}
		    				else if(diff_selector.getSelectionModel().getSelectedIndex() == 1) {
		    					dl = DifficultyLevel.MEDIOCRE;
		    				}
		    				else if(diff_selector.getSelectionModel().getSelectedIndex() == 2) {
		    					dl = DifficultyLevel.HARD;
		    				}
		    				
		    				if(dl == null) {
		    					error.setVisible(true);
		    					error.setText("Choose Question Difficulty");
		    					return;
		    				}
		    				
		    				ArrayList<Answer> new_Answers = new ArrayList<Answer>();
		    				
		    				int counter = 1;
		    				for(Integer i : answers) {
		    					
		    					if(i == 1) {
		    						if(correctAnswer == 1)
		    							new_Answers.add(new Answer(counter,ta1.getText(),true));
		    						else
		    							new_Answers.add(new Answer(counter,ta1.getText(),false));
		    					}
		    					else if(i == 2) {
		    						if(correctAnswer == 2)
		    							new_Answers.add(new Answer(counter,ta2.getText(),true));
		    						else
		    							new_Answers.add(new Answer(counter,ta2.getText(),false));
		    					}
		    					else if(i == 3) {
		    						if(correctAnswer == 3)
		    							new_Answers.add(new Answer(counter,ta3.getText(),true));
		    						else
		    							new_Answers.add(new Answer(counter,ta3.getText(),false));
		    					}
		    					else if(i == 4) {
		    						if(correctAnswer == 4)
		    							new_Answers.add(new Answer(counter,ta4.getText(),true));
		    						else
		    							new_Answers.add(new Answer(counter,ta4.getText(),false));
		    					}
		    					
		    					counter++;
		    				}
		    				
		    				Question newQues = new Question(QuestionMgmtController.getInstance().getQuestions().size(),questionContent.getText(),dl,new_Answers,"panda");
		    				
		    				boolean foundCorrect = false;
		    				
		    				
		    				for(Answer a : newQues.getAnswers()) {
		    					
		    					if(a.isCorrect()) {
		    						foundCorrect = true;
		    					}
		    					
		    				}
		    				
		    				// correct Answer Cannot Be Empty
		    				if(foundCorrect == false) {
		    					System.out.println("Here");
		    					error.setVisible(true);
		    					error.setText("Choose A Correct Answer That Is Not Blank");
		    					return;
		    					
		    				}
		    				
		    				// Same As Old Question 
		    				
		    				boolean alreadyExists = false;
		    				for(Question Q : QuestionMgmtController.getInstance().getSysData().getQuestions()) {
		    					
		    					if(Q.equals(newQues)) {
		    						alreadyExists = true;
		    					}
		    				}
		    				
		    				if(alreadyExists) {
		    					error.setVisible(true);
		    					error.setText("Question Already Exists");
		    					return;
		    				}
		    				else {
		    					System.out.println("Success");
		    					QuestionMgmtController.getInstance().getSysData().addQuestion(newQues);
		    					QuestionMgmtController.getInstance().WriteQuestions();
		    					questionList = new ListView<Question>();
		    					questionList.setPrefHeight(230);
		    					questionList.setPrefWidth(780);
		    					questionList.setCellFactory(new Callback<ListView<Question>, ListCell<Question>>() {
		    					    @Override 
		    					    public ListCell<Question> call(ListView<Question> list) {
		    					        return new QuestionListCell();
		    					    }
		    					});
		    					ObservableList<Question> data = FXCollections.observableArrayList();
		    					
		    					for(Question q : QuestionMgmtController.getInstance().getQuestions()) {
		    						
		    						data.add(q);
		    						
		    					}
		    					questionList.getSelectionModel().selectedItemProperty().addListener(listener);
		    					questionList.getItems().addAll(data);
		    					ta1.setText("");
		    		    		ta2.setText("");
		    		    		ta3.setText("");
		    		    		ta4.setText("");
		    		    		questionContent.setText("");
		    		    		diff_selector.getSelectionModel().clearSelection();
		    		    		questionList.getSelectionModel().clearSelection();
		    		    		listScroll.setContent(questionList);
		    		    		error.setVisible(true);
		    					error.setText("Successfuly Added!");
		    					error.setTextFill(Color.web("#4A95FF"));
		    					
		    					PauseTransition visiblePause = new PauseTransition(
		    					        Duration.seconds(3)
		    					);
		    					visiblePause.setOnFinished(t -> error.setVisible(false)
		    					         
		    					);
		    					visiblePause.play();
		    				
		    				
		    				
		    			}
		    			
		    			}
		    				
		    			}
		    			
		    			
		    		});

		    	}
		    	
		};
		
			questionList.getSelectionModel().selectedItemProperty().addListener(listener);
		
		
			btnSaveOrAdd.setOnAction(new EventHandler<ActionEvent>() {
			
			
			@Override
			public void handle(ActionEvent event) {
				
				
				
				error.setTextFill(Color.web("#FF0000"));
				
			if(((Button) event.getSource()).getId()=="Add") {
				
				ArrayList<Integer> answers = new ArrayList<Integer>();
				int count_answers = 0;
				
				if(ta1.getText().equals("") || ta1.getText() == null) {
					
				}
				else if(ta1.getText().length() != 0) {
					count_answers++;
					answers.add(1);
				}
				if(ta2.getText().equals("") || ta2.getText() == null) {
					
				}
				else if(ta2.getText().length() != 0) {
					count_answers++;
					answers.add(2);
				}
				if(ta3.getText().equals("") || ta3.getText() == null) {
					
				}
				else if(ta3.getText().length() != 0) {
					count_answers++;
					answers.add(3);
				}
				if(ta4.getText().equals("") || ta4.getText() == null) {
					
				}
				else if(ta4.getText().length() != 0) {
					count_answers++;
					answers.add(4);
				}
				
				// at least two answers
				
				if(count_answers < 2) {
					error.setVisible(true);
					error.setText("Fill In At Least Two Answers");
					return;
				}
				
				// correct answer 
				
				int correctAnswer = 0;
				// no correct answer marked
				if(toggleGroup.getSelectedToggle() == null) {
					error.setVisible(true);
					error.setText("Select A Correct Answer");
					return;
				}
				else if(toggleGroup.getSelectedToggle().equals(ans1)) {
					correctAnswer = 1;
				}
				else if(toggleGroup.getSelectedToggle().equals(ans2)) {
					correctAnswer = 2;
				}
				else if(toggleGroup.getSelectedToggle().equals(ans3)) {
					correctAnswer = 3;
				}
				else if(toggleGroup.getSelectedToggle().equals(ans4)) {
					correctAnswer = 4;
				}
				
				// fill in question content
				
				if(questionContent.getText().length() == 0 ) {
					error.setVisible(true);
					error.setText("Fill In Question Content");
					return;
					
				}
				
				DifficultyLevel dl = null;
				
				// fill in difficulty 
				if(diff_selector.getSelectionModel().getSelectedItem() == null) {
					error.setVisible(true);
					error.setText("Choose Question Difficulty");
					return;
				}
				
				else if(diff_selector.getSelectionModel().getSelectedIndex() == 0) {
					dl = DifficultyLevel.EASY;
				}
				else if(diff_selector.getSelectionModel().getSelectedIndex() == 1) {
					dl = DifficultyLevel.MEDIOCRE;
				}
				else if(diff_selector.getSelectionModel().getSelectedIndex() == 2) {
					dl = DifficultyLevel.HARD;
				}
				
				if(dl == null) {
					error.setVisible(true);
					error.setText("Choose Question Difficulty");
					return;
				}
				
				ArrayList<Answer> new_Answers = new ArrayList<Answer>();
				
				int counter = 1;
				for(Integer i : answers) {
					
					if(i == 1) {
						if(correctAnswer == 1)
							new_Answers.add(new Answer(counter,ta1.getText(),true));
						else
							new_Answers.add(new Answer(counter,ta1.getText(),false));
					}
					else if(i == 2) {
						if(correctAnswer == 2)
							new_Answers.add(new Answer(counter,ta2.getText(),true));
						else
							new_Answers.add(new Answer(counter,ta2.getText(),false));
					}
					else if(i == 3) {
						if(correctAnswer == 3)
							new_Answers.add(new Answer(counter,ta3.getText(),true));
						else
							new_Answers.add(new Answer(counter,ta3.getText(),false));
					}
					else if(i == 4) {
						if(correctAnswer == 4)
							new_Answers.add(new Answer(counter,ta4.getText(),true));
						else
							new_Answers.add(new Answer(counter,ta4.getText(),false));
					}
					
					counter++;
				}
				
				Question newQues = new Question(QuestionMgmtController.getInstance().getQuestions().size(),questionContent.getText(),dl,new_Answers,"panda");
				
				boolean foundCorrect = false;
				
				for(Answer a : newQues.getAnswers()) {
					
					if(a.isCorrect()) {
						foundCorrect = true;
					}
				}
				
				// correct Answer Cannot Be Empty
				if(foundCorrect == false) {
					error.setVisible(true);
					error.setText("Choose A Correct Answer That Is Not Blank");
					return;
					
				}
				
				// Same As Old Question 
				
				boolean alreadyExists = false;
				for(Question Q : QuestionMgmtController.getInstance().getSysData().getQuestions()) {
					
					if(Q.equals(newQues)) {
						alreadyExists = true;
					}
				}
				
				if(alreadyExists) {
					error.setVisible(true);
					error.setText("Question Already Exists");
					return;
				}
				else {
					System.out.println("Success");
					QuestionMgmtController.getInstance().getSysData().addQuestion(newQues);
					QuestionMgmtController.getInstance().WriteQuestions();
					questionList = new ListView<Question>();
					questionList.setCellFactory(new Callback<ListView<Question>, ListCell<Question>>() {
					    @Override 
					    public ListCell<Question> call(ListView<Question> list) {
					        return new QuestionListCell();
					    }
					});
					ObservableList<Question> data = FXCollections.observableArrayList();
					
					for(Question q : QuestionMgmtController.getInstance().getQuestions()) {
						
						data.add(q);
						
					}
					questionList.getItems().addAll(data);
					questionList.getSelectionModel().selectedItemProperty().addListener(listener);
					questionList.setPrefHeight(230);
					questionList.setPrefWidth(780);
					listScroll.setContent(questionList);
					ta1.setText("");
		    		ta2.setText("");
		    		ta3.setText("");
		    		ta4.setText("");
		    		questionContent.setText("");
		    		diff_selector.getSelectionModel().clearSelection();
		    		questionList.getSelectionModel().clearSelection();
		    		
		    		error.setVisible(true);
					error.setText("Successfuly Added!");
					error.setTextFill(Color.web("#4A95FF"));
					
					PauseTransition visiblePause = new PauseTransition(
					        Duration.seconds(3)
					);
					visiblePause.setOnFinished(t -> error.setVisible(false)
					         
					);
					visiblePause.play();
				
				
				
			}
			
			}
			}
		});
		
		
		
		
		
		
		ImageView backbutton = new ImageView(new Image(getClass().getResourceAsStream("/View/pictures/back.png")));
		backbutton.setLayoutX(10.0);
		backbutton.setLayoutY(5.0);
		backbutton.setFitWidth(45.0);
		backbutton.setFitHeight(45.0);
		backbutton.setPickOnBounds(true);
		backbutton.setPreserveRatio(true);
		backbutton.setCursor(Cursor.HAND);
		backbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			DisplayController.getInstance().closeManageQuestions();
			event.consume();
		});
		
		mainAnchor.getChildren().add(backbutton);
					

	}
	

}
