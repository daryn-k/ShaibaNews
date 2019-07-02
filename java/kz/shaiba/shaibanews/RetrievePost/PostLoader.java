package kz.shaiba.shaibanews.RetrievePost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;

import kz.shaiba.shaibanews.BaseUtils;
import kz.shaiba.shaibanews.DB.DBHelper;
import kz.shaiba.shaibanews.DB.DBUtils;
import kz.shaiba.shaibanews.JSON.JSONUtils;

public class PostLoader extends android.support.v4.content.AsyncTaskLoader<PostItemData> {

    Context ctx;
    DBHelper dbHelper;
    String content_json;
    String post_id;
    PostItemData postItem;

    public PostLoader(Context context, String post_id) {
        super(context);
        this.ctx = context;
        this.post_id = post_id;
        dbHelper = new DBHelper(ctx);
    }

    @Override
    public PostItemData loadInBackground() {

        content_json = DBUtils.getContentJSONById(post_id, dbHelper);

        if(content_json.equalsIgnoreCase("")){
            if(!BaseUtils.isNetworkAvailable(ctx)){
                return null;
            }
            else {
                String postURL = "http://moscow2016iihf.com/mobile/get_post/" + post_id;
                content_json = JSONUtils.getJSON(postURL, ctx);
                DBUtils.updatePostInDB(content_json, post_id, dbHelper);
            }
        }

        postItem = new PostItemData();
        postItem  = new Gson().fromJson(content_json, PostItemData.class);

        return postItem;
    }




}
