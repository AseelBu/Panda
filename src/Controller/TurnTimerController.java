package Controller;

import Model.Board;
import Model.Game;
import Model.Tile;
import Utils.SeconderyTileColor;
import View.BoardGUI;
import javafx.application.Platform;

public class TurnTimerController extends Thread{


	private BoardGUI boardGUI=DisplayController.boardGUI;
	boolean addedGreen ;
	boolean addedOrange;

	public void run() 
	{ 

		addedGreen = false;
		addedOrange = false;
		try
		{
			while(Game.getInstance().isGameRunning()) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {

						int seconds = (int) Game.getInstance().getTurn().getTimer().getSeconds();
						DisplayController.boardGUI.updateTurnTimer(seconds);

						//TODO add boolean variable to declare if the special tiles already added
						if(!addedOrange && seconds >= 10) {
							//TODO Orange Tile's feature to be added 
							boardGUI.removeAllColoredTiles();
							//add to board model
							Board.getInstance().addOrangeTiles();
							//add to board Gui
							Board.getInstance().updateColoredTileListAfterOrange();
							BoardController.getInstance().loadTilesColors();
							
							System.out.println("adding orange tile");

							addedOrange = true;
							//TODO Orange Tile's feature to be added 
						
					}else if(!addedGreen && seconds >= 5) {
						//TODO Green Tile's feature to be added
						//add to board model
						Tile addedTile = Board.getInstance().AddGreenTile();
						if(addedTile!=null) {
							//add to board Gui
							boardGUI.addColoredTileToBoard(addedTile.getLocation().getRow(), addedTile.getLocation().getColumn(), SeconderyTileColor.GREEN);
							System.out.println("adding greeen tile");
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
public void resetColors() {
	addedOrange = false;
	addedGreen = false;

}
}
