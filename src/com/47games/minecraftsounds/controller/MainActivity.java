package com.foursevengames.minecraftsounds.controller;

import com.foursevengames.minecraftsounds.R;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {
  public static final String TAG = MainActivity.class.getSimpleName();
 
  public void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "onCreate");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
 
    Resources resources = getResources(); 
    TabHost tabHost = getTabHost(); 
 
    // Mobs tab
    Intent intentMob = new Intent().setClass(this, MobListActivity.class);
    TabSpec tabSpecMob = tabHost
      .newTabSpec("Mobs")
      .setIndicator("", resources.getDrawable(R.drawable.icon_mob_config))
      .setContent(intentMob);
 
    // Environment tab
    Intent intentEnv = new Intent().setClass(this, EnvListActivity.class);
    TabSpec tabSpecEnv = tabHost
      .newTabSpec("Environment")
      .setIndicator("", resources.getDrawable(R.drawable.icon_env_config))
      .setContent(intentEnv);
 
    // Music tab
    Intent intentMusic = new Intent().setClass(this, MusicListActivity.class);
    TabSpec tabSpecMusic = tabHost
      .newTabSpec("Music")
      .setIndicator("", resources.getDrawable(R.drawable.icon_music_config))
      .setContent(intentMusic);

    // add all tabs 
    tabHost.addTab(tabSpecMob);
    tabHost.addTab(tabSpecEnv);
    tabHost.addTab(tabSpecMusic);
 
    //set Mobs tab as default (zero based)
    tabHost.setCurrentTab(0);
  }
}
