package meerkatchallenge.activities;

import levels.Level;
import levels.Preferences;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Shows the "End level" screen that bounces in
 */
public class EndLevel extends Activity {
	// Delay before enabling the button in ms
	final static int ENABLED_BUTTON_DELAY = 700;
	Level level;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_level);

		int score = getIntent().getExtras().getInt("main.score");
		level = (Level) getIntent().getExtras().getSerializable("main.level");

		String title, description;
		
		if (score >= level.getTargetScore()) {
			// Update's the user's progress
			if(level.getNumber() != 20) {
				// Only update progress if the user hasn't completed this level
				if(level.getNumber() >= Preferences.getLevel(this)) { 
					Preferences.setLevel(this, level.getNumber() + 1);
				}
			}
			
			title = "Congratulations!";
			description = "You did it!";
		} else {
			title = "Oh No!";
			description = "You didn't make it :(";
		}

		TextView titleView = (TextView) findViewById(R.id.level_end_title);
		titleView.setText(title);
		TextView descriptionView = (TextView) findViewById(R.id.level_end_description);
		descriptionView.setText(description);
		TextView meerkatCountView = (TextView) findViewById(R.id.level_end_meerkat_count);
		meerkatCountView.setText(Integer.toString(score) + "/" + level.getTargetScore());
		
		/* Enable the button after a delay
		 * This stops the player hitting a button when they 
		 * were aiming at a meerkat that's suddenly been replaced
		 * by a button */
		delayedEnable();
		
		final LinearLayout wholeView = (LinearLayout) findViewById(R.id.level_end_container);
		final Animation fadeIn = AnimationUtils.loadAnimation(this,
				R.anim.anim_in);
		wholeView.setAnimation(fadeIn);
	}
	
	/**
	 * Enables the buttons after a delay
	 */
	public void delayedEnable() {
		Handler h = new Handler();
		Runnable r = new Runnable() {
			@Override
			public void run() {
				Button next = (Button) findViewById(R.id.level_end_continue_button);
				next.setOnClickListener(new OnClickListener() { 
					public void onClick(View v) {
						Intent intent = new Intent(EndLevel.this, LevelSelect.class); 
						startActivity(intent);
						overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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
