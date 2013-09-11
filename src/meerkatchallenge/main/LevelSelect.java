package meerkatchallenge.main;

import levels.Preferences;
import levels.StartLevel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LevelSelect extends Activity {
	int[] textViews = { R.id.level1, R.id.level2, R.id.level3, R.id.level4,
			R.id.level5, R.id.level6, R.id.level7, R.id.level8, R.id.level9,
			R.id.level10, R.id.level11, R.id.level12, R.id.level13,
			R.id.level14, R.id.level15, R.id.level16, R.id.level17,
			R.id.level18, R.id.level19, R.id.level20, };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level_select);
		final Activity reference = this;

		final String lockedLevel = "-";
		final int currentLevel = Preferences.getLevel(this);

		// Set a border around the active level
		for (int i : textViews) {
			final TextView tv = (TextView) findViewById(i);
			if (Integer.parseInt(tv.getText().toString()) == currentLevel) {
				LinearLayout ll = (LinearLayout) tv.getParent();
				ll.setBackgroundColor(getResources().getColor(R.color.current_level));
			}
		}

		// Display levels not yet completed by "-" (not including the current
		// level)
		// and stop them being selected
		for (int i : textViews) {
			TextView tv = (TextView) findViewById(i);
			// TODO: this code shouldn't rely on the text in the textview
			if (Integer.parseInt(tv.getText().toString()) > currentLevel) {
				tv.setText(lockedLevel);
			}
			tv.setOnClickListener(null);
		}

		// Set the on click listeners for completed levels
		for (int i : textViews) {
			final TextView tv = (TextView) findViewById(i);
			if (!tv.getText().toString().equals(lockedLevel)) {
				tv.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(reference,
								StartLevel.class);
						Bundle bundle = new Bundle();
						bundle.putInt("level",
								Integer.parseInt(tv.getText().toString()));
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});
			}
		}

	}
	
	// Always go back to the start screen
	@Override
	public void onBackPressed() {
		Intent i = new Intent(LevelSelect.this, StartScreen.class);
		startActivity(i);
	}
}
