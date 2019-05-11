package com.example.sjw;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 创建时间：2019/5/11
 * 作者：zhangchao
 * 描述：
 */
public class MusicService extends Service {

  public int id;

  @Override
  public IBinder onBind(Intent intent) {
    id = intent.getIntExtra("id",-1);
    if(id != -1){
      BackgroundMusic.getInstance(this).playBackgroundMusic(id,true);
    }
    return null;
  }

  @Override
  public boolean onUnbind(Intent intent) {
    BackgroundMusic.getInstance(this).end();
    return super.onUnbind(intent);
  }
}
