package com.tatait.tataweibo;

import com.tatait.tataweibo.bean.Constants;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

public class MentionsActivity extends Activity {
	private static final Uri PROFILE_URI = Uri.parse(Constants.MENTIONS_SCHEMA);
	private String uid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		extractUidFromUri();
	}

	private void extractUidFromUri() {
		Uri uri = getIntent().getData();
		if (uri != null && PROFILE_URI.getScheme().equals(uri.getScheme())) {
			uid = uri.getQueryParameter(Constants.PARAM_UID);
		}
	}
}
