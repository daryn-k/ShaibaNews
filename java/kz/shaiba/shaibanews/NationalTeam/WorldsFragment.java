package kz.shaiba.shaibanews.NationalTeam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wefika.flowlayout.FlowLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;

import kz.shaiba.shaibanews.BaseUtils;
import kz.shaiba.shaibanews.R;
import kz.shaiba.shaibanews.UrlImageViewHelper.UrlImageViewHelper;


public class WorldsFragment extends Fragment {

    String type;
    String year;
    String division;
    String dates;
    String city;
    String country;
    String image;
    ArrayList<TeamData> participants;
    ArrayList<GameData> games;

    View v;
    LinearLayout listGames;
    TextView textLocation;
    TextView textDates;
    TextView textDivision;
    ImageView worldsImage;
    RelativeLayout divisionLine;
    FlowLayout flParticipants;

    public WorldsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);

        Log.d("myLogs", "onCreate");

        Intent intent = getActivity().getIntent();

        this.type = intent.getStringExtra("type");
        this.year = intent.getStringExtra("year");
        this.division = intent.getStringExtra("division");
        this.dates = intent.getStringExtra("dates");
        this.city = intent.getStringExtra("city");
        this.country = intent.getStringExtra("country");
        this.image = intent.getStringExtra("image");
        this.participants = intent.getParcelableArrayListExtra("participants");
        this.games = intent.getParcelableArrayListExtra("games");

        if(!BaseUtils.isNetworkAvailable(getActivity())) BaseUtils.showNoConnectionAlert(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("myLogs", "onCreateView");

        v = inflater.inflate(R.layout.worlds_page, null);

        textLocation= (TextView) v.findViewById(R.id.worlds_location);
        textDates = (TextView) v.findViewById(R.id.worlds_dates);
        textDivision = (TextView) v.findViewById(R.id.worlds_division);
        worldsImage = (ImageView) v.findViewById(R.id.worlds_image);
        divisionLine = (RelativeLayout) v.findViewById(R.id.division_line);
        flParticipants = (FlowLayout) v.findViewById(R.id.flowlayoutParticipants);
        listGames = (LinearLayout) v.findViewById(R.id.listGames);


        textLocation.setText(city + ", "+ country);
        textDates.setText(dates);
        textDivision.setText(division);
        if(division.equalsIgnoreCase("")){
            divisionLine.setVisibility(View.GONE);
        }
        UrlImageViewHelper.setUrlDrawable(worldsImage, image);

        if(city.equalsIgnoreCase("")){
            textLocation.setText("Определится позже");
        }
        if(dates.equalsIgnoreCase("")){
            textLocation.setText("Определится позже");
        }

        if(participants.size()==0){
            ((LinearLayout) v.findViewById(R.id.teams_title_list)).setVisibility(View.GONE);
        }
        else {
            for (TeamData mTeamData : participants) {

                View v2 = inflater.inflate(R.layout.participant_item, null);
                CardView cardview = (CardView) v2.findViewById(R.id.cv_participant);
                ((TextView) v2.findViewById(R.id.team_name)).setText(mTeamData.team);
                UrlImageViewHelper.setUrlDrawable((ImageView) v2.findViewById(R.id.team_flag), mTeamData.flag);

                FlowLayout.LayoutParams layoutparams = new FlowLayout.LayoutParams(
                        CardView.LayoutParams.WRAP_CONTENT,
                        CardView.LayoutParams.WRAP_CONTENT
                );
                layoutparams.setMargins(5, 5, 5, 5);
                cardview.setLayoutParams(layoutparams);

                flParticipants.addView(cardview);

            }
        }

        if(games.size()==0){
            ((LinearLayout) v.findViewById(R.id.games_title_list)).setVisibility(View.GONE);
        }
        else {
            for (GameData gameData : games) {

                View v3 = inflater.inflate(R.layout.worlds_game_item, null);
                LinearLayout cardview3 = (LinearLayout) v3.findViewById(R.id.cv_worlds_games);
                ((TextView) v3.findViewById(R.id.team1_name)).setText(gameData.team1);
                ((TextView) v3.findViewById(R.id.team2_name)).setText(gameData.team2);
                ((TextView) v3.findViewById(R.id.team1_score)).setText(gameData.score1);
                ((TextView) v3.findViewById(R.id.team2_score)).setText(gameData.score2);
                ((TextView) v3.findViewById(R.id.game_date)).setText(gameData.date + ", " + gameData.time);
                UrlImageViewHelper.setUrlDrawable((ImageView) v3.findViewById(R.id.team1_flag), gameData.flag1);
                UrlImageViewHelper.setUrlDrawable((ImageView) v3.findViewById(R.id.team2_flag), gameData.flag2);

                if (gameData.score1.equalsIgnoreCase("")) {
                    ((TextView) v3.findViewById(R.id.twodots)).setVisibility(View.GONE);
                    ((TextView) v3.findViewById(R.id.tire)).setVisibility(View.VISIBLE);
                }

                if (gameData.ot.equalsIgnoreCase("1")) {
                    if (gameData.so.equalsIgnoreCase("1")) {
                        ((TextView) v3.findViewById(R.id.game_so)).setVisibility(View.VISIBLE);
                    } else {
                        ((TextView) v3.findViewById(R.id.game_ot)).setVisibility(View.VISIBLE);
                    }
                }

                LinearLayout.LayoutParams layoutparams3 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutparams3.setMargins(5, 5, 5, 5);
                cardview3.setLayoutParams(layoutparams3);

                listGames.addView(cardview3);

            }
        }


        return v;

    }


}
