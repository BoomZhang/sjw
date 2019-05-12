package com.example.sjw;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import static com.example.sjw.CreateActivity.getRealFilePath;

/**
 * 创建时间：2019/5/12
 * 作者：sjw
 * 描述：
 */
public class ShareActivity extends AppCompatActivity {

  private ShareActionProvider mShareActionProvider;
  private ImageView imageView;
  private String imagePath;
  private Uri uri;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.share_layout);
    initView();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate menu resource file.
    getMenuInflater().inflate(R.menu.share, menu);

    // Locate MenuItem with ShareActionProvider
    MenuItem item = menu.findItem(R.id.menu_item_share);

    // Fetch and store ShareActionProvider
    mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

    mShareActionProvider.setShareIntent(getShareIntent());
    // Return true to display menu
    return true;
  }


  private void initView() {
    imagePath = getIntent().getStringExtra("path");
    imageView = (ImageView) findViewById(R.id.image_share);
    if(!("".equals(imagePath) || imagePath == null)){
      uri = Uri.parse(imagePath);
      CreateGvAdpater.MyTask task = new CreateGvAdpater.MyTask();
      task.setmIv(imageView);
      task.execute(getRealFilePath(this,uri));
    }
  }

  /**
   * 获得跳转的Intent
   *
   * @return
   */
  private Intent getShareIntent() {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("image/*");
    intent.putExtra(Intent.EXTRA_STREAM, uri);
    return intent;
  }
}
