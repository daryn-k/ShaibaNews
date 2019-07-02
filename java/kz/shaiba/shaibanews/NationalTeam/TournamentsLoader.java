package kz.shaiba.shaibanews.NationalTeam;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;

import kz.shaiba.shaibanews.BaseUtils;
import kz.shaiba.shaibanews.DB.DBHelper;
import kz.shaiba.shaibanews.DB.DBUtils;
import kz.shaiba.shaibanews.JSON.JSONUtils;

public class TournamentsLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<TournamentData>> {
    
    ArrayList<TournamentData> listData;
    Context ctx;
    String data;
    DBHelper dbHelper;

    public TournamentsLoader(Context context) {
        super(context);
        this.ctx = context;
        dbHelper = new DBHelper(ctx);
    }

    @Override
    public ArrayList<TournamentData> loadInBackground() {

        data = DBUtils.getSavenJSONforPage(dbHelper, "NationalTeam");

        if(data==null){
            if(!BaseUtils.isNetworkAvailable(ctx)){
                return null;
            }
            else {
                data = JSONUtils.getJSON("http://moscow2016iihf.com/mobile/wc", ctx);
                DBUtils.putSavedJSONofPage(dbHelper, "NationalTeam", data);
            }
        }

        JSONResultTournaments result  = new Gson().fromJson(data, JSONResultTournaments.class);

        if(result==null) return null;

        listData = result.tournament;

        return listData;
    }

}
