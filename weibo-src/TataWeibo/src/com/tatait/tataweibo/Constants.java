package com.tatait.tataweibo;

import java.util.HashMap;

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
			+ "/2/statuses/home_timeline.json";
	/**
     * DALVIK
     */
	private static boolean DALVIK;
	public static boolean isDalvik() {
		return DALVIK;
	}
	
	
	
	
	/**
     * 允许上传的图片类型
     */
    public final static HashMap<String, String> imageType = new HashMap<String, String>();

    static {
        imageType.put("jpg", "image");
        imageType.put("jpeg", "image");
        imageType.put("png", "image");
        imageType.put("bmp", "image");
        imageType.put("gif", "image");
    }

    /**
     * 图片来源选择菜单
     */
    public final static CharSequence[] meun_items = { "相册", "拍照", "清除图片"};
    public final static CharSequence[] home_items = { "设置", "注销", "关于", "返回"};
    public final static CharSequence[] beautiful_items = {
    	"久坐不益健康，出来走几步。", 
    	"激情工作，快乐生活。",
    	"没有什么比时间更具有说服力了，因为时间无需通知我们就可以改变一切。",
    	"世界上一成不变的东西，只有“任何事物都是在不断变化的”这条真理。 —— 斯里兰卡",
    	"笨蛋自以为聪明，聪明人才知道自己是笨蛋。 —— 莎士比亚",
    	"良好的健康状况和高度的身体训练，是有效的脑力劳动的重要条件。 —— 克鲁普斯卡娅",
    	"成功的秘诀，在永不改变既定的目的。 —— 卢梭",
    	"从不浪费时间的人，没有工夫抱怨时间不够。 —— 杰弗逊",
    	"友谊是灵魂的结合，这个结合是可以离异的，这是两个敏感，正直的人之间心照不宣的契约。 —— 伏尔泰",
    	"将人生投于赌博的赌徒，当他们胆敢妄为的时候，对自己的力量有充分的自信，并且认为大胆的冒险是唯一的形式。 —— 茨威格",
    	"最甜美的是爱情，最苦涩的也是爱情。 —— 菲·贝利",
    	"生活是一种绵延不绝的渴望，渴望不断上升，变得更伟大而高贵。 —— 杜伽尔",
    	"最成功的说谎者是那些使最少量的谎言发挥最大的作用的人。 —— 塞·巴特勒",
    	"我渴望随着命运指引的方向，心平气和地、没有争吵、悔恨、羡慕，笔直走完人生旅途。 —— 魏尔伦",
    	"父亲子女兄弟姊妹等称谓，并不是简单的荣誉称号，而是一种负有完全确定的异常郑重的相互义务的称呼，这些义务的总和便构成这些民族的社会制度的实质部分。 —— 恩格斯",
    	"时间是一切财富中最宝贵的财富。 —— 德奥弗拉斯多"};
    
	/**
     * 允许输入的文字长度
     */
    public final static int WEIBO_MAX_LENGTH = 140;
    
    /**
     * 获得公共的微博 标记
     */
    public final static int GET_PUBLIC = 0;
    public final static int GET_PUBLIC_REFLASH = 1;
    public final static int GET_PUBLIC_LOADMORE = 2;
    /**
     * 获得关注的微博 标记
     */
    public final static int GET_FRIENDS = 6;
    /**
     * 获得自己发的微博 标记
     */
    public final static int GET_USER_TIMELINE = 10;
    /**
     * 发布一条文字微博信息
     */
    public final static String WEIBO_WRITER_TXT = WEIBO_WEBSITE+ "/statuses/update.json";
    /**
     * 发布一条 图 文 微博信息
     */
    public final static String WEIBO_WRITER_TXT_IMG = WEIBO_WEBSITE+ "/statuses/upload.json";
    
    public static final String MENTIONS_SCHEMA ="devdiv://tata_profile_jing";
	public static final String TRENDS_SCHEMA ="devdiv://tata_profile_at";
	public static final String PARAM_UID ="uid";
}
