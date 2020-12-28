
import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Exceptions.LocationException;
import Model.Board;
import Model.Game;
import Model.Location;
import Model.Piece;
import Model.Player;

import Model.Soldier;
import Utils.Directions;
import Utils.PrimaryColor;

/**
 * Test ID: 1
 * @author maryam
 *
 */
public class SoldierLegalMoveTest {

	static final int ROW_LOCATION = 8;
	static final char COLUMN_LOCATION = 'H';
	static final Directions DIR = Directions.UP_RIGHT;

	
	@Before
	public void initiatePlayersNames() {
		Player.getInstance(0).setNickname("Tester1");
        Player.getInstance(1).setNickname("Tester2");
	}
	
	@After
	public void endGame() {
		Game.getInstance().finishGame();
		Game.destruct();
		Board.destruct();
		Player.destruct();
	}
	
	@Test
	public void soldierLegalMove() throws LocationException {
		Player[] players = {Player.getInstance(0),Player.getInstance(1)};
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
			e.printStackTrace();
		}
		//Soldier soldierr = (Soldier) Game.getInstance().getBoard().getTilesMap().get(mainSoldier.getLocation().getRow()).get(mainSoldier.getLocation().getColumn() - 'A').getPiece();
//		queen2.move(Game.getInstance().getBoard().getTilesMap().get(ROW_LOCATION).get(COLUMN_LOCATION - 'A'),DIR);
//		Game.getInstance().getBoard().printBoard();
		Soldier soldierr = (Soldier) Game.getInstance().getBoard().getTilesMap().get(mainSoldier.getLocation().getRow()).get(mainSoldier.getLocation().getColumn() - 'A').getPiece();
		
			assertTrue("Yeah You Can Move :) ", soldierr.isMoveLegal(Game.getInstance().getBoard().getTilesMap().get(ROW_LOCATION).get(COLUMN_LOCATION - 'A').getLocation()));
	

	}

}

