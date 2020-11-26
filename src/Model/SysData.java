package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Calendar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import Utils.DifficultyLevel;
import Utils.PrimaryColor;

public class SysData {

	/**
	 * @author saleh
	 */

	private static final SysData instance = new SysData();
	private ArrayList<Question> questions = new ArrayList<Question>();
	private ArrayList<Player> scoreboard = new ArrayList<>();

	/**
	 * Using this singelton instance to acces data sturctures and methods
	 * 
	 * @return - Sysdata Class Instance
	 */

	public static SysData getInstance() {
		return instance;
	}

	/**
	 * Question DataStructure
	 * 
	 * @return
	 */

	public ArrayList<Question> getQuestions() {
		return this.questions;
	}

	/**
	 * ScoreBoard DataStructre
	 * 
	 * @return
	 */

	public ArrayList<Player> getScoreboard() {
		return this.scoreboard;
	}

	/**
	 * Adds scores to the scoreboard
	 * 
	 * @param hs HighScore To Add
	 */

	public void addScoreToHistory(Player hs) {

		this.sortHighscores();

		if (this.getScoreboard().size() < 10) {
			this.getScoreboard().add(hs);
			this.getScoreboard().remove(0);
			this.sortHighscores();
			return;
		}

		if (hs.getCurrentScore() <= this.getScoreboard().get(0).getCurrentScore()) {
			return;
		} else {

			this.getScoreboard().add(hs);
			this.getScoreboard().remove(0);
			this.sortHighscores();
			return;

		}

	}

	/**
	 * Write Questions To File Including Updated Questions
	 */

	public void WriteQuestions() {

		JsonArray questions = new JsonArray();

		for (Question q : this.questions) {

			JsonObject question = new JsonObject();

			JsonArray answerArray = new JsonArray();

			int correct = 0;

			for (Answer a : q.getAnswers()) {

				if (a.isCorrect())
					correct = a.getId();

				answerArray.add(a.getContent());

			}
			int difficulty = 0;
			if (q.getDifficulty().equals(DifficultyLevel.EASY)) {
				difficulty = 1;
			} else if (q.getDifficulty().equals(DifficultyLevel.MEDIOCRE)) {
				difficulty = 2;
			} else if (q.getDifficulty().equals(DifficultyLevel.HARD)) {
				difficulty = 3;
			}

			question.addProperty("question", q.getContent());
			question.add("answers", answerArray);
			question.addProperty("correct_ans", String.valueOf(correct));

			question.addProperty("level", String.valueOf(difficulty));
			question.addProperty("team", "animal");

			questions.add(question);

		}

		JsonObject root = new JsonObject();
		root.add("questions", questions);

		// write to file

		try {
			Writer w = new FileWriter("question_data.json");
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(root, w);
			w.flush();
			w.close();
			System.out.println("Success");
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Load Trivia Questions From JSON File
	 */

	public void LoadQuestions() {

		// empty data structure before loading
		this.questions.clear();

		Gson gson = new Gson();
		JsonReader reader = null;
		try {
			reader = new JsonReader(new FileReader("question_data.json"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
		final JsonArray data = jsonObject.getAsJsonArray("questions");

		for (JsonElement element : data) {

			Question q;

			String content = ((JsonObject) element).get("question").getAsString();

			// question answers
			JsonArray answersArray = (((JsonObject) element).getAsJsonArray("answers"));

			@SuppressWarnings("unchecked")
			ArrayList<String> answers = gson.fromJson(answersArray, ArrayList.class);

			int difficulty = ((JsonObject) element).get("level").getAsInt();

			int correct = ((JsonObject) element).get("correct_ans").getAsInt();

			String team = ((JsonObject) element).get("team").getAsString();
			if (this.getQuestions().size() > 0) {
				q = new Question(this.getQuestions().get(this.getQuestions().size() - 1).getId() + 1, content, null,
						new ArrayList<Answer>(), team);
			} else {
				q = new Question(0, content, null, new ArrayList<Answer>(), team);
			}
			DifficultyLevel diff_level;
			if (difficulty == 1) {
				diff_level = DifficultyLevel.EASY;
			} else if (difficulty == 2) {
				diff_level = DifficultyLevel.MEDIOCRE;
			} else {
				diff_level = DifficultyLevel.HARD;
			}

			q.setDifficulty(diff_level);

			int correctAnswer_Checker = 0;

			for (String s : answers) {
				correctAnswer_Checker++;
				Answer a = null;

				if (correctAnswer_Checker == correct) {
					a = new Answer(correctAnswer_Checker, s, true);
				} else {
					a = new Answer(correctAnswer_Checker, s, false);
				}
				q.addAnswer(a);

			}

			questions.add(q);

		}

	}

	/**
	 * Add Question To Questions DataStructure
	 * 
	 * @param question to add
	 */

	public void addQuestion(Question q) {

		if (q != null) {
			this.getQuestions().add(q);
		}

	}

	/**
	 * remove a question from the DataStructure
	 * 
	 * @param id - id of question to be removed
	 */

	public void removeQuestion(int id) {

		int i = -1;
		int iterator = 0;

		for (Question q : this.getQuestions()) {

			if (q.getId() == id) {

				i = iterator;
				break;

			}

			iterator++;

		}

		if (i != -1) {
			this.getQuestions().remove(i);
		}

	}

	/**
	 * 
	 * @param id               the id of the question to be updated
	 * @param updated_question new question containing all updated details
	 */

	public void editQuestion(int id, Question updated_question) {

		for (Question q : this.getQuestions()) {

			if (q.getId() == id) {
				q.setId(id);
				q.setContent(updated_question.getContent());
				q.setDifficulty(updated_question.getDifficulty());
				q.setTeam(updated_question.getTeam());
				q.updateAnswers(updated_question.getAnswers());
			}

		}

	}

	/**
	 * this method sorts HighScores
	 */

	public void sortHighscores() {

		Collections.sort(this.getScoreboard(), new Comparator<Player>() {
			public int compare(Player p1, Player p2) {
				return Integer.valueOf(p1.getCurrentScore()).compareTo(Integer.valueOf(p2.getCurrentScore()));
			}
		});

	}

	/**
	 * write HighScores to file
	 */

	public void writeHistory() {

		try {
			OutputStream outputStream = new FileOutputStream("highscores.ser");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(this.getScoreboard());
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * load HighScores from file
	 */

	public void loadHistory() {

		try {
			InputStream inputStream = new FileInputStream("highscores.ser");
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			try {
				this.scoreboard = (ArrayList<Player>) objectInputStream.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("");
				e.printStackTrace();
			}
			this.sortHighscores();
			objectInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * load game from text file
	 */

	public HashMap<Character, ArrayList<Piece>> loadGame(String path) {

		Character turn;
		Scanner scanner = null;

		try {
			scanner = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			System.err.println("Load file was not found in  " + path);
			return null;
		}
		ArrayList<String> board_map = new ArrayList<String>();

		while (scanner.hasNext()) {

			board_map.addAll(Arrays.asList(scanner.nextLine().split(",")));

		}

		if (board_map.size() != 33) {
			System.err.println("Invalid File : number of parameters supplied dosent match 33");
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
				System.err.println("Invalid File : unknown characters in the file");
				return null;
			}

		}

		if (countW > 12 || countB > 12) {

			System.err.println("Invalid File :  one of the players has more than 12 pieces");
			return null;

		}

		if (board_map.get(32).equals("B")) {
			turn = 'B';
		} else if (board_map.get(32).equals("W")) {
			turn = 'W';
		} else {
			System.err
					.println("Invalid File : last paramater must indicate the turn (should contain the letter B or W)");
			return null;
		}

		int helper = 8;
		int row = 1;
		int cnt = 1;

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

			if (helper % 2 == 0) {
				if (row == 1) {
					if (returnColor(s).equals("WS")) {
						loaded.add(new Soldier(cnt, PrimaryColor.WHITE, new Location(helper, 'B')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("WQ")) {
						loaded.add(new Queen(cnt, PrimaryColor.WHITE, new Location(helper, 'B')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("BS")) {
						loaded.add(new Soldier(cnt, PrimaryColor.BLACK, new Location(helper, 'B')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("BQ")) {
						loaded.add(new Queen(cnt, PrimaryColor.BLACK, new Location(helper, 'B')));
						row++;
						cnt++;
						continue;
					}

				} else if (row == 2) {
					if (returnColor(s).equals("WS")) {
						loaded.add(new Soldier(cnt, PrimaryColor.WHITE, new Location(helper, 'D')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("WQ")) {
						loaded.add(new Queen(cnt, PrimaryColor.WHITE, new Location(helper, 'D')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("BS")) {
						loaded.add(new Soldier(cnt, PrimaryColor.BLACK, new Location(helper, 'D')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("BQ")) {
						loaded.add(new Queen(cnt, PrimaryColor.BLACK, new Location(helper, 'D')));
						row++;
						cnt++;
						continue;
					}
				} else if (row == 3) {
					if (returnColor(s).equals("WS")) {
						loaded.add(new Soldier(cnt, PrimaryColor.WHITE, new Location(helper, 'F')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("WQ")) {
						loaded.add(new Queen(cnt, PrimaryColor.WHITE, new Location(helper, 'F')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("BS")) {
						loaded.add(new Soldier(cnt, PrimaryColor.BLACK, new Location(helper, 'F')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("BQ")) {
						loaded.add(new Queen(cnt, PrimaryColor.BLACK, new Location(helper, 'F')));
						row++;
						cnt++;
						continue;
					}
				} else if (row == 4) {
					if (returnColor(s).equals("WS")) {
						loaded.add(new Soldier(cnt, PrimaryColor.WHITE, new Location(helper, 'H')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("WQ")) {
						loaded.add(new Queen(cnt, PrimaryColor.WHITE, new Location(helper, 'H')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("BS")) {
						loaded.add(new Soldier(cnt, PrimaryColor.BLACK, new Location(helper, 'H')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("BQ")) {
						loaded.add(new Queen(cnt, PrimaryColor.BLACK, new Location(helper, 'H')));
						row++;
						cnt++;
						continue;
					}
				}
			} else {
				if (row == 1) {
					if (returnColor(s).equals("WS")) {
						loaded.add(new Soldier(cnt, PrimaryColor.WHITE, new Location(helper, 'A')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("WQ")) {
						loaded.add(new Queen(cnt, PrimaryColor.WHITE, new Location(helper, 'A')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("BS")) {
						loaded.add(new Soldier(cnt, PrimaryColor.BLACK, new Location(helper, 'A')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("BQ")) {
						loaded.add(new Queen(cnt, PrimaryColor.BLACK, new Location(helper, 'A')));
						row++;
						cnt++;
						continue;
					}

				} else if (row == 2) {
					if (returnColor(s).equals("WS")) {
						loaded.add(new Soldier(cnt, PrimaryColor.WHITE, new Location(helper, 'C')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("WQ")) {
						loaded.add(new Queen(cnt, PrimaryColor.WHITE, new Location(helper, 'C')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("BS")) {
						loaded.add(new Soldier(cnt, PrimaryColor.BLACK, new Location(helper, 'C')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("BQ")) {
						loaded.add(new Queen(cnt, PrimaryColor.BLACK, new Location(helper, 'C')));
						row++;
						cnt++;
						continue;
					}
				} else if (row == 3) {
					if (returnColor(s).equals("WS")) {
						loaded.add(new Soldier(cnt, PrimaryColor.WHITE, new Location(helper, 'E')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("WQ")) {
						loaded.add(new Queen(cnt, PrimaryColor.WHITE, new Location(helper, 'E')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("BS")) {
						loaded.add(new Soldier(cnt, PrimaryColor.BLACK, new Location(helper, 'E')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("BQ")) {
						loaded.add(new Queen(cnt, PrimaryColor.BLACK, new Location(helper, 'E')));
						row++;
						cnt++;
						continue;
					}
				} else if (row == 4) {
					if (returnColor(s).equals("WS")) {
						loaded.add(new Soldier(cnt, PrimaryColor.WHITE, new Location(helper, 'G')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("WQ")) {
						loaded.add(new Queen(cnt, PrimaryColor.WHITE, new Location(helper, 'G')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("BS")) {
						loaded.add(new Soldier(cnt, PrimaryColor.BLACK, new Location(helper, 'G')));
						row++;
						cnt++;
						continue;
					} else if (returnColor(s).equals("BQ")) {
						loaded.add(new Queen(cnt, PrimaryColor.BLACK, new Location(helper, 'G')));
						row++;
						cnt++;
						continue;
					}
				}

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
		
		
		String path = "saved_games/game_" + Calendar.getInstance().getTime().toString();
		
		
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

}
