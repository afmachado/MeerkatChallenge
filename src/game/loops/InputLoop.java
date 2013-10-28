package game.loops;

import game.interfaces.status.ReceivesInput;

import java.util.ArrayList;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Receives and processes user input
 * @author hqs71687
 *
 */
public class InputLoop implements OnTouchListener {

	ArrayList<ReceivesInput> listeners = new ArrayList<ReceivesInput>();

	/**
	 * Register a listener to be alerted to a user input event
	 * @param listener
	 */
	public void register(ReceivesInput listener) {
		listeners.add(listener);
	}

	/**
	 * When we receive a user input event, call each listener.
	 */
	@Override
	public boolean onTouch(View v, MotionEvent ev) {
		for (ReceivesInput l : listeners) {
			l.onInput(v, ev);
		}
		return true;
	}
}
