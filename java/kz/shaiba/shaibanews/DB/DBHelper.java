package kz.shaiba.shaibanews.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "shaiba.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("myLogs", "--- onCreate database ---");

        db.execSQL("create table headlines ("
                + "id integer primary key autoincrement,"
                + "title text,"
                + "link text,"
                + "date text,"
                + "date_unix text,"
                + "description text,"
                + "image text,"
                + "content_json text default ''"
                + ");");

        db.execSQL("create table saved_json ("
                + "id integer primary key autoincrement,"
                + "page_name text,"
                + "content text"
                + ");");

        ContentValues cv = new ContentValues();
        cv.put("page_name", "NationalTeam");
        cv.put("content", "");
        db.insert("saved_json", null, cv);

        ContentValues cv2 = new ContentValues();
        cv2.put("page_name", "Clubs");
        cv2.put("content", "");
        db.insert("saved_json", null, cv2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
