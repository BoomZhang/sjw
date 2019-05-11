package com.example.sjw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建时间：2019/4/27
 * 作者：sjw
 * 描述：
 */
public class CreateGvAdpater extends BaseAdapter{

  private List<String> data;
  private Context context;

  public CreateGvAdpater(Context context, List<String> data){
    this.context = context;
    this.data = data;
  }

  public void setData(List<String> data){
    this.data = data;
  }

  @Override
  public int getCount() {
    return data.size();
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    ViewHolder viewHolder;
    if(null == convertView){
      convertView = LayoutInflater.from(context).inflate(R.layout.activity_create_gridview_item,null);
      viewHolder = new ViewHolder(convertView);
      convertView.setTag(viewHolder);
    }else{
      viewHolder = (ViewHolder) convertView.getTag();
    }
    //viewHolder.mImage.setImageBitmap(getLoacalBitmap(data.get(position).getImage_path()));

    MyTask task = new MyTask();
    task.setmIv(viewHolder.mImage);
    task.execute(data.get(position));

    return convertView;
  }

  public static Map<String,Bitmap> map = new HashMap<String,Bitmap>();

  /**
   * 加载本地图片
   * @param url
   * @return
   */
  public static Bitmap getLoacalBitmap(String url) {

    if(map.containsKey(url)){
      Log.d("sjw","step");
      return map.get(url);
    }

    try {
      FileInputStream fis = new FileInputStream(url);
      Bitmap bitmap = BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片
      map.put(url,bitmap);
      Log.d("sjw","bitmap == null is" + String.valueOf(bitmap == null));
      return bitmap;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static class MyTask extends AsyncTask<String,Integer,Bitmap>{

    private ImageView mIv;

    public void setmIv(ImageView mIv){
      this.mIv = mIv;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
      return getLoacalBitmap(strings[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
      super.onPostExecute(bitmap);
      mIv.setImageBitmap(bitmap);
    }
  }

  public final class ViewHolder{

    public ImageView mImage;

    public ViewHolder(View view){
      mImage = (ImageView) view.findViewById(R.id.choosen_picture_iv);
    }

  }

}
