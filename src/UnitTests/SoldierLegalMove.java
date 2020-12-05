package UnitTests;
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import Model.Game;
import Model.Location;
import Model.Piece;
import Model.Player;

import Model.Soldier;
import Utils.Directions;
import Utils.PrimaryColor;

public class SoldierLegalMove {

	static final int ROW_LOCATION = 8;
	static final char COLUMN_LOCATION = 'H';
	static final Directions DIR = Directions.UP_RIGHT;

	@Test
	public void test() {
		Player player1 = Player.getInstance(0);
		Player player2 = Player.getInstance(1);
		player1.setNickname("Jack");
		player2.setNickname("Max");
		Player[] players = new Player[] {
								player1,
								player2
								};
		ArrayList<Piece> pieces = new ArrayList<>();
		Piece mainSoldier= new Soldier(1, PrimaryColor.WHITE, new Location(6,'F'));
		Piece soldier = new Soldier(2, PrimaryColor.BLACK, new Location(4,'H'));
		Piece soldier2 = new Soldier(3, PrimaryColor.BLACK, new Location(7,'G'));
		
		pieces.add(mainSoldier);
		pieces.add(soldier);
		pieces.add(soldier2);

		try {
			Game.getInstance().startGame(players, pieces, 'W');
			Game.getInstance().getBoard().printBoard();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Soldier soldierr = (Soldier) Game.getInstance().getBoard().getTilesMap().get(mainSoldier.getLocation().getRow()).get(mainSoldier.getLocation().getColumn() - 'A').getPiece();
//		queen2.move(Game.getInstance().getBoard().getTilesMap().get(ROW_LOCATION).get(COLUMN_LOCATION - 'A'),DIR);
//		Game.getInstance().getBoard().printBoard();
		Soldier soldierr = (Soldier) Game.getInstance().getBoard().getTilesMap().get(mainSoldier.getLocation().getRow()).get(mainSoldier.getLocation().getColumn() - 'A').getPiece();
		//System.out.println("ssssssssssssss  dddd"+soldierr);
			assertTrue("Yeah You Can Move :) ", soldierr.isMoveLegal(Game.getInstance().getBoard().getTilesMap().get(ROW_LOCATION).get(COLUMN_LOCATION - 'A').getLocation()));
	

	}

}

