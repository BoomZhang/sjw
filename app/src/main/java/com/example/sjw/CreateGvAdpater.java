package com.example.sjw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.ysq.album.bean.ImageBean;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * 创建时间：2019/4/27
 * 作者：zhangchao
 * 描述：
 */
public class CreateGvAdpater extends BaseAdapter {

  private List<ImageBean> data;
  private Context context;

  public CreateGvAdpater(Context context, List<ImageBean> data){
    this.context = context;
    this.data = data;
  }

  public void setData(List<ImageBean> data){
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
    task.execute(data.get(position).getImage_path());

    return convertView;
  }

  /**
   * 加载本地图片
   * @param url
   * @return
   */
  public static Bitmap getLoacalBitmap(String url) {
    try {
      FileInputStream fis = new FileInputStream(url);
      return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  public class MyTask extends AsyncTask<String,Integer,Bitmap>{

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
      mImage = view.findViewById(R.id.choosen_picture_iv);
    }

  }

}
