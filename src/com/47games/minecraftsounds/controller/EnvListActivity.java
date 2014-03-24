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

public class EnvListActivity extends Activity {
  public static final String TAG = EnvListActivity.class.getSimpleName();
  Context context = this;
  final SoundPool sp = new SoundPool(25, AudioManager.STREAM_MUSIC, 0);
  private MyItemClickListener click;
  public static int[] soundIds = new int[25];
  public static int[] sounds = {
    R.raw.env_chest_open, R.raw.env_cloth, R.raw.env_door,
    R.raw.env_fire, R.raw.env_firework_launch, R.raw.env_glass,
    R.raw.env_grass, R.raw.env_gravel, R.raw.env_lava,
    R.raw.env_minecart, R.raw.env_piston, R.raw.env_portal,
    R.raw.env_sand, R.raw.env_snow, R.raw.env_stone,
    R.raw.env_tnt, R.raw.env_water, R.raw.env_wood
  };
  Integer[] images = {
    R.drawable.env_chest, R.drawable.env_cloth, R.drawable.env_door,
    R.drawable.env_fire, R.drawable.env_firework, R.drawable.env_glass,
    R.drawable.env_grass, R.drawable.env_gravel, R.drawable.env_lava,
    R.drawable.env_minecart, R.drawable.env_piston, R.drawable.env_portal,
    R.drawable.env_sand, R.drawable.env_snow, R.drawable.env_stone, 
    R.drawable.env_tnt, R.drawable.env_water, R.drawable.env_wood
  };
  public static String[] names = {
    "Chest", "Wool", "Door",
    "Fire", "Firework", "Glass",
    "Grass", "Gravel", "Lava",
    "Minecart", "Piston", "Portal",
    "Sand", "Snow", "Stone",
    "TNT", "Water", "Wood"
  };
  @Override
  public void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "onCreate");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.env);
    GridView main = (GridView) findViewById(R.id.env_grid);
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
      Toast.makeText(context, "About was Selected, env", Toast.LENGTH_SHORT).show();
      return true;

    case R.id.menu_help:
      Toast.makeText(context, "Help was Selected, env", Toast.LENGTH_SHORT).show();
      return true;

    case R.id.menu_report_bug:
      Toast.makeText(context, "Report a bug was Selected, env", Toast.LENGTH_SHORT).show();
      return true;

    default:
      return super.onOptionsItemSelected(item);
    }
  }*/
}
