package com.tatait.tataweibo.bean;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;

import com.sina.weibo.sdk.openapi.models.User;

/**
 * 用户信息
 * 
 * @author WSXL
 * 
 */
public class UserInfo {
	private Long id;
	private String token;
	private String token_secret;
	/* 用户昵称 */
	private String screen_name;
	/* 用户ID */
	private String user_id;
	/* 用户昵称 */
	private String user_name;
	/* 用户描述 */
	private String description;
	/* 用户位置 */
	private String location;
	/* 用户头像 */
	private Drawable user_head;
	/* 用户性别 */
	private String gender;
	/* 用户粉丝数 */
	private String followers_count;
	/* 用户关注数 */
	private String friends_count;
	/* 用户的微博数 */
	private String statuses_count;
	/* 用户的收藏数 */
	private String favourites_count;

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
			String token_secret, String description, Drawable user_head) {
		this(user_id, user_name, token, token_secret, description);
		this.user_head = user_head;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", token=" + token + ", token_secret="
				+ token_secret + ", screen_name=" + screen_name + ", user_id="
				+ user_id + ", user_name=" + user_name + ", description="
				+ description + ", location=" + location + ", user_head="
				+ user_head + ", gender=" + gender + ", followers_count="
				+ followers_count + ", friends_count=" + friends_count
				+ ", statuses_count=" + statuses_count + ", favourites_count="
				+ favourites_count + "]";
	}

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

	public Drawable getUser_head() {
		return user_head;
	}

	public void setUser_head(Drawable user_head) {
		this.user_head = user_head;
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
		user.allow_all_act_msg = jsonObject.optBoolean("allow_all_act_msg",
				false);
		user.geo_enabled = jsonObject.optBoolean("geo_enabled", false);
		user.verified = jsonObject.optBoolean("verified", false);
		user.verified_type = jsonObject.optInt("verified_type", -1);
		user.remark = jsonObject.optString("remark", "");
		// user.status = jsonObject.optString("status", ""); // XXX: NO Need ?
		user.allow_all_comment = jsonObject.optBoolean("allow_all_comment",
				true);
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
