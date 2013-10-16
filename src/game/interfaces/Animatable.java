package game.interfaces;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public interface Animatable {
	public void startAnimation(Animator a);
	public void stopAnimation(Animator a);
	public Bitmap getBitmap();
	public Matrix getMatrix();
}
