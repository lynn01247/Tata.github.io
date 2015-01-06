package com.tatait.tataweibo;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 该类定义了微博授权时所需要的参数。
 * 
 * @author SINA
 * @since 2013-10-07
 */
public class AccessTokenKeeper {
    private static final String PREFERENCES_NAME = "tatait_tataweibo";

    private static final String KEY_UID           = "uid";
    private static final String KEY_ACCESS_TOKEN  = "access_token";
    private static final String KEY_EXPIRES_IN    = "expires_in";
    
    private static final String LOAD = "tatait_load";
    private static final String LOADVILABLE = "true";
    
    private static final String MUSIC = "tatait_music";
    private static final String MUSICINIT = "no";
    private static final String MUSICVILABLE = "true";
    /**
     * 保存 Token 对象到 SharedPreferences。
     * 
     * @param context 应用程序上下文环境
     * @param token   Token 对象
     */
    public static void writeAccessToken(Context context, Oauth2AccessToken token) {
        if (null == context || null == token) {
            return;
        }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putString(KEY_UID, token.getUid());
        editor.putString(KEY_ACCESS_TOKEN, token.getToken());
        editor.putLong(KEY_EXPIRES_IN, token.getExpiresTime());
        editor.commit();
    }

    /**
     * 从 SharedPreferences 读取 Token 信息。
     * 
     * @param context 应用程序上下文环境
     * 
     * @return 返回 Token 对象
     */
    public static Oauth2AccessToken readAccessToken(Context context) {
        if (null == context) {
            return null;
        }
        
        Oauth2AccessToken token = new Oauth2AccessToken();
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        token.setUid(pref.getString(KEY_UID, ""));
        token.setToken(pref.getString(KEY_ACCESS_TOKEN, ""));
        token.setExpiresTime(pref.getLong(KEY_EXPIRES_IN, 0));
        return token;
    }

    /**
     * 清空 SharedPreferences 中 Token信息。
     * 
     * @param context 应用程序上下文环境
     */
    public static void clear(Context context) {
        if (null == context) {
            return;
        }
        
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
    
    /**
     * 保存 是否加载动画。
     * 
     * @param context 应用程序上下文环境
     * @param flag 是否加载动画
     */
    public static void writeLoad(Context context,boolean flag) {
        if (null == context) {
            return;
        }
        SharedPreferences pref = context.getSharedPreferences(LOAD, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putBoolean(LOADVILABLE, flag);
        editor.commit();
    }
    /**
     * 从 SharedPreferences 是否加载动画
     * 
     * @param context 应用程序上下文环境
     * 
     * @return true/false
     */
    public static boolean readLoad(Context context) {
        if (null == context) {
            return true;
        }
        SharedPreferences pref = context.getSharedPreferences(LOAD, Context.MODE_APPEND);
        return pref.getBoolean(LOADVILABLE, false);
    }
    
    /**
     * 保存 是否后台播放音乐。
     * 
     * @param context 应用程序上下文环境
     * @param flag 是否后台播放音乐
     */
    public static void writeMusic(Context context,boolean flag) {
        if (null == context) {
            return;
        }
        SharedPreferences pref = context.getSharedPreferences(MUSIC, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putString(MUSICINIT, "yes");
        editor.putBoolean(MUSICVILABLE, flag);
        editor.commit();
    }
    /**
     * 从 SharedPreferences 是否后台播放音乐
     * 
     * @param context 应用程序上下文环境
     * 
     * @return true/false
     */
    public static boolean readMusic(Context context) {
        if (null == context) {
            return true;
        }
        SharedPreferences pref = context.getSharedPreferences(MUSIC, Context.MODE_APPEND);
        return pref.getBoolean(MUSICVILABLE, false);
    }
}
