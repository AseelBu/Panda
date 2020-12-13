package Controller;

import View.MainscreenGUI;
import javafx.application.Application;

public class Main {
	
	public static DisplayController displayController;
	
	public static void main(String[] args) {		
		QuestionMgmtController.getInstance().LoadQuestions();
		ScoreBoardController.getInstance().loadHistory();
		
		displayController = DisplayController.getInstance();
        Application.launch(MainscreenGUI.class, args);
	}
}