package activities;

import meerkatchallenge.activities.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Congratulations extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_congratulations);
		
		ImageView balloon1 = (ImageView) findViewById(R.id.balloon_1);
		balloon1.startAnimation(getFlyUpBounce());
		
		ImageView balloon2 = (ImageView) findViewById(R.id.balloon_2);
		new DelayedAnimation(balloon2, getFlyUpBounce(), 1000).execute();
		
		ImageView balloon3 = (ImageView) findViewById(R.id.balloon_3);
		new DelayedAnimation(balloon3, getFlyUpBounce(), 1500).execute();
	}
	
	/**
	 * Returns an animationset to fly ballons up and bounce them
	 * @return The animationset
	 */
	private AnimationSet getFlyUpBounce() {
		Animation bounce = AnimationUtils.loadAnimation(this, R.anim.balloon_bounce);
		Animation flyUp = AnimationUtils.loadAnimation(this, R.anim.float_up);
		Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		AnimationSet flyUpBounce = new AnimationSet(false);
		flyUpBounce.addAnimation(fadeIn);
		flyUpBounce.addAnimation(flyUp);
		flyUpBounce.addAnimation(bounce);
		return flyUpBounce;
	}

}
