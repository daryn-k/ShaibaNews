package kz.shaiba.shaibanews;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import kz.shaiba.shaibanews.About.AboutFragment;
import kz.shaiba.shaibanews.Clubs.ClubsFragment;
import kz.shaiba.shaibanews.DB.DBHelper;
import kz.shaiba.shaibanews.DB.DBUtils;
import kz.shaiba.shaibanews.NationalTeam.NationalTeamFragment;
import kz.shaiba.shaibanews.RetrieveFeed.RetrieveFeedFragment;
import kz.shaiba.shaibanews.SlidingMenu.DrawerListAdapter;
import kz.shaiba.shaibanews.SlidingMenu.NavItem;

public class ToolbarActivity extends AppCompatActivity {

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    protected DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    SwitchCompat mSwitch;
    SharedPreferences sPref;
    Boolean notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mNavItems.add(new NavItem("Новости", R.drawable.ic_launcher));
        mNavItems.add(new NavItem("Сборная", R.drawable.ic_launcher));
        mNavItems.add(new NavItem("Команды", R.drawable.ic_launcher));
        mNavItems.add(new NavItem("О проекте", R.drawable.ic_launcher));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        mSwitch = (SwitchCompat) findViewById(R.id.switch_notif);

        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, myToolbar ,  R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };


        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

        sPref = getSharedPreferences("notifications", MODE_PRIVATE);
        notification = sPref.getBoolean("breaking_news", true);
        mSwitch.setChecked(notification);

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor ed = sPref.edit();
                if(isChecked){
                    ed.putBoolean("breaking_news", true);
                }
                else{
                    ed.putBoolean("breaking_news", false);
                }
                ed.commit();
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBHelper dbHelper = new DBHelper(this);
        DBUtils.deleteAllSavedJSONFromDB(dbHelper);
        Log.d("myLogs", "deleteAllSavedJSONFromDB");
    }

    private void selectItemFromDrawer(int position) {

        switch (position){
            case 0: RetrieveFeedFragment fragment_rf = new RetrieveFeedFragment(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment_rf, "NewsFeedTag").commit();
                    break;
            case 1: NationalTeamFragment fragment_nt = new NationalTeamFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment_nt, "NTTag").commit();
                    break;
            case 2: ClubsFragment fragment_clubs = new ClubsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment_clubs, "ClubsTag").commit();
                    break;
            case 3: AboutFragment fragment_about = new AboutFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment_about, "AboutTag").commit();
                    break;
        }


        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).mTitle);

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }



}
