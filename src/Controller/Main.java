package Controller;

import java.util.ArrayList;
import java.util.Random;

import Model.Game;
import Model.GameTimer;
import Model.Location;
import Model.Piece;
import Model.Player;
import Model.Soldier;
import Utils.PrimaryColor;


public class Main {
	public static void main(String[] args) {
		
		Game game = null;
		
		// scores & color should not be provided --- only name
		Player[] players = {new Player("Jack", "White", 0),new Player("Jack", "Black", 0)};
		
		if(players != null) {
			System.out.print("Player 1 : " + players[0].getNickname() + " || ");
			System.out.println("Player 2 : " + players[0].getNickname());
		}

		ArrayList<Piece> pieces = new ArrayList<>();
		
		for(int i = 1 ; i <= 3 ; i++)
			for(char c = 'A' ; c <= 'H' ; c+=2) {
				if(i == 2 && c == 'A') c = 'B';
				Piece soldier = new Soldier(PrimaryColor.WHITE, new Location(i,c));
				pieces.add(soldier);
			}
		
		for(int i = 6 ; i <= 8 ; i++)
			for(char c = 'B' ; c <= 'H' ; c+=2) {
				if(i == 7 && c == 'B') c = 'A';
				Piece soldier = new Soldier(PrimaryColor.BLACK, new Location(i,c));
				pieces.add(soldier);
			}
		
		
		try {
			game.initiateGame(players, pieces);
			game = Game.getInstance();
						
			game.getBoard().printBoard();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
//		try {
//			GameTimer timer = new GameTimer();
//			timer.startTimer(500);
//			System.out.println(timer.getSeconds() + " ~~ 500");

//			Thread.sleep(5000);

//			System.out.println(timer.getSeconds() + " ~~ 505");
//
//		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		try {	
//			GameTimer timer = new GameTimer();
//			timer.startTimer();
//		
//			Thread.sleep(5000);
//			
//			System.out.println(timer.getSeconds() + " ~~ 5");
//			
//			timer.pauseTimer();
//			
//			Thread.sleep(5000);
//			
//			System.out.println(timer.getSeconds() + " ~~ 5");
//			
//			timer.unpauseTimer();
//			
//			Thread.sleep(5000);
//			
//			System.out.println(timer.getSeconds() + " ~~ 10");
//			
//			timer.resetTimer();
//			System.out.println(timer.getSeconds() + " ~~ 0");
//			
//			Thread.sleep(5000);
//			
//			System.out.println(timer.getSeconds() + " ~~ 5");
//			
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
}
