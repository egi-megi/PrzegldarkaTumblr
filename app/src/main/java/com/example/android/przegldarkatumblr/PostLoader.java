package com.example.android.przegldarkatumblr;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by egi-megi on 10.10.18.
 */

public class PostLoader extends AsyncTaskLoader<List<Post>> {

    private String mUrl;


    public PostLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Post> loadInBackground() {
        if (mUrl == null) {

                try {
                    List<Post> posts = QueryUtilis.fetchPostData(getContext().getAssets().open("test.json"));
                    // Update the information displayed to the user.
                    return posts;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            return null;
        }
        // Create URL object
        // Perform the HTTP request for post data and process the response.
        List<Post> posts = QueryUtilis.fetchPostData(mUrl);
        // Update the information displayed to the user.
        return posts;
    }
}