package com.tatait.tataweibo;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tatait.tataweibo.bean.UserInfo;
import com.tatait.tataweibo.dao.UserDao;
import com.tatait.tataweibo.util.AccessTokenKeeper;
import com.tatait.tataweibo.util.Tools;
/**
 * 加载logo页面
 * @author WSXL
 *
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		/**
		 * 4.0 版本处理主线程网络请求，参考：http://www.tuicool.com/articles/MvmeYr
		 */
		if (android.os.Build.VERSION.SDK_INT > 9) {
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	    }
		if(AccessTokenKeeper.readLoad(MainActivity.this)){
			ImageView loadImage = (ImageView)findViewById(R.id.mainImage);
			//设置透明度渐变效果(0.0-1.0)
			AlphaAnimation animation = new AlphaAnimation(0.3f,1.0f);
			//设置动画持续时间
			animation.setDuration(2000);
			//将组件与动画进行关联
			loadImage.setAnimation(animation);
			animation.setAnimationListener(new AnimationListener() {
				//动画开始
				@Override
				public void onAnimationStart(Animation animation) {
					Toast.makeText(MainActivity.this, R.string.welcome, Toast.LENGTH_SHORT).show();
				}
				//动画结束
				@Override
				public void onAnimationEnd(Animation animation) {
					init();
				}
				//动画重复
				@Override
				public void onAnimationRepeat(Animation animation) {}
			});
		}else{
			init();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.load, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 初始化动作
	 */
	public void init() {
		//校验网络状态
		if (!Tools.checkNetwork(MainActivity.this)) {
			TextView netSetting = new TextView(MainActivity.this);
			netSetting.setText("当前没有可用的网络连接，请设置网络");
			new AlertDialog.Builder(MainActivity.this)
					.setIcon(R.drawable.sad)
					.setTitle("网络状态提示")
					.setView(netSetting)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									startActivity(new Intent(
											Settings.ACTION_WIRELESS_SETTINGS));
//									finish();
								}
							}).create().show();
		}else{
			//校验登陆状态
			UserDao dao = new UserDao(MainActivity.this);
			List<UserInfo> userList = dao.findAllUser();
			if(userList==null||userList.isEmpty()){
				//如果没有登录则跳转到登录授权页面
				Intent intent = new Intent(this,OAuthActivity.class);
				startActivity(intent);
				finish();
			}else{
				//如果登录过则跳转到登录页面
				Intent intent = new Intent(this,LoginCircleActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}
	public static class UserSession {
        public static UserInfo nowUser;
    }
}
