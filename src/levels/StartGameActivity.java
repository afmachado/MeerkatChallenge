package levels;

import meerkatchallenge.main.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_game);
		// Start the game
		Preferences.setLevel(this, 1);
		Intent intent = new Intent(this, LevelActivity.class);
		startActivity(intent);
	}
}