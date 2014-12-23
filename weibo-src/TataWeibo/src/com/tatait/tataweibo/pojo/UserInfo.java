package com.tatait.tataweibo.pojo;

import org.json.JSONException;
import org.json.JSONObject;

import com.sina.weibo.sdk.openapi.models.User;

import android.graphics.drawable.Drawable;
/**
 * 用户信息
 * @author WSXL
 *
 */
public class UserInfo {
	private Long id;
	private String user_id;
	private String user_name;
	private String token;
	private String token_secret;
	private String description;
	private Drawable user_head;

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
			String token_secret,String description, Drawable user_head) {
		this(user_id, user_name, token,token_secret, description);
		this.user_head = user_head;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", user_id=" + user_id + ", user_name="
				+ user_name + ", token=" + token + ", token_secret="
				+ token_secret + ", description=" + description
				+ ", user_head=" + user_head + "]";
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
        user.id                 = jsonObject.optString("id", "");
        user.idstr              = jsonObject.optString("idstr", "");
        user.screen_name        = jsonObject.optString("screen_name", "");
        user.name               = jsonObject.optString("name", "");
        user.province           = jsonObject.optInt("province", -1);
        user.city               = jsonObject.optInt("city", -1);
        user.location           = jsonObject.optString("location", "");
        user.description        = jsonObject.optString("description", "");
        user.url                = jsonObject.optString("url", "");
        user.profile_image_url  = jsonObject.optString("profile_image_url", "");
        user.profile_url        = jsonObject.optString("profile_url", "");
        user.domain             = jsonObject.optString("domain", "");
        user.weihao             = jsonObject.optString("weihao", "");
        user.gender             = jsonObject.optString("gender", "");
        user.followers_count    = jsonObject.optInt("followers_count", 0);
        user.friends_count      = jsonObject.optInt("friends_count", 0);
        user.statuses_count     = jsonObject.optInt("statuses_count", 0);
        user.favourites_count   = jsonObject.optInt("favourites_count", 0);
        user.created_at         = jsonObject.optString("created_at", "");
        user.following          = jsonObject.optBoolean("following", false);
        user.allow_all_act_msg  = jsonObject.optBoolean("allow_all_act_msg", false);
        user.geo_enabled        = jsonObject.optBoolean("geo_enabled", false);
        user.verified           = jsonObject.optBoolean("verified", false);
        user.verified_type      = jsonObject.optInt("verified_type", -1);
        user.remark             = jsonObject.optString("remark", "");
        //user.status             = jsonObject.optString("status", ""); // XXX: NO Need ?
        user.allow_all_comment  = jsonObject.optBoolean("allow_all_comment", true);
        user.avatar_large       = jsonObject.optString("avatar_large", "");
        user.avatar_hd          = jsonObject.optString("avatar_hd", "");
        user.verified_reason    = jsonObject.optString("verified_reason", "");
        user.follow_me          = jsonObject.optBoolean("follow_me", false);
        user.online_status      = jsonObject.optInt("online_status", 0);
        user.bi_followers_count = jsonObject.optInt("bi_followers_count", 0);
        user.lang               = jsonObject.optString("lang", "");
        
        // 注意：以下字段暂时不清楚具体含义，OpenAPI 说明文档暂时没有同步更新对应字段含义
        user.star               = jsonObject.optString("star", "");
        user.mbtype             = jsonObject.optString("mbtype", "");
        user.mbrank             = jsonObject.optString("mbrank", "");
        user.block_word         = jsonObject.optString("block_word", "");
        
        return user;
    }
}
