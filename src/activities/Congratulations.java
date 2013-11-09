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
		new DelayedAnimation(balloon1, getFlyUpBounce(), 600).execute();
		
		ImageView balloon2 = (ImageView) findViewById(R.id.balloon_2);
		new DelayedAnimation(balloon2, getFlyUpBounce(), 650).execute();
		
		ImageView balloon3 = (ImageView) findViewById(R.id.balloon_3);
		new DelayedAnimation(balloon3, getFlyUpBounce(), 100).execute();
		
		ImageView balloon4 = (ImageView) findViewById(R.id.balloon_4);
		new DelayedAnimation(balloon4, getFlyUpBounce(), 650).execute();
		
		ImageView balloon5 = (ImageView) findViewById(R.id.balloon_5);
		new DelayedAnimation(balloon5, getFlyUpBounce(), 600).execute();
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
//		flyUpBounce.addAnimation(fadeIn);
		flyUpBounce.addAnimation(flyUp);
		flyUpBounce.addAnimation(bounce);
		return flyUpBounce;
	}

}
