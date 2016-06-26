package com.tatait.tataweibo.bean;
/**
 * 微博内容
 * @author WSXL
 *
 */
public class ContentInfo {
	// 文章id
	private String id;
	// 文章内容
	private String text;
	// 发布人id
	private String userId;
	// 发布人名字
	private String userName;
	// 发布人头像
	private String userIcon;
	private String biaotai_num;
	private String pinglun_num;
	private String share_num;
	private Boolean follow_me;
	// 发布时间
	private String time;
	// 来自
	private String content_source;
	// 是否有图片
	private Boolean haveImage = false;
	// 显示图片内容
	private String image_context;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent_source() {
		return content_source;
	}

	public void setContent_source(String content_source) {
		this.content_source = content_source;
	}

	public String getImage_context() {
		return image_context;
	}

	public void setImage_context(String image_context) {
		this.image_context = image_context;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Boolean getHaveImage() {
		return haveImage;
	}

	public void setHaveImage(Boolean haveImage) {
		this.haveImage = haveImage;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getFollow_me() {
		return follow_me;
	}

	public void setFollow_me(Boolean follow_me) {
		this.follow_me = follow_me;
	}

	public String getBiaotai_num() {
		return biaotai_num;
	}

	public void setBiaotai_num(String biaotai_num) {
		this.biaotai_num = biaotai_num;
	}

	public String getPinglun_num() {
		return pinglun_num;
	}

	public void setPinglun_num(String pinglun_num) {
		this.pinglun_num = pinglun_num;
	}

	public String getShare_num() {
		return share_num;
	}

	public void setShare_num(String share_num) {
		this.share_num = share_num;
	}
}