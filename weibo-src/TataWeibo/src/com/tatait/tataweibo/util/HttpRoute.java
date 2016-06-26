package com.tatait.tataweibo.util;

public class HttpRoute {

    public static final String IMAGE_HEAD = "";
    public static final String SHARE_HEAD = "";
    //	public static final String IMAGE_HEAD = "http://api.rm520.cn/";
    public static final String SHARED_HEAD = "http://www.hongyushengmaoyi.cn/";
    //    public static final String URL_HEAD = "http://192.168.1.118/nhgz/index.php/";
    public static final String URL_HEAD = "http://api.laigao520.com/";
    public static final String SHARE_FRIEND = "http://a.app.qq.com/o/simple.jsp?pkgname=com.hq.new4men";

    // QQ/微信 检查用户
    public static final String URL_CHECK = "userwx/wxlog";
    // QQ/微信 注册用户
    public static final String URL_REG = "userwx/wxreg";
    // 最新
    public static final String URL_GETNEWEST = "usercomment/getnewest";
    // 热门
    public static final String URL_HOT = "usercomment/gethot";
    // 撸吧
    public static final String URL_SEX = "usercomment/getsex";
    // 看吧
    public static final String URL_SEE = "usercomment/getsee";
    // 笑吧
    public static final String URL_LAUGH = "usercomment/getlaugh";
    // 穿越
    public static final String URL_PASS = "usercomment/getpassthrough";
    // 最新
    public static final String URL_NEW = "usercomment/getnewest";
    // 广场
    public static final String URL_GETSQUARE = "usercomment/getsquare";
    // 热门
    public static final String URL_GETGRATUITY = "usercomment/getgratuity";
    // 点赞
    public static final String URL_PARISE = "usercollect/create";
    // 分享文章
    public static final String URL_SHARED = "usercomment/shared/";
    // 分享作者
    public static final String URL_SHARED_USER = "usercomment/zhongxin/";
    //获取新闻详情
    public static final String URL_NEW_DETAIL = "usercomment/detail";
    // 获取详情的评论
    public static final String URL_REPLY_LIST = "usercomment/comments";
    // 获取来聊的评论
    public static final String URL_COMMENTSLIST = "lailiao/commentslist";
    // 获取来聊的评论
    public static final String URL_TALKREPLYLIST = "lailiao/talkreplylist";
    // 关闭来聊订单
    public static final String URL_CLOSEORDER = "lailiao/closeorder";
    // 确定来聊订单
    public static final String URL_SUREORDER = "lailiao/newsureorder";
    // 获取来聊记录
    public static final String URL_GETGOODRECORD = "lailiao/getgoodrecord";
    // 获取来聊订单-新
    public static final String URL_GETGOODRECORDNEW = "lailiao/getgoodrecordnew";
    // 获取一二级好友
    public static final String URL_LEVEL = "superuser/level";
    // 提交截图/来聊投诉
    public static final String URL_COMPLAINT = "lailiao/complaintnew";
    // 发布求聊
    public static final String URL_FORCHAT = "lailiao/forchat";
    // 订单评价
    public static final String URL_FINISHTALK = "lailiao/finishtalk";
    // 同一订单
    public static final String URL_TONGYIDINGDAN = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    // 回调地址
    public static final String URL_HUIDIAOADDR = "http://api.laigao520.com/payment/weixin";
    // 获取广告
    public static final String URL_AD = "ads/get";
    // 获取最新版块头5条
    public static final String URL_GETNEWSETTOP = "usercomment/getnewsettop";
    // 上传图片
    public static final String URL_IMAGE = "uploadbase64";
    // 发布档案
    public static final String URL_SUBMIT_ARCHIVES = "usercomment/create";
    // 用户动态 和 发布
    public static final String URL_USER_SUBMIT = "usercomment/get";
    // 获取用户收藏列表（个人中心）
    public static final String URL_USER_PRAISE = "usercollect/get";
    // 获取用户分享列表（个人中心）
    public static final String URL_USER_SHARE = "usercomment/getshare";
    // 获取用户作品列表（个人中心）
    public static final String URL_USER_OWN = "usercomment/getown";
    // 获取用户消息列表（个人中心）
    public static final String URL_USER_MESSAGE = "usercollect/userreply";
    // 提交反馈
    public static final String URL_FEEDBACK = "feedback/create";
    // 获取用户信息
    public static final String URL_USER_INFORMATION = "profile/index";
    // 获取超级用户信息
    public static final String URL_USER_INFORMATIONS = "profile/indexs";
    // 修改用户信息
    public static final String URL_UPDATE_USER_INFORMATION = "profile/modify_ad";
    // 提交认证接口
    public static final String URL_GOTORENZHENG = "usercomment/gotorenzheng";
    // 新增回复
    public static final String URL_ADD_REPLY = "usercomment/addreply";
    // 新增收藏
    public static final String URL_COLLECT = "usercollect/create";
    // 删除收藏
    public static final String URL_CANCEL_COLLECT = "usercollect/delete_create";
    // 删除点赞
    public static final String URL_CANCEL_PARISED = "usercomment/delete_parised";
    // 删除点赞
    public static final String URL_CANCEL_ZUOPIN = "usercomment/delete";
    // 删除分享
    public static final String URL_CANCEL_SHARE = "usercomment/delete_share";
    // 新增分享
    public static final String URL_SHARE = "usercomment/share";
    // 新增点赞
    public static final String URL_PARISED = "usercomment/parised";
    // 取消点赞
    public static final String URL_CANCEL_PARISE = "usercomment/delete_parised";
    // 存储用户的自动定位信息
    public static final String AUTO_LOC = "lailiao/adduserlocation";
    // 删除档案
    public static final String URL_DELECT_ARCHIVH = "usercomment/delete";
    // 刷新置顶
    public static final String URL_REFRESH_TOUP = "usercomment/refreshtoup";
    // 检查版本
    public static final String CHECK_VERSION = "version/get";
    // 来聊下单
    public static final String URL_ADDTALK = "lailiao/addtalk";
    // 二次下单
    public static final String URL_TWOORDERS = "lailiao/twoorders";
    // 生成充值订单
    public static final String URL_RECHARGE = "payment/recharge";
    // 生成超极用户充值订单
    public static final String URL_RECHARGEROOT = "payment/rechargeroot";
    // 完成订单
    public static final String URL_RECHARGEEND = "payment/rechargeend";
    // 完成订单
    public static final String URL_RECHARGEROOTEND = "payment/rechargerootend";
    // 根据ID获取详情
    public static final String URL_GET_ALL_DETAIL = "usercomment/all";
    // 举报
    public static final String URL_REPORT = "report/create";
    // 提交图片
    public static final String URL_SUBMIT_PIC = "usercomment/contribute";
    // 提交小视频
    public static final String URL_MYVIDEO = "usercomment/myvideo";
    // 请求最新评论数目
    public static final String URL_GET_REPLYNUM = "usercollect/userreplynum";
    // 搜索
    public static final String URL_SEARCH = "usercomment/search";
    // 推荐接口
    public static final String URL_GET_RELA = "usercomment/getrela";
    // 获取热词
    public static final String URL_GET_HOTKEY = "gethotkey/index";
    // 绑定推送
    public static final String URL_GET_BINDUSER = "usercomment/binduser";
    // 关注TA
    public static final String URL_ATTENTION_TA = "usercomment/attentionta";
    // 关注TA
    public static final String URL_CANCELATTENTION_TA = "usercomment/delete_guanzhu";
    // 获取关注列表
    public static final String URL_ATTENTION = "usercomment/getattention";
    // 获取被关注列表
    public static final String URL_ATTENTIONED = "usercomment/getattentioned";
    // 获取分享地址
    public static final String URL_PROMOTION = "usercomment/getpromotion";
    // 获取积分商城
    public static final String URL_SCOREMALL = "usercomment/getscoremall";
    // 积分兑换商品
    public static final String URL_EXCHANGEGOODS = "usercomment/exchangegoods";
    // 记录ID
    public static final String URL_UPLOADCID = "usercomment/uploadcid";
    // 每日签到
    public static final String URL_GETSIGN = "usercomment/getsign";
    // 提醒TA成为搞手
    public static final String URL_REMINDTA = "usercomment/remindta";
    // 申请成为搞手
    public static final String URL_TOBEGAOSHOU = "userwx/tobegaoshou";
    // 新增踩楼
    public static final String URL_ADDDOWN = "usercomment/adddown";
    // 评论点赞
    public static final String URL_ADDNICEPARISED = "usercomment/addniceparised";
    // 获取神回复列表
    public static final String URL_GETNICECOMMENT = "usercomment/getnicecomment";
    // 获取历史账单
    public static final String URL_HISTORY = "userwx/history";
    // 获取规则
    public static final String HTML_GUIZHE = "guize.htmly";
    // 获取排行榜
    public static final String URL_RANK = "usercomment/getrank";
    // 来聊搜索
    public static final String URL_GETSEARCH = "lailiao/getsearch";
    // 来聊搜索推荐
    public static final String URL_GETSEARCHRECOMMEND = "lailiao/getsearchrecommend";
    // 获取来聊排行榜
    public static final String URL_LAILIAORANK = "lailiao/getleaderboard";
    // 获取排行榜
    public static final String URL_DUIHUAN = "usercomment/exchangerecord";
    // 第三方登录
    public static final String URL_LOGIN = "userwx/ownlogin";
    // 第三方注册
    public static final String URL_SIGNUP = "userwx/ownreg";
    // 忘记密码
    public static final String URL_FORGETPASSWORD = "userwx/forgetpassword";
    // 重置密码
    public static final String URL_RESETPASSWORD = "userwx/resetpassword";
    // 用户买花
    public static final String URL_BUYFLOWER = "usercomment/buyflower";
    // 用户卖花
    public static final String URL_SOLDFLOWER = "usercomment/soldflower";
    // 用户卖花
    public static final String URL_BESHENGAO = "usercomment/beshengao";
    // 来聊主页列表
    public static final String URL_GETTALKLIST = "lailiao/gettalklistnew";
    // 我来审稿
    public static final String URL_GOTOSHENGAO = "usercomment/gotoshengao";
    // 我来审稿
    public static final String URL_SURPLUS = "usercomment/surplus";
    // 送花接口
    public static final String URL_FLOWERS = "lailiao/flowers";
    // 获取用户九个信息
    public static final String URL_USERINFO = "profile/userinfo_ad";
    // 获取阿里支付宝
    public static final String URL_ALIPAYSERVICE = "payment/alipayService";
    // 获取微信
    public static final String URL_WEIXINSERVICE = "payment/weixinService";
}
