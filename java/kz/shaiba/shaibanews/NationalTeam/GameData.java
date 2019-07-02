package kz.shaiba.shaibanews.NationalTeam;

import android.os.Parcel;
import android.os.Parcelable;

public class GameData implements Parcelable {

    String team1;
    String team2;
    String flag1;
    String flag2;
    String score1;
    String score2;
    String ot;
    String so;
    String date;
    String time;
    String arena;

    protected GameData(Parcel in) {
        team1 = in.readString();
        team2 = in.readString();
        flag1 = in.readString();
        flag2 = in.readString();
        score1 = in.readString();
        score2 = in.readString();
        ot = in.readString();
        so = in.readString();
        date = in.readString();
        time = in.readString();
        arena = in.readString();
    }

    public static final Creator<GameData> CREATOR = new Creator<GameData>() {
        @Override
        public GameData createFromParcel(Parcel in) {
            return new GameData(in);
        }

        @Override
        public GameData[] newArray(int size) {
            return new GameData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(team1);
        parcel.writeString(team2);
        parcel.writeString(flag1);
        parcel.writeString(flag2);
        parcel.writeString(score1);
        parcel.writeString(score2);
        parcel.writeString(ot);
        parcel.writeString(so);
        parcel.writeString(date);
        parcel.writeString(time);
        parcel.writeString(arena);
    }
}
