package game.actor.interfaces;

import game.interfaces.visual.Animator;
import android.graphics.Bitmap;
import android.graphics.Matrix;

public interface Animatable {
	public void startAnimation(Animator a);
	public void stopAnimation(Animator a);
	public Bitmap getBitmap();
	public Matrix getMatrix();
}
