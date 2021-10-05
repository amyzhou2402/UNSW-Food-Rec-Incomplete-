package au.edu.unsw.infs3634.opennow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class DetailActivity extends AppCompatActivity {
    public static final String INTENT_EXTRA = "au.edu.unsw.infs3634.opennow.intent_extra";

    private VenueDatabase mDatabase;
    private ImageView mImage;
    private TextView mName;
    private TextView mLocation;
    private TextView mHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mImage = findViewById(R.id.image);
        mName = findViewById(R.id.name);
        mLocation = findViewById(R.id.location);
        mHours = findViewById(R.id.hours);

        // TODO for Question 1b)
        // Complete the DetailActivity class so that it displays all details about a venue
        // and integrates with all other classes of the app.
        // Data about the venue need to be collected from the database.

        Intent intent = getIntent();
        String message = intent.getStringExtra(INTENT_EXTRA);

        mDatabase = Room.databaseBuilder(getApplicationContext(), VenueDatabase.class, "venue-database").build();
        Executors.newSingleThreadExecutor().execute(new Runnable(){
            @Override
            public void run() {
                Venue venue = mDatabase.venueDao().getVenuesByID(message);
                runOnUiThread((new Runnable() {
                    @Override
                    public void run() {
//                        setTitle(venue.getName());
//                        Glide.with(DetailActivity.this)
//                                .load(venue.getUrl().toLowerCase() + "/flat/64.png")
//                                .fitCenter()
//                                .into(mImage);
//                        mName.setText(venue.getName());
//                        mLocation.setText(venue.getLocation());
//                        mHours.setText(venue.getOpens() + " - " + venue.getCloses());

                    }
                }));
            }
        });
        // Check the activity_detail.xml file to see which data needs to be displayed
    }
}