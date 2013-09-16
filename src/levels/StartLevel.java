
package levels;

import game.entities.Game;
import levels.EditNameDialog.EditNameDialogListener;
import meerkatchallenge.main.LevelSelect;
import meerkatchallenge.main.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
/**
 * The "Start a level" activity
 * @author John Casson
 *
 */
public class StartLevel extends FragmentActivity implements OnClickListener, EditNameDialogListener {
	Level level;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level);
		Bundle extras = getIntent().getExtras();
		// Hack for testing
//		int levelNumber = extras.getInt("level");
		int levelNumber = 5;

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
		showEditDialog();
		
	}
	
	private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditNameDialog editNameDialog = new EditNameDialog();
        editNameDialog.show(fm, "fragment_edit_name");
	}
	
	@Override
    public void onFinishEditDialog(String inputText) {
        Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
    }


	/**
	 * When the start button is pressed, start the game
	 */
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, Game.class);
		intent.putExtra("main.challenge", level);
		startActivity(intent);
	}

	/**
	 * When the back button is pressed show level select
	 */
	@Override
	public void onBackPressed() {
		Intent i = new Intent(StartLevel.this, LevelSelect.class);
		startActivity(i);
	}
}