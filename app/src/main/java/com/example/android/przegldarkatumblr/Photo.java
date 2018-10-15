package com.example.android.przegldarkatumblr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * Created by egi-megi on 14.10.18.
 */

public class Photo extends Post {

    private String mPhoto;

    private Spanned mCaption;

    public Photo(String image, Spanned caption, String publicationDate, String webUrl, String numberOfNotes) {
        super(publicationDate, webUrl, numberOfNotes);
        mPhoto = image;
        mCaption = caption;
    }

    public String getImage() {
        return mPhoto;
    }

    public Spanned getCaption() {
        return mCaption;
    }

    @Override
    public View displayPost(View listItemView, final Context ctx, ViewGroup parent, int position) {
        if (listItemView == null) {
            listItemView = LayoutInflater.from(ctx).inflate(
                    R.layout.list_item_photo, parent, false);
        }
        if (listItemView.findViewById(R.id.description_text_view)!=null) {
            listItemView = LayoutInflater.from(ctx).inflate(
                    R.layout.list_item_photo, parent, false);
        }

        // Go to webpage with post if someone click on list'item
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getWebUrl()));
                ctx.startActivity(intent);
            }
        };
        listItemView.setOnClickListener(listener);

        // Set photo in WebView
        WebView photoImageView = (WebView) listItemView.findViewById(R.id.photo_image_view);
        photoImageView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        photoImageView.getSettings().setLoadWithOverviewMode(true);
        photoImageView.getSettings().setUseWideViewPort(true);

        if (getImage()!=null) {
            photoImageView.loadUrl(getImage());
        } else {
            Log.e("","empty url");
        }

        //Go to webpage with post when someone click on photo
        WebView.OnClickListener listenerWebView = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getWebUrl()));
                ctx.startActivity(intent);
            }
        };
        photoImageView.setOnClickListener(listenerWebView);

        TextView captionTextView = (TextView) listItemView.findViewById(R.id.caption_text_view);
        captionTextView.setText(getCaption());

        TextView numberOfNotesTextView = (TextView) listItemView.findViewById(R.id.number_of_likes_text_view);
        numberOfNotesTextView.setText(getNumberOfNotes());

        // Set only date without time
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        if (getPublicationDate().substring(5,6).equalsIgnoreCase("0")) {
            dateTextView.setText(getPublicationDate().substring(6,16));
        } else {
            dateTextView.setText(getPublicationDate().substring(5, 16));
        }

        return listItemView;
    }



    }

