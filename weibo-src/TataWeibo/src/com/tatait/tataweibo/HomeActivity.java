package com.tatait.tataweibo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.tatait.tataweibo.LoadActivity.UserSession;
import com.tatait.tataweibo.adapter.HomeAdapters;
import com.tatait.tataweibo.pojo.ContentInfo;
import com.tatait.tataweibo.pojo.UserInfo;
import com.tatait.tataweibo.util.Tools;
/**
 * 主页面
 * @author WSXL
 *
 */
public class HomeActivity extends Activity {
	private static final String TAG = "HomeActivity";

	private LinearLayout load_progress = null;
	private ListView home_lv = null;
	// 保存需要显示的多条微博数据
	public ArrayList<ContentInfo> contentList = null;
	private Button refresh_weibo, writer_weibo;
	private Oauth2AccessToken accessToken = null;
	private HomeAdapters adapater = null;
	private Tools tools;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		load_progress = (LinearLayout) findViewById(R.id.load_progress);

		home_lv = (ListView) findViewById(R.id.home_lv);
		refresh_weibo = (Button) findViewById(R.id.btn_refresh);
		writer_weibo = (Button) findViewById(R.id.btn_writer);
		MyClick click = new MyClick();
		refresh_weibo.setOnClickListener(click);
		writer_weibo.setOnClickListener(click);
		Log.i(TAG, "onCreate");
	}

	@Override
	protected void onResume() {
		Log.i(TAG, "onResume");
		init();
		super.onResume();
	}

	class MyClick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_refresh:
				init();
				break;
			case R.id.btn_writer:
				// Intent intent = new Intent(HomeActivity.this,
				// WriterWeiboActivity.class);
				// startActivity(intent);
				break;
			}
		}
	}

	public void init() {
		Log.i(TAG, "init");
		tools = Tools.getInstance();
		// 获得主要显示的数据
		accessToken = AccessTokenKeeper.readAccessToken(this);
		UserInfo user = UserSession.nowUser;
		Toast.makeText(this, user.getUser_id().toString(), Toast.LENGTH_LONG)
				.show();
		contentList = tools.loadHomeData(Constants.GET_PUBLIC, accessToken);
		//
		if (contentList != null) {
			// 创建一个Adapter设置ListView中的每项Item项数据
			adapater = new HomeAdapters(this, contentList);
			// 设置listview上的item点击事件处理
			home_lv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Object obj = view.getTag();
					if (obj != null) {
						// 获得一条weibo数据的id（唯一标识）
						String weiboId = obj.toString();
						// 跳转到一条具体的微博显示页面
						// Intent intent = new
						// Intent(HomeActivity.this,ContentActivity.class);
						Bundle bundle = new Bundle();
						// 参数的设置
						bundle.putString("weiboId", weiboId);
						// 参数绑定
						// intent.putExtras(bundle);
						// startActivity(intent);
					}
				}
			});
			// 将adapter和listview关联
			home_lv.setAdapter(adapater);
		}
		// 隐藏进度条
		load_progress.setVisibility(View.GONE);
	}
}
