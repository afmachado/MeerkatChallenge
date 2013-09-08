package levels;

import game.entities.VisibleScore;
import game.interfaces.StopAction;
import android.app.Activity;
import android.content.Intent;

/**
 * Shows the level end screen
 * @author John Casson
 *
 */
public class ShowLevelEnd implements StopAction {
	private Activity activity;
	private VisibleScore score;
	private Level level;
	
	public ShowLevelEnd(Activity activity, VisibleScore score, Level level) {
		this.activity = activity;
		this.score = score;
		this.level = level;
	}
	
	@Override
	public void onStop() {
		Intent intent = new Intent(activity, LevelEndActivity.class);
		intent.putExtra("main.score", score.get());
		intent.putExtra("main.level", level);
		activity.startActivity(intent);
	}
	
}
