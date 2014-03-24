package com.foursevengames.minecraftsounds.controller;

import com.foursevengames.minecraftsounds.R;
import com.foursevengames.minecraftsounds.util.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.os.AsyncTask;
import android.content.Context;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.view.ViewGroup.LayoutParams;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.widget.Toast;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.util.Log;
import java.io.IOException;

public class MusicListActivity extends Activity {
  public static final String TAG = EnvListActivity.class.getSimpleName();
  Context context = this;
  MediaPlayer[] mediaPlayers = new MediaPlayer[12];
  private MyMpItemClickListener click;
  int[] sounds = {
    R.raw.music_blocks,
    R.raw.music_cat,
    R.raw.music_chirp,
    R.raw.music_eleven,
    R.raw.music_far,
    R.raw.music_mall,
    R.raw.music_mellohi,
    R.raw.music_stal,
    R.raw.music_strad,
    R.raw.music_thirteen,
    R.raw.music_ward,
    R.raw.music_wait
  };
  public static Integer[] images = {
    R.drawable.music_blocks,
    R.drawable.music_cat,
    R.drawable.music_chirp,
    R.drawable.music_eleven,
    R.drawable.music_far,
    R.drawable.music_mall,
    R.drawable.music_mellohi,
    R.drawable.music_stal,
    R.drawable.music_strad,
    R.drawable.music_thirteen,
    R.drawable.music_ward,
    R.drawable.music_wait
  };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "onCreate");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.music);
    GridView main = (GridView) findViewById(R.id.music_grid);
    MpImageAdapter adapt = new MpImageAdapter(context);
    registerForContextMenu(main);
    adapt.giveImageIds(images);
    main.setAdapter(adapt);
    
    this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    // Getting the user sound settings
    AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

    (new LoadSounds()).execute();
    click = new MyMpItemClickListener();
    click.giveAudioManager(audioManager);
    click.setContext(context);
    main.setOnItemClickListener(click);
  }

  private class LoadSounds extends AsyncTask<Void, Void, Void> {
    ProgressDialog dialog;
    protected void onPreExecute() {
      dialog = new ProgressDialog(context);
      dialog.setMessage("Loading Media...");
      dialog.setCancelable(false);
      dialog.setCanceledOnTouchOutside(false);
      dialog.show();
    }
    protected Void doInBackground(Void... params) {
      for (int i = 0; i < sounds.length; i++) {
        MediaPlayer mp = new MediaPlayer();
    
        Resources res = context.getResources();
        AssetFileDescriptor afd = res.openRawResourceFd(sounds[i]);
    
        try {
          mp.reset();
          mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
          mp.setLooping(true);
          mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
          mp.prepare();
        } catch (IOException e) {
          e.printStackTrace();
        }
        mediaPlayers[i] = mp;
      }
      return null;
    }
    protected void onPostExecute(Void result) {
      dialog.dismiss();
      click.giveMediaPlayers(mediaPlayers);
    }
  }

  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.layout.context_menu, menu);
  }

  @Override
  public boolean onContextItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_set_ringtone:
        return true;
      default:
        return super.onContextItemSelected(item);
    }
  }

/*
  @Override
  public boolean onCreateOptionsMenu(Menu menu) { 
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.layout.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {
    case R.id.menu_about:
      Toast.makeText(context, "About was Selected, music", Toast.LENGTH_SHORT).show();
      return true;

    case R.id.menu_help:
      Toast.makeText(context, "Help was Selected, music", Toast.LENGTH_SHORT).show();
      return true;

    case R.id.menu_report_bug:
      Toast.makeText(context, "Report a bug was Selected, music", Toast.LENGTH_SHORT).show();
      return true;

    default:
      return super.onOptionsItemSelected(item);
    }
  }*/
}
