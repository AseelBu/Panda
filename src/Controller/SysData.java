package Controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class SysData {
	
	
	
	 private static final SysData instance = new SysData();
	 private ArrayList<?> questions = new ArrayList<>();
	 private ArrayList<?> scoreboard = new ArrayList<>();
	 
	 
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
	  
	  public ArrayList<?> getQuestions(){
		  return this.questions;
	  }
	  
	  /**
	   * Scoreboard DataStructre
	   * @return
	   */
	  
	  public ArrayList<?> getScoreboard(){
		  return this.scoreboard;
	  }
	  
	
	  /**
	   * Write Questions To File Including Updated Questions
	   */
	
	  
	  public void WriteQuestion() {
		  
		  JsonArray questions = new JsonArray();
		   
		 
		  // for each question
		  
		  JsonObject question  = new JsonObject();
		  
		  JsonArray answerArray = new JsonArray();
		  answerArray.add("answer1");
		  answerArray.add("answer2");
		  answerArray.add("answer3");
		  answerArray.add("answer4");
		  
		  question.addProperty("question", "q1");
		  question.add("answers", answerArray);
		  question.addProperty("correct_ans", "1");
		  question.addProperty("level", "1");
		  question.addProperty("team","animal");
		  
		  
		  questions.add(question);
		  
		  
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
	
	public void LoadQuestion() {
		
		
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
			
			// question context
		    System.out.println(((JsonObject) element).get("question").getAsString());
		    
		    // question answers
		    JsonArray answersArray = (((JsonObject) element).getAsJsonArray("answers"));
		    
		    @SuppressWarnings("unchecked")
			ArrayList<String> answers = gson.fromJson(answersArray,ArrayList.class);
		   
		    // get each answer
		    for(String s : answers) {
		    	System.out.println(s);
		    }
		    
		    int difficulty = ((JsonObject) element).get("level").getAsInt();
	    	System.out.println(difficulty);
	    	
		    int correct = ((JsonObject) element).get("correct_ans").getAsInt();
	    	System.out.println(correct);

		    String team = ((JsonObject) element).get("team").getAsString();
	    	System.out.println(team);	    

		}
		
		// create relative objects and add to data structure
		
	}
	
	/**
	 * Add Question To Questions DataStructure
	 */
	
	
	public void addQuestion() {
		
		
		
	}
	
	/**
	 * Remove Question To Questions DataStructure
	 */
	
	public void removeQuestion() {
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	

}
