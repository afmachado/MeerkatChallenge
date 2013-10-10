package game.entities;

import game.interfaces.Drawable;
import game.interfaces.GameComponent;
import game.interfaces.Pausable;
import game.interfaces.StopCondition;
import meerkatchallenge.activities.GameActivity;
import meerkatchallenge.activities.R;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class VisibleTimer implements StopCondition, GameComponent, Drawable, Pausable {
	boolean finished = false;

	long startTime;
	long timeLimit;
	long pauseTime;
	GameBoard gameBoard;
	Paint textPaint;

	private GameActivity mainActivity;
	
	/**
	 * Stops the game after a specified time
	 * @param gameTime The time to stop after
	 */
	public VisibleTimer(int gameTime, GameBoard gameBoard, GameActivity ma) {
		this.timeLimit = gameTime;
		this.gameBoard = gameBoard;
		this.mainActivity = ma;
		startTime = System.currentTimeMillis();
		setFont();
	}
	
	private void setFont() {
		textPaint = new Paint();  
		textPaint.setColor(Color.BLACK);
		int textSize = mainActivity.getApplicationContext().getResources().getDimensionPixelSize(R.dimen.header);
		textPaint.setTextSize(textSize);
	}
	
	@Override
	public boolean stopCondition() {
		return finished;
	}
	
	// Calculate the current game time and check whether the play time
	// has exceeded the game time
	@Override
	public void play() throws Exception {
		long timePlayed = System.currentTimeMillis() - startTime;
		if(timePlayed > timeLimit) {
			finished = true;
		}
	}

	/**
	 * 
	 */
	@Override
	public void draw(Canvas canvas) {
		long timeTaken = System.currentTimeMillis() - startTime;
		// We add 500 to the visible output 
		// so the timer starts from e.g. 10 instead of 9
		long timeLeft = timeLimit + 500 - timeTaken;
		timeLeft = timeLeft / 1000;
		int leftMargin = mainActivity.dpToPx(10);
		int topMargin = mainActivity.dpToPx(30);
		canvas.drawText("TIME " + Long.toString(timeLeft), leftMargin, topMargin, textPaint);
	}
	
	/** 
	 * When the game is paused, store the time of the pause 
	 */
	@Override
	public void onPause() {
		pauseTime = System.currentTimeMillis();
	}

	/**
	 *  When we're unpaused, update the start time to reflect the
	 *  time spent paused
	 */
	@Override
	public void onUnPause() {
		long pausedTime = System.currentTimeMillis() - pauseTime;
		startTime = startTime + pausedTime;
	}
	
	
}
