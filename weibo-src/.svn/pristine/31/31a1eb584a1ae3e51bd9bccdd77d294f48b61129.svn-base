package com.tatait.tataweibo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 数据库工具类
 * @author WSXL
 *
 */
public class DBHelper extends SQLiteOpenHelper {
	/**
	 * 构造器
	 * @param context 上下文
	 * @param name 数据库名称
	 * @param factory 游标工厂
	 * @param version 数据库版本
	 */
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 创建构造器
	 * @param context 上下文
	 */
	public DBHelper(Context context){
		super(context, DBInfo.DB.DB_NAME, null, DBInfo.DB.VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DBInfo.Table.CREATE_USER_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DBInfo.Table.DROP_TABLE);
		onCreate(db);
	}

}
