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
		
		/**
		 * Sound Constructor
		 */
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
		
		/**
		 * Intro Music
		 */
		public void playIntro() {
			this.introPlayer.play();
		}
		/**
		 * Stops Intro Music
		 */
		public void stopIntro() {
			 this.introPlayer.stop();
		}
		/**
		 * Plays Question Music
		 */
		public void play30() {
			
			 this.quesPlayer.play();
			
		}
		/**
		 * Stops Question Music
		 */
		public void stopQues() {
			
			 this.quesPlayer.stop();
		}
		/**
		 * Play Burn Sound Effect
		 */
		public void playBurn() {
			
			this.burnPlayer.play();
			this.burnPlayer.seek(Duration.ZERO);
		}
		/**
		 * Play Move Sound Effect
		 */
		public void playMove() {
			
			this.movePlayer.play();
			this.movePlayer.seek(Duration.ZERO);
			
		}



		/**
		 * Mute All Sounds
		 */
		public void muteSound() {
			
			this.burnPlayer.setVolume(0);
			this.introPlayer.setVolume(0);
			this.quesPlayer.setVolume(0);	
			this.movePlayer.setVolume(0);
		}
		
		/**
		 * Unmute All Sounds
		 */
		public void unmuteSound() {
			
			this.burnPlayer.setVolume(0.7);
			this.introPlayer.setVolume(0.5);
			this.quesPlayer.setVolume(0.7);
			this.movePlayer.setVolume(0.7);
			
		}
		/**
		 * check of player is muted
		 * @return
		 */
		public boolean isMuted() {
			if(this.introPlayer.getVolume() != 0.0) {
				return false;
			}
			else {
				return true;
			}
		}
			
			
			

}
