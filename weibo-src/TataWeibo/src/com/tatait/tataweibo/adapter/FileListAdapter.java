// 文件列表的适配器
// Leo @ 2010/10/05

package com.tatait.tataweibo.adapter;

import java.util.Vector;

import com.tatait.tataweibo.R;
import com.tatait.tataweibo.R.id;
import com.tatait.tataweibo.R.layout;
import com.tatait.tataweibo.util.file.FileListItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FileListAdapter extends BaseAdapter {

	private Context mContext;
	private Vector<FileListItem> mItems = new Vector<FileListItem>();

	public FileListAdapter(Context context) {
		mContext = context;
	}

	public void addItem(FileListItem item) {
		mItems.add(item);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		// return 0;
		return mItems.size();
	}

	public FileListItem getItem(int position) {
		// TODO Auto-generated method stub
		// return null;
		return (FileListItem) mItems.elementAt(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		// return 0;
		return position;
	}

	public int getItemType(int position) {
		return getItem(position).type;
	}

	public void clearItems() {
		mItems.clear();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// return null;
		LayoutInflater inflate = (LayoutInflater) mContext
				.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
		convertView = (LinearLayout) inflate.inflate(R.layout.filelist, null);
		TextView tv = (TextView) convertView
				.findViewById(R.id.filelist_textview_item_content);
		tv.setText(getItem(position).name);

		return convertView;
	}
}