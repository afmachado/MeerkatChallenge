package main;

import meerkatchallenge.main.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import entities.Preferences;

public class StartGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_game);
		// Start the game
		Preferences.setLevel(this, 1);
		Intent intent = new Intent(this, LevelActivity.class);
		startActivity(intent);
		
		// Useful debugging code - start a specific level
//	Intent intent = new Intent(this, GameActivity.class);
//		intent.putExtra("main.challenge", Levels.get(14));
//		startActivity(intent);
	}
	
	
}