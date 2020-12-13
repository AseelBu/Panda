/**
 * 
 */


import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Controller.BoardController;
import Model.Board;
import Model.Game;
import Model.Location;
import Model.Piece;
import Model.Player;
import Model.Soldier;
import Utils.Directions;
import Utils.PrimaryColor;

/**
 * 
 * @author aseel
 *
 */
public class FinishGameTest {

	private final char currentPlayerColor='B';

	private Player[] players ;

	private ArrayList<Piece> piecesSet1;
	private ArrayList<Piece> piecesSet2;
	private ArrayList<Piece> piecesSet3;
	private ArrayList<Piece> piecesSet4;





	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		//players set up
		Player.getInstance(0).setNickname("Tester1");
		Player.getInstance(1).setNickname("Tester2");
		players = new Player[2];
		players[0]=Player.getInstance(0);
		players[1]=Player.getInstance(1);

		//pieces setup

		//pieceType-tileLocation-pieceColor
		//used in test 1
		Soldier s2bb= new Soldier(1, PrimaryColor.BLACK, new Location(2, 'B'));
		Soldier s3cb=new Soldier(2, PrimaryColor.BLACK, new Location(3, 'C'));
		Soldier s1aw= new Soldier(3, PrimaryColor.WHITE, new Location(1, 'A'));
		Soldier s1cw=new Soldier(4, PrimaryColor.WHITE, new Location(1, 'C'));
		Soldier s2dw=new Soldier(5, PrimaryColor.WHITE, new Location(2, 'D'));
		Soldier s1ew=new Soldier(6, PrimaryColor.WHITE, new Location(1, 'E'));

		piecesSet1 = new ArrayList<>();
		piecesSet1.add(s2bb);
		piecesSet1.add(s3cb);
		piecesSet1.add(s1aw);
		piecesSet1.add(s1cw);
		piecesSet1.add(s2dw);
		piecesSet1.add(s1ew);

		//used in test 2
		Soldier s5ab= new Soldier(7, PrimaryColor.BLACK, new Location(5, 'A'));
		Soldier s5cb=new Soldier(8, PrimaryColor.BLACK, new Location(5, 'C'));
		Soldier s6bb= new Soldier(9, PrimaryColor.BLACK, new Location(6, 'B'));
		Soldier s5db=new Soldier(10, PrimaryColor.BLACK, new Location(5, 'D'));
		Soldier s4dw=new Soldier(11, PrimaryColor.WHITE, new Location(4, 'D'));
		Soldier s4bw=new Soldier(12, PrimaryColor.WHITE, new Location(4, 'B'));

		piecesSet2 = new ArrayList<>();
		piecesSet2.add(s5ab);
		piecesSet2.add(s5cb);
		piecesSet2.add(s6bb);
		piecesSet2.add(s5db);
		piecesSet2.add(s4dw);
		piecesSet2.add(s4bw);

		//used in test 3
		
		piecesSet3 = new ArrayList<>();
//		piecesSet3.add(s5ab);
		piecesSet3.add(s5cb);
		piecesSet3.add(s4dw);
		


		//used in test 4
		
		piecesSet4 = new ArrayList<>();
		piecesSet4.add(s5ab);
		piecesSet4.add(s5cb);
		piecesSet4.add(s4dw);

	}


	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		Game.getInstance().finishGame();
		Game.destruct();
		Board.destruct();
		Player.destruct();
	}


	/**
	 * Test ID: 2
	 */
	@Test
	public void playingPlayerIsStuck() {
		System.out.println("executing test 1 in test finish game ");

		boolean result=false;
		try {
			Game.getInstance().startGame(players, piecesSet1, currentPlayerColor);
			Game.getInstance().getBoard().printBoard();
			result=Game.getInstance().isGameFinished();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertTrue("Playing player must be identified as stuck,game must finish", result);
	}

	/**
	 * Test ID: 3
	 */
	@Test
	public void opponentPlayerIsStuck() {
		System.out.println("executing test 2 in test finish game ");

		boolean result = true;
		try {
			Game.getInstance().startGame(players, piecesSet2, currentPlayerColor);
			Game.getInstance().getBoard().printBoard();
			result=Game.getInstance().isGameFinished();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertFalse("Playing player didn't move yet,game shouldn't finish", result);
	}

	/**
	 * Test ID: 4
	 */
	@Test
	public void playingPlayerHasNoPeicesLeft() {
		System.out.println("executing test 3 in test finish game ");

		boolean result = false;
		try {
			Game.getInstance().startGame(players, piecesSet3, currentPlayerColor);
			Board.getInstance().removeAllSeconderyColorsFromBoard();
			Game.getInstance().getBoard().printBoard();
			BoardController.getInstance().movePiece(5, 'C', 4, 'B',Directions.DOWN_LEFT);
			result=Game.getInstance().isGameFinished();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		assertTrue("Playing player is out of pieces,Game must finish",result);
	}

	/**
	 * Test ID: 5
	 */
	@Test
	public void opponentPlayerHasNoPeicesLeft() {
		System.out.println("executing test 4 in test finish game ");

		boolean result = false;
		try {
			Game.getInstance().startGame(players, piecesSet3, currentPlayerColor);
			Game.getInstance().getBoard().printBoard();
			Board.getInstance().removeAllSeconderyColorsFromBoard();
			BoardController.getInstance().movePiece(5, 'C', 3, 'E',Directions.DOWN_RIGHT);
			result=Game.getInstance().isGameFinished();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		assertTrue("Opponent player is out of pieces,Game must finish",result);
	}

}
