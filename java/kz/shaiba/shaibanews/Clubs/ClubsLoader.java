package kz.shaiba.shaibanews.Clubs;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import kz.shaiba.shaibanews.BaseUtils;
import kz.shaiba.shaibanews.DB.DBHelper;
import kz.shaiba.shaibanews.DB.DBUtils;
import kz.shaiba.shaibanews.JSON.JSONUtils;


public class ClubsLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<ClubData>> {

    ArrayList<ClubData> listData;
    Context ctx;
    String data;
    DBHelper dbHelper;

    public ClubsLoader(Context context) {
        super(context);
        this.ctx = context;
        dbHelper = new DBHelper(ctx);
    }

    @Override
    public ArrayList<ClubData> loadInBackground() {

        data = DBUtils.getSavenJSONforPage(dbHelper, "Clubs");

        if(data==null){
            if(!BaseUtils.isNetworkAvailable(ctx)){
                return null;
            }
            else{
                data = JSONUtils.getJSON("http://moscow2016iihf.com/mobile/clubs", ctx);
                DBUtils.putSavedJSONofPage(dbHelper, "Clubs", data);
            }

        }

        JSONResultClubs result  = new Gson().fromJson(data, JSONResultClubs.class);
        if(result==null) return null;
        listData = result.clubs;

        return listData;
    }
}
