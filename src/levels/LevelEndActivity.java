package levels;

import game.entities.GameActivity;
import meerkatchallenge.main.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LevelEndActivity extends Activity {
	// Delay before enabling the button in ms
	final static int ENABLED_BUTTON_DELAY = 700;
	Level level;
	LevelEndActivity lea;

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
			Preferences.setLevel(this, level.getNumber() + 1);
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
						Intent intent = new Intent(lea, LevelActivity.class);
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
}
