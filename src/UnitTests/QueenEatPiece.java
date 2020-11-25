package UnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Model.Game;
import Model.Location;
import Model.Piece;
import Model.Player;
import Model.Queen;
import Model.Soldier;
import Utils.Directions;
import Utils.PrimaryColor;

public class QueenEatPiece {

	static final int ROW_LOCATION = 7;
	static final char COLUMN_LOCATION = 'C';
	static final Directions DIR = Directions.UP_LEFT;

	
	@Test
	public void test() {
		Player[] players = new Player[] {new Player(),new Player()};
		ArrayList<Piece> pieces = new ArrayList<>();
		Piece queen = new Queen(1, PrimaryColor.WHITE, new Location(1,'A'));
		Piece soldier = new Soldier(2, PrimaryColor.BLACK, new Location(6,'F'));
		Piece soldier2 = new Soldier(3, PrimaryColor.BLACK, new Location(5,'E'));
		
		pieces.add(queen);
		pieces.add(soldier);
		pieces.add(soldier2);

		try {
			Game.getInstance().startGame(players, pieces);
			Game.getInstance().getBoard().printBoard();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Queen queen2 = (Queen) Game.getInstance().getBoard().getTilesMap().get(queen.getLocation().getRow()).get(queen.getLocation().getColumn() - 'A').getPiece();
		Piece p = queen2.getEdiblePieceByDirection(Game.getInstance().getBoard().getTilesMap().get(ROW_LOCATION).get(COLUMN_LOCATION - 'A').getLocation(),DIR);
		assertEquals("F6 To be Eaten", "F6",p.getLocation().getColumn() + "" + p.getLocation().getRow());

	}

}
