package Controller;

import Model.Game;
import Model.GameTimer;
import javafx.application.Platform;

public class QuestionTimerController  extends Thread{

	private GameTimer timer;
	
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
		timer = new GameTimer();
		timer.startTimer();
	}
	
	private double countTimeSeconds() {
		return 30 - timer.getSeconds();
	}
	
	public void pauseTimer() {
		timer.pauseTimer();
	}
	
	public void unpauseTimer() {
		timer.pauseTimer();
	}
}
