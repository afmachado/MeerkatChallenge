package interfaces;

import android.graphics.Bitmap;

public interface Animatable {
	public void register(Animator a);
	public void unregister(Animator a);
	public Bitmap getBitmap();
}
