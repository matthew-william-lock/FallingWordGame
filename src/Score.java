public class Score {
	private int missedWords;
	private int caughtWords;
	private int gameScore;
	public static volatile boolean gameOver; 
	
	Score() {
		missedWords=0;
		caughtWords=0;
		gameScore=0;
		gameOver=false;
	}
		
	// all getters and setters must be synchronized

	public synchronized boolean getGameStatus() {
		return gameOver;
	}
	
	public synchronized int getCaught() {
		return caughtWords;
	}
	
	public int getTotal() {
		return (missedWords+caughtWords);
	}

	public synchronized int getScore() {
		return gameScore;
	}
	public synchronized int getMissed() {
		return missedWords;
	}
	
	public synchronized void missedWord() {
		missedWords++;
	}

	public synchronized void caughtWord(int length) {
		caughtWords++;
		gameScore+=length;
	}

	public synchronized void resetScore() {
		caughtWords=0;
		missedWords=0;
		gameScore=0;
		gameOver=false;
	}

	public synchronized void setGameStatus(boolean bool) {
		gameOver=bool;
	}
}
