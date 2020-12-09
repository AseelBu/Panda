package Controller;

import Model.Board;
import Model.Game;
import Model.Tile;
import Utils.SeconderyTileColor;
import View.BoardGUI;
import javafx.application.Platform;

public class TurnTimerController extends Thread{

	private BoardGUI boardGUI=DisplayController.boardGUI;
	boolean addedGreen =false;

	public void run() 
	{ 
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
							//TODO Orange Tile's feature to be added 

						}else if(!addedGreen && seconds >= 30 ) {
							//TODO Green Tile's feature to be added
							//add to board model
							Tile addedTile = Board.getInstance().AddGreenTile();
							if(addedTile!=null) {
							//add to board Gui
							boardGUI.addColoredTileToBoard(addedTile.getLocation().getRow(), addedTile.getLocation().getColumn(), SeconderyTileColor.GREEN);
							}		
							addedGreen=true;
						}
					}
				});
				TurnTimerController.sleep(1000);
			}
			
		} 
		catch (Exception e) 
		{
			System.out.println ("Exception is caught"); 
		} 
	}
}
