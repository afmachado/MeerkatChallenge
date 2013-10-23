package game.loops;

import game.interfaces.status.ReceivesInput;

import java.util.ArrayList;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class InputLoop implements OnTouchListener {

	ArrayList<ReceivesInput> listeners = new ArrayList<ReceivesInput>();

	public void register(ReceivesInput listener) {
		listeners.add(listener);
	}

	@Override
	public boolean onTouch(View v, MotionEvent ev) {
		for (ReceivesInput l : listeners) {
			l.onInput(v, ev);
		}
		return true;
	}
}
