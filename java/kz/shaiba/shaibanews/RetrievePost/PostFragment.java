package kz.shaiba.shaibanews.RetrievePost;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import kz.shaiba.shaibanews.BaseUtils;
import kz.shaiba.shaibanews.DateUtils;
import kz.shaiba.shaibanews.R;


public class PostFragment extends Fragment implements LoaderManager.LoaderCallbacks<PostItemData> {

    String post_id;
    String title;
    String date_unix;
    String date_for_post;
    String HTMLwithTitle;
    String postHTML;
    View v;
    LinearLayout progressBarLinearLayoutPost;
    WebView webView;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);

        Log.d("myLogs", "onCreate");

        Intent intent = getActivity().getIntent();

        this.post_id = intent.getStringExtra("post_id");
        this.title = intent.getStringExtra("title");
        this.date_unix = intent.getStringExtra("date_unix");
        this.postHTML = intent.getStringExtra("post_content");

        getActivity().getSupportLoaderManager().initLoader(1, null, this).forceLoad();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.post_page, null);
        progressBarLinearLayoutPost = (LinearLayout) v.findViewById(R.id.progressBarLinearLayoutPost);
        webView = (WebView) v.findViewById(R.id.webView);

        if(BaseUtils.isNetworkAvailable(getActivity())){
            if(postHTML.equalsIgnoreCase("")){
                progressBarLinearLayoutPost.setVisibility(View.VISIBLE);
            }
            else{
                progressBarLinearLayoutPost.setVisibility(View.GONE);
            }
        }


        return v;

    }


    @Override
    public Loader<PostItemData> onCreateLoader(int id, Bundle args) {

        return new PostLoader(getActivity(), post_id);

    }

    @Override
    public void onLoadFinished(Loader<PostItemData> loader, PostItemData postItem) {

        if(postItem!=null) {

            date_for_post = DateUtils.dateFromUNIXtoString(date_unix);

            HTMLwithTitle = "<style>img {padding-bottom: 10px;}</style>"
                          + "<div><font color=\"#262626\" size=\"5\"><b>"+title+"</b></font></div>"
                          + "<div style=\"padding-top: 10px\">"
                             + "<font color=\"#006399\" size=\"3\">"+date_for_post+"</font>"
                          + "</div>"
                          + postItem.postHTML;

            webView = (WebView) v.findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setBackgroundColor(Color.TRANSPARENT);
            webView.loadData(HTMLwithTitle, "text/html; charset=UTF-8", null);
            webView.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView view, String url) {
                    webView.setVisibility(View.VISIBLE);
                    progressBarLinearLayoutPost.setVisibility(LinearLayout.GONE);
                }
            });
        }
        else{
            if(!BaseUtils.isNetworkAvailable(getActivity())) BaseUtils.showNoConnectionAlert(getActivity());
        }

    }

    @Override
    public void onLoaderReset(Loader<PostItemData> loader) {

    }
}
