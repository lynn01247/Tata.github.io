// 打开文件的Activity
// Leo @ 2010/10/05

package com.tatait.tataweibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tatait.tataweibo.adapter.FileListAdapter;
import com.tatait.tataweibo.util.file.FileListItem;

import java.io.File;

public class OpenFileActivity extends Activity {
    private final static String mFileExt = "txt";    // 只处理txt格式的文件
    public final static int TXT_FILE = 0;            // 文本文件的标志
    public final static int FOLDER = 1;                // 文件夹的标志

    private File mCurrentDirectory = new File("/sdcard/");    // 根目录
    private ListView mlvFileList;                            // 显示文件选项的容器
    private FileListAdapter mAdapter;                        // 文件列表适配器

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openfile);

        mlvFileList = (ListView) findViewById(R.id.openfile_listview_file_select);
        mAdapter = new FileListAdapter(this);
        mlvFileList.setAdapter(mAdapter);

        // 设置点到ListView项时的监听函数
        ListView.OnItemClickListener itemClick = new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                int itemType = mAdapter.getItemType((int) id);
                String mPath = "";
                if (itemType == FOLDER) {                                // 是文件夹
                    String s = mAdapter.getItem((int) id).name;
                    if (s.equals("..")) {
                        mPath = mCurrentDirectory.getParent();
                    } else {
                        mPath = mCurrentDirectory.getPath() + "/" + s + "/";
                    }

                    mCurrentDirectory = new File(mPath);
                    ListFile(mCurrentDirectory);
                } else {                                            // 是文件
                    Bundle bundle = new Bundle();
                    bundle.putString("FILE_PATH", mCurrentDirectory.getPath() + "/"
                            + mAdapter.getItem((int) id).name);
                    Intent mIntent = new Intent();
                    mIntent.putExtras(bundle);
                    setResult(RESULT_OK, mIntent);
                    OpenFileActivity.this.finish();
                }
            }
        };

        ListFile(mCurrentDirectory);
        mlvFileList.setOnItemClickListener(itemClick);
    }

    private void ListFile(File aDirectory) {
        mAdapter.clearItems();
        mAdapter.notifyDataSetChanged();
        mlvFileList.postInvalidate();

        if (!aDirectory.getPath().equals("/sdcard")) {
            FileListItem item = new FileListItem();
            item.name = "..";
            item.type = FOLDER;
            mAdapter.addItem(item);
        }

        for (File f : aDirectory.listFiles()) {
            if (f.isDirectory()) {
                FileListItem item = new FileListItem();
                item.name = f.getName();
                item.type = FOLDER;
                mAdapter.addItem(item);
            } else {
                if (checkExt(f.getName().toLowerCase())) {
                    FileListItem item = new FileListItem();
                    item.name = f.getName();
                    item.type = TXT_FILE;
                    mAdapter.addItem(item);
                }
            }
        }

        mAdapter.notifyDataSetChanged();
        mlvFileList.postInvalidate();
    }

    private boolean checkExt(String itemName) {
        return itemName.endsWith(mFileExt);
    }
}
