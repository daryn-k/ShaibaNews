package kz.shaiba.shaibanews;

import android.os.Bundle;
import android.util.Log;

import kz.shaiba.shaibanews.NationalTeam.WorldsFragment;

public class WorldsPage extends ToolbarActivity {

    WorldsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Сборная");

        if(savedInstanceState == null){
            fragment = new WorldsFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, "WorldsTag").commit();
            Log.d("myLogs", "New Post Fragment in Activity");
        }
        else{
            fragment = (WorldsFragment) getSupportFragmentManager().findFragmentByTag("WorldsTag");
            Log.d("myLogs", "Get old Post Fragment by tag in Activity");
        }

    }
}
