package View;

import Controller.QuestionMgmtController;
import Model.Question;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
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
		listScroll.setPrefHeight(335);
		listScroll.setLayoutX(2);
		listScroll.setLayoutY(30);
		
		ListView<String> questionList = new ListView<String>();
		ObservableList<String> data = FXCollections.observableArrayList();
		
		for(Question q : QuestionMgmtController.getInstance().getQuestions()) {
			
			data.add(q.getContent());
			
		}
		
		
		
	}
	
	

}
