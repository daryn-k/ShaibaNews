package kz.shaiba.shaibanews.Clubs;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import kz.shaiba.shaibanews.BaseUtils;
import kz.shaiba.shaibanews.R;
import kz.shaiba.shaibanews.UrlImageViewHelper.UrlImageViewHelper;

public class ClubPageFragment extends Fragment {

    String name;
    String city;
    String logo;
    String bg;
    String league;
    String founded;
    String farm;
    String farmof;
    String arena;
    String arena_image_inside;
    String arena_image_outside;

    View v;
    ImageView logo_image;
    ImageView bg_image;
    TextView team_name;
    TextView team_city;
    TextView team_founded;
    TextView team_arena;
    TextView team_farmof;
    TextView team_league;
    LinearLayout farmofLL;

    public ClubPageFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);

        Intent intent = getActivity().getIntent();

        this.name = intent.getStringExtra("name");
        this.city = intent.getStringExtra("city");
        this.logo = intent.getStringExtra("logo");
        this.bg = intent.getStringExtra("bg");
        this.league = intent.getStringExtra("league");
        this.founded = intent.getStringExtra("founded");
        this.farm = intent.getStringExtra("farm");
        this.farmof = intent.getStringExtra("farmof");
        this.arena = intent.getStringExtra("arena");
        this.arena_image_inside = intent.getStringExtra("arena_image_inside");
        this.arena_image_outside = intent.getStringExtra("arena_image_outside");

        if(!BaseUtils.isNetworkAvailable(getActivity())) BaseUtils.showNoConnectionAlert(getActivity());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.club_page, null);

        logo_image = (ImageView) v.findViewById(R.id.club_page_logo);
        bg_image = (ImageView) v.findViewById(R.id.club_page_bg);
        team_name = (TextView) v.findViewById(R.id.club_page_name);
        team_city = (TextView) v.findViewById(R.id.club_page_city);
        team_founded = (TextView) v.findViewById(R.id.club_page_founded);
        team_arena = (TextView) v.findViewById(R.id.club_page_arena);
        team_farmof = (TextView) v.findViewById(R.id.club_page_farmof);
        farmofLL = (LinearLayout) v.findViewById(R.id.club_page_farmof_item);
        team_league = (TextView) v.findViewById(R.id.club_page_league);

        UrlImageViewHelper.setUrlDrawable(logo_image, logo);
        UrlImageViewHelper.setUrlDrawable(bg_image, bg);
        team_name.setText(name);
        team_city.setText(city);
        team_founded.setText(founded);
        team_arena.setText(arena);
        if(farmof.equalsIgnoreCase("")){
            farmofLL.setVisibility(View.GONE);
        }
        else{
            team_farmof.setText(farmof);
        }
        team_league.setText(league);


        return v;
    }



}
