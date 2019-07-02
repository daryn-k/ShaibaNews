package kz.shaiba.shaibanews.RetrieveFeed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import kz.shaiba.shaibanews.BaseUtils;
import kz.shaiba.shaibanews.DB.DBHelper;
import kz.shaiba.shaibanews.DB.DBUtils;
import kz.shaiba.shaibanews.R;


public class RetrieveFeedFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<RSSItemData>> {

    private RecyclerView rvNews;
    LinearLayout progBarLinearLayout;
    RSSAdapter RSSAdapter;
    ArrayList<RSSItemData> listData;
    SwipeRefreshLayout mSwipeRefreshLayout;
    DBHelper dbHelper;
    Boolean onCreateDone = false;
    Boolean showNewsFromDB;

    public RetrieveFeedFragment(Boolean showNewsFromDB){
        this.showNewsFromDB = showNewsFromDB;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        onCreateDone = true;
        getActivity().getSupportLoaderManager().destroyLoader(0);

        if(!showNewsFromDB){
            getActivity().getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.news_list_fragment, null);

        rvNews = (RecyclerView) v.findViewById(R.id.listViewNews);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvNews.setLayoutManager(llm);
        rvNews.setHasFixedSize(true);

        progBarLinearLayout = (LinearLayout) v.findViewById(R.id.progressBarLinearLayout);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        if(BaseUtils.isNetworkAvailable(getActivity())) progBarLinearLayout.setVisibility(LinearLayout.VISIBLE);

        if(!onCreateDone | showNewsFromDB){

            dbHelper = new DBHelper(getActivity());
            listData = DBUtils.getNewsFromDB(dbHelper);

            if(listData.size()!=0){
                RSSAdapter = new RSSAdapter(listData, getActivity());
                rvNews.setAdapter(RSSAdapter);
                progBarLinearLayout.setVisibility(LinearLayout.INVISIBLE);
            }
        }

        onCreateDone = false;

        return v;

    }

    void refreshItems() {
        getActivity().getSupportLoaderManager().initLoader(0, null, this).forceLoad();

    }


    @Override
    public Loader<ArrayList<RSSItemData>> onCreateLoader(int id, Bundle args) {
        return new FeedLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<RSSItemData>> loader, ArrayList<RSSItemData> listData) {

        RSSAdapter = new RSSAdapter(listData, getActivity());
        rvNews.setAdapter(RSSAdapter);
        progBarLinearLayout.setVisibility(LinearLayout.GONE);
        mSwipeRefreshLayout.setRefreshing(false);

        if(!BaseUtils.isNetworkAvailable(getActivity())) BaseUtils.showNoConnectionAlert(getActivity());

    }
    @Override
    public void onLoaderReset(Loader<ArrayList<RSSItemData>> loader) {
    }

}
