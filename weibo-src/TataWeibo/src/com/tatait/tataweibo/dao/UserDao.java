package com.tatait.tataweibo.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.tatait.tataweibo.bean.UserInfo;
import com.tatait.tataweibo.db.DBHelper;
import com.tatait.tataweibo.db.DBInfo;
/**
 * 数据库dao
 * @author WSXL
 *
 */
public class UserDao {
	private DBHelper dbHelper = null;
	private SQLiteDatabase db = null;

	private String[] columns = { DBInfo.Table._ID,DBInfo.Table.USER_ID,DBInfo.Table.USER_NAME,DBInfo.Table.TOKEN,
			DBInfo.Table.TOKEN_SECRET,DBInfo.Table.DESCRIPTION,DBInfo.Table.USER_HEAD,DBInfo.Table.LOCATION
			,DBInfo.Table.GENDER,DBInfo.Table.FAVOURITES_COUNT,DBInfo.Table.FOLLOWERS_COUNT,DBInfo.Table.FRIENDS_COUNT
			,DBInfo.Table.STATUSES_COUNT};
	public UserDao(Context context) {
		dbHelper = new DBHelper(context);
	}

	// 新增
	public long insertUser(UserInfo user){
		db = dbHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(DBInfo.Table.USER_ID, user.getUser_id());
		values.put(DBInfo.Table.USER_NAME, user.getUser_name());
		values.put(DBInfo.Table.TOKEN, user.getToken());
		values.put(DBInfo.Table.TOKEN_SECRET, user.getToken_secret());
		values.put(DBInfo.Table.DESCRIPTION, user.getDescription());
		values.put(DBInfo.Table.LOCATION, user.getLocation());
		values.put(DBInfo.Table.GENDER, user.getGender());
		values.put(DBInfo.Table.FAVOURITES_COUNT, user.getFavourites_count());
		values.put(DBInfo.Table.FOLLOWERS_COUNT, user.getFollowers_count());
		values.put(DBInfo.Table.FRIENDS_COUNT, user.getFriends_count());
		values.put(DBInfo.Table.STATUSES_COUNT, user.getStatuses_count());
		//将图片类型的数据进行转换再存储到BLOB类型中
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		BitmapDrawable drawable= (BitmapDrawable)user.getUser_head();
		//压缩成PNG编码数据，存储质量为100%
		drawable.getBitmap().compress(CompressFormat.PNG, 100, os);
		
		values.put(DBInfo.Table.USER_HEAD, os.toByteArray());
		long rowId = db.insert(DBInfo.Table.USER_TABLE, DBInfo.Table.USER_NAME, values);
		
		db.close();
		
		return rowId;
	}

	// 更新
	public int updateUser(UserInfo user) {
		return 1;
	}

	// 删除
	public void deleteUser(UserInfo user) {

	}

	// 更加ID查询用户
	public UserInfo findUserByUserId(String user_id) {
		return null;
	}

	// 查询全部用户
	public List<UserInfo> findAllUser() {
		db = dbHelper.getReadableDatabase();
		List<UserInfo> userList = null;
		UserInfo user = null;
		Cursor cursor = db.query(DBInfo.Table.USER_TABLE, columns, null, null, null, null, null);
		if(cursor!=null&&cursor.getCount()>0){
			userList = new ArrayList<UserInfo>(cursor.getCount());
			while(cursor.moveToNext()){
				user = new UserInfo();
				user.setId(cursor.getLong(cursor.getColumnIndex(DBInfo.Table._ID)));
				user.setUser_id(cursor.getString(cursor.getColumnIndex(DBInfo.Table.USER_ID)));
				user.setUser_name(cursor.getString(cursor.getColumnIndex(DBInfo.Table.USER_NAME)));
				user.setToken(cursor.getString(cursor.getColumnIndex(DBInfo.Table.TOKEN)));
				user.setToken_secret(cursor.getString(cursor.getColumnIndex(DBInfo.Table.TOKEN_SECRET)));
				user.setDescription(cursor.getString(cursor.getColumnIndex(DBInfo.Table.DESCRIPTION)));
				byte[] btyeHead = cursor.getBlob(cursor.getColumnIndex(DBInfo.Table.USER_HEAD));
				
				ByteArrayInputStream is = new ByteArrayInputStream(btyeHead);
				Drawable user_head = Drawable.createFromStream(is, "headImage");
				user.setUser_head(user_head);
				user.setLocation(cursor.getString(cursor.getColumnIndex(DBInfo.Table.LOCATION)));
				user.setGender(cursor.getString(cursor.getColumnIndex(DBInfo.Table.GENDER)));
				user.setFavourites_count(cursor.getString(cursor.getColumnIndex(DBInfo.Table.FAVOURITES_COUNT)));
				user.setFollowers_count(cursor.getString(cursor.getColumnIndex(DBInfo.Table.FOLLOWERS_COUNT)));
				user.setFriends_count(cursor.getString(cursor.getColumnIndex(DBInfo.Table.FRIENDS_COUNT)));
				user.setStatuses_count(cursor.getString(cursor.getColumnIndex(DBInfo.Table.STATUSES_COUNT)));
				
				userList.add(user);
			}
		}
		cursor.close();
		db.close();
		return userList;
	}
}
