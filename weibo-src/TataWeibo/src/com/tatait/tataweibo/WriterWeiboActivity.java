package com.tatait.tataweibo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.tatait.tataweibo.LoadActivity.UserSession;
import com.tatait.tataweibo.bean.UserInfo;
import com.tatait.tataweibo.util.FileService;
import com.tatait.tataweibo.util.Tools;

/**
 * 发微博（图 文）
 * 
 * @author WSXL
 * 
 */
public class WriterWeiboActivity extends Activity {
	private static final String TAG = "WriterWeiboActivity";
	private Oauth2AccessToken mAccessToken;
	private Button refresh_weibo, writer_weibo, menu_btn;
	private Button upImage_btn;
	private ImageView images;
	private EditText weibo_txt;
	private TextView tv_text_limit, txt_wb_title = null;
	// 发送图片的路径
	private String image_path = "";
	LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
			LayoutParams.WRAP_CONTENT);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.writer);
		weibo_txt = (EditText) findViewById(R.id.weibo_txt);
		tv_text_limit = (TextView) findViewById(R.id.tv_text_limit);
		txt_wb_title = (TextView) findViewById(R.id.txt_wb_title);
		// 设置当前登陆人
		UserInfo user = UserSession.nowUser;
		txt_wb_title.setText(user.getUser_name());
		menu_btn = (Button) findViewById(R.id.menu_btn);
		refresh_weibo = (Button) findViewById(R.id.btn_refresh);
		refresh_weibo.setVisibility(View.GONE);
		// 设置返回按钮图片和底色
		menu_btn.setBackgroundResource(R.drawable.left);
		params.leftMargin = 0;
		params.topMargin = 6;
		menu_btn.setLayoutParams(params);
		writer_weibo = (Button) findViewById(R.id.btn_writer);
		upImage_btn = (Button) findViewById(R.id.upImage_btn);
		images = (ImageView) findViewById(R.id.images);
		// 设置发送按钮图片
		writer_weibo.setBackgroundResource(R.drawable.send_default);
		MyClick click = new MyClick();
		writer_weibo.setOnClickListener(click);
		upImage_btn.setOnClickListener(click);
		mAccessToken = AccessTokenKeeper.readAccessToken(this);
		// 注册输入框内容监听器
		weibo_txt.addTextChangedListener(new TextWatcher() {
			/**
			 * 当输入框的内容变化的时候执行
			 */
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				boolean flag = false;
				// 获得输入框的内容
				String mText = weibo_txt.getText().toString();
				int len = mText.length();// 获得输入框的内容长度
				if (len <= Constants.WEIBO_MAX_LENGTH) {// 比较已经输入的内容长度是不是超过了规定的长度（140）
					len = Constants.WEIBO_MAX_LENGTH - len;// 计算还允许输入内容个数
					tv_text_limit.setTextColor(Color.GREEN);// 设置提示text颜色
					if (writer_weibo.getVisibility() == View.GONE) {// 判断发送按钮是不是启用状态
						// writer_weibo.setEnabled(true);// 启用发送按钮
						writer_weibo.setVisibility(View.VISIBLE);// 显示发送按钮
					}
				} else {
					len = len - Constants.WEIBO_MAX_LENGTH;// 计算输入内容超过允许输入的个数
					tv_text_limit.setTextColor(Color.RED);// 设置提示text颜色
					if (writer_weibo.getVisibility() == View.VISIBLE) {// 判断发送按钮是不是启用状态
						// writer_weibo.setEnabled(false);// 设置发送按钮是不启用状态
						writer_weibo.setVisibility(View.GONE);// 隐藏发送按钮
					}
					flag = true;
				}
				tv_text_limit.setText(flag ? "-" + len : String.valueOf(len));// 设置允许输入内容个数提示内容
			}

			/**
			 * 当输入框内容改变后执行
			 */
			public void afterTextChanged(Editable s) {
			}

			/**
			 * 当输入框内容改前执行
			 */
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
		});

	}

	class MyClick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.upImage_btn:// 添加图片
				imageChooseItem(Constants.meun_items);
				break;
			case R.id.btn_writer:// 发送微博
				int flag = 1;// 1:表示发送文字微博 2:表示发送图文微博
				List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("status", weibo_txt.getText().toString()));
				if (!"".equals(image_path)) {
					flag = 2;
					params.add(new BasicNameValuePair("pic", image_path));
				}
				if (Tools.getInstance().writerWeibo(params, flag,mAccessToken)) {
					Toast.makeText(
							WriterWeiboActivity.this,
							WriterWeiboActivity.this.getResources().getString(
									R.string.send_sucess), Toast.LENGTH_LONG)
							.show();
					Intent intent = new Intent(WriterWeiboActivity.this,
							HomeActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(
							WriterWeiboActivity.this,
							WriterWeiboActivity.this.getResources().getString(
									R.string.send_failed), Toast.LENGTH_LONG)
							.show();
				}
				break;
			case R.id.menu_btn:// 返回按钮
				Intent intent = new Intent(WriterWeiboActivity.this,
						HomeActivity.class);
				startActivity(intent);
				break;
			}

		}
	}

	/**
	 * 表示去sdcard获得图片
	 */
	private static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0;
	/**
	 * 表示去相机获得图片
	 */
	private static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 1;
	private String thisLarge = null;

	/**
	 * 操作选择
	 * 
	 * @param items
	 */
	public void imageChooseItem(CharSequence[] items) {
		AlertDialog imageDialog = new AlertDialog.Builder(this)
				.setTitle("添加图片")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						// 手机选图
						if (item == 0) {
							Intent intent = new Intent(
									Intent.ACTION_GET_CONTENT);
							intent.setType("image/*");
							startActivityForResult(intent,
									REQUEST_CODE_GETIMAGE_BYSDCARD);
						} else if (item == 1) {// 拍照
							Intent intent = new Intent(
									"android.media.action.IMAGE_CAPTURE");

							String camerName = Tools.getFileName();
							String fileName = "facejava-" + camerName + ".png";

							File camerFile = new File(Tools.getCamerPath(),
									fileName);

							image_path = camerFile.getPath();
							thisLarge = Tools
									.getLatestImage(WriterWeiboActivity.this);

							Uri originalUri = Uri.fromFile(camerFile);
							intent.putExtra(MediaStore.EXTRA_OUTPUT,
									originalUri);
							startActivityForResult(intent,
									REQUEST_CODE_GETIMAGE_BYCAMERA);
						} else if (item == 2) {
							thisLarge = null;
							images.setImageBitmap(null);
							image_path = "";
							images.setVisibility(View.GONE);
						}
					}
				}).create();

		imageDialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_GETIMAGE_BYSDCARD) {
			if (resultCode != RESULT_OK) {
				return;
			}
			if (data == null) {
				return;
			}
			String[] imageArray = Tools.getAbsoluteImagePath(this,
					data.getData());

			String suffix = FileService.getFileFormat(imageArray[1]);
			if (suffix.equals("") || !Constants.imageType.containsKey(suffix)) {
				Toast.makeText(this, "请选择图片文件！", Toast.LENGTH_SHORT).show();
				return;
			}
			File file = new File(imageArray[1]);
			Bitmap bitmap_Image = BitmapFactory.decodeFile(file.getPath());
			if (bitmap_Image != null) {
				images.setVisibility(View.VISIBLE);
				images.setImageBitmap(bitmap_Image);
				image_path = file.getPath();
			}
		} else if (requestCode == REQUEST_CODE_GETIMAGE_BYCAMERA) {// 拍摄图片
			File file = new File(image_path);
			Bitmap bitmap_Image = BitmapFactory.decodeFile(file.getPath());
			if (bitmap_Image != null) {
				images.setVisibility(View.VISIBLE);
				images.setImageBitmap(bitmap_Image);
			}
		}

		images.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(android.content.Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(new File(thisLarge)),
						"image/*");
				startActivity(intent);
			}
		});

		super.onActivityResult(requestCode, resultCode, data);

	}

	// 主菜单点击返回键，弹出对话框
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent intent = new Intent(WriterWeiboActivity.this,
					HomeActivity.class);
			startActivity(intent);
		}
		return super.onKeyDown(keyCode, event);
	}
}
