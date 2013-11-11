package eu.johncasson.meerkatchallenge.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import eu.johncasson.meerkatchallenge.R;

/**
 * Shows the pause screen
 */
public class Pause extends VolumeControlActivity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pause);
		Button unPause = (Button) findViewById(R.id.level_pause_unpause_button);
		unPause.setOnClickListener(this);
	}

	/**
	 * When the button is clicked, go back to the game activity
	 */
	@Override
	public void onClick(View arg0) {
		finish();
	}
	
	/**
	 * Explicitly end the activity when it's not visible. This 
	 * stops the pause screen being shown on top of the level select
	 * screen after the screen has been powered off.
	 */
	@Override
	public void onStop() {
		finish();
		super.onStop();
	}
}