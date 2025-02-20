package basement.lab.mudclient;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

	public final static int SPLASH_DISPLAY_TIME = 3000;
	private ImageView logo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		new Handler().postDelayed(() -> {
			Intent mainIntent = new Intent(Splash.this, MenuActivity.class);
			logo.setAnimation(null);
			Splash.this.startActivity(mainIntent);
			Splash.this.finish();
		}, SPLASH_DISPLAY_TIME);
		RotateAnimation anim = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(1);
		anim.setDuration(SPLASH_DISPLAY_TIME);

		AnimationSet set = new AnimationSet(true);

		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(SPLASH_DISPLAY_TIME);
		set.addAnimation(animation);

		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -5.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-3.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(SPLASH_DISPLAY_TIME);
		set.addAnimation(animation);
		set.addAnimation(anim);

		logo = findViewById(R.id.splashimage);
		logo.setAnimation(set);
	}
}