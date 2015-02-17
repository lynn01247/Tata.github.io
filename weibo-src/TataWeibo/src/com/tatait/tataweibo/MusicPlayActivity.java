package com.tatait.tataweibo;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.tatait.tataweibo.adapter.MusicAdapter;
import com.tatait.tataweibo.bean.MusicInfo;
import com.tatait.tataweibo.service.MusicService;
import com.tatait.tataweibo.util.AccessTokenKeeper;
import com.tatait.tataweibo.util.Tools;
import com.tatait.tataweibo.util.show.CircularImage;

public class MusicPlayActivity extends Activity {
	private static MusicPlayActivity instance = null;
	public static MusicPlayActivity getInstance() {
		if (instance == null) {
			instance = new MusicPlayActivity();
		}
		return instance;
	}
	// 歌曲列表
	ListView mListView;
	public static MusicAdapter mAdapter;
	// 上一首，播放暂停、下一首
	public static ImageButton pre_button, play_button, next_button;
	// 歌曲当前播放时间、歌曲总时间、判断按下返回的时间
	public static int playingTime = 0;
	public static int songTime = 0;
	private long exitTime = 0;
	// 绑定SeekBar和时间TextView、播放模式
	public static SeekBar seekbar;
	public TextView play_time, txt_wb_title;
	public TextView play_style;
	// 歌手名和专辑图像
	public static TextView mName;
	public static ImageView mAlbum;
	public static int play_mode = MusicInfo.LISTREPEAT;
	public int styleNum = 0;
	private int position = 0;

	private ImageView circularImage, home_title_bar_image;
	private Button menu_btn_right;

	private void findBy() {
		// 歌曲列表
		mListView = (ListView) findViewById(R.id.song_lv);
		// 上一首，播放暂停、下一首
		pre_button = (ImageButton) findViewById(R.id.pre_button);
		play_button = (ImageButton) findViewById(R.id.play_button);
		next_button = (ImageButton) findViewById(R.id.next_button);

		txt_wb_title = (TextView) findViewById(R.id.home_title_login_user);
		home_title_bar_image = (ImageView) findViewById(R.id.home_title_bar_image);
		circularImage = (CircularImage) findViewById(R.id.home_title_bar_user_photo);
		menu_btn_right = (Button) findViewById(R.id.menu_btn_right);
		txt_wb_title.setText(R.string.love_music);
		home_title_bar_image.setVisibility(View.GONE);
		circularImage.setVisibility(View.GONE);
		menu_btn_right.setVisibility(View.GONE);

		// 进度条
		seekbar = (SeekBar) findViewById(R.id.mseekBar);
		// 时间TextView
		play_time = (TextView) findViewById(R.id.play_time);
		play_style = (TextView) findViewById(R.id.play_style);
		// 歌手名和专辑图像
		mName = (TextView) findViewById(R.id.mText);
		mAlbum = (ImageView) findViewById(R.id.album_imageView);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_play);
		// 绑定
		findBy();
		// TabHost
		mAdapter = new MusicAdapter(this);
		mListView.setAdapter(mAdapter);
		pre_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (MusicPlayActivity.mAdapter.musicList != null
						&& MusicPlayActivity.mAdapter.musicList.size() != 0) {
					Intent pre = new Intent(MusicPlayActivity.this,
							MusicService.class);
					pre.putExtra("control", "previous");
					startService(pre);
				}
			}
		});
		play_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (MusicPlayActivity.mAdapter.musicList != null
						&& MusicPlayActivity.mAdapter.musicList.size() != 0) {
					Intent play = new Intent(MusicPlayActivity.this,
							MusicService.class);
					play.putExtra("control", "play");
					startService(play);
				}
			}
		});

		next_button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (MusicPlayActivity.mAdapter.musicList != null
						&& MusicPlayActivity.mAdapter.musicList.size() != 0) {
					Intent next = new Intent(MusicPlayActivity.this,
							MusicService.class);
					next.putExtra("control", "next");
					startService(next);
				}
			}
		});
		play_style.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				styleNum++;
				if (styleNum == 100) {
					styleNum = 0;
				}
				if (styleNum % 3 == 0) {
					play_mode = MusicInfo.LISTREPEAT;
					play_style.setText(R.string.list_repeat);
				} else if (styleNum % 3 == 1) {
					play_mode = MusicInfo.SINGLEREPEAT;
					play_style.setText(R.string.single_repeat);
				} else if (styleNum % 3 == 2) {
					play_mode = MusicInfo.RANDOM;
					play_style.setText(R.string.random_style);
				}

			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent play = new Intent(MusicPlayActivity.this,
						MusicService.class);
				play.putExtra("control", "listClick");
				play.putExtra("musicId", arg2);
				startService(play);
			}
		});
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (MusicService.mplayer != null) {
					if (fromUser) {
						MusicService.mplayer.seekTo(progress);
					}
					play_time.setText(mAdapter.toTime(progress));
				} else {
					seekBar.setMax(0);
				}

			}
		});
	}

	// 主菜单点击返回键，弹出对话框
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), R.string.quit,
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				stopService(new Intent(MusicPlayActivity.this,
						MusicService.class));
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onPause() {// 如果突然电话到来，停止播放音乐
		if (MusicService.mplayer != null && MusicService.mplayer.isPlaying()
				&& AccessTokenKeeper.readMusic(MusicPlayActivity.this)) {
			position = MusicService.mplayer.getCurrentPosition();// 保存当前播放点
			MusicService.mplayer.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (position > 0) {// 如果电话结束，继续播放音乐
			Uri mp3Uri = Uri.parse(MusicPlayActivity.mAdapter.musicList.get(
					MusicService.playing_id).getMusicPath());
			if (MusicService.mplayer != null) {
				MusicService.mplayer.stop();
				MusicService.mplayer.reset();
				MusicService.mplayer = null;
			}
			MusicService.mplayer = MediaPlayer.create(this, mp3Uri);
			MusicService.mplayer.start();// 播放
			MusicService.mplayer.seekTo(position);
			position = 0;
		}
		super.onResume();
	}
	public int getSize(){
		if (MusicPlayActivity.mAdapter !=null&&MusicPlayActivity.mAdapter.musicList != null
				&& MusicPlayActivity.mAdapter.musicList.size() != 0) {
			return MusicPlayActivity.mAdapter.musicList.size();
		}else{
			return -1;
		}
	}
}
