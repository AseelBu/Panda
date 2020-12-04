package UnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Exceptions.IllegalMoveException;
import Model.Game;
import Model.Location;
import Model.Piece;
import Model.Player;
import Model.Queen;
import Model.Soldier;
import Utils.Directions;
import Utils.PrimaryColor;

public class QueenLegalMove {

	static final int ROW_LOCATION = 8;
	static final char COLUMN_LOCATION = 'D';
	static final Directions DIR = Directions.UP_LEFT;

	
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
		Piece queen = new Queen(1, PrimaryColor.WHITE, new Location(2,'B'));
		Piece soldier = new Soldier(2, PrimaryColor.BLACK, new Location(5,'G'));
		Piece soldier2 = new Soldier(3, PrimaryColor.BLACK, new Location(7,'G'));
		
		pieces.add(queen);
		pieces.add(soldier);
		pieces.add(soldier2);

		try {
			Game.getInstance().startGame(players, pieces, 'W');
			Game.getInstance().getBoard().printBoard();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Queen queen2 = (Queen) Game.getInstance().getBoard().getTilesMap().get(queen.getLocation().getRow()).get(queen.getLocation().getColumn() - 'A').getPiece();
//		queen2.move(Game.getInstance().getBoard().getTilesMap().get(ROW_LOCATION).get(COLUMN_LOCATION - 'A'),DIR);
//		Game.getInstance().getBoard().printBoard();

		try {
			assertTrue("Can Move", queen2.isMoveLegalByDirection(Game.getInstance().getBoard().getTilesMap().get(ROW_LOCATION).get(COLUMN_LOCATION - 'A').getLocation(),DIR));
		} catch (IllegalMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
