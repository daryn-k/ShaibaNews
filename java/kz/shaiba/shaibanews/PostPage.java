package kz.shaiba.shaibanews;

import android.os.Bundle;

import kz.shaiba.shaibanews.RetrievePost.PostFragment;

public class PostPage extends ToolbarActivity {

    PostFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Новости");

        if(savedInstanceState == null){
            fragment = new PostFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, "PostTag").commit();
        }
        else{
            fragment = (PostFragment) getSupportFragmentManager().findFragmentByTag("PostTag");
        }

    }

}