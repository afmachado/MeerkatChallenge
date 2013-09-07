package interfaces;

import android.view.MotionEvent;
import android.view.View;

public interface ReceivesInput {
	public void onInput(View view, MotionEvent ev);
}
