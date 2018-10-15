package com.example.android.przegldarkatumblr;

import android.content.Context;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by egi-megi on 10.10.18.
 */

public abstract class Post {

    private String mPublicationDate;

    private String mWebUrl;

    private String mNumberOfNotes;

    public Post() { }

    public Post(String publicationDate, String webUrl, String numberOfNotes) {
        mPublicationDate = publicationDate;
        mWebUrl = webUrl;
        mNumberOfNotes = numberOfNotes;
    }


    public String getPublicationDate() {
        return mPublicationDate;
    }

    public String getWebUrl() {
        return mWebUrl;
    }

    public String getNumberOfNotes() {
        return mNumberOfNotes;
    }

    public abstract View displayPost(View listItemView, final Context ctx, ViewGroup parent, int position);

}
