package com.example.android.przegldarkatumblr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


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
                Post p = new Post(writeUserName);
                    Intent listOfPostIntent = new Intent(MainActivity.this, ListOfPosts.class);
                    listOfPostIntent.putExtra("userName", p.getUserName());
                    startActivity(listOfPostIntent);
                    return; // Here the method is breaking
            }
        });
    }


}