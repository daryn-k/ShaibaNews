package kz.shaiba.shaibanews.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import kz.shaiba.shaibanews.JSON.JSONUtils;
import kz.shaiba.shaibanews.RetrieveFeed.JSONResult;
import kz.shaiba.shaibanews.RetrieveFeed.RSSItemData;


public class DBUtils {

    public static boolean doWeHaveAnyNews(DBHelper dbHelper, RSSItemData rssItem, Context ctx){

        ArrayList<RSSItemData> listDataFromSite = new ArrayList<RSSItemData>();
        ArrayList<RSSItemData> listDataFromDB = new ArrayList<RSSItemData>();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        /* Смотрим из сайта последнюю новость */

        String data = JSONUtils.getJSON("http://moscow2016iihf.com/mobile/get_lastnews", ctx);
        JSONResult result  = new Gson().fromJson(data, JSONResult.class);
        if(result!=null) listDataFromSite = result.items;

        /* Смотрим из БД последнюю новость */

        Cursor cursor = db.rawQuery("select * from headlines limit 1",null);

        if (cursor.moveToFirst()) {
            rssItem = new RSSItemData();
            rssItem.postLink = cursor.getString(cursor.getColumnIndex("link"));
            listDataFromDB.add(rssItem);
        }

        /* Сверяем последнюю новость из сайта и последнюю новость из БД.
        Если новости совпадают, то новых новостей нет - false.
        Если БД пуста, то новости есть - true. */

        if(cursor!=null && cursor.getCount()>0 && listDataFromSite.size()>0) {
            if (listDataFromSite.get(0).postLink.equalsIgnoreCase(listDataFromDB.get(0).postLink)) {
                return false;
            }
        }

        return true;
    }

    public static void putNewsIntoDB(ArrayList<RSSItemData> listData, DBHelper dbHelper){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        for(RSSItemData item : listData) {
            cv.put("title", item.postTitle);
            cv.put("link", item.postLink);
            cv.put("date", item.postDate);
            cv.put("date_unix", item.postDateUNIX);
            cv.put("description", item.postDesc);
            cv.put("image", item.postImage);
            cv.put("content_json", item.postHTML);
            db.insert("headlines", null, cv);
        }
    }

    public static void deleteAllNewsFromDB(DBHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("headlines", null, null);
    }

    public static void deleteAllSavedJSONFromDB(DBHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("saved_json", null, null);
    }

    public static ArrayList<RSSItemData> getNewsFromDB(DBHelper dbHelper){

        RSSItemData rssItem;

        ArrayList<RSSItemData> listDataFromDB = new ArrayList<RSSItemData>();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from headlines", null);

        if (cursor .moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                rssItem = new RSSItemData();
                rssItem.postTitle = cursor.getString(cursor.getColumnIndex("title"));
                rssItem.postLink = cursor.getString(cursor.getColumnIndex("link"));
                rssItem.postDate = cursor.getString(cursor.getColumnIndex("date"));
                rssItem.postDateUNIX = cursor.getString(cursor.getColumnIndex("date_unix"));
                rssItem.postDesc = cursor.getString(cursor.getColumnIndex("description"));
                rssItem.postImage = cursor.getString(cursor.getColumnIndex("image"));
                rssItem.postHTML = cursor.getString(cursor.getColumnIndex("content_json"));
                cursor.moveToNext();
                listDataFromDB.add(rssItem);
            }
        }

        return listDataFromDB;
    }

    public static void updatePostInDB(String data, String post_id, DBHelper dbHelper){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("content_json", data);
        db.update("headlines", cv, "link = ?", new String[] { post_id });

    }

    public static String getContentJSONById(String post_id, DBHelper dbHelper){

        String content_json = "";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String queryString = "select * from headlines where link = ?";
        String[] whereArgs = new String[] { post_id };
        Cursor cursor = db.rawQuery(queryString, whereArgs);

        if (cursor.moveToFirst()) {
            content_json = cursor.getString(cursor.getColumnIndex("content_json"));
        }

        if(content_json==null){
            content_json = "";
        }

        return content_json;

    }

    public static void putSavedJSONofPage(DBHelper dbHelper, String page_name, String data){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("content", data);
        db.update("saved_json", cv, "page_name = ?", new String[] { page_name });
    }

    public static String getSavenJSONforPage(DBHelper dbHelper, String page_name){

        String content_json = "";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String queryString = "select * from saved_json where page_name = ?";
        String[] whereArgs = new String[] { page_name };
        Cursor cursor = db.rawQuery(queryString, whereArgs);

        Log.d("myLogs", "cursor rows = " + cursor.getCount());

        if (cursor.moveToFirst()) {
            content_json = cursor.getString(cursor.getColumnIndex("content"));
        }

        Log.d("myLogs", "content_json = " + content_json);

        if(content_json.equalsIgnoreCase("")) return null;

        return content_json;

    }
}
