package com.foursevengames.minecraftsounds.util;

import com.foursevengames.minecraftsounds.controller.*;
import com.foursevengames.minecraftsounds.R;

import android.content.Context;
import android.media.AudioManager;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.media.AudioManager;
import android.media.MediaPlayer;
import java.lang.Object;
import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;
import android.widget.ImageView;

public class MyMpItemClickListener implements OnItemClickListener {
  Context context;
  AudioManager manager;
  MediaPlayer mp;
  MediaPlayer[] mediaPlayers;

  @Override
  public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    float actualVolume = (float) manager.getStreamVolume(AudioManager.STREAM_MUSIC);
    float maxVolume = (float) manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    float volume = actualVolume / maxVolume;
    ImageView view = (ImageView) v;
    try {
      MediaPlayer mp = mediaPlayers[position];
      if (mp.isPlaying()) {
        mp.pause();
        view.setImageResource(MusicListActivity.images[position]);
      } else {
        mp.start();
        view.setImageResource(R.drawable.music_pause);
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
  }
  
  public void setContext(Context context) {
    this.context = context;
  }
  
  public void giveAudioManager(AudioManager manager) {
    this.manager = manager;
  }

  public void giveMediaPlayers(MediaPlayer[] mediaPlayers) {
    this.mediaPlayers = mediaPlayers;
  }
}
