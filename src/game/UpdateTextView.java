package game;

import android.widget.TextView;
import game.interfaces.status.GameComponent;
import game.interfaces.status.Updater;

/**
 * Updates a TextView with the status of an Updater
 * @author John Casson
 *
 */
public class UpdateTextView implements GameComponent {
	
	private TextView timerText;
	private Updater updater;

	public UpdateTextView(Updater updater, TextView timerText) {
		this.timerText = timerText;
		this.updater = updater;
	}
	
	@Override
	public void play() throws Exception {
		String text = updater.getUpdate();
		timerText.setText(text);
	}
}
