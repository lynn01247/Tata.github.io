package com.tatait.tataweibo.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tatait.tataweibo.AboutUsActivity;
import com.tatait.tataweibo.DeviceInfoActivity;
import com.tatait.tataweibo.R;
import com.tatait.tataweibo.SetFrontActivity;
import com.tatait.tataweibo.bean.DataResult;
import com.tatait.tataweibo.util.AlertDialog;
import com.tatait.tataweibo.util.FileUtils;
import com.tatait.tataweibo.util.StringUtils;
import com.tatait.tataweibo.util.ToastUtil;
import com.tatait.tataweibo.util.ViewHolder;

import java.util.ArrayList;

/**
 * 用于单行展示 OK
 * 目前有主题界面和设备信息界面使用到本布局
 */
public class SetThemeAdapter extends BaseAdapter {
    private ArrayList<DataResult> listAttentionData;
    private Context mContext;
    private Activity mAcitivity;
//    public UmengUtils umeng;

    public SetThemeAdapter(Activity mAcitivity, Context mContext, ArrayList<DataResult> listAttentionData) {
        this.mContext = mContext;
        this.mAcitivity = mAcitivity;
        this.listAttentionData = listAttentionData;
//        umeng = new UmengUtils(mContext);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) mContext).getLayoutInflater().inflate(
                    R.layout.item_settheme_layout, null);
        }
        final TextView item_settheme_layout_type_tv = ViewHolder.get(convertView,
                R.id.item_settheme_layout_type_tv);
        ImageView item_settheme_layout_type_iv = ViewHolder.get(convertView,
                R.id.item_settheme_layout_type_iv);
        final LinearLayout item_settheme_layout_type = ViewHolder.get(convertView,
                R.id.item_settheme_layout_type);
        DataResult result = listAttentionData.get(position);
        item_settheme_layout_type_tv.setText(StringUtils.getObjString(result.getUsername()));
        //显示右边
        if (!StringUtils.isEmpty2(StringUtils.getObjString(result.getType())) && "onlyName".equals(StringUtils.getObjString(result.getType()))) {
            item_settheme_layout_type_iv.setVisibility(View.GONE);
            item_settheme_layout_type.setClickable(false);
        } else if (!StringUtils.isEmpty2(StringUtils.getObjString(result.getType())) && "videtemp".equals(StringUtils.getObjString(result.getType()))) {
            SpannableStringBuilder builder2 = new SpannableStringBuilder(StringUtils.getObjString(item_settheme_layout_type_tv.getText()));
            builder2.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffc600")), 5, StringUtils.getObjString(item_settheme_layout_type_tv.getText()).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            item_settheme_layout_type_tv.setText(builder2);
        } else {
            item_settheme_layout_type_iv.setVisibility(View.VISIBLE);
            item_settheme_layout_type.setClickable(true);
        }
        item_settheme_layout_type.setTag(R.id.item_settheme_layout_type, StringUtils.getObjString(result.getType()));
        item_settheme_layout_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("deviceInfo".equals(StringUtils.getObjString(item_settheme_layout_type.getTag(R.id.item_settheme_layout_type)))) {
                    mContext.startActivity(new Intent(mContext,
                            DeviceInfoActivity.class));
                } else if ("aboutUs".equals(StringUtils.getObjString(item_settheme_layout_type.getTag(R.id.item_settheme_layout_type)))) {
                    mContext.startActivity(new Intent(mContext,
                            AboutUsActivity.class));
                } else if ("setfront".equals(StringUtils.getObjString(item_settheme_layout_type.getTag(R.id.item_settheme_layout_type)))) {
                    mContext.startActivity(new Intent(mContext, SetFrontActivity.class));
                } else if ("videtemp".equals(StringUtils.getObjString(item_settheme_layout_type.getTag(R.id.item_settheme_layout_type)))) {
                    if ("文件缓存: 0.0Byte(s)".equals(StringUtils.getObjString(item_settheme_layout_type_tv.getText()))) {
                        ToastUtil.show("真棒！没有发现缓存文件，无须执行清理缓存哦！");
                    } else {
                        new AlertDialog(mContext).builder().setTitle("温馨提示")
                                .setMsg("是否清除缓存文件")
                                .setNegativeButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        FileUtils.deleteFolderFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/videoTest/", false);
                                        ToastUtil.show("清除缓存完毕");
                                        item_settheme_layout_type_tv.setText("文件缓存: 0.0Byte(s)");
                                        SpannableStringBuilder builder2 = new SpannableStringBuilder(StringUtils.getObjString(item_settheme_layout_type_tv.getText()));
                                        builder2.setSpan(new ForegroundColorSpan(Color.parseColor("#ff2323")), 6, StringUtils.getObjString(item_settheme_layout_type_tv.getText()).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                                        item_settheme_layout_type_tv.setText(builder2);
                                    }
                                }).setPositiveButton("稍后再说", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        }).show();
                    }
//                } else if ("sharefrie".equals(StringUtils.getObjString(item_settheme_layout_type.getTag(R.id.item_settheme_layout_type)))) {
//                    //分享处理
//                    umeng.showShareDialog("我发现了一款有趣的应用,快乐试试吧", HttpRoute.SHARE_FRIEND, "我发现了一款有趣的应用,快乐试试吧", Global.app_logo_link);
                }
            }
        });

        return convertView;
    }

    private void restartApplication() {
        new AlertDialog(mContext).builder().setTitle("设置成功！重启app后生效")
                .setMsg("是否立即重启应用")
                .setNegativeButton("立即重启", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        ActivityManager manager = (ActivityManager)mContext.getSystemService(mContext.ACTIVITY_SERVICE);
//                        manager.restartPackage(mContext.getPackageName());
                        mAcitivity.finish();
                        Intent i = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mContext.startActivity(i);
                    }
                }).setPositiveButton("稍后再说", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        }).show();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listAttentionData.get(position);
    }

    @Override
    public int getCount() {
        return listAttentionData.size();
    }
}