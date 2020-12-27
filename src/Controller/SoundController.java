package Controller;


import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.HashMap;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class SoundController {
	
	
		public static SoundController instance;
		private HashMap<String,Media> songs = new HashMap<String,Media>();
		private MediaPlayer introPlayer;
		private MediaPlayer burnPlayer;
		private MediaPlayer quesPlayer;
		private MediaPlayer movePlayer;
		
		
		public static SoundController getInstance() 
		{ 
			if (instance == null) 
			{ 
				instance = new SoundController(); 
			} 
			
			return instance; 
		}
		
		
		public SoundController() {
			
			
			songs.put("burn", new Media(new File("SoundFX/burn.mp3").toURI().toString()));
			songs.put("intro", new Media(new File("SoundFX/bg_music.mp3").toURI().toString()));
			songs.put("ques", new Media(new File("SoundFX/30.mp3").toURI().toString()));
			songs.put("move", new Media(new File("SoundFX/move.wav").toURI().toString()));
			
			this.introPlayer = new MediaPlayer(songs.get("intro"));
			this.burnPlayer = new MediaPlayer(songs.get("burn"));
			this.quesPlayer = new MediaPlayer(songs.get("ques"));
			this.movePlayer = new MediaPlayer(songs.get("move"));
			
			introPlayer.setVolume(0.5);
			burnPlayer.setVolume(0.5);
			quesPlayer.setVolume(0.5);
			
			introPlayer.setOnEndOfMedia(new Runnable() {
			       public void run() {
			    	   introPlayer.seek(Duration.ZERO);
			       }
			   });
			
			
			
			
		}
		
		public void playIntro() {
			this.introPlayer.play();
		}
		
		public void stopIntro() {
			 this.introPlayer.stop();
		}
		
		public void play30() {
			
			 this.quesPlayer.play();
			
		}
		
		public void stopQues() {
			
			 this.quesPlayer.stop();
		}
		
		public void playBurn() {
			
			this.burnPlayer.play();
			this.burnPlayer.seek(Duration.ZERO);
		}
		
		public void playMove() {
			
			this.movePlayer.play();
			this.movePlayer.seek(Duration.ZERO);
			
		}



		
		public void muteSound() {
			
			this.burnPlayer.setVolume(0);
			this.introPlayer.setVolume(0);
			this.quesPlayer.setVolume(0);	
			this.movePlayer.setVolume(0);
		}
		
		public void unmuteSound() {
			
			this.burnPlayer.setVolume(0.7);
			this.introPlayer.setVolume(0.5);
			this.quesPlayer.setVolume(0.7);
			this.movePlayer.setVolume(0.7);
			
		}
			
			
			

}
