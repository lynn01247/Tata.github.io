package com.tatait.tataweibo.bean;

import com.sina.weibo.sdk.openapi.models.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用户信息
 *
 * @author WSXL
 */
public class UserInfo {
    private Long id;
    private String token;
    private String token_secret;
    /* 用户ID */
    private String user_id;
    /* 用户昵称 */
    private String user_name;
    /* 用户头像 */
    private String user_head;
    //	private int id;	//int64	用户UID
    private String idstr;    //string	字符串型的用户UID
    private String screen_name;    //string	用户昵称
    private String name;    //string	友好显示名称
    private int province;    //int	用户所在省级ID
    private int city;    //int	用户所在城市ID
    private String location;    //string	用户所在地
    private String description;    //string	用户个人描述
    private String url;    //string	用户博客地址
    private String profile_image_url;    //string	用户头像地址（中图），50×50像素
    private String profile_url;    //string	用户的微博统一URL地址
    private String domain;    //string	用户的个性化域名
    private String weihao;    //string	用户的微号
    private String gender;    //string	性别，m：男、f：女、n：未知
    private String followers_count;    //int	粉丝数
    private String friends_count;    //int	关注数
    private String statuses_count;    //int	微博数
    private String favourites_count;    //int	收藏数
    private String created_at;    //string	用户创建（注册）时间
    private boolean following;    //boolean	暂未支持
    private boolean allow_all_act_msg;    //boolean	是否允许所有人给我发私信，true：是，false：否
    private boolean geo_enabled;    //boolean	是否允许标识用户的地理位置，true：是，false：否
    private boolean verified;    //boolean	是否是微博认证用户，即加V用户，true：是，false：否
    private int verified_type;    //int	暂未支持
    private String remark;    //string	用户备注信息，只有在查询用户关系时才返回此字段
    private Object status;    //object	用户的最近一条微博信息字段 详细
    private boolean allow_all_comment;    //boolean	是否允许所有人对我的微博进行评论，true：是，false：否
    private String avatar_large;    //string	用户头像地址（大图），180×180像素
    private String avatar_hd;    //string	用户头像地址（高清），高清头像原图
    private String verified_reason;    //string	认证原因
    private boolean follow_me;    //boolean	该用户是否关注当前登录用户，true：是，false：否
    private int online_status;    //int	用户的在线状态，0：不在线、1：在线
    private int bi_followers_count;    //int	用户的互粉数
    private String lang; //string	用户当前的语言版本，zh-cn：简体中文，zh-tw：繁体中文，en：英语

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(String followers_count) {
        this.followers_count = followers_count;
    }

    public String getFriends_count() {
        return friends_count;
    }

    public void setFriends_count(String friends_count) {
        this.friends_count = friends_count;
    }

    public String getStatuses_count() {
        return statuses_count;
    }

    public void setStatuses_count(String statuses_count) {
        this.statuses_count = statuses_count;
    }

    public String getFavourites_count() {
        return favourites_count;
    }

    public void setFavourites_count(String favourites_count) {
        this.favourites_count = favourites_count;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_secret() {
        return token_secret;
    }

    public void setToken_secret(String token_secret) {
        this.token_secret = token_secret;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser_head() {
        return user_head;
    }

    public void setUser_head(String user_head) {
        this.user_head = user_head;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getWeihao() {
        return weihao;
    }

    public void setWeihao(String weihao) {
        this.weihao = weihao;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public boolean isAllow_all_act_msg() {
        return allow_all_act_msg;
    }

    public void setAllow_all_act_msg(boolean allow_all_act_msg) {
        this.allow_all_act_msg = allow_all_act_msg;
    }

    public boolean isGeo_enabled() {
        return geo_enabled;
    }

    public void setGeo_enabled(boolean geo_enabled) {
        this.geo_enabled = geo_enabled;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getVerified_type() {
        return verified_type;
    }

    public void setVerified_type(int verified_type) {
        this.verified_type = verified_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public boolean isAllow_all_comment() {
        return allow_all_comment;
    }

    public void setAllow_all_comment(boolean allow_all_comment) {
        this.allow_all_comment = allow_all_comment;
    }

    public String getAvatar_large() {
        return avatar_large;
    }

    public void setAvatar_large(String avatar_large) {
        this.avatar_large = avatar_large;
    }

    public String getAvatar_hd() {
        return avatar_hd;
    }

    public void setAvatar_hd(String avatar_hd) {
        this.avatar_hd = avatar_hd;
    }

    public String getVerified_reason() {
        return verified_reason;
    }

    public void setVerified_reason(String verified_reason) {
        this.verified_reason = verified_reason;
    }

    public boolean isFollow_me() {
        return follow_me;
    }

    public void setFollow_me(boolean follow_me) {
        this.follow_me = follow_me;
    }

    public int getOnline_status() {
        return online_status;
    }

    public void setOnline_status(int online_status) {
        this.online_status = online_status;
    }

    public int getBi_followers_count() {
        return bi_followers_count;
    }

    public void setBi_followers_count(int bi_followers_count) {
        this.bi_followers_count = bi_followers_count;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public static User parse(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return User.parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public UserInfo() {
    }

    public UserInfo(String userId, String token, String token_secret) {
        this.user_id = userId;
        this.token = token;
        this.token_secret = token_secret;
    }

    public UserInfo(String user_id, String user_name, String token,
                    String token_secret, String description) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.token = token;
        this.token_secret = token_secret;
        this.description = description;
    }

    public UserInfo(String user_id, String user_name, String token,
                    String token_secret, String description, String user_head) {
        this(user_id, user_name, token, token_secret, description);
        this.user_head = user_head;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static User parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }

        User user = new User();
        user.id = jsonObject.optString("id", "");
        user.idstr = jsonObject.optString("idstr", "");
        user.screen_name = jsonObject.optString("screen_name", "");
        user.name = jsonObject.optString("name", "");
        user.province = jsonObject.optInt("province", -1);
        user.city = jsonObject.optInt("city", -1);
        user.location = jsonObject.optString("location", "");
        user.description = jsonObject.optString("description", "");
        user.url = jsonObject.optString("url", "");
        user.profile_image_url = jsonObject.optString("profile_image_url", "");
        user.profile_url = jsonObject.optString("profile_url", "");
        user.domain = jsonObject.optString("domain", "");
        user.weihao = jsonObject.optString("weihao", "");
        user.gender = jsonObject.optString("gender", "");
        user.followers_count = jsonObject.optInt("followers_count", 0);
        user.friends_count = jsonObject.optInt("friends_count", 0);
        user.statuses_count = jsonObject.optInt("statuses_count", 0);
        user.favourites_count = jsonObject.optInt("favourites_count", 0);
        user.created_at = jsonObject.optString("created_at", "");
        user.following = jsonObject.optBoolean("following", false);
        user.allow_all_act_msg = jsonObject.optBoolean("allow_all_act_msg", false);
        user.geo_enabled = jsonObject.optBoolean("geo_enabled", false);
        user.verified = jsonObject.optBoolean("verified", false);
        user.verified_type = jsonObject.optInt("verified_type", -1);
        user.remark = jsonObject.optString("remark", "");
        // user.status = jsonObject.optString("status", ""); // XXX: NO Need ?
        user.allow_all_comment = jsonObject.optBoolean("allow_all_comment", true);
        user.avatar_large = jsonObject.optString("avatar_large", "");
        user.avatar_hd = jsonObject.optString("avatar_hd", "");
        user.verified_reason = jsonObject.optString("verified_reason", "");
        user.follow_me = jsonObject.optBoolean("follow_me", false);
        user.online_status = jsonObject.optInt("online_status", 0);
        user.bi_followers_count = jsonObject.optInt("bi_followers_count", 0);
        user.lang = jsonObject.optString("lang", "");

        // 注意：以下字段暂时不清楚具体含义，OpenAPI 说明文档暂时没有同步更新对应字段含义
        user.star = jsonObject.optString("star", "");
        user.mbtype = jsonObject.optString("mbtype", "");
        user.mbrank = jsonObject.optString("mbrank", "");
        user.block_word = jsonObject.optString("block_word", "");

        return user;
    }
}
