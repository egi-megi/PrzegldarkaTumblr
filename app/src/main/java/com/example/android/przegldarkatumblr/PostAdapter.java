package com.example.android.przegldarkatumblr;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by egi-megi on 10.10.18.
 */

public class PostAdapter extends ArrayAdapter<Post> {

    public PostAdapter(Activity context, ArrayList<Post> posts) {
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        final Post currentPost = getItem(position);

        return currentPost.displayPost(listItemView, getContext(), parent, position);
    }


}

