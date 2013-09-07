package main;

import interfaces.Actor;
import interfaces.Drawable;
import interfaces.StopCondition;
import meerkatchallenge.main.R;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Timer implements StopCondition, Actor, Drawable {
	boolean finished = false;

	long startTime;
	long gameTime;
	GameBoard gameBoard;
	Paint textPaint;

	private GameActivity mainActivity;
	
	/**
	 * Stops the game after a specified time
	 * @param gameTime The time to stop after
	 */
	public Timer(int gameTime, GameBoard gameBoard, GameActivity ma) {
		this.gameTime = gameTime;
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
	public void act() throws Exception {
		long timePlayed = System.currentTimeMillis() - startTime;
		if(timePlayed > gameTime) {
			finished = true;
		}
	}

	@Override
	public void draw(Canvas canvas) {
		long currentTime = System.currentTimeMillis() - startTime; 
		long timeLeft = gameTime - currentTime;
		timeLeft = timeLeft / 1000;
		int leftMargin = mainActivity.dpToPx(10);
		int topMargin = mainActivity.dpToPx(30);
		canvas.drawText("TIME " + Long.toString(timeLeft), leftMargin, topMargin, textPaint);
	}
	
	
}
