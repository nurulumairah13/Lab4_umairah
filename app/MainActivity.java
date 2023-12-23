package com.example.lab4firebaseumairah;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




public class MainActivity extends AppCompatActivity {

    private void addArtist() {
        //get artistname and convert to string from editextname
        String name = editTextName.getText().toString().trim();

        //check if the name is not empty
        if (!TextUtils.isEmpty(name)) {
            //if exist push data to firebase database
            //store inside id in database
            //every time data stored the id will be unique
            String id = databaseArtists.push().getKey();
            //store
            Artist artist = new Artist(id, name);
            //store artist inside unique id
            databaseArtists.child(id).setValue(artist);
            Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show();

        } else {
            //if the name is empty
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextName;
        Button buttonAdd;

        //Database reference object
        DatabaseReference databaseArtists;

        FirebaseApp.initializeApp(this);

        //Create database reference

        databaseArtists = FirebaseDatabase.getInstance().getReference("artists");
        //get values from XML

        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonAdd = (Button) findViewById(R.id.buttonAddArtist);

        //attach clicklistener to the button

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArtist();
            }
        });




    }
    
}