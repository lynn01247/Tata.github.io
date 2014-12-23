package com.tatait.tataweibo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.openapi.models.User;
import com.tatait.tataweibo.dao.UserDao;
import com.tatait.tataweibo.pojo.UserInfo;
import com.tatait.tataweibo.util.Response;
import com.tatait.tataweibo.util.Tools;

/**
 * 授权页面
 * 
 * @author WSXL
 * 
 */
public class OAuthActivity extends Activity {
	private long exitTime = 0;
	private static final String TAG = "OAuthActivity";
	private Dialog dialog;
	/**
	 * OAuth2.0认证--SSO授权
	 */
	// 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
	private Oauth2AccessToken mAccessToken;
	// 注意：SsoHandler 仅当 SDK 支持 SSO 时有效
	private SsoHandler mSsoHandler;
	private AuthInfo mAuthInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "----------in method:onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load);
		/**
		 * 初始化悬浮提示授权弹出框
		 */
		View diaView = View.inflate(this, R.layout.oauth_dialog, null);
		dialog = new Dialog(this, R.style.dialog);
		dialog.setContentView(diaView);
		dialog.show();
//		/**
//		 * 模拟器区别初始化:获取当前已保存过的 Token
//		 */
//		mAccessToken = AccessTokenKeeper.readAccessToken(this);
		mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        mSsoHandler = new SsoHandler(OAuthActivity.this, mAuthInfo);
		/**
		 * 授权按钮的动作
		 */
		Button startBtn = (Button) diaView.findViewById(R.id.btn_start);
		startBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/**
				 * 执行SSO授权
				 */
				 mSsoHandler.authorize(new AuthListener());
//				getUserInfo(mAccessToken);
			}

		});
//		/**
//		 * 注销登陆的动作
//		 */
//		Button cancelBtn = (Button) diaView.findViewById(R.id.btn_cancel);
//		cancelBtn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				/**
//				 * 注销,取消授权，去掉token信息
//				 */
//				AccessTokenKeeper.clear(getApplicationContext());
//				mAccessToken = new Oauth2AccessToken();
//			}
//		});
	}

	/**
	 * 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
	 * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
	 * SharedPreferences 中。
	 */
	class AuthListener implements WeiboAuthListener {
		// 授权完成时
		@Override
		public void onComplete(Bundle values) {
			// 从 Bundle 中解析 Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			if (mAccessToken.isSessionValid()) {
				// 保存 Token 到 SharedPreferences
				AccessTokenKeeper.writeAccessToken(OAuthActivity.this,
						mAccessToken);
				Toast.makeText(OAuthActivity.this,
						R.string.weibosdk_demo_toast_auth_success,
						Toast.LENGTH_SHORT).show();
				getUserInfo(mAccessToken);
			} else {
				// 以下几种情况，您会收到 Code：
				// 1. 当您未在平台上注册的应用程序的包名与签名时；
				// 2. 当您注册的应用程序包名与签名不正确时；
				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
				String code = values.getString("code");
				String message = getString(R.string.weibosdk_demo_toast_auth_failed);
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code: " + code;
				}
				Toast.makeText(OAuthActivity.this, message, Toast.LENGTH_SHORT)
						.show();
			}
		}

		// 授权被取消时
		@Override
		public void onCancel() {
			Toast.makeText(OAuthActivity.this,
					R.string.weibosdk_demo_toast_auth_canceled,
					Toast.LENGTH_SHORT).show();
		}

		// 授权出错时
		@Override
		public void onWeiboException(WeiboException arg0) {
			Toast.makeText(OAuthActivity.this,
					"Auth exception : " + arg0.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}
	}

	/**
	 * 获取用户信息
	 * 
	 * @param mAccessToken
	 */
	public void getUserInfo(Oauth2AccessToken mAccessToken) {
		UserInfo userInfo = new UserInfo();
		HttpClient client = Tools.getInstance().httpClientInit();
		// source false string 采用OAuth授权方式不需要此参数，其他授权方式为必填参数，数值为应用的AppKey。
		// access_token false string 采用OAuth授权方式为必填参数，其他授权方式不需要此参数，OAuth授权后获得。
		// uid false int64 需要查询的用户ID。
		// screen_name false string 需要查询的用户昵称。
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		// 认证参数设置
		params.add(new BasicNameValuePair("source", Constants.APP_KEY));
		params.add(new BasicNameValuePair("uid", mAccessToken.getUid()));
		params.add(new BasicNameValuePair("access_token", mAccessToken
				.getToken()));
		Response res = Tools.getInstance().get(client,
				Constants.WEIBO_GET_USER_INFO, params);
		String response = res.toString();
		User user = User.parse(response);
		if (user != null) {
			userInfo.setUser_id(user.id);
			userInfo.setUser_name(user.name);
			userInfo.setToken(mAccessToken.getToken());
			userInfo.setToken_secret(mAccessToken.getUid());
			userInfo.setDescription(user.description);
			userInfo.setUser_head(Tools
					.getDrawableFromUrl(user.profile_image_url));
			UserDao dao = new UserDao(OAuthActivity.this);
			dao.insertUser(userInfo);
			Toast.makeText(OAuthActivity.this,
					R.string.weibosdk_demo_toast_auth_success,
					Toast.LENGTH_SHORT).show();
			dialog.dismiss();
			makeToLogin();
		} else {
			Toast.makeText(OAuthActivity.this,
					R.string.weibosdk_demo_toast_auth_failed,
					Toast.LENGTH_SHORT).show();
			dialog.dismiss();
		}
	}

	/**
	 * 跳转登陆页面
	 */
	protected void makeToLogin() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	// 主菜单点击返回键，弹出对话框
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), R.string.quit,
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
