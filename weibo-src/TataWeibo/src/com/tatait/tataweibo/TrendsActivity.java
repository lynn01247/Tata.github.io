package com.tatait.tataweibo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.tatait.tataweibo.bean.Constants;

public class TrendsActivity extends Activity {
    private static final Uri PROFILE_URI = Uri.parse(Constants.TRENDS_SCHEMA);
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
