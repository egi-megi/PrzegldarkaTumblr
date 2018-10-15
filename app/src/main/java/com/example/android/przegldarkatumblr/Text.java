package com.example.android.przegldarkatumblr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by egi-megi on 14.10.18.
 */

public class Text extends Post {

    private String mTitle;

    private Spanned mDescription;

    public Text(String title, Spanned description, String publicationDate, String webUrl, String numberOfNotes) {
        super(publicationDate, webUrl, numberOfNotes);
        mTitle = title;
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public Spanned getDescription() {
        return mDescription;
    }


    public View displayPost(View listItemView, final Context ctx, ViewGroup parent, int position) {

        if (listItemView == null) {
            listItemView = LayoutInflater.from(ctx).inflate(
                    R.layout.list_item_text, parent, false);
        }
        if (listItemView.findViewById(R.id.description_text_view) == null) {
            listItemView = LayoutInflater.from(ctx).inflate(
                    R.layout.list_item_text, parent, false);
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

        //Set title if there is title - there is not written text "null"
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_text_view);
        if (!getTitle().equalsIgnoreCase("null")) {
            titleTextView.setText(getTitle());
        } else {
            titleTextView.setHeight(1);
        }

        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.description_text_view);
        descriptionTextView.setText(this.getDescription());

        TextView numberOfNotesTextView = (TextView) listItemView.findViewById(R.id.number_of_likes_text_view);
        numberOfNotesTextView.setText(getNumberOfNotes());

        // Set only date without time
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        if (getPublicationDate().substring(5, 6).equalsIgnoreCase("0")) {
            dateTextView.setText(getPublicationDate().substring(6, 16));
        } else {
            dateTextView.setText(getPublicationDate().substring(5, 16));
        }

        return listItemView;
    }

}
