package kz.shaiba.shaibanews.Clubs;

import android.app.Activity;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kz.shaiba.shaibanews.R;
import kz.shaiba.shaibanews.UrlImageViewHelper.UrlImageViewHelper;


public class ClubsAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ClubData> clubs;
    Activity ctx;

    public ClubsAdapter(ArrayList<ClubData> clubs, FragmentActivity ctx){
        this.clubs = clubs;
        this.ctx = ctx;
    }

    public  static  class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView club_name;
        TextView club_city;
        TextView club_farm;
        ImageView club_logo;
        ImageView club_bg;

        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_club);
            club_name = (TextView) itemView.findViewById(R.id.club_name);
            club_city = (TextView) itemView.findViewById(R.id.club_city);
            club_farm = (TextView) itemView.findViewById(R.id.club_farm);
            club_logo = (ImageView) itemView.findViewById(R.id.club_logo);
            club_bg = (ImageView) itemView.findViewById(R.id.club_bg);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.club_item, viewGroup, false);
        ViewHolder rhv = new ViewHolder(v);
        return rhv;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        ViewHolder vh = (ViewHolder) holder;
        vh.club_name.setText(clubs.get(i).name);
        vh.club_city.setText(clubs.get(i).city);
        vh.club_farm.setText(clubs.get(i).farm);
        UrlImageViewHelper.setUrlDrawable(vh.club_logo, clubs.get(i).logo);
        UrlImageViewHelper.setUrlDrawable(vh.club_bg, clubs.get(i).bg);
    }

    @Override
    public int getItemCount() {
        return clubs.size();
    }
}
