package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import Model.Player;
import Model.SysData;
import View.Scoreboard;

public class ScoreBoardController {
	
	private SysData sysData;
	private Scoreboard highscores_screen;
	private static ScoreBoardController instance;
	
	private ScoreBoardController() {
		
		
		this.sysData = SysData.getInstance();
		this.highscores_screen = new Scoreboard();
		
			
	}
	
	/**
	 * Get Instance
	 * @return - Controller's instance
	 */
	
	public static ScoreBoardController getInstance() 
	{ 
		if (instance == null) 
		{ 
			instance = new ScoreBoardController(); 
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
	 * GUI Scoreboard Getter
	 * @return
	 */
	public Scoreboard getScoreBoardScreen() {
		return this.highscores_screen;
	}
	
	/**
	 * GUI Setter
	 * @param questionScreen
	 */
	public void setScoreScreen(Scoreboard score_screen) {
		this.highscores_screen = score_screen;
	}
	
	/**
	 * write HighScores to file
	 */
	public void writeHistory() {

		try {
			OutputStream outputStream = new FileOutputStream("highscores.ser");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(this.getSysData().getScoreboard());
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	/**
	 * Load History From File 
	 */
	public void loadHistory() {

		try {
			InputStream inputStream = new FileInputStream("highscores.ser");
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			try {
				ArrayList<Player> scores = (ArrayList<Player>) objectInputStream.readObject();
				if(scores == null) {
					scores = new ArrayList<Player>();
				}
				this.getSysData().setScoreboard(scores);
			} catch (ClassNotFoundException e) {
				System.out.println("");
				e.printStackTrace();
			}
			this.getSysData().sortHighscores();
			objectInputStream.close();
		}catch(FileNotFoundException e){
			
			this.writeHistory();
			this.loadHistory();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}
	
	
	

}
