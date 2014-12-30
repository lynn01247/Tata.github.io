/**
 * @(#)HomeAdapters.java
 *
 * @Version: 1
 * @JDK: jdk 1.6.0.XXX
 * @Module: CrazyitWeibo
 */
/*- 				History
 **********************************************
 *  ID      DATE           PERSON       REASON
 *  1     2011-11-29     hanfei.li    Created
 **********************************************
 */

package com.tatait.tataweibo.adapter;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.drawable.Drawable;
import android.text.util.Linkify;
import android.text.util.Linkify.MatchFilter;
import android.text.util.Linkify.TransformFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tatait.tataweibo.Constants;
import com.tatait.tataweibo.HomeActivity;
import com.tatait.tataweibo.R;
import com.tatait.tataweibo.bean.ContentInfo;
import com.tatait.tataweibo.util.AsyncImageLoader;
import com.tatait.tataweibo.util.AsyncImageLoader.ImageCallback;

/**
 * 主页适配器类
 * 
 * @author WSXL
 * 
 */

public class HomeAdapters extends BaseAdapter {

	/**
	 * 为提高效率，缓存数据准备的一个自定义类 对应一条微博数据
	 */
	class ContentHolder {
		public ImageView content_image; // 对应微博显示的图片
		public ImageView content_icon; // 对应发微博人的头像
		public TextView content_user; // 对应发微博人的名称
		public TextView content_time; // 对应发微博的时间
		public TextView content_text; // 对应发微博的内容
		public TextView content_source; // 对应发微博的来自
		public TextView id; // 对应发微博的来自
	}

	private HomeActivity homeActivity = null;
	/**
	 * 数据集
	 */
	public LinkedList<ContentInfo> contentList = null;

	/**
	 * @param homeActivity
	 * @param contentList
	 */
	public HomeAdapters(HomeActivity homeActivity,
			LinkedList<ContentInfo> contentList) {
		this.homeActivity = homeActivity;
		this.contentList = contentList;
	}

	// 微博图片的异步下载类
	AsyncImageLoader asyncImageLoader;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		asyncImageLoader = new AsyncImageLoader();
		// 记载微博的每条需要显示在什么布局上的布局对象
		convertView = LayoutInflater.from(
				this.homeActivity.getApplicationContext()).inflate(
				R.layout.home_item_weibo, null);
		// 创建一个层次对应组件的类
		ContentHolder ch = new ContentHolder();
		// 将R.layout.home_item 对应的组件和ContentHolder对象进行关联，提高效率
		ch.content_icon = (ImageView) convertView
				.findViewById(R.id.content_head);
		ch.content_time = (TextView) convertView
				.findViewById(R.id.content_time);
		ch.content_user = (TextView) convertView
				.findViewById(R.id.content_user);
		ch.id = (TextView) convertView.findViewById(R.id.content_user_id);
		ch.content_image = (ImageView) convertView
				.findViewById(R.id.content_image);
		ch.content_text = (TextView) convertView
				.findViewById(R.id.content_text);
		//超链接处理
//		extractMention2Link(ch.content_text);
		ch.content_source = (TextView) convertView
				.findViewById(R.id.content_source);
		// 获得一条微博数据
		ContentInfo info = this.contentList.get(position);
		if (info != null) {
			convertView.setTag(info.getId());
			ch.content_user.setText(info.getUserName());
			ch.content_time.setText(info.getTime());
			String str = info.getContent_source();
			Pattern p = Pattern.compile("<a[^>]*>([^<]*)</a>");
			Matcher m = p.matcher(str);
			String source = "";
			while (m.find()) {
				source = m.group(1);
			}
			ch.content_source.setText("来自 " + source);
			ch.content_text.setText(info.getText(),
					TextView.BufferType.SPANNABLE);
			ch.id.setText(info.getId());
			if (info.getHaveImage()) {// 是否有图片信息
				// 异步记载图片内容
				Drawable content_image = asyncImageLoader.loadDrawable(
						info.getImage_context(), ch.content_image,
						new ImageCallback() {
							public void imageLoaded(Drawable drawable,
									ImageView iv, String url) {
								iv.setImageDrawable(drawable);
							}
						});
				if (content_image == null) {
					ch.content_image.setImageResource(R.drawable.picture_dark);
				} else {
					ch.content_image.setImageDrawable(content_image);
				}
			}
			// 异步记载用户头像数据
			Drawable cachedImage = asyncImageLoader.loadDrawable(
					info.getUserIcon(), ch.content_icon, new ImageCallback() {
						public void imageLoaded(Drawable drawable,
								ImageView iv, String url) {
							iv.setImageDrawable(drawable);
						}
					});
			if (cachedImage == null) {
				ch.content_icon.setImageResource(R.drawable.people);
			} else {
				ch.content_icon.setImageDrawable(cachedImage);
			}
		}
		return convertView;
	}

	@Override
	public int getCount() {
		return this.contentList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.contentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	/**
	 * 超链接处理
	 * 
	 * @param v
	 */
	public static void extractMention2Link(TextView v) {
		v.setAutoLinkMask(0);
		Pattern mentionsPattern = Pattern.compile("@(\\w+?)(?=\\W|$)(.)");
		String mentionsScheme = String.format("%s/?%s=",
				Constants.MENTIONS_SCHEMA, Constants.PARAM_UID);
		Linkify.addLinks(v, mentionsPattern, mentionsScheme, new MatchFilter() {
			@Override
			public boolean acceptMatch(CharSequence s, int start, int end) {
				return s.charAt(end - 1) != '.';
			}
		}, new TransformFilter() {
			@Override
			public String transformUrl(Matcher match, String url) {
				return match.group(1);
			}
		});
		Pattern trendsPattern = Pattern.compile("#(\\w+?)#");
		String trendsScheme = String.format("%s/?%s=", Constants.TRENDS_SCHEMA,
				Constants.PARAM_UID);
		Linkify.addLinks(v, trendsPattern, trendsScheme, null,
				new TransformFilter() {
					@Override
					public String transformUrl(Matcher match, String url) {
						return match.group(1);
					}
				});

	}
}