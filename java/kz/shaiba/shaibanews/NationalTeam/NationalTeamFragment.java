package kz.shaiba.shaibanews.NationalTeam;

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
import kz.shaiba.shaibanews.R;


public class NationalTeamFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<TournamentData>> {

    private RecyclerView rvTournaments;
    TournamentAdapter TournamentAdapter;

    public NationalTeamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setRetainInstance(true);

        getActivity().getSupportLoaderManager().destroyLoader(2);
        getActivity().getSupportLoaderManager().initLoader(2, null, this).forceLoad();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.national_team_fragment, null);

        rvTournaments = (RecyclerView) v.findViewById(R.id.listViewTournaments);
        rvTournaments.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvTournaments.setLayoutManager(llm);
        rvTournaments.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        return v;
    }

    @Override
    public Loader<ArrayList<TournamentData>> onCreateLoader(int id, Bundle args) {
        return new TournamentsLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<TournamentData>> loader, ArrayList<TournamentData> listData) {

        if(listData!=null){
            TournamentAdapter = new TournamentAdapter(listData, getActivity());

            List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                    new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

            sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0,"Недавние турниры"));
            sections.add(new SimpleSectionedRecyclerViewAdapter.Section(1,"Ближайшие турниры"));

            SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
            SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                    SimpleSectionedRecyclerViewAdapter(getActivity(), R.layout.section, R.id.section_text, TournamentAdapter, listData);
            mSectionedAdapter.setSections(sections.toArray(dummy));

            rvTournaments.setAdapter(mSectionedAdapter);
        }
        else{
            if(!BaseUtils.isNetworkAvailable(getActivity())) BaseUtils.showNoConnectionAlert(getActivity());
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<TournamentData>> loader) {

    }
}
