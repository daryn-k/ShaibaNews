package kz.shaiba.shaibanews;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kz.shaiba.shaibanews.Clubs.ClubPageFragment;

public class ClubPage extends ToolbarActivity {

    ClubPageFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Команды");

        if(savedInstanceState == null){
            fragment = new ClubPageFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, "ClubPageTag").commit();
            Log.d("myLogs", "New Club Page Fragment in Activity");
        }
        else{
            fragment = (ClubPageFragment) getSupportFragmentManager().findFragmentByTag("ClubPageTag");
            Log.d("myLogs", "Get old Page Fragment by tag in Activity");
        }

    }


}
