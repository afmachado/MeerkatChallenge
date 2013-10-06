package game.entities;

import game.interfaces.Drawable;
import game.interfaces.Scorer;
import levels.Level;
import meerkatchallenge.main.R;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class VisibleScore implements Drawable, Scorer {
	private int score = 0;
	private Paint textPaint;
	private GameBoard gameBoard;
	private GameActivity mainActivity;
	private int rightMargin;
	private int topMargin;
	private Level level;
	
	public VisibleScore(GameBoard gameBoard, GameActivity ma, Level level) {
		this.gameBoard = gameBoard;
		this.mainActivity = ma;
		this.level = level;
		setFont();
		preCalculateLayout();
	}
	
	private void setFont() {
		textPaint = new Paint();  
		textPaint.setColor(Color.BLACK); 
		int textSize = mainActivity.getApplicationContext().getResources().getDimensionPixelSize(R.dimen.header);
		textPaint.setTextSize(textSize);
	}
	
	private void preCalculateLayout() {
		rightMargin = mainActivity.dpToPx(10);
		topMargin = mainActivity.dpToPx(30);
	}

	@Override
	public void draw(Canvas canvas) {
		int meerkatsLeft = level.getTargetScore() - score;
		String scoreText = "MEERKATS ";
		// If the target hasn't been met show the number of meerkats left
		if(meerkatsLeft >= 0) {
			scoreText += Integer.toString(meerkatsLeft);
		} else {
			scoreText += "+" + (meerkatsLeft * -1);
		}
		
		// Find the size of the score text
		char[] cha = scoreText.toCharArray();
		Rect textRect = new Rect();
		textPaint.getTextBounds(cha, 0, cha.length, textRect);
		
		canvas.drawText(scoreText, gameBoard.getWidth() - textRect.width() - rightMargin, topMargin, textPaint);
	}
	
	synchronized public void add(int toAdd) {
		score = score + toAdd;
	}
	
	public int get() {
		return this.score;
	}

}
