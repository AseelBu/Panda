package Model;

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
import java.util.Collections;
import java.util.Comparator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import Utils.DifficultyLevel;

public class SysData {
	
	
	/**
	 * @author saleh
	 */
	
	
	
	 private static final SysData instance = new SysData();
	 private ArrayList<Question> questions = new ArrayList<Question>();
	 private ArrayList<HighScoreEntity> scoreboard = new ArrayList<>();
	 
	 
	 /**
	  * Using this singelton instance to acces data sturctures and methods
	  * @return - Sysdata Class Instance
	  */
	
	  public static SysData getInstance() {
	        return instance;
	  }
	  
	  
	  /**
	   * Question DataStructure
	   * @return 
	   */
	  
	  public ArrayList<Question> getQuestions(){
		  return this.questions;
	  }
	  
	  /**
	   * ScoreBoard DataStructre
	   * @return
	   */
	  
	  public ArrayList<HighScoreEntity> getScoreboard(){
		  return this.scoreboard;
	  }
	  
	  
	  /**
	   * Adds scores to the scoreboard
	   * @param hs HighScore To Add
	   */
	  
	  public void addScoreToHistory(HighScoreEntity hs) {
		  
		  this.sortHighscores();
		  
		  if(this.getScoreboard().size() < 10) {
			  this.getScoreboard().add(hs);
			  this.getScoreboard().remove(0);
			  this.sortHighscores();
			  return;
		  }
		  		  
		 if(hs.getPoints() <= this.getScoreboard().get(0).getPoints()) {
			 return;
		 }
		 else {
			 
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
		   
		 
		  for(Question q : this.questions) {
		  
		  JsonObject question  = new JsonObject();
		  
		  JsonArray answerArray = new JsonArray();
		  
		  int correct = 0;
		  
		  for(Answer a : q.getAnswers()) {
			  
			  if(a.isCorrect())
				  correct = a.getId();
			  
			  answerArray.add(a.getContent());

		  }
		  int difficulty = 0;
		  if(q.getDifficulty().equals(DifficultyLevel.EASY)) {
			  difficulty = 1;
		  }
		  else if (q.getDifficulty().equals(DifficultyLevel.MEDIOCRE)) {
			  difficulty = 2;
		  }
		  else if (q.getDifficulty().equals(DifficultyLevel.HARD)) {
			  difficulty = 3;
		  }
		  
		  question.addProperty("question", q.getContent());
		  question.add("answers", answerArray);
		  question.addProperty("correct_ans", String.valueOf(correct));
		  
		
		  
		  
		  
		  question.addProperty("level", String.valueOf(difficulty));
		  question.addProperty("team","animal");
		  
		  
		  questions.add(question);
		  
		  }
		  
		  
		  JsonObject root = new JsonObject();
		  root.add("questions", questions);
		  
		  //write to file
		  
		  try {
			Writer w = new FileWriter("question_data.json");
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(root,w);
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
			ArrayList<String> answers = gson.fromJson(answersArray,ArrayList.class);
		   
		    
		    int difficulty = ((JsonObject) element).get("level").getAsInt();
	    	
	    	
		    int correct = ((JsonObject) element).get("correct_ans").getAsInt();
	    	
		    String team = ((JsonObject) element).get("team").getAsString();
		    if(this.getQuestions().size() > 0) {
		    	q = new Question(this.getQuestions().get(this.getQuestions().size() - 1).getId() + 1,content,null,new ArrayList<Answer>(),team);   
		    }
		    else {
		    	q = new Question(0,content,null,new ArrayList<Answer>(),team);
		    }
	    	DifficultyLevel diff_level;
	    	if(difficulty == 1) {
	    		diff_level = DifficultyLevel.EASY;
	    	}
	    	else if (difficulty == 2) {
	    		diff_level = DifficultyLevel.MEDIOCRE;
	    	}
	    	else {
	    		diff_level = DifficultyLevel.HARD;
	    	}
	    	
	    	q.setDifficulty(diff_level);
	    	
	    	int correctAnswer_Checker = 0;
	    	
	    	for(String s : answers) {
	    		correctAnswer_Checker++;
	    		Answer a = null;
	    		
	    		if(correctAnswer_Checker == correct) {
	    			a = new Answer(correctAnswer_Checker,s,true);
	    		}
	    		else {
	    			a = new Answer(correctAnswer_Checker,s,false);
	    		}
	    		q.addAnswer(a);
	    			   		
	    	}
	    	
	    	questions.add(q);
	    		

		}
		
	}
	
	/**
	 * Add Question To Questions DataStructure
	 * @param question to add
	 */
	
	
	public void addQuestion(Question q) {
		
		
		if(q!=null) {
			this.getQuestions().add(q);
		}
		
		
	}
	
	/**
	 * remove a question from the DataStructure
	 * @param id - id of question to be removed
	 */
	
	public void removeQuestion(int id) {
		
		int i = -1;
		int iterator = 0;
		
		for(Question q : this.getQuestions()) {
			
			if(q.getId() == id) {
				
				i = iterator;
				break;

			}
			
			iterator++;
			
		}
		
		if(i != -1) {
			this.getQuestions().remove(i);
		}
		
	}
	
	/**
	 * 
	 * @param id the id of the question to be updated
	 * @param updated_question new question containing all updated details
	 */
	
	public void editQuestion(int id , Question updated_question) {
		
		for(Question q : this.getQuestions()) {
			
			if(q.getId() == id) {
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
		
		 Collections.sort(this.getScoreboard() ,new Comparator<HighScoreEntity>() {
			  public int compare(HighScoreEntity p1, HighScoreEntity p2) {
				  return Integer.valueOf(p1.getPoints()).compareTo(Integer.valueOf(p2.getPoints()));
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
				this.scoreboard = (ArrayList<HighScoreEntity>) objectInputStream.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
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
	
	public void loadGame() {
		
		
		
		
		
		return;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
