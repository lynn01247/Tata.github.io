/**
 * @(#)HomeAdapters.java
 * @Version: 1
 * @JDK: jdk 1.6.0.XXX
 * @Module: CrazyitWeibo
 */
/*- 				History
 **********************************************
 *  ID      DATE           PERSON       REASON
 *  1     2011-11-29     hanfei.li    Created
 **********************************************
 */

package com.tatait.tataweibo.adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.util.Linkify;
import android.text.util.Linkify.MatchFilter;
import android.text.util.Linkify.TransformFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.tatait.tataweibo.HomeActivity;
import com.tatait.tataweibo.R;
import com.tatait.tataweibo.bean.Constants;
import com.tatait.tataweibo.bean.ContentInfo;
import com.tatait.tataweibo.bean.ShareBean;
import com.tatait.tataweibo.util.AsyncImageLoader;
import com.tatait.tataweibo.util.CommonUtil;
import com.tatait.tataweibo.util.Global;
import com.tatait.tataweibo.util.HttpUtils;
import com.tatait.tataweibo.util.ImageLoadUtils;
import com.tatait.tataweibo.util.StringUtils;
import com.tatait.tataweibo.util.ToastUtil;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 主页适配器类
 *
 * @author WSXL
 */

public class HomeAdapters extends BaseAdapter {

    /**
     * 为提高效率，缓存数据准备的一个自定义类 对应一条微博数据
     */
    private class ContentHolder {
        private ImageView content_image; // 对应微博显示的图片
        private ImageView content_icon; // 对应发微博人的头像
        private TextView content_user; // 对应发微博人的名称
        private TextView content_time; // 对应发微博的时间
        private TextView guanzhu; // 是否关注
        private TextView biaotai_num; // 是否关注
        private TextView pinglun_num; // 是否关注
        private TextView share_num; // 是否关注
        private TextView content_text; // 对应发微博的内容
        private TextView content_source; // 对应发微博的来自
        private TextView id; // 对应发微博的来自
    }

    private HomeActivity homeActivity = null;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    /**
     * 数据集
     */
    public LinkedList<ContentInfo> contentList = null;

    /**
     * @param homeActivity
     * @param contentList
     */
    public HomeAdapters(HomeActivity homeActivity,
                        LinkedList<ContentInfo> contentList) {
        this.homeActivity = homeActivity;
        this.contentList = contentList;
        ImageLoadUtils.initImageLoads(homeActivity);
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.bgpic)
//                .showImageOnFail(R.drawable.bgpic)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    // 微博图片的异步下载类
    AsyncImageLoader asyncImageLoader;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        asyncImageLoader = new AsyncImageLoader();
        // 记载微博的每条需要显示在什么布局上的布局对象
        if (convertView == null) {
            convertView = LayoutInflater.from(this.homeActivity.getApplicationContext()).inflate(R.layout.home_item_weibo, null);
        }
        // 创建一个层次对应组件的类
        final ContentHolder ch = new ContentHolder();
        // 将R.layout.home_item 对应的组件和ContentHolder对象进行关联，提高效率
        ch.content_icon = (ImageView) convertView.findViewById(R.id.content_head);
        ch.content_time = (TextView) convertView.findViewById(R.id.content_time);
        ch.content_user = (TextView) convertView.findViewById(R.id.content_user);
        ch.guanzhu = (TextView) convertView.findViewById(R.id.guanzhu);
        ch.biaotai_num = (TextView) convertView.findViewById(R.id.biaotai_num);
        ch.pinglun_num = (TextView) convertView.findViewById(R.id.pinglun_num);
        ch.share_num = (TextView) convertView.findViewById(R.id.share_num);
        ch.id = (TextView) convertView.findViewById(R.id.content_user_id);
        ch.content_image = (ImageView) convertView.findViewById(R.id.content_image);
        ch.content_text = (TextView) convertView.findViewById(R.id.content_text);
        //超链接处理
//		extractMention2Link(ch.content_text);
        ch.content_source = (TextView) convertView.findViewById(R.id.content_source);
        // 获得一条微博数据
        final ContentInfo info = this.contentList.get(position);
        if (info != null) {
            convertView.setTag(info.getId());
            ch.guanzhu.setTag(info.getId());
            ch.content_user.setText(info.getUserName());
            ch.content_time.setText(info.getTime());
            if(!StringUtils.isEmpty2(info.getBiaotai_num()) && !"0".equals(info.getBiaotai_num())) {
                ch.biaotai_num.setText("表态 " + info.getBiaotai_num());
            }
            if(!StringUtils.isEmpty2(info.getPinglun_num()) && !"0".equals(info.getPinglun_num())) {
                ch.pinglun_num.setText("评论 " + info.getPinglun_num());
            }
            if(!StringUtils.isEmpty2(info.getShare_num()) && !"0".equals(info.getShare_num())) {
                ch.share_num.setText("转发 " + info.getShare_num());
            }
            String str = info.getContent_source();
            Pattern p = Pattern.compile("<a[^>]*>([^<]*)</a>");
            Matcher m = p.matcher(str);
            String source = "";
            while (m.find()) {
                source = m.group(1);
            }
            ch.content_source.setText("来自" + source);
            if (info.getFollow_me()) {
                ch.guanzhu.setText(" 已关注 ");
                ch.guanzhu.setTextColor(homeActivity.getResources().getColor(R.color.left_itembg_pressed));
                ch.guanzhu.setBackgroundResource(R.drawable.corner_white_5dp_gray);
            } else {
                ch.guanzhu.setText(" 关注 ");
                ch.guanzhu.setTextColor(homeActivity.getResources().getColor(R.color.sblue));
                ch.guanzhu.setBackgroundResource(R.drawable.corner_white_5dp_blue);
            }
            ch.guanzhu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (info.getFollow_me()) {
//                        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(homeActivity);
//                        RequestParams requestParams = new RequestParams();
//                        requestParams.put("uid", ch.guanzhu.getTag());
//                        requestParams.put("access_token", accessToken.getToken());
//                        guanzhu(0, requestParams);
                    } else {
//                        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(homeActivity);
//                        RequestParams requestParams = new RequestParams();
//                        requestParams.put("uid", ch.guanzhu.getTag());
//                        requestParams.put("access_token", accessToken.getToken());
//                        guanzhu(1, requestParams);
                    }
                }
            });
            ch.share_num.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShareBean shareBean = new ShareBean();
                    shareBean.setTitle("来自Tata.Dev客户端");
                    shareBean.setTitleUrl("http://lynn01247.github.io/tata.dev.html");// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                    shareBean.setText(info.getText());
                    shareBean.setUrl("http://lynn01247.github.io/tata.dev.html");// url仅在微信（包括好友和朋友圈）中使用
                    shareBean.setComment("");
                    shareBean.setSite(Global.app_name);
                    shareBean.setSiteUrl("http://lynn01247.github.io/tata.dev.html");// siteUrl是分享此内容的网站地址，仅在QQ空间使用
                    CommonUtil.showShare(shareBean,homeActivity.getApplicationContext());
                }
            });
            ch.content_text.setText(info.getText(), TextView.BufferType.SPANNABLE);
            ch.id.setText(info.getId());
            if (!StringUtils.isEmpty2(StringUtils.getObjString(info.getUserIcon()))) {
                Picasso.with(homeActivity).load(StringUtils.getObjString(info.getUserIcon())).resize(40, 40).placeholder(new ColorDrawable(Color.parseColor("#f5f5f5"))).into(ch.content_icon);
            } else {
                Picasso.with(homeActivity).load(R.drawable.user_default).resize(40, 40).placeholder(new ColorDrawable(Color.parseColor("#f5f5f5"))).into(ch.content_icon);
            }
            if (info.getHaveImage()) {// 是否有图片信息
                imageLoader.displayImage(info.getImage_context(), ch.content_image, options);
            }
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return this.contentList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.contentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void guanzhu(final int type, RequestParams requestParams) {
        String url = "";
        if (0 == type) {
            url = Constants.WEIBO_QUXIAO_GUANZHU;
        } else {
            url = Constants.WEIBO_GUANZHU;
        }
        HttpUtils.postRequest(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                ToastUtil.show(R.string.notice_get_userinfo_fail);
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
                if(!StringUtils.isEmpty2(response.toString())){
                    if (0 == type) {
                        Toast.makeText(homeActivity, "已取消关注该用户.", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(homeActivity, "成功关注该用户.", Toast.LENGTH_LONG).show();
                    }
                }
                super.onSuccess(statusCode, headers, response);
            }

        });
    }

    /**
     * 超链接处理
     *
     * @param v
     */
    public static void extractMention2Link(TextView v) {
        v.setAutoLinkMask(0);
        Pattern mentionsPattern = Pattern.compile("@(\\w+?)(?=\\W|$)(.)");
        String mentionsScheme = String.format("%s/?%s=",
                Constants.MENTIONS_SCHEMA, Constants.PARAM_UID);
        Linkify.addLinks(v, mentionsPattern, mentionsScheme, new MatchFilter() {
            @Override
            public boolean acceptMatch(CharSequence s, int start, int end) {
                return s.charAt(end - 1) != '.';
            }
        }, new TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return match.group(1);
            }
        });
        Pattern trendsPattern = Pattern.compile("#(\\w+?)#");
        String trendsScheme = String.format("%s/?%s=", Constants.TRENDS_SCHEMA,
                Constants.PARAM_UID);
        Linkify.addLinks(v, trendsPattern, trendsScheme, null,
                new TransformFilter() {
                    @Override
                    public String transformUrl(Matcher match, String url) {
                        return match.group(1);
                    }
                });

    }
}