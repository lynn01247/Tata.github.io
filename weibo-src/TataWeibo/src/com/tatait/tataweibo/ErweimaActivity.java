package com.tatait.tataweibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.android.decode.CaptureActivity;
import com.google.zxing.client.android.encode.QRCodeEncoder;
import com.google.zxing.common.HybridBinarizer;

import java.util.EnumMap;
import java.util.Map;


public class ErweimaActivity extends Activity implements View.OnClickListener {
    private static final int REQUEST_CODE = 0;

    private Button scannerQRCode, generateQRCode;
    private ImageView qrcodeImg;
    private EditText erweima_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erweima);
        erweima_et = (EditText) findViewById(R.id.erweima_edittext);
        scannerQRCode = (Button) findViewById(R.id.qrcode_dencode);
        generateQRCode = (Button) findViewById(R.id.qrcode_encode);
        qrcodeImg = (ImageView) findViewById(R.id.qrcode_img);


        scannerQRCode.setOnClickListener(this);
        generateQRCode.setOnClickListener(this);
        //长按图片识别二维码
        qrcodeImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                qrcodeImg.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(qrcodeImg.getDrawingCache());
                qrcodeImg.setDrawingCacheEnabled(false);
                decodeQRCode(bitmap,getApplicationContext());
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.qrcode_dencode: //扫描
                intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.qrcode_encode: //生成

                try {
                    Bitmap mBitmap = QRCodeEncoder.encodeAsBitmap(erweima_et.getText().toString(), 300);
                    qrcodeImg.setImageBitmap(mBitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 解析二维码图片
     *
     * @param bitmap   要解析的二维码图片
     */
    private final Map<DecodeHintType, Object> HINTS = new EnumMap<>(DecodeHintType.class);

    private void decodeQRCode(final Bitmap bitmap, final Context mContext) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    int[] pixels = new int[width * height];
                    bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
                    RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
                    Result result = new MultiFormatReader().decode(new BinaryBitmap(new HybridBinarizer(source)), HINTS);
                    return result.getText();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                Log.d("wxl", "result=" + result);
                Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();
            }
        }.execute();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { //RESULT_OK = -1
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            Toast.makeText(getApplicationContext(), scanResult, Toast.LENGTH_LONG).show();
        }
    }
}
