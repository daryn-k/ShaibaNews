package kz.shaiba.shaibanews.RetrieveFeed;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.google.gson.Gson;

import java.util.ArrayList;

import kz.shaiba.shaibanews.BaseUtils;
import kz.shaiba.shaibanews.DB.DBHelper;
import kz.shaiba.shaibanews.DB.DBUtils;
import kz.shaiba.shaibanews.JSON.JSONUtils;

public class FeedLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<RSSItemData>> {

    ArrayList<RSSItemData> listData;
    Context ctx;
    RSSItemData rssItem;
    final Integer HTTP_CONNECTION_TIMEOUT = 15;
    DBHelper dbHelper;


    public FeedLoader(Context context) {
        super(context);
        this.ctx = context;
        dbHelper = new DBHelper(ctx);
    }


    @Override
    public ArrayList<RSSItemData> loadInBackground() {

        listData = new ArrayList<RSSItemData>();

        if(!BaseUtils.isNetworkAvailable(ctx)) return DBUtils.getNewsFromDB(dbHelper);

        if(DBUtils.doWeHaveAnyNews(dbHelper, rssItem, ctx)){

            String data = JSONUtils.getJSON("http://moscow2016iihf.com/mobile", ctx);
            JSONResult result  = new Gson().fromJson(data, JSONResult.class);
            listData = result.items;

            DBUtils.deleteAllNewsFromDB(dbHelper);
            DBUtils.putNewsIntoDB(listData, dbHelper);

        }
        else{
            listData = DBUtils.getNewsFromDB(dbHelper);
        }

        return listData;
    }



}
