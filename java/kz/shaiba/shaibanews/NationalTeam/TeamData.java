package kz.shaiba.shaibanews.NationalTeam;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class TeamData implements Parcelable {
    String team;
    String flag;

    protected TeamData(Parcel in) {
        team = in.readString();
        flag = in.readString();
    }

    public static final Creator<TeamData> CREATOR = new Creator<TeamData>() {
        @Override
        public TeamData createFromParcel(Parcel in) {
            return new TeamData(in);
        }

        @Override
        public TeamData[] newArray(int size) {
            return new TeamData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(team);
        parcel.writeString(flag);
    }
}
