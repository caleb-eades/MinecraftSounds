package com.foursevengames.minecraftsounds.controller;

import com.foursevengames.minecraftsounds.R;
import com.foursevengames.minecraftsounds.util.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.AsyncTask;
import android.content.Context;
import android.widget.GridView;
import android.widget.Button;
import android.view.ViewGroup.LayoutParams;
import android.media.AudioManager;
import android.media.SoundPool;
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

public class MobListActivity extends Activity {
  public static final String TAG = MobListActivity.class.getSimpleName();
  Activity act = this;
  Context context = (Context) act;
  final SoundPool sp = new SoundPool(21, AudioManager.STREAM_MUSIC, 0);
  private MyItemClickListener click;
  public static int[] soundIds = new int[21];
  public static int[] sounds = {
    R.raw.mob_bat, R.raw.mob_blaze, R.raw.mob_cat,
    R.raw.mob_chicken, R.raw.mob_cow, R.raw.mob_creeper,
    R.raw.mob_enderdragon, R.raw.mob_enderman, R.raw.mob_ghast,
    R.raw.mob_irongolem, R.raw.mob_magmacube, R.raw.mob_pig,
    R.raw.mob_sheep, R.raw.mob_silverfish, R.raw.mob_skeleton,
    R.raw.mob_slime, R.raw.mob_spider, R.raw.mob_wither,
    R.raw.mob_wolf, R.raw.mob_zombie, R.raw.mob_zombiepigman
  };
  Integer[] images = {
    R.drawable.mob_bat, R.drawable.mob_blaze, R.drawable.mob_cat,
    R.drawable.mob_chicken, R.drawable.mob_cow, R.drawable.mob_creeper,
    R.drawable.mob_enderdragon, R.drawable.mob_enderman, R.drawable.mob_ghast,
    R.drawable.mob_irongolem, R.drawable.mob_magmacube, R.drawable.mob_pig,
    R.drawable.mob_sheep, R.drawable.mob_silverfish, R.drawable.mob_skeleton,
    R.drawable.mob_slime, R.drawable.mob_spider, R.drawable.mob_wither,
    R.drawable.mob_wolf, R.drawable.mob_zombie, R.drawable.mob_zombiepigman 
  };
  public static String[] names = {
    "Bat", "Blaze", "Cat",
    "Chicken", "Cow", "Creeper",
    "Ender Dragon", "Enderman", "Ghast",
    "Iron Golem", "Magma Cube", "Pig",
    "Sheep", "Silverfish", "Skeleton",
    "Slime", "Spider", "Wither",
    "Wolf", "Zombie", "Zombie Pig"
  };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "onCreate");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.mobs);
    GridView main = (GridView) findViewById(R.id.mob_grid);
    ImageAdapter adapt = new ImageAdapter(context);
    registerForContextMenu(main);
    adapt.giveImageIds(images);
    main.setAdapter(adapt);
    
    this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    // Getting the user sound settings
    AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

    (new LoadSounds()).execute();
    click = new MyItemClickListener();
    click.giveSoundPool(sp);
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
        soundIds[i] = sp.load(context, sounds[i], 1);
      }
      return null;
    }
    protected void onPostExecute(Void result) {
      dialog.dismiss();
      click.giveSoundIds(soundIds);
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
        Ringtone ring = new Ringtone(item, getContentResolver(), act);
        ring.setAsRingtone();
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
      Toast.makeText(context, "About was Selected, mobs", Toast.LENGTH_SHORT).show();
      return true;

    case R.id.menu_help:
      Toast.makeText(context, "Help was Selected, mobs", Toast.LENGTH_SHORT).show();
      return true;

    case R.id.menu_report_bug:
      Toast.makeText(context, "Report a bug was Selected, mobs", Toast.LENGTH_SHORT).show();
      return true;

    default:
      return super.onOptionsItemSelected(item);
    }
  }*/
}
