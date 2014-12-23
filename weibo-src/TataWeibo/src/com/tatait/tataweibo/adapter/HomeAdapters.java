/**
 * @(#)HomeAdapters.java
 *
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

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tatait.tataweibo.HomeActivity;
import com.tatait.tataweibo.R;
import com.tatait.tataweibo.pojo.ContentInfo;
import com.tatait.tataweibo.util.AsyncImageLoader;
import com.tatait.tataweibo.util.AsyncImageLoader.ImageCallback;

/**
 * 主页适配器类
 * @author WSXL
 *
 */

public class HomeAdapters extends BaseAdapter {
    
    /**
     * 为提高效率，缓存数据准备的一个自定义类 对应一条微博数据
     */
    class ContentHolder {
        public ImageView content_image; // 对应微博显示的图片
        public ImageView content_icon;  // 对应发微博人的头像
        public TextView content_user;   // 对应发微博人的名称
        public TextView content_time;   // 对应发微博的时间
        public TextView content_text;   // 对应发微博的内容
    }
    
    private  HomeActivity homeActivity = null;
    /**
     * 数据集
     */
    public ArrayList<ContentInfo> contentList = null;
    /**
     * @param homeActivity
     * @param contentList 
     */
    public HomeAdapters(HomeActivity homeActivity, ArrayList<ContentInfo> contentList) {
        this.homeActivity = homeActivity;
        this.contentList = contentList;
    }

    //微博图片的异步下载类
    AsyncImageLoader asyncImageLoader;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        asyncImageLoader = new AsyncImageLoader();
        //记载微博的每条需要显示在什么布局上的布局对象
        convertView = LayoutInflater.from(this.homeActivity.getApplicationContext()).inflate(R.layout.home_item, null);
        //创建一个层次对应组件的类
        ContentHolder ch = new ContentHolder();
        //将R.layout.home_item 对应的组件和ContentHolder对象进行关联，提高效率
        ch.content_icon = (ImageView) convertView.findViewById(R.id.content_head);
        ch.content_time = (TextView) convertView.findViewById(R.id.content_time);
        ch.content_user = (TextView) convertView.findViewById(R.id.content_user);
        ch.content_image = (ImageView) convertView.findViewById(R.id.content_image);
        ch.content_text = (TextView) convertView.findViewById(R.id.content_text);
        //获得一条微博数据
        ContentInfo info = this.contentList.get(position);
        if (info != null) {
            convertView.setTag(info.getId());
            ch.content_user.setText(info.getUserName());
            ch.content_time.setText(info.getTime());
            ch.content_text.setText(info.getText(),
                    TextView.BufferType.SPANNABLE);
            
            if (info.getHaveImage()) {// 是否有图片信息
                //异步记载图片内容
                Drawable content_image = asyncImageLoader.loadDrawable(
                        info.getImage_context(), ch.content_image,
                        new ImageCallback() {
                            public void imageLoaded(Drawable drawable,
                                    ImageView iv, String url) {
                                iv.setImageDrawable(drawable);
                            }
                        });
                if (content_image == null) {
                    ch.content_image.setImageResource(R.drawable.info_icon);
                } else {
                    ch.content_image.setImageDrawable(content_image);
                }
            }
            //异步记载用户头像数据
            Drawable cachedImage = asyncImageLoader.loadDrawable(
                    info.getUserIcon(), ch.content_icon,
                    new ImageCallback() {
                        public void imageLoaded(Drawable drawable,
                                ImageView iv, String url) {
                            iv.setImageDrawable(drawable);
                        }
                    });
            if (cachedImage == null) {
                ch.content_icon.setImageResource(R.drawable.angel);
            } else {
                ch.content_icon.setImageDrawable(cachedImage);
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
}