package UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Exceptions.IllegalMoveException;
import Exceptions.LocationException;
import Model.Board;
import Model.Game;
import Model.Location;
import Model.Piece;
import Model.Player;
import Model.Queen;
import Model.Soldier;
import Utils.Directions;
import Utils.PrimaryColor;

public class QueenMovement {
	
	
	
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
	public void queenBlocked() throws LocationException {
		System.out.println("Start Test 3\n");
		
		Player[] players = {Player.getInstance(0),Player.getInstance(1)};
		ArrayList<Piece> pieces = new ArrayList<>();
		Piece queen = new Queen(1, PrimaryColor.WHITE, new Location(2,'B'));
		Piece soldier = new Soldier(2, PrimaryColor.WHITE, new Location(6,'F'));
		Piece soldier2 = new Soldier(3, PrimaryColor.BLACK, new Location(5,'E'));
		
		pieces.add(queen);
		pieces.add(soldier);
		pieces.add(soldier2);

		try {
			Game.getInstance().startGame(players, pieces, 'W');
			Game.getInstance().getBoard().printBoard();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Queen queen2 = (Queen) Game.getInstance().getBoard().getTilesMap().get(queen.getLocation().getRow()).get(queen.getLocation().getColumn() - 'A').getPiece();
		boolean isBlocked = queen2.isPieceBlockedByDirection(Game.getInstance().getBoard().getTilesMap().get(7).get('E' - 'A').getLocation(),Directions.UP_LEFT);
		System.out.println("\nEnd Test 3");
		assertTrue("Piece is blocked", isBlocked);
	}
	
	@Test
	public void queenEat() throws LocationException {
		System.out.println("Start Test 2\n");
		
		Player[] players = {Player.getInstance(0),Player.getInstance(1)};
        
		ArrayList<Piece> pieces = new ArrayList<>();
		Piece queen = new Queen(1, PrimaryColor.WHITE, new Location(1,'A'));
		Piece soldier = new Soldier(2, PrimaryColor.BLACK, new Location(6,'F'));
		Piece soldier2 = new Soldier(3, PrimaryColor.BLACK, new Location(5,'E'));
		
		pieces.add(queen);
		pieces.add(soldier);
		pieces.add(soldier2);

		try {
			Game.getInstance().startGame(players, pieces, 'W');
			Game.getInstance().getBoard().printBoard();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Queen queen2 = (Queen) Game.getInstance().getBoard().getTilesMap().get(queen.getLocation().getRow()).get(queen.getLocation().getColumn() - 'A').getPiece();
		
		Piece p = queen2.getEdiblePieceByDirection(Game.getInstance().getBoard().getTilesMap().get(7).get('C' - 'A').getLocation(),Directions.UP_LEFT);
		System.out.println("\nEnd Test 2");

		assertEquals("F6 To be Eaten", "E5",p.getLocation().getColumn() + "" + p.getLocation().getRow());

	}
	
	@Test
	public void legalDestination() throws LocationException {
		System.out.println("Start Test 1\n");
		
		Player[] players = {Player.getInstance(0),Player.getInstance(1)};
        
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
			e.printStackTrace();
		}
		Queen queen2 = (Queen) Game.getInstance().getBoard().getTilesMap().get(queen.getLocation().getRow()).get(queen.getLocation().getColumn() - 'A').getPiece();
		boolean canMove = false;
		try {
			canMove = queen2.isMoveLegalByDirection(Game.getInstance().getBoard().getTilesMap().get(8).get('D' - 'A').getLocation(),Directions.UP_LEFT);
		} catch (IllegalMoveException e1) {
			e1.printStackTrace();
		}
		System.out.println("\nEnd Test 1");

		assertTrue("Can Move", canMove);

	}
	
}
