package com.example.sjw;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 创建时间：2019/2/22
 * 作者：sjw
 * 描述：获取时间的工具类，进行一次封装
 */
public final class DateUtil {

  public static String getTime(){
    long time = System.currentTimeMillis();
    Date date = new Date(time);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String data = format.format(date);
    return data;
  }
}
