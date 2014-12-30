package com.tatait.tataweibo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.tatait.tataweibo.Constants;
import com.tatait.tataweibo.LoadActivity;
import com.tatait.tataweibo.LoadActivity.UserSession;
import com.tatait.tataweibo.R;
import com.tatait.tataweibo.bean.ContentInfo;
import com.tatait.tataweibo.bean.UserInfo;

/**
 * 工具类
 * 
 * @author WSXL
 * 
 */
public class Tools {
	private static final String TAG = "Tools";
	private MultiThreadedHttpConnectionManager connectionManager;
	private static Tools instance = null;
	LinkedList<ContentInfo> contentList = null;
	private FileService file;
	int page;

	private Tools() {
	}

	public HttpClient httpClientInit() {
		int maxConPerHost = 150;
		int conTimeOutMs = 30000;
		int soTimeOutMs = 30000;
		connectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = connectionManager.getParams();
		params.setDefaultMaxConnectionsPerHost(maxConPerHost);
		params.setConnectionTimeout(conTimeOutMs);
		params.setSoTimeout(soTimeOutMs);

		HttpClientParams clientParams = new HttpClientParams();
		// 忽略cookie 避免 Cookie rejected 警告
		clientParams.setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		HttpClient client = new org.apache.commons.httpclient.HttpClient(
				clientParams, connectionManager);
		return client;
	}

	public static Tools getInstance() {
		if (instance == null) {
			instance = new Tools();
		}
		return instance;
	}

	/**
	 * 检测网络状态
	 * 
	 * @param loadActivity
	 */
	public static void checkNetwork(final LoadActivity loadActivity) {
		ConnectivityManager manager = (ConnectivityManager) loadActivity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 默认没有联网状态
		boolean isNetworkAvailabel = false;
		if (manager != null) {
			NetworkInfo[] info = manager.getAllNetworkInfo();
			if (info != null) {
				for (NetworkInfo networkInfo : info) {
					if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
						// 检测到网络为连接状态
						isNetworkAvailabel = true;
					}
				}
			}
		}
		if (!isNetworkAvailabel) {
			TextView netSetting = new TextView(loadActivity);
			netSetting.setText("当前没有可用的网络连接，请设置网络");
			new AlertDialog.Builder(loadActivity)
					.setIcon(R.drawable.sad)
					.setTitle("网络状态提示")
					.setView(netSetting)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									loadActivity.startActivity(new Intent(
											Settings.ACTION_WIRELESS_SETTINGS));
									loadActivity.finish();
								}
							}).create().show();
		}
	}

	/**
	 * 从URL获取图片
	 * 
	 * @param url
	 * @return
	 */
	public static Drawable getDrawableFromUrl(String url) {
		URLConnection urls;
		try {
			urls = new URL(url).openConnection();
			return Drawable.createFromStream(urls.getInputStream(), "image");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从流中获得服务器返回的字符串数据
	 * 
	 * @param response
	 * @return String
	 * @throws IOException
	 */
	public String getData(HttpResponse response) throws IOException {
		InputStream is = response.getEntity().getContent();
		Reader reader = new BufferedReader(new InputStreamReader(is), 4000);
		StringBuilder buffer = new StringBuilder((int) response.getEntity()
				.getContentLength());
		try {
			char[] tmp = new char[1024];
			int l;
			while ((l = reader.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
		} finally {
			reader.close();
		}
		return buffer.toString();
	}

	/**
	 * 处理http getmethod 请求
	 * 
	 * @param url
	 *            拼接好参数的URL
	 * @param params
	 */
	public Response get(HttpClient client, String url,
			List<BasicNameValuePair> params) {
		Log.i("online", "getting data...........");
		String getPath = "";
		for (int i = 0; i < params.size(); i++) {
			BasicNameValuePair nameValuePair = (BasicNameValuePair) params
					.get(i);
			if (!"".equals(nameValuePair.getName())
					&& nameValuePair.getName() != null) {
				getPath += nameValuePair.getName() + "="
						+ nameValuePair.getValue() + "&";
			}
		}
		getPath = url + "?" + getPath.subSequence(0, getPath.length() - 1);
		GetMethod getmethod = new GetMethod(getPath);
		return httpRequest(client, getmethod);

	}

	/**
	 * 处理http post请求
	 */
	public Response post(HttpClient client, String url,
			List<BasicNameValuePair> params) {
		PostMethod postMethod = new PostMethod(url);
		for (int i = 0; i < params.size(); i++) {
			BasicNameValuePair nameValuePair = (BasicNameValuePair) params
					.get(i);
			if (!"".equals(nameValuePair.getName())
					&& nameValuePair.getName() != null) {
				postMethod.addParameter(nameValuePair.getName(),
						nameValuePair.getValue());
			}
		}
		HttpMethodParams param = postMethod.getParams();
		param.setContentCharset("UTF-8");
		return httpRequest(client, postMethod);
	}

	/**
	 * 兼容get和post方式请求数据
	 * 
	 * @param method
	 * @param WithTokenHeader
	 * @return
	 */
	public Response httpRequest(HttpClient client, HttpMethod method) {
		Response response = new Response();
		try {
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(3, false));
			client.executeMethod(method);

			response.setResponseAsString(method.getResponseBodyAsString());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return response;
	}

	/**
	 * 获得主页数据
	 * 
	 * @param flag
	 *            TODO
	 * @param accessToken
	 */
	public LinkedList<ContentInfo> loadHomeData(int flag,
			Oauth2AccessToken accessToken, Context context) {
		file = new FileService(context);
		if (flag == 0) {
			// 首次加载
			page = 1;
		} else if (flag == 2) {
			// 获得主页数据--加载更多
			++page;
		}
		String since_id = "0";
		String count = "5";
		String jsonString = "";
		if (UserSession.nowUser != null) {
			if (flag == 0) {
				// 首次加载
				if (file.dataExpired()) {
					HttpClient client = Tools.getInstance().httpClientInit();
					// 创建参数绑定集合
					List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
					// 参数绑定
					params.add(new BasicNameValuePair("source",
							Constants.APP_KEY));
					params.add(new BasicNameValuePair("access_token",
							accessToken.getToken()));
					params.add(new BasicNameValuePair("count", count));
					params.add(new BasicNameValuePair("page", Integer.valueOf(
							page).toString()));

					// 执行数据获取操作
					Response res = Tools.getInstance().get(client,
							Constants.WEIBO_GET_STATUS_PUBLIC_TIMELINE, params);
					jsonString = res.toString();
					file.saveDate(jsonString);
				} else {
					try {
						jsonString = file.readFile(FileService.PUBLICLINEINFO);
					} catch (Exception e) {
						Log.e(TAG, e.toString());
					}
				}
			} else if (flag == 1) {
				since_id = contentList.getFirst().getId();
				HttpClient client = Tools.getInstance().httpClientInit();
				List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("source", Constants.APP_KEY));
				params.add(new BasicNameValuePair("access_token", accessToken
						.getToken()));
				params.add(new BasicNameValuePair("since_id", since_id));
				Response res = Tools.getInstance().get(client,
						Constants.WEIBO_GET_STATUS_PUBLIC_TIMELINE, params);
				jsonString = res.toString();
			} else if (flag == 2) {
				HttpClient client = Tools.getInstance().httpClientInit();
				List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("source", Constants.APP_KEY));
				params.add(new BasicNameValuePair("access_token", accessToken
						.getToken()));
				params.add(new BasicNameValuePair("count", count));
				params.add(new BasicNameValuePair("page", Integer.valueOf(page)
						.toString()));
				Response res = Tools.getInstance().get(client,
						Constants.WEIBO_GET_STATUS_PUBLIC_TIMELINE, params);
				jsonString = res.toString();
			}
			try {
				JSONObject json = new JSONObject(jsonString);
				String statuses = json.getString("statuses");
				JSONArray data = new JSONArray(statuses);

				// 因为返回的是JSONArray表示包含了多条weibo数据，所以进行循环解析
				for (int i = 0; i < data.length(); i++) {
					// 获得单条微博数据
					JSONObject d = data.getJSONObject(i);
					if (d != null) {
						// 创建一个对象存储每条微博数据
						ContentInfo contentInfo = new ContentInfo();
						// 获得用户数据
						JSONObject u = d.getJSONObject("user");
						if (d.has("retweeted_status")) {
							JSONObject r = d.getJSONObject("retweeted_status");
						}
						// 获得一条wiebo id
						String id = d.getString("id");
						// 获得发weibo 用户id
						String userId = u.getString("id");
						// 获得发weibo 用户的名称
						String userName = u.getString("screen_name");
						// 获得发weibo 用户的头像url链接
						String userIcon = u.getString("profile_image_url");
						// 获得发weibo的时间
						String time = d.getString("created_at");
						// 获得weibo内容
						String text = d.getString("text");
						Boolean haveImg = false;
						// 判断微博存在带图片信息
						if (d.has("thumbnail_pic")) {
							haveImg = true;
							// 获得缩略图url链接
							String thumbnail_pic = d.getString("thumbnail_pic");
							contentInfo.setImage_context(thumbnail_pic);

						}
						// 通过字符串构造发微博的时间
						Date startDate = new Date(time);
						// 获得当前时间
						Date nowDate = Calendar.getInstance().getTime();
						// 比较发表微博时间和当前时间之间距离时常
						time = new DateUtils().twoDateDistance(startDate,
								nowDate);
						String content_source = d.getString("source");
						if (contentList == null) {
							// 创建存储每条微博的集合
							contentList = new LinkedList<ContentInfo>();
						}
						// 数据设置
						contentInfo.setId(id);
						contentInfo.setUserId(userId);
						contentInfo.setUserName(userName);
						contentInfo.setTime(time);
						contentInfo.setText(text);
						contentInfo.setHaveImage(haveImg);
						contentInfo.setUserIcon(userIcon);
						contentInfo.setContent_source(content_source);

						if (flag == 1) {
							// 将单条微博数据设置到集合前方
							contentList.addFirst(contentInfo);
						} else {
							// 将单条微博数据设置到集合尾部
							contentList.addLast(contentInfo);
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return contentList;
	}

	/**
	 * 
	 * @param params
	 * @param flag
	 *            1 txt 2txt_image
	 * @param mAccessToken
	 * @return
	 */
	public boolean writerWeibo(List<BasicNameValuePair> params, int flag,
			Oauth2AccessToken mAccessToken) {
		boolean status = true;
		String url = "";
		params.add(new BasicNameValuePair("source", Constants.APP_KEY));
		params.add(new BasicNameValuePair("access_token", mAccessToken
				.getToken()));
		switch (flag) {
		case 1:
			url = Constants.WEIBO_WRITER_TXT;
			break;
		case 2:
			url = Constants.WEIBO_WRITER_TXT_IMG;
			break;
		}

		HttpClient client = Tools.getInstance().httpClientInit();
		Tools.getInstance().post(client, url, params);
		return status;
	}

	/**
	 * 使用当前时间戳拼接一个唯一的文件名
	 * 
	 * @param format
	 * @return
	 */
	public static String getFileName() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SS");
		return format.format(new Timestamp(System.currentTimeMillis()));
	}

	/**
	 * 获取照相机使用的目录
	 * 
	 * @return
	 */
	public static String getCamerPath() {
		return Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DCIM).toString();
	}

	/**
	 * 获取SD卡中最新图片路径
	 * 
	 * @return
	 */
	public static String getLatestImage(Activity activity) {
		String latestImage = null;
		String[] items = { MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DATA };
		Cursor cursor = activity.managedQuery(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, items, null,
				null, MediaStore.Images.Media._ID + " desc");

		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				latestImage = cursor.getString(1);
				break;
			}
		}

		return latestImage;
	}

	/**
	 * 根据路径获取适应屏幕大小的Bitmap
	 * 
	 * @param context
	 * @param filePath
	 * @param maxWidth
	 * @return
	 */
	public static Bitmap getScaleBitmap(Context context, String filePath) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = 4;

		return BitmapFactory.decodeFile(filePath, opts);
	}

	/**
	 * 根据Uri获得图片的绝对路径
	 * 
	 * @param uri
	 * @return String[]{imgNo,imgPath,imgName,imgSize}
	 */
	public static String[] getAbsoluteImagePath(Activity activity, Uri uri) {

		Cursor cursor = activity.getContentResolver().query(uri, null, null,
				null, null);
		cursor.moveToFirst();
		String imgNo = cursor.getString(0); // 图片编号
		String imgPath = cursor.getString(1); // 图片文件路径
		String imgSize = cursor.getString(2); // 图片大小
		String imgName = cursor.getString(3); // 图片文件名
		return new String[] { imgNo, imgPath, imgName, imgSize };
	}
}