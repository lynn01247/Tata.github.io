package com.tatait.tataweibo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * 信息页面
 * 
 * @author WSXL
 * 
 */
public class MsgActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("pro", "MsgActivity-----------onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msg);
	}

}
