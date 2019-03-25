package com.example.android.przegldarkatumblr;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SearchUsername extends AppCompatActivity {

    public ArrayList<String> listUsernames = new ArrayList<String>();

    static final String USER_LIST_NAME_IN_PREFERENCE = "preferenceusers";


    public void setActuallyUsername(String username) {

        if (!listUsernames.contains(username)) {
            listUsernames.add(0, username);
        }
        if (listUsernames.size() > 6) {
            listUsernames.remove(6);
        }

        getPreferences(Context.MODE_PRIVATE).edit().putStringSet(USER_LIST_NAME_IN_PREFERENCE, new HashSet<String>(listUsernames)).commit();
    }

    void openNewListOfPosts(String username) {
        setActuallyUsername(username);
        Intent listOfPostIntent = new Intent(SearchUsername.this, ListOfPosts.class);
        listOfPostIntent.putExtra("userName", username);
        startActivity(listOfPostIntent);
    }

    // Create buttons (with all settings) with earlier written usernames
    public Button makeButton(final String text) {
        Button button = new Button(this);
        button.setText(text);
        button.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        button.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setTextSize(getResources().getDimension(R.dimen.text_button));
        button.setTextColor(getResources().getColor(R.color.colorWhite));
        button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        button.setAllCaps(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewListOfPosts(text);
            }
        });
        return button;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Make method for searching the username.
        final EditText searchUserEditText = (EditText) findViewById(R.id.search_editText);
        Button searchUserButton = (Button) findViewById(R.id.search_button);

        searchUserButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the Button for searching is clicked on.
            @Override
            public void onClick(View view) {
                String writeUserName = searchUserEditText.getText().toString();

                if (writeUserName != null && !writeUserName.isEmpty()) {
                    openNewListOfPosts(writeUserName);
                }

                return;
            }
        });


    }

    // Restore list of usernames form Preference without of order
    void restoreList() {
        Set<String> stringSet = getPreferences(Context.MODE_PRIVATE).getStringSet(USER_LIST_NAME_IN_PREFERENCE, new HashSet<String>());
        if (listUsernames.isEmpty()) {
            listUsernames.addAll(stringSet);
        }

    }

    void addButtons() {
        LinearLayout myRoot = (LinearLayout) findViewById(R.id.lastSelectedUsernames);
        myRoot.removeAllViewsInLayout();
        for (String s : listUsernames) {
            Button b = makeButton(s);
            myRoot.addView(b);
            ((ViewGroup.MarginLayoutParams) b.getLayoutParams()).setMargins(0, 0, 0, 15);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreList();
        //Add the default usernames
        if (listUsernames.isEmpty()) {
            listUsernames.add("teacoffeebooks");
            listUsernames.add("isareadsbooks");
        }
        addButtons();
    }


}