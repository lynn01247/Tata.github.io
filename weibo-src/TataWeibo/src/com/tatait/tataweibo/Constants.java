package com.tatait.tataweibo;

/**
 * 该类定义了微博授权时所需要的参数。
 * @author WSXL
 *
 */
public class Constants {

	public static final String APP_KEY = "2094141541";

	/**
	 * 第三方应用可以使用自己的回调页。
	 */
	public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

	/**
	 * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
	 * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利 选择赋予应用的功能。
	 */
	public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog," + "invitation_write";

	/**
	 * 微博根路径
	 */
	public final static String WEIBO_WEBSITE = "https://api.weibo.com";
	/**
	 * 用户接口之根据用户ID获取用户信息
	 */
	public final static String WEIBO_GET_USER_INFO = WEIBO_WEBSITE
			+ "/2/users/show.json";
	/**
	 * 微博接口之获取最新的公共微博
	 */
	public final static String WEIBO_GET_STATUS_PUBLIC_TIMELINE = WEIBO_WEBSITE
			+ "/2/statuses/public_timeline.json";
	/**
     * DALVIK
     */
	private static boolean DALVIK;
	public static boolean isDalvik() {
		return DALVIK;
	}
	/**
     * 允许输入的文字长度
     */
    public final static int WEIBO_MAX_LENGTH = 140;
    
    /**
     * 获得公共的微博 标记
     */
    public final static int GET_PUBLIC = 0;
    /**
     * 获得关注的微博 标记
     */
    public final static int GET_FRIENDS = 1;
    /**
     * 获得自己发的微博 标记
     */
    public final static int GET_USER_TIMELINE = 2;
}
