package com.tatait.tataweibo;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.tatait.tataweibo.MainActivity.UserSession;
import com.tatait.tataweibo.bean.UserInfo;

public class UserInfoActivity extends Activity {
	String weiboId = null;
	private ImageView userInfo_image;
	private TextView userInfo_icon;
	private TextView userInfo_userName;
	private TextView userInfo_address;
	private TextView userInfo_loginName;
	private TextView userInfo_friendsCount;
	private TextView userInfo_statusesCount;
	private TextView userInfo_followingCount;
	private TextView userinfo_favourites_count;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfo);
		init();
	}

	/**
	 * 初始化数据操作
	 * 
	 * @param weiboId
	 * @throws JSONException
	 */
	private void init() {
		UserInfo user = UserSession.nowUser;
		userInfo_image = (ImageView) findViewById(R.id.userinfo_image);
		userInfo_icon = (TextView) findViewById(R.id.userinfo_icon);
		userInfo_userName = (TextView) findViewById(R.id.userinfo_username);
		userInfo_address = (TextView) findViewById(R.id.userinfo_address);
		userInfo_loginName = (TextView) findViewById(R.id.userinfo_loginName);
		userInfo_friendsCount = (TextView) findViewById(R.id.userinfo_friendsCount);
		userInfo_statusesCount = (TextView) findViewById(R.id.userinfo_statusesCount);
		userInfo_followingCount = (TextView) findViewById(R.id.userinfo_followingCount);
		userinfo_favourites_count = (TextView) findViewById(R.id.userinfo_favourites_count);

		/** 给控件赋值 */
		userInfo_image.setImageDrawable(user.getUser_head());
		if ("m".equalsIgnoreCase(user.getGender())) {
			userInfo_icon.setText("女");
		} else if ("f".equalsIgnoreCase(user.getGender())) {
			userInfo_icon.setText("男");
		}else{
			userInfo_icon.setText("");
		}
		userInfo_userName.setText(user.getScreen_name());
		userInfo_address.setText("地址：" + user.getLocation());
		userInfo_loginName.setText("登录名：" + user.getScreen_name());
		userInfo_friendsCount.setText("关注数：" + user.getFriends_count());
		userInfo_followingCount.setText("粉丝数：" + user.getFollowers_count());
		userInfo_statusesCount.setText("微博数：" + user.getStatuses_count());
		userinfo_favourites_count.setText("收藏数：" + user.getFavourites_count());

	}

}
