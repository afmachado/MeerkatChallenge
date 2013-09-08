package game.interfaces;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public interface Animatable {
	public void registerAnimation(Animator a);
	public void unregisterAnimation(Animator a);
	public Bitmap getBitmap();
	public Matrix getMatrix();
}
