package Controller;

import Model.Game;
import javafx.application.Platform;

public class TurnTimerController extends Thread{

	boolean addGreen;
	boolean addOrange;
	
	public void run() 
    { 
		addGreen = false;
		addOrange = false;
        try
        {
        	while(Game.getInstance().isGameRunning()) {
            	Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                    	int seconds = (int) Game.getInstance().getTurn().getTimer().getSeconds();
                    	DisplayController.boardGUI.updateTurnTimer(seconds);
                    	
                    	//TODO add boolean variable to declare if the special tiles already added
                    	if(seconds >= 90) {
                    		if(!addOrange) {
                    			addOrange = true;
                        		//TODO Orange Tile's feature to be added 
                    		}
                    	}else if(seconds >= 30) {
                    		if(!addGreen) {
                    			addGreen = true;
                        		//TODO Green Tile's feature to be added
                    		}
                    	}
                    	
                    }
                  });
            	this.sleep(1000);
            }
        } 
        catch (Exception e) 
        {
            System.out.println ("Exception is caught"); 
        } 
    }
	
	public void resetColors() {
		addOrange = false;
		addGreen = false;
	}
}
