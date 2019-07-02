package kz.shaiba.shaibanews.NationalTeam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kz.shaiba.shaibanews.PostPage;
import kz.shaiba.shaibanews.R;
import kz.shaiba.shaibanews.RetrieveFeed.ItemClickSupport;
import kz.shaiba.shaibanews.UrlImageViewHelper.UrlImageViewHelper;
import kz.shaiba.shaibanews.WorldsPage;


public class TournamentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<TournamentData> tournament_items;
    Activity ctx;

    public TournamentAdapter(ArrayList<TournamentData> tournament_items, FragmentActivity ctx){
        this.tournament_items = tournament_items;
        this.ctx = ctx;
    }


    public  static  class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tournament_type;
        TextView tournament_year;
        TextView tournament_division;
        TextView tournament_dates;
        TextView tournament_city;
        ImageView tournament_image;
        ImageView tournament_logo;

        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_tournament);
            tournament_type = (TextView) itemView.findViewById(R.id.tournament_type);
            tournament_division = (TextView) itemView.findViewById(R.id.tournament_division);
            tournament_city = (TextView) itemView.findViewById(R.id.tournament_city);
            tournament_logo = (ImageView) itemView.findViewById(R.id.worlds_logo);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tournament_item, viewGroup, false);
        ViewHolder rhv = new ViewHolder(v);
        return rhv;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        ViewHolder vh = (ViewHolder) holder;
        vh.tournament_type.setText(tournament_items.get(i).type + " - " + tournament_items.get(i).year);
        vh.tournament_division.setText(tournament_items.get(i).division);
        if(tournament_items.get(i).division.equalsIgnoreCase("")){
            vh.tournament_division.setVisibility(View.GONE);
        }
        if(tournament_items.get(i).city.equalsIgnoreCase("")){
            vh.tournament_city.setText("");
        }
        else{
            vh.tournament_city.setText(tournament_items.get(i).city + ", " + tournament_items.get(i).country);
        }
        UrlImageViewHelper.setUrlDrawable(vh.tournament_logo, tournament_items.get(i).logo);
}

    @Override
    public int getItemCount() {
        return tournament_items.size();
    }
}
