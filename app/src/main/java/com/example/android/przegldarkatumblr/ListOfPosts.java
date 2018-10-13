package com.example.android.przegldarkatumblr;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egi-megi on 12.10.18.
 */

public class ListOfPosts extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Post>> {

    // Constant value for the post loader ID
    private static final int POST_LOADER_ID = 1;

    public static final String USGS_REQUEST_URL =
            "https://userName.tumblr.com/api/read/json?type=text";
//            "https://content.guardianapis.com/search?q=architecture&from-date=2017-01-01&api-key=21bb4e65-d7b8-4e81-ae28-632e388ed476&show-tags=contributor";

    /**
     * Adapter for the list of posts
     */
    private PostAdapter mAdapter;

    public ImageView emptyPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_posts);

        final String userName = getIntent().getStringExtra("userName");

        TextView userNameTextView = (TextView) findViewById(R.id.user_name_text_view);
        userNameTextView.setText(userName);

        // Setting FAB to open EditorActivity
        FloatingActionButton fabChooseNewUser = (FloatingActionButton) findViewById(R.id.fab_search);
        fabChooseNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListOfPosts.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Find a reference to the {@link ListView} in the layout
        ListView postsListView = (ListView) findViewById(R.id.list);

        // Find a reference to the Image View in empty page in the layout
        emptyPage = (ImageView) findViewById(R.id.empty_text_view);
        postsListView.setEmptyView(findViewById(R.id.empty_text_view));

        // Create a new {@link ArrayAdapter} of posts
        mAdapter = new PostAdapter(this, new ArrayList<Post>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        postsListView.setAdapter(mAdapter);


        postsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current post that was clicked on
                Post currentPost = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri postUri = Uri.parse(currentPost.getWebUrl());

                // Create a new intent to view the post URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, postUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader
            loaderManager.initLoader(POST_LOADER_ID, null, this);
        } else {
            View progressBar = findViewById(R.id.loading_progress_bar);
            progressBar.setVisibility(View.GONE);
            emptyPage.setImageResource(R.drawable.no_connection);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }


    @Override
    public Loader<List<Post>> onCreateLoader(int i, Bundle bundle) {


        // Create a new loader for the given URL
        return new PostLoader(this, null);//USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Post>> loader, List<Post> posts) {

        View progressBar = findViewById(R.id.loading_progress_bar);
        progressBar.setVisibility(View.GONE);

        emptyPage.setImageResource(R.drawable.no_posts);
        // Clear the adapter of previous post
        mAdapter.clear();

        // If there is a valid list of {@link Post}s, then add them to the adapter's
        // posts set. This will trigger the ListView to update.
        if (posts != null && !posts.isEmpty()) {
            mAdapter.addAll(posts);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Post>> loader) {
        mAdapter.clear();
    }




}