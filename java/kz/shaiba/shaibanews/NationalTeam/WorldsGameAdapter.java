package kz.shaiba.shaibanews.NationalTeam;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.AbstractCollection;
import java.util.ArrayList;

import kz.shaiba.shaibanews.R;


public class WorldsGameAdapter extends RecyclerView.Adapter {

    ArrayList<GameData> games;
    Activity ctx;

    public WorldsGameAdapter(ArrayList<GameData> games, FragmentActivity ctx) {
        this.games = games;
        this.ctx = ctx;
        Log.d("myLogs", "WorldsGameAdapter Construstor");
    }

    public  static  class ViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView team1_name;
        TextView team2_name;
        TextView team1_score;
        TextView team2_score;

        public ViewHolder(View itemView) {
            super(itemView);

            Log.d("myLogs", "ViewHolder");

            cv = (CardView) itemView.findViewById(R.id.cv_worlds_games);
            team1_name = (TextView) itemView.findViewById(R.id.team1_name);
            team2_name = (TextView) itemView.findViewById(R.id.team2_name);
            team1_score = (TextView) itemView.findViewById(R.id.team1_score);
            team2_score = (TextView) itemView.findViewById(R.id.team2_score);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        Log.d("myLogs", "onCreateViewHolder");
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.worlds_game_item, viewGroup, false);
        ViewHolder rhv = new ViewHolder(v);
        return rhv;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        Log.d("myLogs", "onBindViewHolder");
        ViewHolder vh = (ViewHolder) holder;
        vh.team1_name.setText(games.get(i).team1);
        vh.team2_name.setText(games.get(i).team2);
        vh.team1_score.setText(games.get(i).score1);
        vh.team2_score.setText(games.get(i).score2);
    }

    @Override
    public int getItemCount() {
        Log.d("myLogs", "getItemCount" + games.size());
        return games.size();
    }
}
