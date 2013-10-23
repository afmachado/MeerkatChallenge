package game.interfaces.status;

import android.view.MotionEvent;
import android.view.View;
/**
 * Classes that can receive updates about input
 * @author John Casson
 *
 */
public interface ReceivesInput {
	public void onInput(View view, MotionEvent ev);
}
