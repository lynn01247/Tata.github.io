package com.tatait.tataweibo.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Download {
	/**
	 * 通过url参数获得调用的图片资源
	 * 
	 * @param url
	 * @return Drawable
	 */
	public static Drawable getDrawableFromUrl(String url) {
		InputStream in = null;
		URLConnection con = null;
		try {
			// 打开连接
			con = new URL(url).openConnection();
			in = con.getInputStream();
			return Drawable.createFromStream(in, "image");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 从一个URL取得一个图片
	 * 
	 * @param url
	 * @return
	 */
	public static BitmapDrawable getImageFromUrl(URL url) {
		BitmapDrawable icon = null;
		try {
			HttpURLConnection hc = (HttpURLConnection) url.openConnection();
			icon = new BitmapDrawable(hc.getInputStream());
			hc.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return icon;
	}
}
