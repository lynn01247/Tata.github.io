package com.tatait.tataweibo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tatait.tataweibo.adapter.SetThemeAdapter;
import com.tatait.tataweibo.bean.DataResult;
import com.tatait.tataweibo.util.SharedPreferencesUtils;
import com.tatait.tataweibo.util.StringUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 设备信息 OK
 */
public class DeviceInfoActivity extends Activity implements
        OnClickListener {

    @Bind(R.id.getinfo_list_new_layout_list)
    ListView list;
    @Bind(R.id.getinfo_layout_back)
    LinearLayout getinfo_layout_back;
    @Bind(R.id.getinfo_title)
    TextView getinfo_title;

    private ArrayList<DataResult> listData;
    private SetThemeAdapter adapter;
    private Context mContext;
    private Activity mActivity;
    private final String mPageName = "DeviceInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getinfo);
        mContext = this;
        mActivity = this;
        ButterKnife.bind(this);
        initData();
        refreshData();
    }

    private void initData() {
        getinfo_layout_back.setOnClickListener(this);
        getinfo_title.setText("设备信息");
        listData = new ArrayList<DataResult>();
        adapter = new SetThemeAdapter(mActivity, mContext, listData);
        list.setAdapter(adapter);
    }

    public void refreshData() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        listData.clear();

        DataResult result1 = new DataResult();
        result1.setType("onlyName");
        result1.setUsername("IMEI：" + tm.getDeviceId());
        listData.add(result1);

        DataResult result3 = new DataResult();
        result3.setType("onlyName");
        String datestata = "";
        if (tm.getDataState() == TelephonyManager.DATA_CONNECTED) {
            datestata = "已连接";
        } else if (tm.getDataState() == TelephonyManager.DATA_CONNECTING) {
            datestata = "正在连接";
        } else if (tm.getDataState() == TelephonyManager.DATA_DISCONNECTED) {
            datestata = "断开";
        } else {
            datestata = "暂停";
        }
        result3.setUsername("手机数据连接状态：" + datestata);
        listData.add(result3);

        DataResult result2 = new DataResult();
        result2.setType("onlyName");
        String netWorkname = "未知";
        if (!StringUtils.isEmpty2(tm.getNetworkOperatorName())) {
            netWorkname = tm.getNetworkOperatorName();
        }
        result2.setUsername("运营商：" + netWorkname);
        listData.add(result2);

        DataResult result4 = new DataResult();
        result4.setType("onlyName");
        String isRoam = "";
        if ("1".equals(tm.isNetworkRoaming())) {
            isRoam = "是";
        } else {
            isRoam = "否";
        }
        result4.setUsername("手机是否处于漫游状态：" + isRoam);
        listData.add(result4);

        DataResult result5 = new DataResult();
        result5.setType("onlyName");

        result5.setUsername("用户Id：" + StringUtils.getObjString(SharedPreferencesUtils.getParam(mContext, "randid", "")));
        listData.add(result5);

        DataResult result6 = new DataResult();
        result6.setType("onlyName");
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int heapSize = manager.getMemoryClass();
        result6.setUsername("手机临时可用内存：" + StringUtils.getObjString(Integer.valueOf(heapSize).toString()));
        listData.add(result6);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            //返回
            case R.id.getinfo_layout_back:
                finish();
                break;
        }
    }
}