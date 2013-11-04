package activities;

import meerkatchallenge.activities.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Congratulations extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_congratulations);
		
		ImageView balloon1 = (ImageView) findViewById(R.id.balloon_1);
		Animation bounce = AnimationUtils.loadAnimation(this, R.anim.balloon_float);
		balloon1.startAnimation(bounce);
		
		ImageView balloon2 = (ImageView) findViewById(R.id.balloon_2);
		Animation bounce2 = AnimationUtils.loadAnimation(this, R.anim.balloon_float);
		bounce2.setStartOffset(750);
		balloon2.startAnimation(bounce2);
		
		ImageView balloon3 = (ImageView) findViewById(R.id.balloon_3);
		Animation bounce3 = AnimationUtils.loadAnimation(this, R.anim.balloon_float);
		bounce3.setStartOffset(1250);
		balloon3.startAnimation(bounce3);
	}

}
