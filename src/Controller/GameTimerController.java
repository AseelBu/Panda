package Controller;

import Model.Game;
import javafx.application.Platform;

/**
 * Thread for game timer visualization
 *
 */
public class GameTimerController extends Thread{
	
	@SuppressWarnings("static-access")
	public void run() 
    { 
        try
        { 
            while(Game.getInstance().isGameRunning()) {
            	Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                    	if(Game.getInstance().getTimer().getPauseTime() != -1)
                    		return;
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
