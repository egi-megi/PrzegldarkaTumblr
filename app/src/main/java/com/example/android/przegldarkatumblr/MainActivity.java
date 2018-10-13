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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public ArrayList<String> listUsernames = new ArrayList<String>();


    public void setActuallyUsername(String username) {
        listUsernames.add(username);
        getPreferences(Context.MODE_PRIVATE).edit().putStringSet("la",new HashSet<String>(listUsernames)).commit();
    }

    void openNewListOfPosts(String username) {
        setActuallyUsername(username);
        Intent listOfPostIntent = new Intent(MainActivity.this, ListOfPosts.class);
        listOfPostIntent.putExtra("userName", username);
        startActivity(listOfPostIntent);
    }

    public Button makeButton(final String text) {
        Button button = new Button(this);
        button.setText(text);
        button.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        button.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
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


        // Set the content of the activity to use the list_of_posts.xml layout file
        setContentView(R.layout.main_activity);


        // Make method for searching the song after title. It is not complete method
        // because it finds only first song for searching word
        final EditText searchUserEditText = (EditText) findViewById(R.id.search_editText);
        Button searchUserButton = (Button) findViewById(R.id.search_button);

        searchUserButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the Button for searching is clicked on.
            @Override
            public void onClick(View view) {
                String writeUserName = searchUserEditText.getText().toString();
                openNewListOfPosts(writeUserName);
                return;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Set<String> stringSet=getPreferences(Context.MODE_PRIVATE).getStringSet("la",new HashSet<String>());
        if (listUsernames.isEmpty()){
            listUsernames.addAll(stringSet);
        }
        LinearLayout myRoot = (LinearLayout) findViewById(R.id.lastSelectedUsernames);
        for (String s : listUsernames) {
            myRoot.addView(makeButton(s));
        }

    }



}