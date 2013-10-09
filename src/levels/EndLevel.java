package levels;

import game.entities.GameActivity;
import meerkatchallenge.main.LevelSelect;
import meerkatchallenge.main.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EndLevel extends Activity {
	// Delay before enabling the button in ms
	final static int ENABLED_BUTTON_DELAY = 700;
	Level level;
	EndLevel lea;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level_end);

		lea = this;

		int score = getIntent().getExtras().getInt("main.score");
		level = (Level) getIntent().getExtras().getSerializable("main.level");

		String message;
		boolean showNext = false;
		if (score >= level.getTargetScore()) {
			// A hack to put the level back to 1 if the game is won (level 20 complete)
			if(level.getNumber() != 20) {
				// If the user has already completed this level don't increment
				if(level.getNumber() >= Preferences.getLevel(this)) { 
					Preferences.setLevel(this, level.getNumber() + 1);
				}
			} else {
				Preferences.setLevel(this, 1);
			}
			message = "Congratulations!\nYou did it!\n\nYour score:";
			showNext = true;
		} else {
			message = "Oh No!\n You didn't make it :(\n\nYour score:";
		}

		TextView tv = (TextView) findViewById(R.id.resultsHeader);
		tv.setText(message);
		TextView tv2 = (TextView) findViewById(R.id.results);
		tv2.setText(Integer.toString(score));

		// Hide the next button if the level hasn't been won
		if (!showNext) {
			Button b = (Button) findViewById(R.id.next_level);
			b.setVisibility(View.GONE);
		}
		
		delayedEnable();
	}

	public void delayedEnable() {
		Handler h = new Handler();
		Runnable r = new Runnable() {
			@Override
			public void run() {
				Button next = (Button) findViewById(R.id.next_level);
				next.setOnClickListener(new OnClickListener() { 
					public void onClick(View v) {
						Bundle bundle = new Bundle();
						level = Levels.get(Preferences.getLevel(lea));
						bundle.putSerializable("level", level);
						Intent intent = new Intent(lea, GameActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});
				
				Button retry = (Button) findViewById(R.id.retry);
				retry.setOnClickListener(new OnClickListener() { 
					public void onClick(View v) {				
						Intent intent = new Intent(lea, GameActivity.class);
						intent.putExtra("main.challenge", level);
						startActivity(intent);
					}
				});
			}
		};			

		h.postDelayed(r, ENABLED_BUTTON_DELAY);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent i = new Intent(EndLevel.this, LevelSelect.class);
			startActivity(i);
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
}
