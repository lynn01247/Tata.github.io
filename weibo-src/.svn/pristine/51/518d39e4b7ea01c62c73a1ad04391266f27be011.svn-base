package com.tatait.tataweibo.service;

import java.util.Random;

import com.tatait.tataweibo.TabMainActivity;
import com.tatait.tataweibo.MusicPlayActivity;
import com.tatait.tataweibo.R;
import com.tatait.tataweibo.bean.MusicInfo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class MusicService extends Service implements OnCompletionListener {
	public static MediaPlayer mplayer;
	public static int playing_id = 0;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		initMediaSource(initMusicUri(0));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		String playFlag = intent.getExtras().getString("control");
		if ("play".equals(playFlag)) {
			playMusic();
		} else if ("next".equals(playFlag)) {
			offHandler();
			playNext();
		} else if ("previous".equals(playFlag)) {
			offHandler();
			playPre();
		} else if ("listClick".equals(playFlag)) {
			offHandler();
			playing_id = intent.getExtras().getInt("musicId");
			initMediaSource(initMusicUri(playing_id));
			playMusic();
		}
	}

	public void offHandler() {
		playFlag = false;
	}

	/**
	 * 初始化媒体对象
	 * 
	 * @param mp3Path
	 */
	public void initMediaSource(String mp3Path) {
		Uri mp3Uri = Uri.parse(mp3Path);
		if (mplayer != null) {
			mplayer.stop();
			mplayer.reset();
			mplayer = null;
		}
		mplayer = MediaPlayer.create(this, mp3Uri);
		mplayer.setOnCompletionListener(this);
	}

	/**
	 * 返回列表第几行的歌曲路径
	 * 
	 * @param _id
	 *            表示歌曲序号，从0开始
	 * @return
	 */
	public String initMusicUri(int _id) {
		playing_id = _id;
		return MusicPlayActivity.mAdapter.musicList.get(playing_id).getMusicPath();
	}

	/**
	 * 音乐播放方法，并且带有暂停方法
	 */
	public Thread thread;
	public boolean playFlag = true;

	public void playMusic() {

		if (mplayer != null) {
			if (mplayer.isPlaying()) {
				MusicPlayActivity.play_button
						.setImageResource(R.drawable.play_button_xml);
				TabMainActivity.menuPlayButton
						.setImageResource(R.drawable.play_button_xml);
				mplayer.pause();
			} else {
				setInfo();
				MusicPlayActivity.play_button
						.setImageResource(R.drawable.pause_button_xml);
				TabMainActivity.menuNextButton
						.setImageResource(R.drawable.pause_button_xml);
				mplayer.start();
			}
			mHandler.post(mRunnable);
			playFlag = true;
			thread = new Thread() {
				@Override
				public void run() {
					while (playFlag) {
						MusicPlayActivity.playingTime = mplayer.getCurrentPosition();
						MusicPlayActivity.seekbar.setProgress(MusicPlayActivity.playingTime);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}
			};
			thread.start();
		}
		mplayer.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				if (MusicPlayActivity.play_mode == MusicInfo.LISTREPEAT) {
					playNext();
				} else if (MusicPlayActivity.play_mode == MusicInfo.SINGLEREPEAT) {
					initMediaSource(initMusicUri(playing_id));
					playMusic();
				} else {
					Random rand = new Random();
					int i = rand.nextInt(); // int范围类的随机数
					int size = MusicPlayActivity.mAdapter.musicList.size();
					i = rand.nextInt(size); // 生成0-100以内的随机数
					initMediaSource(initMusicUri(i));
					playMusic();
				}

			}
		});
	}

	public void setInfo() {
		// 获得歌曲时间
		MusicPlayActivity.songTime = MusicPlayActivity.mAdapter.musicList.get(
				MusicService.playing_id).getMusicTime();
		MusicPlayActivity.seekbar.setMax(MusicPlayActivity.songTime);
		MusicPlayActivity.mName.setText(MusicPlayActivity.mAdapter
				.toMp3(MusicPlayActivity.mAdapter.musicList
						.get(MusicService.playing_id).getMusicName()));
		String url = MusicPlayActivity.mAdapter
				.getAlbumArt(MusicPlayActivity.mAdapter.musicList.get(
						MusicService.playing_id).getMusicId());
		if (url != null) {
			MusicPlayActivity.mAlbum.setImageURI(Uri.parse(url));
		} else {
			MusicPlayActivity.mAlbum.setImageResource(R.drawable.album);
		}
	}

	// 上一首
	public void playPre() {
		if (MusicPlayActivity.play_mode == MusicInfo.RANDOM) {
			Random rand = new Random();
			int i = rand.nextInt(); // int范围类的随机数
			int size = MusicPlayActivity.mAdapter.musicList.size();
			i = rand.nextInt(size); // 生成0-100以内的随机数
			initMediaSource(initMusicUri(i));
		} else {
			if (playing_id == 0) {
				playing_id = MusicPlayActivity.mAdapter.musicList.size() - 1;
				initMediaSource(initMusicUri(playing_id));
			} else {
				initMediaSource(initMusicUri(--playing_id));
			}
		}
		playMusic();
	}

	// 下一首
	public void playNext() {
		if (MusicPlayActivity.play_mode == MusicInfo.RANDOM) {
			Random rand = new Random();
			int i = rand.nextInt(); // int范围类的随机数
			int size = MusicPlayActivity.mAdapter.musicList.size();
			i = rand.nextInt(size); // 生成0-100以内的随机数
			initMediaSource(initMusicUri(i));
		} else {
			if (playing_id == MusicPlayActivity.mAdapter.musicList.size() - 1) {
				initMediaSource(initMusicUri(0));
			} else {
				initMediaSource(initMusicUri(++playing_id));
			}
		}
		playMusic();
	}

	public void onCompletion(MediaPlayer arg0) {
		Toast.makeText(this, "onCompletion", Toast.LENGTH_LONG).show();
	}

	Handler mHandler = new Handler();

	Runnable mRunnable = new Runnable() {
		public void run() {
			mHandler.postDelayed(mRunnable, 100);
		}
	};
}