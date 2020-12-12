package Controller;

import Model.Game;
import javafx.application.Platform;

public class GameTimerController extends Thread{
	
	public void run() 
    { 
        try
        { 
            while(Game.getInstance().isGameRunning()) {
            	Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                    	DisplayController.boardGUI.updateFullTimer((int) Game.getInstance().getGameTime());
                    }
                  });
            	this.sleep(1000);
            }
  
        } 
        catch (Exception e) 
        { 
            // Throwing an exception 
            System.out.println ("Exception is caught");
        } 
    }
}
