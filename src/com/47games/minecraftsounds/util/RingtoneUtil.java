package com.foursevengames.minecraftsounds.util;

import com.foursevengames.minecraftsounds.controller.*;
import com.foursevengames.minecraftsounds.R;

import java.io.File;
import android.net.Uri;
import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import android.content.ContentValues;
import android.provider.MediaStore;
import android.content.Context;
import android.media.RingtoneManager;
import android.view.MenuItem;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.os.Environment;
import java.lang.NullPointerException;

public class Ringtone {
  private MenuItem menuItem;
  private ContentResolver mCr;
  private Context context;
  private Environment envir;
  private File newSoundFile;

  public Ringtone(MenuItem menuItem, ContentResolver mCr, Context context) {
    this.menuItem = menuItem;
    this.mCr = mCr;
    this.context = context;
  }

  public void setAsRingtone() {
    AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuItem.getMenuInfo();
    int position = info.position;
    try {
      newSoundFile = new File(context.getExternalFilesDir(envir.DIRECTORY_RINGTONES).toString(), MobListActivity.names[position] + ".oog");
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    try {
      newSoundFile.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Uri mUri = Uri.parse("android.resource://com.foursevengames.minecraftsounds/" + MobListActivity.sounds[position]);
    AssetFileDescriptor soundFile;
    try {
      soundFile= mCr.openAssetFileDescriptor(mUri, "r");
    } catch (FileNotFoundException e) {
      soundFile=null;   
    }

    try {
      byte[] readData = new byte[1024];
      FileInputStream fis = soundFile.createInputStream();
      FileOutputStream fos = new FileOutputStream(newSoundFile);
      int i = fis.read(readData);

      while (i != -1) {
        fos.write(readData, 0, i);
        i = fis.read(readData);
      }

      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    ContentValues values = new ContentValues();
    values.put(MediaStore.MediaColumns.DATA, newSoundFile.getAbsolutePath());
    values.put(MediaStore.MediaColumns.TITLE, MobListActivity.names[position]);
    values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/oog");
    values.put(MediaStore.MediaColumns.SIZE, newSoundFile.length());
    values.put(MediaStore.Audio.Media.ARTIST, R.string.app_name);
    values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
    values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
    values.put(MediaStore.Audio.Media.IS_ALARM, true);
    values.put(MediaStore.Audio.Media.IS_MUSIC, false);

    Uri uri = MediaStore.Audio.Media.getContentUriForPath(newSoundFile.getAbsolutePath());
    Uri newUri = mCr.insert(uri, values);

    try {
      RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, newUri);
    } catch (Throwable t) {
    
    }
  }
}
