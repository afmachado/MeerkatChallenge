package activities;

import meerkatchallenge.activities.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Shows the pause screen
 */
public class Pause extends VolumeControlActivity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pause);
		
		TextView title = (TextView) findViewById(R.id.level_info_start_title);
		title.setText("Paused");
		
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
}