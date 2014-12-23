package com.tatait.tataweibo.db;

public class DBInfo {

	public static class DB {
		/**
		 * 数据库名称
		 */
		public static final String DB_NAME = "tatait_weibo.db";
		/**
		 * 数据库版本
		 */
		public static final int VERSION = 1;
	}

	public static class Table {
		// 用户表
		public static String USER_TABLE = "userinfo";
		// 用户表字段
		public static String _ID = "_id";
		public static String USER_ID = "user_id";
		public static String USER_NAME = "user_name";
		public static String TOKEN = "token";
		public static String TOKEN_SECRET = "token_secret";
		public static String DESCRIPTION = "description";
		public static String USER_HEAD = "user_head";
		// 数据库建表操作
		public static final String CREATE_USER_TABLE = "create table if not exists "
				+ USER_TABLE + "(" + _ID
				+ " integer primary key autoincrement," + USER_ID + " text,"
				+ USER_NAME + " text," + TOKEN + " text," + TOKEN_SECRET
				+ " text," + DESCRIPTION + " text," + USER_HEAD + " BLOB);";
		// 数据库删表操作
		public static final String DROP_TABLE = "drop table " + USER_TABLE;
	}
}
