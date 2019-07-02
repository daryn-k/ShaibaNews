package kz.shaiba.shaibanews.Clubs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kz.shaiba.shaibanews.BaseUtils;
import kz.shaiba.shaibanews.NationalTeam.DividerItemDecoration;
import kz.shaiba.shaibanews.NationalTeam.SimpleSectionedRecyclerViewAdapter;
import kz.shaiba.shaibanews.R;

public class ClubsFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<ClubData>> {

    RecyclerView recycleviewClubs;
    ClubsAdapter clubsAdapter;
    ArrayList<ClubData> clubs;

    public ClubsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);

        getActivity().getSupportLoaderManager().destroyLoader(3);
        getActivity().getSupportLoaderManager().initLoader(3, null, this).forceLoad();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.clubs, null);

        recycleviewClubs = (RecyclerView) v.findViewById(R.id.recycleviewClubs);
        recycleviewClubs.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recycleviewClubs.setLayoutManager(llm);


        return v;
    }

    @Override
    public Loader<ArrayList<ClubData>> onCreateLoader(int id, Bundle args) {
        return new ClubsLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ClubData>> loader, ArrayList<ClubData> listData) {

        if(listData!=null){
            clubsAdapter = new ClubsAdapter(listData, getActivity());

            List<SimpleSectionedRecyclerViewAdapterClubs.Section> sections =
                    new ArrayList<SimpleSectionedRecyclerViewAdapterClubs.Section>();

            sections.add(new SimpleSectionedRecyclerViewAdapterClubs.Section(0,"КХЛ"));
            sections.add(new SimpleSectionedRecyclerViewAdapterClubs.Section(1,"ВХЛ"));
            sections.add(new SimpleSectionedRecyclerViewAdapterClubs.Section(3,"Чемпионат Казахстана"));
            sections.add(new SimpleSectionedRecyclerViewAdapterClubs.Section(13,"МХЛ"));

            SimpleSectionedRecyclerViewAdapterClubs.Section[] dummy = new
                    SimpleSectionedRecyclerViewAdapterClubs.Section[sections.size()];
            SimpleSectionedRecyclerViewAdapterClubs mSectionedAdapter = new
                    SimpleSectionedRecyclerViewAdapterClubs(getActivity(),
                    R.layout.section_clubs, R.id.section_clubs_text, clubsAdapter, listData);
            mSectionedAdapter.setSections(sections.toArray(dummy));

            recycleviewClubs.setAdapter(mSectionedAdapter);
        }
        else{
            if(!BaseUtils.isNetworkAvailable(getActivity())) BaseUtils.showNoConnectionAlert(getActivity());
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ClubData>> loader) {

    }
}
