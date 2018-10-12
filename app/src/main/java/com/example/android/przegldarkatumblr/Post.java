package com.example.android.przegldarkatumblr;

import android.text.Spanned;

/**
 * Created by egi-megi on 10.10.18.
 */

public class Post {
    private  String mUserName;

    private  String mPostId;

    private String mSlugTitle;

    private Spanned mDescrition;

    private String mPublicationDate;

    private String mWebUrl;

    private String mNumberOfNotes;

    private int mImage;

    private String mKeyWord;

    public Post() { }

    public Post(String userName) {
        mUserName = userName;
    }

    public Post(String postId, String slugTitle, Spanned description, String publicationDate, String webUrl, String numberOfNotes) {
        mPostId = postId;
        mSlugTitle = slugTitle;
        mDescrition = description;
        mPublicationDate = publicationDate;
        mWebUrl = webUrl;
        mNumberOfNotes = numberOfNotes;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getPostId() {
        return mPostId;
    }

    public String getSlugTitle() {
        return mSlugTitle;
    }

    public Spanned getDescrition() {
        return mDescrition;
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
}
