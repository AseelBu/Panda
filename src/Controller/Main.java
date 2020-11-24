package Controller;

import java.util.Scanner;
import Model.Game;
import Model.Player;


public class Main {
	
	static Game game = null;
	static Player[] players;
	
	public static void main(String[] args) {
		
		System.out.print("Enter player 1 nickname: ");
		Scanner scanner = new Scanner(System.in);
		String name1 = scanner.nextLine();
		System.out.print("Enter player 2 nickname: ");
		scanner = new Scanner(System.in);
		String name2 = scanner.nextLine();

		// scores & color should not be provided --- only name
		players = new Player[] {
				new Player(name1, "White", 0),
				new Player(name2, "Black", 0)
				};
		
		if(players != null) {
			System.out.print("Player 1 : " + players[0].getNickname() + " || ");
			System.out.println("Player 2 : " + players[1].getNickname());
		}
		
		game = Game.getInstance();
		System.out.println("Write /start to start the game");
		System.out.println("Please enter a command:");
		
		while(true) {
			scanner = new Scanner(System.in);
			String str = scanner.nextLine();
			if(str.charAt(0) == '/') {
				command(str.substring(1));
			}
		}
				
	}
	
	
	private static void command(String cmd) {
		switch(cmd) {
			case "start":
				try {
					game.startGame(players);
					game.getBoard().printBoard();
				} catch (Exception e) {
					// TODO Auto-generated catch block
//					System.err.println(e.getMessage());
					e.printStackTrace();
					return;
				}
				break;
			case "pause":
				game.pauseGame();
				break;
			case "unpause":
				game.unpauseGame();
				break;
			case "board":
				game.getBoard().printBoard();
				break;
			case "exit":
				System.out.println("Game Terminated.");
				System.exit(1);
				break;
			default:
				System.err.println("Invalid Command");
		}
	}
}
