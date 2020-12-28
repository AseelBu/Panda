package Controller;

import Model.Game;
import Model.Timer;
import javafx.application.Platform;

public class QuestionTimerController  extends Thread{

	private Timer timer;
	
	public void run() 
	{
		try
		{
			initiateTimer();
			while(Game.getInstance().isGameRunning() && ((int) countTimeSeconds()) > 0) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						DisplayController.questions.setTimerSeconds((int) countTimeSeconds());
					}
				});
				TurnTimerController.sleep(1000);
			}
		    Platform.runLater(() -> {
				DisplayController.questions.setTimerSeconds((int) countTimeSeconds());
		    	DisplayController.questions.outOfTime();	
		    });
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println ("Exception is caught"); 
		} 
	}
	
	private void initiateTimer() {
		timer = new Timer();
		timer.startTimer();
	}
	
	private double countTimeSeconds() {
		return 30 - timer.getSeconds();
	}
}
