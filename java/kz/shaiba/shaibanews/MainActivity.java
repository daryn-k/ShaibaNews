package kz.shaiba.shaibanews;


import android.os.Bundle;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import kz.shaiba.shaibanews.RetrieveFeed.RetrieveFeedFragment;

public class MainActivity extends ToolbarActivity{

    RetrieveFeedFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();

        setTitle("Новости");

        if(savedInstanceState == null){
            fragment = new RetrieveFeedFragment(false);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, "NewsFeedTag").commit();
        }
        else{
            fragment = (RetrieveFeedFragment) getSupportFragmentManager().findFragmentByTag("NewsFeedTag");
        }

    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d("myLogs", "News Activity onStart");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d("myLogs", "News Activity onStop");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("myLogs", "News Activity onDestroy");
    }

}
