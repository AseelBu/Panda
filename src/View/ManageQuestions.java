package View;


import java.util.ArrayList;
import java.util.Arrays;

import Controller.QuestionMgmtController;
import Model.Answer;
import Model.Question;
import Model.SysData;
import Utils.DifficultyLevel;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ManageQuestions extends Application {
	
    private AnchorPane mainAnchor;
	private static Stage primary;
	
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		mainAnchor = FXMLLoader.load(getClass().getResource("/View/ManageQuestions.fxml"));
		Scene scene = new Scene(mainAnchor);
		scene.getStylesheets().add(getClass().getResource("/View/manage.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hamka");
		primaryStage.setResizable(false);
//		primaryStage.initStyle(StageStyle.UNDECORATED);  is Used to lock windows, wont be able to move the window
//		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("logo1.png"))); add logo
		primaryStage.show();
		
		
		loadDesign();
		
	}

	public Stage getPrimary() {
		if(primary == null) {
			primary = new Stage();
		}
		return primary;
	}
	
	private void loadDesign(){
		
		mainAnchor.getChildren().clear();
		
		AnchorPane background = new AnchorPane();
		background.setId("background");
		AnchorPane.setBottomAnchor(background, 0.0);
		AnchorPane.setLeftAnchor(background, 0.0);
		AnchorPane.setRightAnchor(background, 0.0);
		AnchorPane.setTopAnchor(background, 0.0);
		mainAnchor.getChildren().add(background);
		
		ScrollPane listScroll = new ScrollPane();
		listScroll.setPrefWidth(800);
		listScroll.setPrefHeight(230);
		listScroll.setLayoutX(125);
		listScroll.setLayoutY(30);
		
		ListView<String> questionList = new ListView<String>();
		ObservableList<String> data = FXCollections.observableArrayList();
		
		for(Question q : QuestionMgmtController.getInstance().getQuestions()) {
			
			data.add(q.getId()+ "." + q.getContent());
			
		}
		
		questionList.setPrefHeight(230);
		questionList.setPrefWidth(800);
		
		questionList.getItems().addAll(data);
		listScroll.setContent(questionList);
		mainAnchor.getChildren().add(listScroll);
		
		Label question = new Label("Question Content :");
		question.setLayoutX(125);
		question.setLayoutY(280);
		question.setFont(new Font("verdana", 18));
		mainAnchor.getChildren().add(question);
		
		TextArea questionContent = new TextArea();
		questionContent.setLayoutX(125);
		questionContent.setLayoutY(315);
		questionContent.setPrefHeight(40);
		questionContent.setPrefWidth(515);
		mainAnchor.getChildren().add(questionContent);
		
		Label answer1 = new Label("Answer 1 :");
		answer1.setLayoutX(125);
		answer1.setLayoutY(370);
		answer1.setFont(new Font("verdana", 14));
		mainAnchor.getChildren().add(answer1);
		
		Label answer2 = new Label("Answer 2 :");
		answer2.setLayoutX(125);
		answer2.setLayoutY(410);
		answer2.setFont(new Font("verdana", 14));
		mainAnchor.getChildren().add(answer2);
		
		Label answer3 = new Label("Answer 3 :");
		answer3.setLayoutX(125);
		answer3.setLayoutY(450);
		answer3.setFont(new Font("verdana", 14));
		mainAnchor.getChildren().add(answer3);
		
		Label answer4 = new Label("Answer 4 :");
		answer4.setLayoutX(125);
		answer4.setLayoutY(490);
		answer4.setFont(new Font("verdana", 14));
		mainAnchor.getChildren().add(answer4);
		
		Label difficulty = new Label("Difficulty :");
		difficulty.setLayoutX(125);
		difficulty.setLayoutY(530);
		difficulty.setFont(new Font("verdana", 14));
		mainAnchor.getChildren().add(difficulty);
		
		CheckBox ans1 = new CheckBox();
		ans1.setLayoutX(210);
		ans1.setLayoutY(371);
		mainAnchor.getChildren().add(ans1);
		
		CheckBox ans2 = new CheckBox();
		ans2.setLayoutX(210);
		ans2.setLayoutY(411);
		mainAnchor.getChildren().add(ans2);
		
		CheckBox ans3 = new CheckBox();
		ans3.setLayoutX(210);
		ans3.setLayoutY(451);
		mainAnchor.getChildren().add(ans3);
		
		CheckBox ans4 = new CheckBox();
		ans4.setLayoutX(210);
		ans4.setLayoutY(491);
		mainAnchor.getChildren().add(ans4);
		
		String diffs[] = 
            { "EASY", "INTERMEDIATE", "HARD"
                             }; 
		
		ComboBox<String> diff_selector = new ComboBox<String>();
		diff_selector.getItems().addAll(Arrays.asList(diffs));
		diff_selector.setLayoutX(210);
		diff_selector.setLayoutY(528);
		mainAnchor.getChildren().add(diff_selector);
		
		TextField ta1 = new TextField();
		ta1.setLayoutX(240);
		ta1.setLayoutY(370);
		ta1.setPrefHeight(3);
		ta1.setPrefWidth(400);
			
		mainAnchor.getChildren().add(ta1);
		
		TextField ta2 = new TextField();
		ta2.setLayoutX(240);
		ta2.setLayoutY(410);
		ta2.setPrefHeight(3);
		ta2.setPrefWidth(400);
			
		mainAnchor.getChildren().add(ta2);
		
		TextField ta3 = new TextField();
		ta3.setLayoutX(240);
		ta3.setLayoutY(450);
		ta3.setPrefHeight(3);
		ta3.setPrefWidth(400);
			
		mainAnchor.getChildren().add(ta3);
		
		TextField ta4 = new TextField();
		ta4.setLayoutX(240);
		ta4.setLayoutY(490);
		ta4.setPrefHeight(3);
		ta4.setPrefWidth(400);
			
		mainAnchor.getChildren().add(ta4);
		
		Button btnCancel = new Button("Cancel");
		Button btnSaveOrAdd = new Button("Add");
		Button btnDelete = new Button("Delete");
		
		btnDelete.setVisible(false);
		
		btnCancel.setLayoutX(260);
		btnCancel.setLayoutY(570);
		
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				
				btnSaveOrAdd.setText("Add");
	    		btnSaveOrAdd.setDisable(false);
	    		btnDelete.setVisible(false);
	    		
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
	    		
	    		
	    		questionList.getSelectionModel().clearSelection();
				
				
			}
			
			
			
			
		});
		
		btnSaveOrAdd.setLayoutX(350);
		btnSaveOrAdd.setLayoutY(570);
		
		btnDelete.setLayoutX(430);
		btnDelete.setLayoutY(570);
		
		mainAnchor.getChildren().add(btnCancel);
		mainAnchor.getChildren().add(btnSaveOrAdd);
		mainAnchor.getChildren().add(btnDelete);
		
		questionList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        
		    	if(newValue == null) {
		    		btnSaveOrAdd.setText("Add");
		    		btnDelete.setVisible(false);
		    		ta1.setText("");
		    		ta2.setText("");
		    		ta3.setText("");
		    		ta4.setText("");
		    		questionContent.setText("");
		    		diff_selector.getSelectionModel().clearSelection();
		    	}else if(!newValue.equals(oldValue)){
		    		
		    		System.out.print("here");
		    		
		    		btnSaveOrAdd.setText("Save");
		    		btnSaveOrAdd.setDisable(true);
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
		    		ArrayList<String> data = new ArrayList<String>(Arrays.asList(newValue.split("\\.")));
		    		
		    		int questionID = Integer.valueOf(data.get(0));
		    		String Content = data.get(1);
		    		
		    		Question q = QuestionMgmtController.getInstance().getSysData().getQuesById(questionID);
		    		
		    		questionContent.setText(q.getContent());
		    		
		    		int count = 0;		    		
		    		
		    		System.out.println(q.getAnswers().size());
		    		
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
							
							btnSaveOrAdd.setText("Add");
				    		btnSaveOrAdd.setDisable(false);
				    		btnDelete.setVisible(false);
				    		
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
				    		
				    		QuestionMgmtController.getInstance().removeQuestions(Integer.valueOf(questionList.getSelectionModel().getSelectedItems().get(0).split("\\.")[0]));
				    		QuestionMgmtController.getInstance().WriteQuestions();
				    		questionList.getItems().remove(questionList.getSelectionModel().getSelectedIndex());
				    		
				    		
							
						}
		    								    			
		    		});
		    		
		    		btnSaveOrAdd.setOnAction(new EventHandler<ActionEvent>() {
		    			
		    			
		    			@Override
						public void handle(ActionEvent event) {
		    				
		    			if(((Button) event.getSource()).getText().equals("Save")) {
		    				
		    				
		    				
		    				
		    			}
		    				
		    			}
		    			
		    			
		    		});


		    	}
		    }
		
		
		
		
		});
		

		
		
	}
	

	

}
