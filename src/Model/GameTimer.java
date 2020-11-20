package Model;

/**
 * 
 * @author firas
 * 
 */
public class GameTimer {

	private long startTime;
	private long anonStartTime;
	private long pauseTime;
	
	
	public GameTimer() {
		super();
		setPauseTime(-1);
		setStartTime(-1);
		setAnonStartTime(-1);
	}

	/**
	 * Start timer
	 */
	public void startTimer() {
		setStartTime(System.currentTimeMillis());
		setAnonStartTime(System.currentTimeMillis());
	}
	
	/**
	 * set time count in seconds and start timer
	 * @param seconds
	 */
	public void startTimer(float seconds) {
		setStartTime((long) (System.currentTimeMillis() - seconds*(1000)));
		setAnonStartTime((long) (System.currentTimeMillis() - seconds*(1000)));
		setPauseTime(-1);
	}
	/**
	 * set time count in seconds and start timer
	 * @param seconds
	 */
	public void startTimer(int seconds) {
		setStartTime((long) (System.currentTimeMillis() - seconds*(1000)));
		setAnonStartTime((long) (System.currentTimeMillis() - seconds*(1000)));
		setPauseTime(-1);
	}
	/**
	 * reset timer
	 */
	public void resetTimer() {
		setPauseTime(-1);
		startTimer();
	}
	/**
	 * pause timer
	 */
	public void pauseTimer() {
		setPauseTime(System.currentTimeMillis());

	}
	/**
	 * unpause timer
	 */
	public void unpauseTimer() {
		long temp = anonStartTime + (System.currentTimeMillis() - getPauseTime());
		setAnonStartTime(temp);
		setPauseTime(-1);
	}
	/**
	 * get time count in seconds (float)
	 * @return seconds count
	 */
	public float getSeconds() {
		if(pauseTime > -1) {
			long temp = anonStartTime + (System.currentTimeMillis() - getPauseTime());
			return ( System.currentTimeMillis() -  temp) / 1000F;
			
		}else return ( System.currentTimeMillis() -  getAnonStartTime() ) / 1000F;
	}
	
	/**
	 * getters/setters for internal use
	 */
	private long getStartTime() {
		return startTime;
	}
	private void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	private long getPauseTime() {
		return pauseTime;
	}
	private void setPauseTime(long pauseTime) {
		this.pauseTime = pauseTime;
	}
	private long getAnonStartTime() {
		return anonStartTime;
	}
	private void setAnonStartTime(long anonStartTime) {
		this.anonStartTime = anonStartTime;
	}
}