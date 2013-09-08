package loops;

import interfaces.InputReceiver;

import java.util.ArrayList;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class InputLoop implements OnTouchListener {

	ArrayList<InputReceiver> listeners = new ArrayList<InputReceiver>();

	public void register(InputReceiver listener) {
		listeners.add(listener);
	}

	@Override
	public boolean onTouch(View v, MotionEvent ev) {
		for (InputReceiver l : listeners) {
			l.onInput(v, ev);
		}
		return true;
	}
}
