package interfaces;

import android.view.MotionEvent;
import android.view.View;

public interface InputReceiver {
	public void onInput(View view, MotionEvent ev);
}
