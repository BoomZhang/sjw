package com.example.sjw;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * 创建时间：2019/5/11
 * 作者：zhangchao
 * 描述：
 */
public class MyContentProvider extends ContentProvider {
  @Override
  public boolean onCreate() {
    return false;
  }

  
  @Override
  public Cursor query( Uri uri,  String[] projection,  String selection,
       String[] selectionArgs,  String sortOrder) {
    SQLiteDatabase db = DbManger.getIntance(getContext()).getWritableDatabase();
    String all = "select * from vi";
    Cursor cursor =  DbManger.selectSQL(db,all,null);
    return cursor;
  }

  
  @Override
  public String getType( Uri uri) {
    return null;
  }

  
  @Override
  public Uri insert( Uri uri,  ContentValues values) {
    return null;
  }

  @Override
  public int delete( Uri uri,  String selection,
       String[] selectionArgs) {
    return 0;
  }

  @Override
  public int update( Uri uri,  ContentValues values,  String selection,
       String[] selectionArgs) {
    return 0;
  }
}
