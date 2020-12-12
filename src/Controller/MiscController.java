package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import Exceptions.LocationException;
import Model.Board;
import Model.Game;
import Model.Location;
import Model.Piece;
import Model.Queen;
import Model.Soldier;
import Model.Tile;
import Utils.PrimaryColor;

public class MiscController {
	
	
	private static MiscController instance;
	
	public MiscController() {
		
		
		
		
		
	}
	
	public static MiscController getInstance() 
	{ 
		if (instance == null) 
		{ 
			instance = new MiscController(); 
		} 
		
		return instance; 
	}
	
	/**
	 * load game from text file
	 */
	@SuppressWarnings("resource")
	public HashMap<Character, ArrayList<Piece>> loadGame(File file) {

		Character turn;
		Scanner scanner = null;

		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("Load file was not found in  " + file.getAbsolutePath());
			return null;
		}
		ArrayList<String> board_map = new ArrayList<String>();

		while (scanner.hasNext()) {

			board_map.addAll(Arrays.asList(scanner.nextLine().split(",")));

		}

		if (board_map.size() != 33) {
			System.out.println("Invalid File : number of parameters supplied dosent match 33");
			return null;
		}

		int countW = 0;
		int countB = 0;

		for (String s : board_map) {

			if (s.equals("1") || s.equals("11"))
				countW++;

			if (s.equals("2") || s.equals("22"))
				countB++;
			if (!s.equals("0") && !s.equals("1") && !s.equals("2") && !s.equals("11") && !s.equals("22")
					&& !s.equals("B") && !s.equals("W")) {
				System.out.println("Invalid File : unknown characters in the file");
				return null;
			}

		}

		if (countW > 12 || countB > 12) {

			System.out.println("Invalid File :  one of the players has more than 12 pieces");
			return null;

		}

		if (board_map.get(32).equals("B")) {
			turn = 'B';
		} else if (board_map.get(32).equals("W")) {
			turn = 'W';
		} else {
			System.out.println("Invalid File : last paramater must indicate the turn (should contain the letter B or W)");
			return null;
		}

		int helper = 8;
		int row = 1;
		int cnt = 1;
		
		if(board_map.get(31).equals("2") || board_map.get(30).equals("2") || board_map.get(29).equals("2") || board_map.get(28).equals("2")) {
			System.out.println("Invalid File : Bounds Cant Be Queens");
			return null;
		}
		else if(board_map.get(0).equals("1") || board_map.get(1).equals("1") || board_map.get(2).equals("1") || board_map.get(3).equals("1")) {
			System.out.println("Invalid File : Bounds Cant Be Queens)");
			return null;
		}

		ArrayList<Piece> loaded = new ArrayList<Piece>();

		for (String s : board_map) {

			if (cnt == 33) {
				break;
			}

			if (s.equals("0")) {
				cnt++;
				row++;
				if (row >= 6) {
					helper--;
					row = 2;
				}
				continue;
			}

			if (row >= 5) {
				helper--;
				row = 1;
			}
			
			char indicator = 0;
			if (helper % 2 == 0) {
				if (row == 1) {
					indicator = 'B';
				}
				else if (row == 2) {
					indicator = 'D';
				}
				else if(row == 3) {
					indicator = 'F';
				}
				else if(row == 4) {
					indicator = 'H';
				}
			} 
			else if(helper % 2 != 0) {
				
				if (row == 1) {
					indicator = 'A';
				}
				else if (row == 2) {
					indicator = 'C';
				}
				else if(row == 3) {
					indicator = 'E';
				}
				else if(row == 4) {
					indicator = 'G';
				}
			}
			try {
				if (returnColor(s).equals("WS")) {
							loaded.add(new Soldier(cnt, PrimaryColor.WHITE, new Location(helper, indicator)));
							row++;
							cnt++;
							continue;
				} else if (returnColor(s).equals("WQ")) {
							loaded.add(new Queen(cnt, PrimaryColor.WHITE, new Location(helper, indicator)));
							row++;
							cnt++;
							continue;
				} else if (returnColor(s).equals("BS")) {
							loaded.add(new Soldier(cnt, PrimaryColor.BLACK, new Location(helper, indicator)));
							row++;
							cnt++;
							continue;
				} else if (returnColor(s).equals("BQ")) {
							loaded.add(new Queen(cnt, PrimaryColor.BLACK, new Location(helper, indicator)));
							row++;
							cnt++;
							continue;
				}
			}catch(LocationException e) {
				e.printStackTrace();
			}

				
		}


		HashMap<Character, ArrayList<Piece>> map = new HashMap<Character, ArrayList<Piece>>();
		if (turn.equals('W')) {
			map.put('W', loaded);
		} else {
			map.put('B', loaded);
		}
		
		return map;
	}
	
	/**
	 * return the color and type of piece based on code in save text file
	 * 
	 * @param p code to check (1,11,2,22)
	 * @return if white/black and if soldier/queen
	 */
	public String returnColor(String p) {

		if (p.equals("1")) {
			return "WS";
		} else if (p.equals("2")) {
			return "BS";
		} else if (p.equals("11")) {
			return "WQ";
		} else if (p.equals("22")) {
			return "BQ";
		} else
			return null;
	}
	
	/**
	 * saves current game to a text file
	 */
	public void saveGame() {
		
		ArrayList<Tile> tiles = Board.getInstance().getAllBoardTiles();
		
		String data_line = "";
		
		for(Tile l : tiles) {
			
			if(l.getColor1().equals(PrimaryColor.WHITE)) {
				continue;
			}
			if(l.getPiece() == null) {
				data_line+="0,";
				continue;
			}
			else if(l.getPiece() instanceof Queen){
				if(l.getPiece().getColor().equals(PrimaryColor.WHITE)) {
					data_line+="11,";
					continue;
				}
				else {
					data_line+="22,";
					continue;
				}
			}
			else if(l.getPiece() instanceof Soldier){
				if(l.getPiece().getColor().equals(PrimaryColor.WHITE)) {
					data_line+="1,";
					continue;
				}
				else {
					data_line+="2,";
					continue;
				}
			}
		}
		
		if(Game.getInstance().getTurn().getCurrentPlayer().getColor().equals(PrimaryColor.WHITE))
		{
			data_line+="W";
		}
		else {
			data_line+="B";
		}
		
		String dateTime = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
		String path = "saved_games/game_"+dateTime+".txt";
		
		
		 try {
		      File myObj = new File(path);
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		 } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();   
		 }
		 
		 try {
		      FileWriter myWriter = new FileWriter(path);
		      myWriter.write(data_line);
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		  	 	 
	}
	
	

}
