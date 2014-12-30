package com.tatait.tataweibo.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileService {
	private File public_line_info_file;
	public static final String PUBLICLINEINFO = "home_timeline.txt";
	private static final String TAG = "FileService";
	private Context context;

	public FileService(Context context) {
		this.context = context;
	}

	/**
	 * 保存数据
	 * 
	 * @param content
	 */
	public void saveDate(String content) {
		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				saveToSDCard(PUBLICLINEINFO, content);
			} else {
				save(PUBLICLINEINFO, content);
			}
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}

	}

	/**
	 * 读取文件内容
	 * 
	 * @param filename
	 *            文件名称
	 * @return
	 * @throws Exception
	 */
	public String readFile(String filename) throws Exception {
		FileInputStream inStream;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File file = new File(context.getExternalFilesDir(null),
					filename);
			inStream = new FileInputStream(file);
		} else {
			inStream = context.openFileInput(filename);
		}
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();// 得到文件的二进制数据
		outStream.close();
		inStream.close();
		return new String(data);
	}

	/**
	 * 判断文件是否过期
	 * 
	 * @return
	 */
	public boolean dataExpired() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			public_line_info_file = new File(context.getExternalFilesDir(null)
					+ "/" + PUBLICLINEINFO);
		} else {
			public_line_info_file = new File(context.getFilesDir() + "/"
					+ PUBLICLINEINFO);
		}
		if (public_line_info_file.exists()) {
			long lastTime = public_line_info_file.lastModified();
			Date lastDate = new Date(lastTime);
			Date nowDate = Calendar.getInstance().getTime();
			long between = (nowDate.getTime() - lastDate.getTime()) / 1000;// 除以1000是为了转换成秒
			if (between >= 120) {
				return true;
			} else {
				return false;
			}
		} else {
			// 不存在则新建
			return true;
		}

	}

	/**
	 * 以私有文件保存内容到内存卡
	 * 
	 * @param filename
	 *            文件名称
	 * @param content
	 *            文件内容
	 * @throws Exception
	 */
	public void saveToSDCard(String filename, String content) throws Exception {
		File file = new File(context.getExternalFilesDir(null),
				filename);
		FileOutputStream outStream = new FileOutputStream(file);
		outStream.write(content.getBytes());
		outStream.close();
	}

	/**
	 * 以私有文件保存内容到本地
	 * 
	 * @param filename
	 *            文件名称
	 * @param content
	 *            文件内容
	 * @throws Exception
	 */
	public void save(String filename, String content) throws Exception {
		FileOutputStream outStream = context.openFileOutput(filename,
				Context.MODE_PRIVATE);
		outStream.write(content.getBytes());
		outStream.close();
	}
	
	public void clearData() {
		Tools.getInstance().contentList = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			public_line_info_file = new File(context.getExternalFilesDir(null)
					+ "/" + PUBLICLINEINFO);
		} else {
			public_line_info_file = new File(context.getFilesDir() + "/"
					+ PUBLICLINEINFO);
		}
		if (public_line_info_file.exists()) {
			public_line_info_file.delete();
		}
	}
	
	/**
     * 获取文件获得后缀名
     * @param fileName
     * @return
     */
    public static String getFileFormat(String fileName) {
        if (fileName == null || fileName == "") {
            return "";
        }
        int point = fileName.lastIndexOf('.');
        return fileName.substring(point + 1);
    }
}
