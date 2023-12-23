package com.example.lab4firebaseumairah;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;  // Import TextUtils
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Declare EditText and DatabaseReference as class variables
    private EditText editTextName;
    private DatabaseReference databaseArtists;

    List<Artist> artistList;
    ListView listViewArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Create database reference
        databaseArtists = FirebaseDatabase.getInstance().getReference("artists");

        // Initialize EditText and Button
        editTextName = findViewById(R.id.editTextName);
        Button buttonAdd = findViewById(R.id.buttonAddArtist);

        listViewArtists = (ListView) findViewById(R.id.ListViewArtist);
        artistList = new ArrayList<>();

        // Attach click listener to the button
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArtist();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                artistList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Artist artist = postSnapshot.getValue(Artist.class);
                    //adding artist to the list
                    artistList.add(artist);
                }

                //creating adapter
                ArtistList artistAdapter = new ArtistList(MainActivity.this, artistList);
                //attaching adapter to the listview
                listViewArtists.setAdapter(artistAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addArtist() {
        // Get artist name and convert to string from EditText
        String name = editTextName.getText().toString().trim();

        // Check if the name is not empty
        if (!TextUtils.isEmpty(name)) {
            // If it exists, push data to the Firebase database
            // Store inside ID in the database
            // Every time data is stored, the ID will be unique
            String id = databaseArtists.push().getKey();
            // Store the artist inside the unique ID
            Artist artist = new Artist(id, name);
            // Store artist inside unique ID
            databaseArtists.child(id).setValue(artist);
            Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show();
        } else {
            // If the name is empty, display a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }
}