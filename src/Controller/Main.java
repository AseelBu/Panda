package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import Model.*;
import Utils.Directions;


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
		cases(2);
	}
	
	static void cases(int index) {
		switch(index){
			case 1:
			{
				try {
					game.startGame(players);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				game.getBoard().printBoard();
				
				System.out.println("\r\nPlayer 1 Move: 3E -> 4F\r\n");
				game.getBoard().movePiece(new Location(3, 'E'), new Location(4, 'F'), Directions.UP_RIGHT);
				
				game.getBoard().printBoard();
				game.switchTurn();
				
				System.out.println("\r\nPlayer 1 Move: 6D -> 5E\r\n");
				game.getBoard().movePiece(new Location(6, 'D'), new Location(5, 'E'), Directions.DOWN_RIGHT);
				game.getBoard().printBoard();
				SysData.getInstance().saveGame();
				break;
			}
			case 2:
			{
				HashMap<Character, ArrayList<Piece>> load = SysData.getInstance().loadGame("saved_games/game1.txt");
				try {
					if(load.containsKey('W')) {
						game.startGame(players, load.get('W'), 'W');
					}else {
						game.startGame(players, load.get('B'),'B');
					}
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				game.getBoard().printBoard();
				
				System.out.println("\r\nPlayer " + game.getTurn().getCurrentPlayer().getNickname() + " Move: 4F -> A1\r\n");
				game.getBoard().movePiece(new Location(4, 'F'), new Location(1, 'A'), Directions.DOWN_RIGHT);
				game.getBoard().printBoard();
				game.switchTurn();
				System.out.println("\r\nPlayer " + game.getTurn().getCurrentPlayer().getNickname() + " Move: A5 -> F2\r\n");
				game.getBoard().movePiece(new Location(5, 'A'), new Location(2, 'F'), Directions.UP_RIGHT);
				game.getBoard().printBoard();
				
				break;
			}
		}
	}
}
