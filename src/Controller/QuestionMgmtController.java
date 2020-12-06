package Controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import Model.Answer;
import Model.Question;
import Model.SysData;
import Utils.DifficultyLevel;
import View.ManageQuestions;

public class QuestionMgmtController {
	
	private SysData sysData;
	private ManageQuestions questionScreen;
	private static QuestionMgmtController instance;
	
	
	/**
	 * 
	 */
	public QuestionMgmtController() {
		
		this.questionScreen = new ManageQuestions();
		this.sysData = SysData.getInstance();
		
		
	}
	
	//TODO java doc
	/**
	 *
	 * @return
	 */
	public static QuestionMgmtController getInstance() 
	{ 
		if (instance == null) 
		{ 
			instance = new QuestionMgmtController(); 
		} 
		return instance; 
	}
	
	/**
	 * SysData Instance Getter
	 */
	public SysData getSysData() {
		return sysData;
	}

	/**
	 * GUI Questions Getter
	 * @return
	 */
	public ManageQuestions getQuestionScreen() {
		return questionScreen;
	}
	
	/**
	 * GUI Setter
	 * @param questionScreen
	 */
	public void setQuestionScreen(ManageQuestions questionScreen) {
		this.questionScreen = questionScreen;
	}
	
	/**
	 * Write Questions To File Including Updated Questions
	 */
	public void WriteQuestions() {

		JsonArray questions = new JsonArray();

		for (Question q : this.sysData.getQuestions()) {

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

		ArrayList<Question> questions = new ArrayList<Question>();
		
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
			
			if (!this.sysData.getQuestions().isEmpty()) {
				q = new Question(this.sysData.getQuestions().size(), content, null,
						new ArrayList<Answer>(), team);
				this.sysData.getQuestions().add(q);
			} else {
				q = new Question(0, content, null, new ArrayList<Answer>(), team);
				this.sysData.getQuestions().add(q);
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
		
		this.getSysData().setQuestions(questions);
		
		

	}
	
	public ArrayList<Question> getQuestions(){
		return this.sysData.getQuestions();
	}
	
	public void removeQuestions(int id){
		 this.sysData.removeQuestion(id);
	}
	
	
	
	
	
	
	
	

}
