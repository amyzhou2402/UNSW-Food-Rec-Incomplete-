package au.edu.unsw.infs3634.opennow;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private VenueAdapter mAdapter;
    private VenueDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fab.isExtended()) {
                    fab.shrink();
                    fab.setIcon(getDrawable(R.drawable.ic_clear));
                    updateListFromDatabase(true);
                } else {
                    fab.extend();
                    fab.setIcon(getDrawable(R.drawable.ic_time));
                    updateListFromDatabase(false);
                }
            }
        });

        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // This command creates a Room database with pre-existing data
        mDb = Room.databaseBuilder(this, VenueDatabase.class, "venues.db").createFromAsset("venues.db").build();
        mAdapter = new VenueAdapter(new ArrayList<Venue>(), new VenueAdapter.VenueClickListener() {
            @Override
            public void onClick(String id) { //this one has to stay as int id
                openDetailActivity(id);
            }
        });
        recyclerView.setAdapter(mAdapter);
        updateListFromDatabase(false);
    }

    // TODO for Question 1a)
    // Implement code that creates an options menu for the menu_main.xml resource

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    // Implement code that sorts the adapter data based on the menu item selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_name:
                mAdapter.sort(VenueAdapter.SORT_METHOD_NAME);
                return true;
            case R.id.sort_location:
                mAdapter.sort(VenueAdapter.SORT_METHOD_LOCATION);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateListFromDatabase(boolean filtered) {
        Executors.newSingleThreadExecutor().execute(() -> {
            ArrayList<Venue> venues = (ArrayList<Venue>) mDb.venueDao().getVenues();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.setData(venues);
                    if(filtered) {
                        mAdapter.getFilter().filter(null);
                    }
                    mAdapter.sort(VenueAdapter.SORT_METHOD_NAME);
                }
            });
        });
    }

    public void openDetailActivity(String id) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.INTENT_EXTRA, id);
        startActivity(intent);
    }
}