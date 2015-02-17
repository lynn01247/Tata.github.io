package com.tatait.tataweibo;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

import com.tatait.tataweibo.MainActivity.UserSession;
import com.tatait.tataweibo.bean.UserInfo;
import com.tatait.tataweibo.dao.UserDao;
import com.tatait.tataweibo.util.show.CircularImage;

/**
 * 登陆界面
 * 
 * @author WSXL
 * 
 */
public class LoginCircleActivity extends Activity {
	private CircularImage cover_user_photo;
	private TextView circle_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_circle);
		UserDao dao = new UserDao(this);
		final List<UserInfo> userData = dao.findAllUser();
		if (userData.isEmpty()) {
			Intent intent = new Intent(LoginCircleActivity.this,
					OAuthActivity.class);
			startActivity(intent);
			finish();
		} else {
			circle_text = (TextView) findViewById(R.id.circle_text);
			AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
			animation.setDuration(1000);
			circle_text.setAnimation(animation);
			animation.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
					UserSession.nowUser = userData.get(0);
					cover_user_photo = (CircularImage) findViewById(R.id.cover_user_photo);
					cover_user_photo.setImageDrawable(userData.get(0)
							.getUser_head());
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					startActivity(new Intent(LoginCircleActivity.this,
							TabMainActivity.class));
					finish();
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}
			});
		}
	}
}
