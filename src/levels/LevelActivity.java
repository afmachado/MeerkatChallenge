package levels;

import game.entities.GameActivity;
import meerkatchallenge.main.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LevelActivity extends Activity implements OnClickListener {
	Level level;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level);
		
		int levelNumber = Preferences.getLevel(this);
		
		level = Levels.get(levelNumber);
		
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("Level " + level.getNumber() + ": " + level.getTitle());
		TextView description = (TextView) findViewById(R.id.description);
		description.setText(level.getDescription());	
		
		TextView meerkatCount = (TextView) findViewById(R.id.meerkats);
		meerkatCount.setText(Integer.toString(level.getTargetScore()));
		
		TextView time = (TextView) findViewById(R.id.time);
		time.setText(Integer.toString(level.getTimeLimit()));
		
		Button b = (Button) findViewById(R.id.start);
		b.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra("main.challenge", level);
		startActivity(intent);
	}
}