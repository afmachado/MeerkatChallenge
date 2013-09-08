package interfaces;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public interface Animatable {
	public void register(Animator a);
	public void unregister(Animator a);
	public Bitmap getBitmap();
	public Matrix getMatrix();
}
