package game;

import android.widget.TextView;
import game.interfaces.status.GameComponent;
import game.interfaces.status.GivesUpdates;

/**
 * Updates a textview with a timer's current time
 * @author hqs71687
 *
 */
public class Updater implements GameComponent {
	
	private TextView timerText;
	private GivesUpdates updater;

	public Updater(GivesUpdates updater, TextView timerText) {
		this.timerText = timerText;
		this.updater = updater;
	}
	
	@Override
	public void play() throws Exception {
		String text = updater.getStatus();
		timerText.setText(text);
	}
}
