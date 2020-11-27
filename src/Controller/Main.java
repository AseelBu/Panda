package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import Model.*;
import Utils.Directions;
import Utils.PrimaryColor;


public class Main {
	
	static Game game = null;
	static Player[] players;
	
	
	public static void main(String[] args) {
		
		Player player1 = Player.getInstance(0);
		Player player2 = Player.getInstance(1);
		player1.setNickname("Jack");
		player2.setNickname("Max");

		players = new Player[] {
								player1,
								player2
								};
		if(players != null) {
			System.out.print("Player 1 : " + players[0].getNickname() + " || ");
			System.out.println("Player 2 : " + players[1].getNickname());
		}
		game = Game.getInstance();
		cases(4);
	}
	
	static void cases(int index) {
		switch(index){
			case 1:
			{
				System.out.println("Running Case " + index + " !! : \r\n\r\n");
				try {
					game.startGame(players);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.err.println(e.getMessage());
				}
				game.getBoard().printBoard();
				
				game.getBoard().movePiece(new Location(3, 'E'), new Location(4, 'F'), Directions.UP_RIGHT);
				game.getBoard().printBoard();

				game.getBoard().movePiece(new Location(6, 'D'), new Location(5, 'E'), Directions.DOWN_RIGHT);
				game.getBoard().printBoard();

				game.getBoard().movePiece(new Location(4, 'F'), new Location(6, 'D'), Directions.UP_LEFT);
				game.getBoard().printBoard();
				
				game.getBoard().movePiece(new Location(7, 'E'), new Location(5, 'C'), Directions.DOWN_LEFT);
				game.getBoard().printBoard();
				
				// not legal relative direction
				game.getBoard().movePiece(new Location(3, 'A'), new Location(5, 'B'), Directions.UP_LEFT);
				game.getBoard().printBoard();
				
				
				game.getBoard().movePiece(new Location(2, 'F'), new Location(3, 'E'), Directions.UP_LEFT);
				game.getBoard().printBoard();
				
				game.getBoard().movePiece(new Location(8, 'F'), new Location(7, 'E'), Directions.DOWN_LEFT);
				game.getBoard().printBoard();
				
				game.getBoard().movePiece(new Location(3, 'C'), new Location(4, 'D'), Directions.UP_RIGHT);
				game.getBoard().printBoard();
				
				game.getBoard().movePiece(new Location(7, 'E'), new Location(6, 'D'), Directions.DOWN_LEFT);
				game.getBoard().printBoard();
				
				game.getBoard().movePiece(new Location(2, 'B'), new Location(3, 'C'), Directions.UP_RIGHT);
				game.getBoard().printBoard();
				
				game.getBoard().movePiece(new Location(8, 'D'), new Location(7, 'E'), Directions.DOWN_RIGHT);
				game.getBoard().printBoard();
				
				game.getBoard().movePiece(new Location(3, 'G'), new Location(4, 'H'), Directions.UP_RIGHT);
				game.getBoard().printBoard();
				
				game.getBoard().movePiece(new Location(6, 'B'), new Location(5, 'A'), Directions.DOWN_LEFT);
				game.getBoard().printBoard();
				
				//move that triggers sequential eating
				System.out.println("******************************************************************************");
				game.getBoard().movePiece(new Location(4, 'D'), new Location(6, 'B'), Directions.UP_LEFT);
				game.getBoard().printBoard();
				
				game.getBoard().movePiece(new Location(6, 'B'), new Location(8, 'D'), Directions.UP_RIGHT);
				game.getBoard().printBoard();
				
				//end o eating sequence
				
				game.getBoard().movePiece(new Location(6, 'D'), new Location(5, 'C'), Directions.DOWN_LEFT);
				game.getBoard().printBoard();
				game.getBoard().movePiece(new Location(6, 'B'), new Location(8, 'D'), Directions.UP_RIGHT);
				game.getBoard().printBoard();
				game.getBoard().movePiece(new Location(6, 'F'), new Location(5, 'E'), Directions.DOWN_RIGHT);
				game.getBoard().printBoard();
				game.getBoard().movePiece(new Location(4, 'H'), new Location(5, 'G'), Directions.UP_LEFT);
				game.getBoard().printBoard();
				game.getBoard().movePiece(new Location(6, 'H'), new Location(4, 'F'), Directions.DOWN_LEFT);
				game.getBoard().printBoard();
				game.getBoard().movePiece(new Location(8, 'D'), new Location(2, 'F'), Directions.DOWN_LEFT);
				game.getBoard().printBoard();
//				game.getBoard().movePiece(new Location(6, 'B'), new Location(8, 'D'), Directions.UP_RIGHT);
//				game.getBoard().printBoard();
				
				//Last move has to be for white piece rather than black
				//eat several pieces in a row
				break;
			}
			case 2:
			{
				System.out.println("Running Case " + index + " !! : \r\n\r\n");
				HashMap<Character, ArrayList<Piece>> load = SysData.getInstance().loadGame("saved_games/game1.txt");
				try {
					if(load.containsKey('W')) {
						game.startGame(players, load.get('W'), 'W');
					}else {
						game.startGame(players, load.get('B'), 'B');
					}
				}catch (Exception e) {
					// TODO Auto-generated catch block
					System.err.println(e.getMessage());
				}
				
				game.getBoard().printBoard();
				
				game.getBoard().movePiece(new Location(4, 'F'), new Location(1, 'A'), Directions.DOWN_RIGHT);
				game.getBoard().printBoard();
				game.switchTurn();
				game.getBoard().movePiece(new Location(5, 'A'), new Location(2, 'F'), Directions.UP_RIGHT);
				game.getBoard().printBoard();
				game.switchTurn();

				break;
			}
			case 3:{
				System.out.println("Running Case " + index + " !! : \r\n\r\n");

				Piece soldier = new Soldier(1, PrimaryColor.WHITE, new Location(1,'G'));
				Piece soldier2 = new Soldier(2, PrimaryColor.WHITE, new Location(1,'E'));
				Piece soldier3 = new Soldier(3, PrimaryColor.BLACK, new Location(2,'H'));
				Piece soldier4 = new Soldier(4, PrimaryColor.BLACK, new Location(2,'F'));
				Piece soldier5 = new Soldier(5, PrimaryColor.BLACK, new Location(2,'D'));
				Piece soldier6 = new Soldier(6, PrimaryColor.BLACK, new Location(3,'C'));
				Piece soldier7 = new Soldier(7, PrimaryColor.BLACK, new Location(3,'E'));
				Piece soldier8 = new Soldier(8, PrimaryColor.BLACK, new Location(3,'G'));

				ArrayList<Piece> pieces = new ArrayList<>();
				pieces.add(soldier);
				pieces.add(soldier2);
				pieces.add(soldier3);
				pieces.add(soldier4);
				pieces.add(soldier5);
				pieces.add(soldier6);
				pieces.add(soldier7);
				pieces.add(soldier8);

				try {
					game.startGame(players, pieces, 'W');
				}catch (Exception e) {
					// TODO Auto-generated catch block
					System.err.println(e.getMessage());
				}

				break;
			}
			case 4:{
				Queen queen = new Queen(1, PrimaryColor.WHITE, new Location(2,'D'));
				Piece soldier2 = new Soldier(2, PrimaryColor.BLACK, new Location(4,'F'));
				Piece soldier3 = new Soldier(3, PrimaryColor.BLACK, new Location(6,'H'));
				
				ArrayList<Piece> pieces = new ArrayList<>();
				pieces.add(queen);
				pieces.add(soldier2);
				pieces.add(soldier3);
				
				
				try {
					game.startGame(players, pieces, 'W');
				}catch (Exception e) {
					// TODO Auto-generated catch block
					System.err.println(e.getMessage());
				}
				game.getBoard().printBoard();

				game.getBoard().movePiece(new Location(2, 'D'), new Location(5, 'G'), Directions.UP_RIGHT);
				game.getBoard().printBoard();
				game.getBoard().movePiece(new Location(5, 'G'), new Location(7, 'A'), Directions.UP_RIGHT);
				game.getBoard().printBoard();
				break;
			}
			
		}
	}
}
